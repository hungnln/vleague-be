package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.RefereeCreateDTO;
import com.hungnln.vleague.DTO.RefereeUpdateDTO;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.constant.referee.RefereeSuccessMessage;
import com.hungnln.vleague.repository.RefereeRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.RefereeResponse;
import com.hungnln.vleague.service.RefereeService;
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
@RequestMapping("/api/v1.0/referees")
@Tag(name = "referee", description = "referee api")
public class RefereeController {
    @Autowired
    RefereeRepository refereeRepository;
    @Autowired
    RefereeService refereeService;
    @GetMapping("")
    @Operation(summary ="Get referees list", description = "Get referees list")
    ResponseEntity<ListResponseDTO> getAllReferees(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        ListResponseDTO<RefereeResponse> responseDTO = new ListResponseDTO<>();
        List<RefereeResponse> list = refereeService.getAllReferees(pageNo, pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(RefereeSuccessMessage.GET_ALL_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getRefereeById(@PathVariable UUID id){
        ResponseDTO<RefereeResponse> responseDTO = new ResponseDTO<>();
        RefereeResponse referee = refereeService.getRefereeById(id);
        responseDTO.setData(referee);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(RefereeSuccessMessage.GET_REFEREE_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("")
    ResponseEntity<ResponseDTO> addReferee(@RequestBody @Valid RefereeCreateDTO dto) throws BindException {
        ResponseDTO<RefereeResponse> responseDTO = new ResponseDTO<>();
        RefereeResponse referee = refereeService.addReferee(dto);
        responseDTO.setData(referee);
        responseDTO.setMessage(RefereeSuccessMessage.CREATE_REFEREE_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO> updateReferee(@PathVariable UUID id, @RequestBody @Valid RefereeUpdateDTO dto) throws BindException{
        ResponseDTO<RefereeResponse> responseDTO = new ResponseDTO<>();
        RefereeResponse referee = refereeService.updateReferee(id,dto);
        responseDTO.setData(referee);
        responseDTO.setMessage(RefereeSuccessMessage.UPDATE_REFEREE_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);

    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO> deleteReferee(@PathVariable UUID id){
        ResponseDTO<RefereeResponse> responseDTO = new ResponseDTO<>();
        String msg = refereeService.deleteReferee(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
