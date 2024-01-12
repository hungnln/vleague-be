package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.StaffCreateDTO;
import com.hungnln.vleague.DTO.StaffUpdateDTO;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.constant.staff.StaffSuccessMessage;
import com.hungnln.vleague.repository.StaffRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.response.StaffResponse;
import com.hungnln.vleague.service.StaffService;
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
@RequestMapping("/api/v1/staffs")
@Tag(name = "staff", description = "staff api")
public class StaffController {
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    StaffService staffService;
    @GetMapping("")
    @Operation(summary ="Get staffs list", description = "Get staffs list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<StaffResponse>>> getAllStaffs(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        ResponseDTO<ResponseWithTotalPage<StaffResponse>> responseDTO = new ResponseDTO<>();
        ResponseWithTotalPage<StaffResponse> list = staffService.getAllStaffs(pageIndex, pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(StaffSuccessMessage.GET_ALL_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO<StaffResponse>> getStaffById(@PathVariable UUID id){
        ResponseDTO<StaffResponse> responseDTO = new ResponseDTO<>();
        StaffResponse staff = staffService.getStaffById(id);
        responseDTO.setData(staff);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(StaffSuccessMessage.GET_STAFF_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("")
    ResponseEntity<ResponseDTO<StaffResponse>> addStaff(@RequestBody @Valid StaffCreateDTO dto) throws BindException {
        ResponseDTO<StaffResponse> responseDTO = new ResponseDTO<>();
        StaffResponse staff = staffService.addStaff(dto);
        responseDTO.setData(staff);
        responseDTO.setMessage(StaffSuccessMessage.CREATE_STAFF_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO<StaffResponse>> updateStaff(@PathVariable UUID id, @RequestBody @Valid StaffUpdateDTO dto) throws BindException{
        ResponseDTO<StaffResponse> responseDTO = new ResponseDTO<>();
        StaffResponse staff = staffService.updateStaff(id,dto);
        responseDTO.setData(staff);
        responseDTO.setMessage(StaffSuccessMessage.UPDATE_STAFF_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);

    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO> deleteStaff(@PathVariable UUID id){
        ResponseDTO<StaffResponse> responseDTO = new ResponseDTO<>();
        String msg = staffService.deleteStaff(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
