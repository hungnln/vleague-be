package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.TournamentCreateDTO;
import com.hungnln.vleague.DTO.TournamentUpdateDTO;
import com.hungnln.vleague.constant.tournament.TournamentFailMessage;
import com.hungnln.vleague.constant.tournament.TournamentSuccessMessage;
import com.hungnln.vleague.entity.Tournament;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.repository.TournamentRepository;
import com.hungnln.vleague.response.PaginationResponse;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.response.StaffResponse;
import com.hungnln.vleague.response.TournamentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class TournamentService  {
    @Autowired
    private TournamentRepository tournamentRepository;
    private final ModelMapper modelMapper;

    public ResponseWithTotalPage<TournamentResponse> getAllTournaments(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Tournament> pageResult = tournamentRepository.findAll(pageable);
        ResponseWithTotalPage<TournamentResponse> response = new ResponseWithTotalPage<>();
        List<TournamentResponse> tournamentList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Tournament tournament :
                    pageResult.getContent()) {
                TournamentResponse tournamentResponse = modelMapper.map(tournament, TournamentResponse.class);
                tournamentList.add(tournamentResponse);
            }

        }
        response.setData(tournamentList);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageIndex(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalCount((int) pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .build();
        response.setPagination(paginationResponse);
        return response;
    }

    public TournamentResponse addTournament(TournamentCreateDTO tournamentCreateDTO) {
        Optional<Tournament> tournament = tournamentRepository.findTournamentByName(tournamentCreateDTO.getName());
        if(tournament.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            Tournament tmp = Tournament.builder()
                    .id(uuid)
                    .start(tournamentCreateDTO.getStart())
                    .name(tournamentCreateDTO.getName())
                    .end(tournamentCreateDTO.getEnd())
                    .build();
            tournamentRepository.save(tmp);
            return modelMapper.map(tmp, TournamentResponse.class);
        }else {
            throw new ExistException(TournamentFailMessage.TOURNAMENT_EXIST);
        }
    }
    public TournamentResponse getTournamentById(UUID id){
        Tournament tournament = tournamentRepository.findTournamentById(id).orElseThrow(()-> new NotFoundException(TournamentFailMessage.TOURNAMENT_NOT_FOUND));
        return modelMapper.map(tournament,TournamentResponse.class);
    }
    public String deleteTournament(UUID id){
        boolean exists = tournamentRepository.existsById(id);
        if(exists){
            tournamentRepository.deleteById(id);
            return TournamentSuccessMessage.REMOVE_TOURNAMENT_SUCCESSFULL;
        }else{
            return TournamentFailMessage.DELETE_TOURNAMENT_FAIL;
        }

    }
    public TournamentResponse updateTournament(UUID id, TournamentUpdateDTO tournamentUpdateDTO){
        Tournament tournament = tournamentRepository.findTournamentById(id).orElseThrow(()-> new NotFoundException(TournamentFailMessage.TOURNAMENT_NOT_FOUND));
        tournament.setName(tournamentUpdateDTO.getName());
        tournament.setStart(tournamentUpdateDTO.getStart());
        tournament.setEnd(tournamentUpdateDTO.getEnd());
        tournamentRepository.save(tournament);
        return  modelMapper.map(tournament,TournamentResponse.class);
    }
}
