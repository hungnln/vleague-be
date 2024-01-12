package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.StadiumCreateDTO;
import com.hungnln.vleague.DTO.StadiumUpdateDTO;
import com.hungnln.vleague.constant.stadium.StadiumFailMessage;
import com.hungnln.vleague.constant.stadium.StadiumSuccessMessage;
import com.hungnln.vleague.entity.Stadium;
import com.hungnln.vleague.entity.Stadium;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.repository.StadiumRepository;
import com.hungnln.vleague.response.PaginationResponse;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.response.StadiumResponse;
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
public class StadiumService {
    @Autowired
    private StadiumRepository stadiumRepository;
    private final ModelMapper modelMapper;

    public ResponseWithTotalPage<StadiumResponse> getAllStadiums(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Stadium> pageResult = stadiumRepository.findAll(pageable);
        ResponseWithTotalPage<StadiumResponse> response = new ResponseWithTotalPage<>();
        List<StadiumResponse> stadiumList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Stadium stadium :
                    pageResult.getContent()) {
                StadiumResponse stadiumResponse = modelMapper.map(stadium, StadiumResponse.class);
                stadiumList.add(stadiumResponse);
            }

        }
        response.setData(stadiumList);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageIndex(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalCount((int) pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .build();
        response.setPagination(paginationResponse);
        return response;
    }

    public StadiumResponse addStadium(StadiumCreateDTO stadiumCreateDTO) {
        Optional<Stadium> stadium = stadiumRepository.findStadiumByName(stadiumCreateDTO.getName());
        if(stadium.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            Stadium tmp = Stadium.builder()
                    .id(uuid)
                    .imageURL(stadiumCreateDTO.getImageURL())
                    .name(stadiumCreateDTO.getName())
                    .address(stadiumCreateDTO.getAddress())
                    .build();
            stadiumRepository.save(tmp);
            return modelMapper.map(tmp, StadiumResponse.class);
        }else {
            throw new ExistException(StadiumFailMessage.STADIUM_EXIST);
        }
    }
    public StadiumResponse getStadiumById(UUID id){
        Stadium stadium = stadiumRepository.findStadiumById(id).orElseThrow(()-> new NotFoundException(StadiumFailMessage.STADIUM_NOT_FOUND));
        return modelMapper.map(stadium,StadiumResponse.class);
    }
    public String deleteStadium(UUID id){
        boolean exists = stadiumRepository.existsById(id);
        if(exists){
            stadiumRepository.deleteById(id);
            return StadiumSuccessMessage.REMOVE_STADIUM_SUCCESSFULL;
        }else{
            return StadiumFailMessage.DELETE_STADIUM_FAIL;
        }

    }
    public StadiumResponse updateStadium(UUID id, StadiumUpdateDTO stadiumUpdateDTO){
        Stadium stadium = stadiumRepository.findStadiumById(id).orElseThrow(()-> new NotFoundException(StadiumFailMessage.STADIUM_NOT_FOUND));
        stadium.setName(stadiumUpdateDTO.getName());
        stadium.setAddress(stadiumUpdateDTO.getAddress());
        stadium.setImageURL(stadiumUpdateDTO.getImageURL());
        stadiumRepository.save(stadium);
        return  modelMapper.map(stadium,StadiumResponse.class);
    }
}
