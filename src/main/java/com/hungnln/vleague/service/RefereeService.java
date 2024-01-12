package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.RefereeCreateDTO;
import com.hungnln.vleague.DTO.RefereeUpdateDTO;
import com.hungnln.vleague.constant.referee.RefereeFailMessage;
import com.hungnln.vleague.constant.referee.RefereeSuccessMessage;
import com.hungnln.vleague.entity.Referee;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.repository.RefereeRepository;
import com.hungnln.vleague.response.PaginationResponse;
import com.hungnln.vleague.response.RefereeResponse;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.response.StaffResponse;
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
public class RefereeService {
    @Autowired
    private RefereeRepository refereeRepository;
    private final ModelMapper modelMapper;

    public ResponseWithTotalPage<RefereeResponse> getAllReferees(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Referee> pageResult = refereeRepository.findAll(pageable);
        ResponseWithTotalPage<RefereeResponse> response = new ResponseWithTotalPage<>();
        List<RefereeResponse> refereeList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Referee referee :
                    pageResult.getContent()) {
                RefereeResponse refereeResponse = modelMapper.map(referee, RefereeResponse.class);
                refereeList.add(refereeResponse);
            }
        }
        response.setData(refereeList);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageIndex(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalCount((int) pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .build();
        response.setPagination(paginationResponse);
        return response;
    }

    public RefereeResponse addReferee(RefereeCreateDTO refereeCreateDTO) {
        Optional<Referee> referee = refereeRepository.findRefereeByName(refereeCreateDTO.getName());
        if(referee.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            Referee tmp = Referee.builder()
                    .id(uuid)
                    .imageURL(refereeCreateDTO.getImageURL())
                    .name(refereeCreateDTO.getName())
                    .build();
            refereeRepository.save(tmp);
            return modelMapper.map(tmp, RefereeResponse.class);
        }else {
            throw new ExistException(RefereeFailMessage.REFEREE_EXIST);
        }
    }
    public RefereeResponse getRefereeById(UUID id){
        Referee referee = refereeRepository.findRefereeById(id).orElseThrow(()-> new NotFoundException(RefereeFailMessage.REFEREE_NOT_FOUND));
        return modelMapper.map(referee,RefereeResponse.class);
    }
    public String deleteReferee(UUID id){
        boolean exists = refereeRepository.existsById(id);
        if(exists){
            refereeRepository.deleteById(id);
            return RefereeSuccessMessage.REMOVE_REFEREE_SUCCESSFULL;
        }else{
            return RefereeFailMessage.DELETE_REFEREE_FAIL;
        }

    }
    public RefereeResponse updateReferee(UUID id, RefereeUpdateDTO refereeUpdateDTO){
        Referee referee = refereeRepository.findRefereeById(id).orElseThrow(()-> new NotFoundException(RefereeFailMessage.REFEREE_NOT_FOUND));
        referee.setName(refereeUpdateDTO.getName());
        referee.setImageURL(refereeUpdateDTO.getImageURL());
        refereeRepository.save(referee);
        return  modelMapper.map(referee,RefereeResponse.class);
    }
}
