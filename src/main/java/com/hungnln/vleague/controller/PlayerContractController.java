package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.PlayerContractCreateDTO;
import com.hungnln.vleague.DTO.PlayerContractUpdateDTO;
import com.hungnln.vleague.constant.player_contract.PlayerContractSuccessMessage;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.repository.PlayerContractRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.PlayerContractResponse;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.service.PlayerContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/player-contracts")
@Tag(name = "player contract", description = "player api")
public class PlayerContractController {
    public static Logger logger = LoggerFactory.getLogger(PlayerContractController.class);
    @Autowired
    PlayerContractRepository playerContractRepository;
    @Autowired
    PlayerContractService playerContractService;

    @GetMapping("")
    @Operation(summary ="Get players contract list", description = "Get players contract list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<PlayerContractResponse>>> getAllPlayerContracts(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) UUID playerId,
            @RequestParam(required = false) UUID clubId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(required = false) String matchDate,
            @RequestParam(defaultValue = "true") Boolean includeEndedContracts

    ){
        ResponseDTO<ResponseWithTotalPage<PlayerContractResponse>> responseDTO = new ResponseDTO<>();
        ResponseWithTotalPage<PlayerContractResponse> list = playerContractService.getAllPlayerContracts(pageIndex, pageSize,playerId,clubId,start,end,matchDate,includeEndedContracts);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(PlayerContractSuccessMessage.GET_PLAYER_CONTRACT_SUCCESSFUL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO<PlayerContractResponse>> getPlayerContractById(@PathVariable UUID id){
        ResponseDTO<PlayerContractResponse> responseDTO = new ResponseDTO<>();
        PlayerContractResponse playerContract = playerContractService.getPlayerContractById(id);
        responseDTO.setData(playerContract);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(PlayerContractSuccessMessage.GET_PLAYER_CONTRACT_SUCCESSFUL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("")
    ResponseEntity<ResponseDTO<PlayerContractResponse>> addPlayerContract(@RequestBody @Valid PlayerContractCreateDTO dto) throws BindException {
        ResponseDTO<PlayerContractResponse> responseDTO = new ResponseDTO<>();
        PlayerContractResponse playerContract = playerContractService.addPlayerContract(dto);
        responseDTO.setData(playerContract);
        responseDTO.setMessage(PlayerContractSuccessMessage.CREATE_PLAYER_CONTRACT_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO<PlayerContractResponse>> updatePlayerContract(@PathVariable UUID id, @RequestBody @Valid PlayerContractUpdateDTO dto) throws BindException{
        ResponseDTO<PlayerContractResponse> responseDTO = new ResponseDTO<>();
        PlayerContractResponse playerContract = playerContractService.updatePlayerContract(id,dto);
        responseDTO.setData(playerContract);
        responseDTO.setMessage(PlayerContractSuccessMessage.UPDATE_PLAYER_CONTRACT_SUCCESSFUL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);

    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO<PlayerContractResponse>> deletePlayerContract(@PathVariable UUID id){
        ResponseDTO<PlayerContractResponse> responseDTO = new ResponseDTO<>();
        String msg = playerContractService.deletePlayerContract(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
