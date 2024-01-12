package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.MatchActivityCreateDTO;
import com.hungnln.vleague.DTO.MatchCreateDTO;
import com.hungnln.vleague.DTO.MatchUpdateDTO;
import com.hungnln.vleague.constant.activity.ActivityType;
import com.hungnln.vleague.constant.club.ClubFailMessage;
import com.hungnln.vleague.constant.match.MatchFailMessage;
import com.hungnln.vleague.constant.match.MatchSuccessMessage;
import com.hungnln.vleague.constant.round.RoundFailMessage;
import com.hungnln.vleague.constant.stadium.StadiumFailMessage;
import com.hungnln.vleague.constant.tournament.TournamentFailMessage;
import com.hungnln.vleague.entity.*;
import com.hungnln.vleague.entity.key.PlayerMatchParticipationKey;
import com.hungnln.vleague.entity.key.RefereeMatchParticipationKey;
import com.hungnln.vleague.entity.key.StaffMatchParticipationKey;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.helper.MatchSpecification;
import com.hungnln.vleague.helper.RoundSpecification;
import com.hungnln.vleague.helper.SearchCriteria;
import com.hungnln.vleague.helper.SearchOperation;
import com.hungnln.vleague.repository.*;
import com.hungnln.vleague.response.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private StadiumRepository stadiumRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private MatchActivityRepository matchActivityRepository;
    @Autowired
    private PlayerMatchParticipationRepository playerMatchParticipationRepository;
    @Autowired
    private StaffMatchParticipationRepository staffMatchParticipationRepository;
    @Autowired
    private RefereeMatchParticipationRepository refereeMatchParticipationRepository;
    private final ModelMapper modelMapper;

    public ResponseWithTotalPage<MatchResponse> getAllMatch(int pageNumber, int pageSize,UUID tournamentId,UUID stadiumId, UUID roundId){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));

        List<Specification<Match>> specificationList = new ArrayList<>();
        List<Specification<Match>> specificationListSpecial = new ArrayList<>();

        if(tournamentId != null){
            Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(()-> new NotFoundException(TournamentFailMessage.TOURNAMENT_NOT_FOUND));
            RoundSpecification roundSpecification = new RoundSpecification(new SearchCriteria("tournament",SearchOperation.EQUALITY,tournament));
            List<Round> roundList =  roundRepository.findAll(roundSpecification);
            if (!roundList.isEmpty()){
                for (Round round : roundList){
                    MatchSpecification matchSpecification = new MatchSpecification(new SearchCriteria("round",SearchOperation.EQUALITY,round));
                    specificationListSpecial.add(matchSpecification);
                }
            }else{
                ResponseWithTotalPage<MatchResponse> response = new ResponseWithTotalPage<>();
                List<MatchResponse> matchList = new ArrayList<>();
                response.setData(matchList);
                PaginationResponse paginationResponse = PaginationResponse.builder()
                        .pageIndex(pageNumber)
                        .pageSize(pageSize)
                        .totalCount(0)
                        .totalPage(0)
                        .build();
                response.setPagination(paginationResponse);
                return  response;
            }

        }
        if (stadiumId != null) {
            Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(()-> new NotFoundException(StadiumFailMessage.STADIUM_NOT_FOUND));
            MatchSpecification specification = new MatchSpecification(new SearchCriteria("stadium",SearchOperation.EQUALITY,stadium));
            specificationList.add(specification);
        }
        if (roundId != null) {
            Round round = roundRepository.findById(roundId).orElseThrow(()-> new NotFoundException(RoundFailMessage.ROUND_NOT_FOUND));
            MatchSpecification specification = new MatchSpecification(new SearchCriteria("round",SearchOperation.EQUALITY,round));
            specificationList.add(specification);
        }
        Page<Match> pageResult = matchRepository.findAll(Specification.anyOf(specificationListSpecial).and(Specification.allOf(specificationList)),pageable);
        ResponseWithTotalPage<MatchResponse> response = new ResponseWithTotalPage<>();
        List<MatchResponse> matchList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Match match :
                    pageResult.getContent()) {
                MatchResponse matchResponse = modelMapper.map(match, MatchResponse.class);
                matchList.add(matchResponse);
            }
        }
        response.setData(matchList);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageIndex(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalCount((int) pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .build();
        response.setPagination(paginationResponse);
        return response;
    }
    public MatchResponse addMatch(MatchCreateDTO matchCreateDTO){
        Club homeClub =clubRepository.findById(matchCreateDTO.getHomeClubId()).orElseThrow(()->new NotFoundException(ClubFailMessage.HOME_CLUB_NOT_FOUND));
        Club awayClub =clubRepository.findById(matchCreateDTO.getAwayClubId()).orElseThrow(()->new NotFoundException(ClubFailMessage.AWAY_CLUB_NOT_FOUND));
        Stadium stadium = stadiumRepository.findById(matchCreateDTO.getStadiumId()).orElseThrow(()-> new NotFoundException(StadiumFailMessage.STADIUM_NOT_FOUND));
        Round round = roundRepository.findById(matchCreateDTO.getRoundId()).orElseThrow(()-> new NotFoundException(RoundFailMessage.ROUND_NOT_FOUND));
        Match match =Match.builder()
                .homeClub(homeClub)
                .awayClub(awayClub)
                .stadium(stadium)
                .round(round)
                .startDate(matchCreateDTO.getStartDate())
                .build();
        matchRepository.save(match);
        return modelMapper.map(match,MatchResponse.class);
    }
//    public MatchResponse updateMatch(UUID matchId,MatchUpdateDTO matchUpdateDTO){
//        Match match = matchRepository.findById(matchId).orElseThrow(()->new NotFoundException(MatchFailMessage.ROUND_NOT_FOUND));
//        Tournament tournament =tournamentRepository.findTournamentById(matchUpdateDTO.getTournamentId()).orElseThrow(()->new NotFoundException(TournamentFailMessage.TOURNAMENT_NOT_FOUND));
//        match.setTournament(tournament);
//        match.setName(matchUpdateDTO.getName());
//        matchRepository.save(match);
//        return modelMapper.map(match,MatchResponse.class);
//    }
    public MatchResponse findMatchById(UUID matchId){
        Match match = matchRepository.findById(matchId).orElseThrow(()->new NotFoundException(MatchFailMessage.MATCH_NOT_FOUND));
        List<MatchActivity> matchActivityList = matchActivityRepository.findAllByMatch(match);
        MatchResponse matchResponse = modelMapper.map(match,MatchResponse.class);
        matchResponse.setActivities(matchActivityList);
        return matchResponse;
    }
    public MatchParticipationResponse getMatchParticipationById(UUID matchId){
        Match match = matchRepository.findById(matchId).orElseThrow(()->new NotFoundException(MatchFailMessage.MATCH_NOT_FOUND));
        return modelMapper.map(match,MatchParticipationResponse.class);
    }
    public MatchActivityResponse addMatchActivity(UUID matchId,MatchActivityCreateDTO dto){
        Match match = matchRepository.findById(matchId).orElseThrow(()->new NotFoundException(MatchFailMessage.MATCH_NOT_FOUND));
        List<PlayerMatchParticipationKey> playerMatchParticipationKeyList = new ArrayList<>();
        List<StaffMatchParticipationKey> staffMatchParticipationKeyList = new ArrayList<>();
        List<RefereeMatchParticipationKey> refereeMatchParticipationKeyList = new ArrayList<>();
        if(dto.getPlayerContractIds() != null && !dto.getPlayerContractIds().isEmpty()){
            for (UUID playerContractId:dto.getPlayerContractIds()){
                PlayerMatchParticipationKey playerMatchParticipationKey =PlayerMatchParticipationKey.builder()
                        .matchId(matchId)
                        .playerContractId(playerContractId)
                        .build();
                playerMatchParticipationKeyList.add(playerMatchParticipationKey);
            }
        }
        List<PlayerMatchParticipation> playerMatchParticipationList = playerMatchParticipationRepository.findAllById(playerMatchParticipationKeyList);


        if(dto.getStaffContractIds()!= null && !dto.getStaffContractIds().isEmpty()){
            for (UUID staffContractId:dto.getStaffContractIds()){
                StaffMatchParticipationKey staffMatchParticipationKey =StaffMatchParticipationKey.builder()
                        .matchId(matchId)
                        .staffContractId(staffContractId)
                        .build();
                staffMatchParticipationKeyList.add(staffMatchParticipationKey);
            }
        }
        List<StaffMatchParticipation> staffMatchParticipationList = staffMatchParticipationRepository.findAllById(staffMatchParticipationKeyList);

        if(dto.getRefereeIds()!= null && !dto.getRefereeIds().isEmpty()){
            for (UUID refereeId:dto.getRefereeIds()){
                RefereeMatchParticipationKey refereeMatchParticipationKey =RefereeMatchParticipationKey.builder()
                        .matchId(matchId)
                        .refereeId(refereeId)
                        .build();
                refereeMatchParticipationKeyList.add(refereeMatchParticipationKey);
            }
        }
        List<RefereeMatchParticipation> refereeMatchParticipationList = refereeMatchParticipationRepository.findAllById(refereeMatchParticipationKeyList);


        MatchActivity matchActivity = MatchActivity.builder()
                .match(match)
                .minuteInMatch(dto.getMinuteInMatch())
                .type(ActivityType.lookup(dto.getType()))
                .playerMatchParticipations(playerMatchParticipationList)
                .refereeMatchParticipations(refereeMatchParticipationList)
                .staffMatchParticipations(staffMatchParticipationList)
                .build();
        matchActivityRepository.save(matchActivity);
        return modelMapper.map(matchActivity,MatchActivityResponse.class);
    }
    public String deleteMatchById(UUID matchId){
        boolean exist = matchRepository.existsById(matchId);
        if (exist){
            matchRepository.deleteById(matchId);
            return MatchSuccessMessage.DELETE_MATCH_SUCCESSFUL;
        }else {
            return MatchFailMessage.DELETE_MATCH_FAIL;
        }
    }
}
