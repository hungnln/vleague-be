package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.RoundCreateDTO;
import com.hungnln.vleague.DTO.RoundUpdateDTO;
import com.hungnln.vleague.constant.round.RoundSuccessMessage;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.repository.RoundRepository;
import com.hungnln.vleague.response.RoundResponse;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.service.RoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rounds")
@Tag(name = "round", description = "round api")
public class RoundController {
    public static Logger logger = LoggerFactory.getLogger(RoundController.class);
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    RoundService roundService;

    @GetMapping("")
    @Operation(summary ="Get round list", description = "Get round list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<RoundResponse>>> getAllRound(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") UUID tournamentId,
            @RequestParam(defaultValue = "") String name
    ){
        ResponseDTO<ResponseWithTotalPage<RoundResponse>> responseDTO = new ResponseDTO<>();
        ResponseWithTotalPage<RoundResponse> list = roundService.getAllRound(pageIndex,pageSize,name,tournamentId);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(RoundSuccessMessage.GET_ALL_SUCCESSFUL);
        return ResponseEntity.ok().body(responseDTO);
    }


    @PostMapping("")
    @Operation(summary ="Add round", description = "Add round")
    ResponseEntity<ResponseDTO<RoundResponse>> addRound(@RequestBody @Valid RoundCreateDTO dto) throws BindException {
        ResponseDTO<RoundResponse> responseDTO = new ResponseDTO<>();
        RoundResponse roundResponse = roundService.addRound(dto);
        responseDTO.setData(roundResponse);
        responseDTO.setMessage(RoundSuccessMessage.CREATE_ROUND_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PutMapping("/{id}")
    @Operation(summary ="Update round", description = "Update round")
    ResponseEntity<ResponseDTO<RoundResponse>> updateRound(@PathVariable UUID id,@RequestBody @Valid RoundUpdateDTO dto) throws BindException {
        ResponseDTO<RoundResponse> responseDTO = new ResponseDTO<>();
        RoundResponse roundResponse = roundService.updateRound(id,dto);
        responseDTO.setData(roundResponse);
        responseDTO.setMessage(RoundSuccessMessage.UPDATE_ROUND_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    @Operation(summary ="Get round by id", description = "Get round by id")
    ResponseEntity<ResponseDTO<RoundResponse>> findRound(@PathVariable UUID id){
        ResponseDTO<RoundResponse> responseDTO = new ResponseDTO<>();
        RoundResponse roundResponse = roundService.findRoundById(id);
        responseDTO.setData(roundResponse);
        responseDTO.setMessage(RoundSuccessMessage.GET_ROUND_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary ="Delete round by id", description = "Delete round by id")
    ResponseEntity<ResponseDTO<RoundResponse>> deleteRound(@PathVariable UUID id){
        ResponseDTO<RoundResponse> responseDTO = new ResponseDTO<>();
        String msg = roundService.deleteRoundById(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
