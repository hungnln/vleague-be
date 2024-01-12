package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.StaffContractCreateDTO;
import com.hungnln.vleague.DTO.StaffContractUpdateDTO;
import com.hungnln.vleague.constant.club.ClubFailMessage;
import com.hungnln.vleague.constant.staff.StaffFailMessage;
import com.hungnln.vleague.constant.staff_contract.StaffContractFailMessage;
import com.hungnln.vleague.constant.staff_contract.StaffContractSuccessMessage;
import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.PlayerContract;
import com.hungnln.vleague.entity.Staff;
import com.hungnln.vleague.entity.StaffContract;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.exceptions.NotValidException;
import com.hungnln.vleague.helper.PlayerContractSpecification;
import com.hungnln.vleague.helper.SearchCriteria;
import com.hungnln.vleague.helper.SearchOperation;
import com.hungnln.vleague.helper.StaffContractSpecification;
import com.hungnln.vleague.repository.ClubRepository;
import com.hungnln.vleague.repository.StaffContractRepository;
import com.hungnln.vleague.repository.StaffRepository;
import com.hungnln.vleague.response.PaginationResponse;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.response.StaffContractResponse;
import com.hungnln.vleague.response.StaffResponse;
import com.hungnln.vleague.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffContractService {
    @Autowired
    private StaffContractRepository staffContractRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private StaffRepository staffRepository;

    private final ModelMapper modelMapper;
    @Autowired
    private DateUtil dateUtil;

    public ResponseWithTotalPage<StaffContractResponse> getAllStaffContracts(int pageNo, int pageSize, UUID clubId, Date start, Date end, Boolean includeEndedContracts){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        ResponseWithTotalPage<StaffContractResponse> response = new ResponseWithTotalPage<>();
        List<StaffContractResponse> staffContractList = new ArrayList<>();
        List<Specification<StaffContract>> specificationList = new ArrayList<>();
        if(clubId != null){
            Club club = clubRepository.findById(clubId).orElseThrow(()-> new NotFoundException(ClubFailMessage.CLUB_NOT_FOUND));
            StaffContractSpecification specification = new StaffContractSpecification(new SearchCriteria("club", SearchOperation.EQUALITY,club));
            specificationList.add(specification);
        }
        if(includeEndedContracts){
            if(start != null){
                StaffContractSpecification specification = new StaffContractSpecification(new SearchCriteria("start", SearchOperation.GREATER_THAN,start));
                specificationList.add(specification);
            }
            if(end != null){
                StaffContractSpecification specification = new StaffContractSpecification(new SearchCriteria("end", SearchOperation.LESS_THAN,end));
                specificationList.add(specification);
            }
        }else{
            Date dateNow = new Date();
            StaffContractSpecification specification = new StaffContractSpecification(new SearchCriteria("start", SearchOperation.GREATER_THAN,dateNow));
            specificationList.add(specification);
        }
        Page<StaffContract> pageResult = staffContractRepository.findAll(Specification.allOf(specificationList),pageable);
        if(pageResult.hasContent()) {
            for (StaffContract staffContract :
                    pageResult.getContent()) {
                StaffContractResponse staffContractResponse = modelMapper.map(staffContract, StaffContractResponse.class);
                staffContractList.add(staffContractResponse);
            }
        }
        response.setData(staffContractList);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageIndex(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalCount((int) pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .build();
        response.setPagination(paginationResponse);
        return response;
    }

    @SneakyThrows
    public StaffContractResponse addStaffContract(StaffContractCreateDTO staffContractCreateDTO) {
        if(!staffContractCreateDTO.isValidDateRange()){
            throw new NotValidException(ValidationMessage.DATE_VALID_MESSAGE);
        }
        Optional<Staff> staff = staffRepository.findStaffById(staffContractCreateDTO.getStaffId());
        System.out.println(staff);
        if (!staff.isPresent()){
            throw new NotFoundException(StaffFailMessage.STAFF_NOT_FOUND);
        }
        Optional<Club> club = clubRepository.findClubById(staffContractCreateDTO.getClubId());
        if (!club.isPresent()){
            throw new NotFoundException(ClubFailMessage.CLUB_NOT_FOUND);
        }
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateStartString =dateUtil.formatDate(staffContractCreateDTO.getStart());
        String dateEndString = dateUtil.formatDate(staffContractCreateDTO.getEnd());
        List<StaffContract> staffContractList = staffContractRepository
                .findAllByStartGreaterThanEqualAndEndLessThanEqualAndStaffId(
                dateStartString,
                dateEndString,
                staffContractCreateDTO.getStaffId());
        if(staffContractList.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            StaffContract tmp = StaffContract.builder()
                    .id(uuid)
                    .start(staffContractCreateDTO.getStart())
                    .end(staffContractCreateDTO.getEnd())
                    .staff(staff.get())
                    .club(club.get())
                    .description(staffContractCreateDTO.getDescription())
                    .salary(staffContractCreateDTO.getSalary())
                    .build();
            staffContractRepository.save(tmp);
            return modelMapper.map(tmp, StaffContractResponse.class);
        }else {
            throw new ExistException(StaffContractFailMessage.STAFF_CONTRACT_EXIST);
        }
    }
    public StaffContractResponse getStaffContractById(UUID id){
        StaffContract staffContract = staffContractRepository.findStaffContractById(id).orElseThrow(()-> new NotFoundException(StaffContractFailMessage.STAFF_CONTRACT_NOT_FOUND));
        return modelMapper.map(staffContract,StaffContractResponse.class);
    }
    public String deleteStaffContract(UUID id){
        boolean exists = staffContractRepository.existsById(id);
        if(exists){
            staffContractRepository.deleteById(id);
            return StaffContractSuccessMessage.REMOVE_STAFF_CONTRACT_SUCCESSFUL;
        }else{
            return StaffContractFailMessage.DELETE_STAFF_CONTRACT_FAIL;
        }

    }
    public StaffContractResponse updateStaffContract(UUID id, StaffContractUpdateDTO staffContractUpdateDTO){
        StaffContract staffContract = staffContractRepository.findStaffContractById(id).orElseThrow(()-> new NotFoundException(StaffContractFailMessage.STAFF_CONTRACT_NOT_FOUND));
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        if(!staffContract.getStart().before(staffContractUpdateDTO.getEnd())){
            throw new NotValidException(ValidationMessage.DATE_VALID_MESSAGE);
        }
        String dateStartString = dateUtil.formatDate(staffContract.getStart());
        String dateEndString = dateUtil.formatDate(staffContractUpdateDTO.getEnd());
        List<StaffContract> staffContractList = staffContractRepository
                .findAllByStartGreaterThanEqualAndEndLessThanEqualAndStaffId(
                        dateStartString,
                        dateEndString,
                        staffContract.getStaff().getId()) ;
      if(staffContractList.size() == 1 ) {
          if(!staffContractList.contains(staffContract)){
              throw new NotFoundException(StaffContractFailMessage.STAFF_CONTRACT_NOT_FOUND);
          }
          staffContract.setEnd(staffContractUpdateDTO.getEnd());
          staffContract.setDescription(staffContractUpdateDTO.getDescription());
          staffContract.setSalary(staffContractUpdateDTO.getSalary());
          staffContractRepository.save(staffContract);
          return modelMapper.map(staffContract, StaffContractResponse.class);
        }else {
            throw new ExistException(StaffContractFailMessage.STAFF_CONTRACT_DATE_END_);
        }
    }
}
