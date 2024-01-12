package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.StaffCreateDTO;
import com.hungnln.vleague.DTO.StaffUpdateDTO;
import com.hungnln.vleague.constant.staff.StaffFailMessage;
import com.hungnln.vleague.constant.staff.StaffSuccessMessage;
import com.hungnln.vleague.entity.Staff;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.repository.StaffRepository;
import com.hungnln.vleague.response.PaginationResponse;
import com.hungnln.vleague.response.PlayerResponse;
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
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    private final ModelMapper modelMapper;

    public ResponseWithTotalPage<StaffResponse> getAllStaffs(int pageIndex, int pageSize){
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Staff> pageResult = staffRepository.findAll(pageable);
        ResponseWithTotalPage<StaffResponse> response = new ResponseWithTotalPage<>();
        List<StaffResponse> staffList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Staff staff :
                    pageResult.getContent()) {
                StaffResponse staffResponse = modelMapper.map(staff, StaffResponse.class);
                staffList.add(staffResponse);
            }
        }
        response.setData(staffList);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageIndex(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalCount((int) pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .build();
        response.setPagination(paginationResponse);
        return response;
    }

    public StaffResponse addStaff(StaffCreateDTO staffCreateDTO) {
        Optional<Staff> staff = staffRepository.findStaffByName(staffCreateDTO.getName());
        if(staff.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            Staff tmp = Staff.builder()
                    .id(uuid)
                    .imageURL(staffCreateDTO.getImageURL())
                    .name(staffCreateDTO.getName())
                    .build();
            staffRepository.save(tmp);
            return modelMapper.map(tmp, StaffResponse.class);
        }else {
            throw new ExistException(StaffFailMessage.STAFF_EXIST);
        }
    }
    public StaffResponse getStaffById(UUID id){
        Staff staff = staffRepository.findStaffById(id).orElseThrow(()-> new NotFoundException(StaffFailMessage.STAFF_NOT_FOUND));
        return modelMapper.map(staff,StaffResponse.class);
    }
    public String deleteStaff(UUID id){
        boolean exists = staffRepository.existsById(id);
        if(exists){
            staffRepository.deleteById(id);
            return StaffSuccessMessage.REMOVE_STAFF_SUCCESSFULL;
        }else{
            return StaffFailMessage.DELETE_STAFF_FAIL;
        }

    }
    public StaffResponse updateStaff(UUID id, StaffUpdateDTO staffUpdateDTO){
        Staff staff = staffRepository.findStaffById(id).orElseThrow(()-> new NotFoundException(StaffFailMessage.STAFF_NOT_FOUND));
        staff.setName(staffUpdateDTO.getName());
        staff.setImageURL(staffUpdateDTO.getImageURL());
        staffRepository.save(staff);
        return  modelMapper.map(staff,StaffResponse.class);
    }
}
