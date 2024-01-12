package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.PlayerCreateDTO;
import com.hungnln.vleague.DTO.PlayerUpdateDTO;
import com.hungnln.vleague.constant.player.PlayerSuccessMessage;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.repository.PlayerRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.PlayerResponse;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.service.PlayerService;
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
@RequestMapping("/api/v1/players")
@Tag(name = "player", description = "player api")
public class PlayerController {
    public static Logger logger = LoggerFactory.getLogger(PlayerController.class);
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerService playerService;

    @GetMapping("")
    @Operation(summary ="Get players list", description = "Get players list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<PlayerResponse>>> getAllPlayers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize
    ){
        ResponseDTO<ResponseWithTotalPage<PlayerResponse>> responseDTO = new ResponseDTO<>();
        ResponseWithTotalPage<PlayerResponse> list = playerService.getAllPlayers(pageNo, pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(PlayerSuccessMessage.GET_PLAYER_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO<PlayerResponse>> getPlayerById(@PathVariable UUID id){
        ResponseDTO<PlayerResponse> responseDTO = new ResponseDTO<>();
        PlayerResponse player = playerService.getPlayerById(id);
        responseDTO.setData(player);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(PlayerSuccessMessage.GET_PLAYER_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("")
    ResponseEntity<ResponseDTO<PlayerResponse>> addPlayer(@RequestBody @Valid PlayerCreateDTO dto) throws BindException {
        ResponseDTO<PlayerResponse> responseDTO = new ResponseDTO<>();
        PlayerResponse player = playerService.addPlayer(dto);
        responseDTO.setData(player);
        responseDTO.setMessage(PlayerSuccessMessage.CREATE_PLAYER_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO<PlayerResponse>> updatePlayer(@PathVariable UUID id, @RequestBody @Valid PlayerUpdateDTO dto) throws BindException{
        ResponseDTO<PlayerResponse> responseDTO = new ResponseDTO<>();
        PlayerResponse player = playerService.updatePlayer(id,dto);
        responseDTO.setData(player);
        responseDTO.setMessage(PlayerSuccessMessage.UPDATE_PLAYER_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);

    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO<PlayerResponse>> deletePlayer(@PathVariable UUID id){
        ResponseDTO<PlayerResponse> responseDTO = new ResponseDTO<>();
        String msg = playerService.deletePlayer(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
