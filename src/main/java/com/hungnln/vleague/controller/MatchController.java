package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.MatchActivityCreateDTO;
import com.hungnln.vleague.DTO.MatchCreateDTO;
import com.hungnln.vleague.DTO.MatchUpdateDTO;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.constant.match.MatchSuccessMessage;
import com.hungnln.vleague.repository.MatchRepository;
import com.hungnln.vleague.response.*;
import com.hungnln.vleague.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/matches")
@Tag(name = "match", description = "match api")
@RequiredArgsConstructor
public class MatchController {
    public static Logger logger = LoggerFactory.getLogger(MatchController.class);
    private final MatchService matchService;

    @GetMapping("")
    @Operation(summary ="Get match list", description = "Get match list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<MatchResponse>>> getAllMatch(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) UUID tournamentId,
            @RequestParam(required = false) UUID stadiumId,
            @RequestParam(required = false) UUID roundId
    ){
        ResponseDTO<ResponseWithTotalPage<MatchResponse>> responseDTO = new ResponseDTO<>();
        ResponseWithTotalPage<MatchResponse> list = matchService.getAllMatch(pageIndex,pageSize,tournamentId,stadiumId,roundId);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(MatchSuccessMessage.GET_ALL_SUCCESSFUL);
        return ResponseEntity.ok().body(responseDTO);
    }


    @PostMapping("")
    @Operation(summary ="Add match", description = "Add match")
    ResponseEntity<ResponseDTO<MatchResponse>> addMatch(@RequestBody @Valid MatchCreateDTO dto) throws BindException {
        ResponseDTO<MatchResponse> responseDTO = new ResponseDTO<>();
        MatchResponse matchResponse = matchService.addMatch(dto);
        responseDTO.setData(matchResponse);
        responseDTO.setMessage(MatchSuccessMessage.CREATE_MATCH_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
//    @PutMapping("/{id}")
//    @Operation(summary ="Update match", description = "Update match")
//    ResponseEntity<ResponseDTO<MatchResponse>> updateMatch(@PathVariable UUID id,@RequestBody @Valid MatchUpdateDTO dto) throws BindException {
//        ResponseDTO<MatchResponse> responseDTO = new ResponseDTO<>();
//        MatchResponse matchResponse = matchService.updateMatch(id,dto);
//        responseDTO.setData(matchResponse);
//        responseDTO.setMessage(MatchSuccessMessage.UPDATE_ROUND_SUCCESSFUL);
//        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
//        return ResponseEntity.ok().body(responseDTO);
//    }
    @GetMapping("/{id}")
    @Operation(summary ="Get match by id", description = "Get match by id")
    ResponseEntity<ResponseDTO<MatchResponse>> findMatch(@PathVariable UUID id){
        ResponseDTO<MatchResponse> responseDTO = new ResponseDTO<>();
        MatchResponse matchResponse = matchService.findMatchById(id);
        responseDTO.setData(matchResponse);
        responseDTO.setMessage(MatchSuccessMessage.GET_MATCH_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary ="Delete match by id", description = "Delete match by id")
    ResponseEntity<ResponseDTO<MatchResponse>> deleteMatch(@PathVariable UUID id){
        ResponseDTO<MatchResponse> responseDTO = new ResponseDTO<>();
        String msg = matchService.deleteMatchById(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("/{id}/activities")
    @Operation(summary ="Add match activities by id", description = "Add match activities by id")
    ResponseEntity<ResponseDTO<MatchActivityResponse>> addMatchActivities(@PathVariable UUID id, @RequestBody @Valid MatchActivityCreateDTO dto) throws BindException{
        ResponseDTO<MatchActivityResponse> responseDTO = new ResponseDTO<>();
        MatchActivityResponse matchActivityResponse = matchService.addMatchActivity(id,dto);
        responseDTO.setData(matchActivityResponse);
        responseDTO.setMessage(MatchSuccessMessage.ADD_MATCH_ACTIVITY_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}/participation")
    @Operation(summary ="Get match participation by id", description = "Get match participation by id")
    ResponseEntity<ResponseDTO<MatchParticipationResponse>> getMatchParticipation(@PathVariable UUID id){
        ResponseDTO<MatchParticipationResponse> responseDTO = new ResponseDTO<>();
        MatchParticipationResponse matchParticipationResponse = matchService.getMatchParticipationById(id);
        responseDTO.setData(matchParticipationResponse);
        responseDTO.setMessage(MatchSuccessMessage.GET_MATCH_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
