package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.TournamentCreateDTO;
import com.hungnln.vleague.DTO.TournamentUpdateDTO;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.constant.tournament.TournamentSuccessMessage;
import com.hungnln.vleague.repository.TournamentRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.TournamentResponse;
import com.hungnln.vleague.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1.0/tournaments")
@Tag(name = "tournament", description = "tournament api")
public class TournamentController {
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    TournamentService tournamentService;
    @GetMapping("")
    @Operation(summary ="Get tournaments list", description = "Get tournaments list")
    ResponseEntity<ListResponseDTO> getAllTournaments(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        ListResponseDTO<TournamentResponse> responseDTO = new ListResponseDTO<>();
        List<TournamentResponse> list = tournamentService.getAllTournaments(pageNo, pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(TournamentSuccessMessage.GET_ALL_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getTournamentById(@PathVariable UUID id){
        ResponseDTO<TournamentResponse> responseDTO = new ResponseDTO<>();
        TournamentResponse tournament = tournamentService.getTournamentById(id);
        responseDTO.setData(tournament);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(TournamentSuccessMessage.GET_TOURNAMENT_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("")
    ResponseEntity<ResponseDTO> addTournament(@RequestBody @Valid TournamentCreateDTO dto) throws BindException {
        ResponseDTO<TournamentResponse> responseDTO = new ResponseDTO<>();
        TournamentResponse tournament = tournamentService.addTournament(dto);
        responseDTO.setData(tournament);
        responseDTO.setMessage(TournamentSuccessMessage.CREATE_TOURNAMENT_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO> updateTournament(@PathVariable UUID id, @RequestBody @Valid TournamentUpdateDTO dto) throws BindException{
        ResponseDTO<TournamentResponse> responseDTO = new ResponseDTO<>();
        TournamentResponse tournament = tournamentService.updateTournament(id,dto);
        responseDTO.setData(tournament);
        responseDTO.setMessage(TournamentSuccessMessage.UPDATE_TOURNAMENT_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);

    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO> deleteTournament(@PathVariable UUID id){
        ResponseDTO<TournamentResponse> responseDTO = new ResponseDTO<>();
        String msg = tournamentService.deleteTournament(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
