package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.StadiumCreateDTO;
import com.hungnln.vleague.DTO.StadiumUpdateDTO;
import com.hungnln.vleague.constant.stadium.StadiumSuccessMessage;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.repository.StadiumRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.response.StadiumResponse;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.service.StadiumService;
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
@RequestMapping("/api/v1/stadiums")
@Tag(name = "stadium", description = "stadium api")
public class StadiumController {
    @Autowired
    StadiumRepository stadiumRepository;
    @Autowired
    StadiumService stadiumService;
    @GetMapping("")
    @Operation(summary ="Get stadiums list", description = "Get stadiums list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<StadiumResponse>>> getAllStadiums(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "20") int pageSize
    ){
        ResponseDTO<ResponseWithTotalPage<StadiumResponse>> responseDTO = new ResponseDTO<>();
        ResponseWithTotalPage<StadiumResponse> list = stadiumService.getAllStadiums(pageIndex, pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(StadiumSuccessMessage.GET_ALL_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO<StadiumResponse>> getStadiumById(@PathVariable UUID id){
        ResponseDTO<StadiumResponse> responseDTO = new ResponseDTO<>();
        StadiumResponse stadium = stadiumService.getStadiumById(id);
        responseDTO.setData(stadium);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(StadiumSuccessMessage.GET_STADIUM_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("")
    ResponseEntity<ResponseDTO<StadiumResponse>> addStadium(@RequestBody @Valid StadiumCreateDTO dto) throws BindException {
        ResponseDTO<StadiumResponse> responseDTO = new ResponseDTO<>();
        StadiumResponse stadium = stadiumService.addStadium(dto);
        responseDTO.setData(stadium);
        responseDTO.setMessage(StadiumSuccessMessage.CREATE_STADIUM_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO<StadiumResponse>> updateStadium(@PathVariable UUID id, @RequestBody @Valid StadiumUpdateDTO dto) throws BindException{
        ResponseDTO<StadiumResponse> responseDTO = new ResponseDTO<>();
        StadiumResponse stadium = stadiumService.updateStadium(id,dto);
        responseDTO.setData(stadium);
        responseDTO.setMessage(StadiumSuccessMessage.UPDATE_STADIUM_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);

    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO> deleteStadium(@PathVariable UUID id){
        ResponseDTO<StadiumResponse> responseDTO = new ResponseDTO<>();
        String msg = stadiumService.deleteStadium(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
