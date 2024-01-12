package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.ClubCreateDTO;
import com.hungnln.vleague.DTO.ClubUpdateDTO;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.constant.club.ClubSuccessMessage;
import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.repository.ClubRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.ClubResponse;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
//import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs")
@Tag(name = "club", description = "club api")
public class ClubController {
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    ClubService clubService;

    @GetMapping("")
    @Operation(summary = "Get clubs list", description = "Get clubs list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<ClubResponse>>> getAllClubs(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        ResponseDTO<ResponseWithTotalPage<ClubResponse>> responseDTO = new ResponseDTO<>();
        ResponseWithTotalPage<ClubResponse> list = clubService.getAllClubs(pageIndex,pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(ClubSuccessMessage.GET_ALL_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO<ClubResponse>> getClubById(@PathVariable UUID id) {
        ResponseDTO<ClubResponse> responseDTO = new ResponseDTO<>();
        ClubResponse club = clubService.getClubById(id);
        responseDTO.setData(club);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(ClubSuccessMessage.GET_CLUB_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("")
    ResponseEntity<ResponseDTO<ClubResponse>> addClub(@RequestBody @Valid ClubCreateDTO dto) throws BindException {
        ResponseDTO<ClubResponse> responseDTO = new ResponseDTO<>();
        ClubResponse club = clubService.addClub(dto);
        responseDTO.setData(club);
        responseDTO.setMessage(ClubSuccessMessage.CREATE_CLUB_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO<ClubResponse>> updateClub(@PathVariable UUID id, @RequestBody @Valid ClubUpdateDTO dto) throws BindException {
        ResponseDTO<ClubResponse> responseDTO = new ResponseDTO<>();
        ClubResponse club = clubService.updateClub(id, dto);
        responseDTO.setData(club);
        responseDTO.setMessage(ClubSuccessMessage.UPDATE_CLUB_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO<ClubResponse>> deleteClub(@PathVariable UUID id) {
        ResponseDTO<ClubResponse> responseDTO = new ResponseDTO<>();
        String msg = clubService.deleteClub(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
