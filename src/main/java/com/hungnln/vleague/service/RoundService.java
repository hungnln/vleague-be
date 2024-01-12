package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.RoundCreateDTO;
import com.hungnln.vleague.DTO.RoundUpdateDTO;
import com.hungnln.vleague.constant.club.ClubFailMessage;
import com.hungnln.vleague.constant.round.RoundFailMessage;
import com.hungnln.vleague.constant.round.RoundSuccessMessage;
import com.hungnln.vleague.constant.tournament.TournamentFailMessage;
import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.Round;
import com.hungnln.vleague.entity.Player;
import com.hungnln.vleague.entity.Tournament;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.helper.RoundSpecification;
import com.hungnln.vleague.helper.SearchCriteria;
import com.hungnln.vleague.helper.SearchOperation;
import com.hungnln.vleague.repository.*;
import com.hungnln.vleague.response.PaginationResponse;
import com.hungnln.vleague.response.RoundResponse;
import com.hungnln.vleague.response.ResponseWithTotalPage;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoundService {
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private TournamentRepository tournamentRepository;

    private final ModelMapper modelMapper;

    public ResponseWithTotalPage<RoundResponse> getAllRound(int pageNo, int pageSize, String name, UUID tournamentId){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));

        List<Specification<Round>> specificationList = new ArrayList<>();
        if (tournamentId != null) {
            Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(()-> new NotFoundException(TournamentFailMessage.TOURNAMENT_NOT_FOUND));
            RoundSpecification specification = new RoundSpecification(new SearchCriteria("tournament",SearchOperation.EQUALITY,tournament));
        specificationList.add(specification);
        }
        if (name != null && !name.isEmpty()) {
            RoundSpecification specTitle = new RoundSpecification(new SearchCriteria("name",SearchOperation.CONTAINS,name));
            specificationList.add(specTitle);
        }
        Page<Round> pageResult = roundRepository.findAll(Specification.allOf(specificationList),pageable);
        ResponseWithTotalPage<RoundResponse> response = new ResponseWithTotalPage<>();
        List<RoundResponse> roundList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Round round :
                    pageResult.getContent()) {
                RoundResponse roundResponse = modelMapper.map(round, RoundResponse.class);
                roundList.add(roundResponse);
            }
            response.setData(roundList);
            PaginationResponse paginationResponse = PaginationResponse.builder()
                    .pageIndex(pageResult.getNumber())
                    .pageSize(pageResult.getSize())
                    .totalCount((int) pageResult.getTotalElements())
                    .totalPage(pageResult.getTotalPages())
                    .build();
            response.setPagination(paginationResponse);

        }else{
            throw new ListEmptyException(RoundFailMessage.LIST_ROUND_IS_EMPTY);
        }
        return response;
    }
    public RoundResponse addRound(RoundCreateDTO roundCreateDTO){
        Tournament tournament =tournamentRepository.findTournamentById(roundCreateDTO.getTournamentId()).orElseThrow(()->new NotFoundException(TournamentFailMessage.TOURNAMENT_NOT_FOUND));
        Round round =Round.builder()
                .tournament(tournament)
                .name(roundCreateDTO.getName())
                .build();
        roundRepository.save(round);
        return modelMapper.map(round,RoundResponse.class);
    }
    public RoundResponse updateRound(UUID roundId,RoundUpdateDTO roundUpdateDTO){
        Round round = roundRepository.findById(roundId).orElseThrow(()->new NotFoundException(RoundFailMessage.ROUND_NOT_FOUND));
        Tournament tournament =tournamentRepository.findTournamentById(roundUpdateDTO.getTournamentId()).orElseThrow(()->new NotFoundException(TournamentFailMessage.TOURNAMENT_NOT_FOUND));
        round.setTournament(tournament);
        round.setName(roundUpdateDTO.getName());
        roundRepository.save(round);
        return modelMapper.map(round,RoundResponse.class);
    }
    public RoundResponse findRoundById(UUID roundId){
        Round round = roundRepository.findById(roundId).orElseThrow(()->new NotFoundException(RoundFailMessage.ROUND_NOT_FOUND));
        return modelMapper.map(round,RoundResponse.class);
    }
    public String deleteRoundById(UUID roundId){
        boolean exist = roundRepository.existsById(roundId);
        if (exist){
            roundRepository.deleteById(roundId);
            return RoundSuccessMessage.DELETE_ROUND_SUCCESSFUL;
        }else {
            return RoundFailMessage.DELETE_ROUND_FAIL;
        }
    }
}
