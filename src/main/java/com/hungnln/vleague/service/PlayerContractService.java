package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.PlayerContractCreateDTO;
import com.hungnln.vleague.DTO.PlayerContractUpdateDTO;
import com.hungnln.vleague.constant.club.ClubFailMessage;
import com.hungnln.vleague.constant.club.ClubSuccessMessage;
import com.hungnln.vleague.constant.player.PlayerFailMessage;
import com.hungnln.vleague.constant.player_contract.PlayerContractFailMessage;
import com.hungnln.vleague.constant.player_contract.PlayerContractSuccessMessage;
import com.hungnln.vleague.constant.stadium.StadiumFailMessage;
import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.Player;
import com.hungnln.vleague.entity.PlayerContract;
import com.hungnln.vleague.entity.Stadium;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.exceptions.NotValidException;
import com.hungnln.vleague.repository.ClubRepository;
import com.hungnln.vleague.repository.PlayerContractRepository;
import com.hungnln.vleague.repository.PlayerRepository;
import com.hungnln.vleague.repository.StadiumRepository;
import com.hungnln.vleague.response.PlayerContractResponse;
import com.hungnln.vleague.utils.DateUtil;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlayerContractService {
    @Autowired
    private PlayerContractRepository playerContractRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private PlayerRepository playerRepository;

    private final ModelMapper modelMapper;
    @Autowired
    private DateUtil dateUtil;

    public List<PlayerContractResponse> getAllPlayerContracts(int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<PlayerContract> pageResult = playerContractRepository.findAll(pageable);
        List<PlayerContractResponse> playerContractList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (PlayerContract playerContract :
                    pageResult.getContent()) {
                PlayerContractResponse playerContractResponse = modelMapper.map(playerContract, PlayerContractResponse.class);
                playerContractList.add(playerContractResponse);
            }
        }else{
            throw new ListEmptyException(PlayerContractFailMessage.LIST_PLAYER_CONTRACT_IS_EMPTY);

        }
        return playerContractList;
    }

    @SneakyThrows
    public PlayerContractResponse addPlayerContract(PlayerContractCreateDTO playerContractCreateDTO) {
        if(!playerContractCreateDTO.isValidDateRange()){
            throw new NotValidException(ValidationMessage.DATE_VALID_MESSAGE);
        }
        Optional<Player> player = playerRepository.findPlayerById(playerContractCreateDTO.getPlayerId());
        System.out.println(player);
        if (!player.isPresent()){
            throw new NotFoundException(PlayerFailMessage.PLAYER_NOT_FOUND);
        }
        Optional<Club> club = clubRepository.findClubById(playerContractCreateDTO.getClubId());
        if (!club.isPresent()){
            throw new NotFoundException(ClubFailMessage.CLUB_NOT_FOUND);
        }
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateStartString =dateUtil.formatDate(playerContractCreateDTO.getStart());
        String dateEndString = dateUtil.formatDate(playerContractCreateDTO.getEnd());
        boolean numberValid = playerContractRepository.countAllByStartGreaterThanEqualAndEndLessThanEqual(dateStartString,dateEndString,playerContractCreateDTO.getNumber()) == 0;
        if(!numberValid){
            throw new NotValidException(ValidationMessage.NUMBER_DUPLICATE_MESSAGE);
        }
        List<PlayerContract> playerContractList = playerContractRepository
                .findAllByStartGreaterThanEqualAndEndLessThanEqualAndPlayerId(
                dateStartString,
                dateEndString,
                playerContractCreateDTO.getPlayerId());
        if(playerContractList.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            PlayerContract tmp = PlayerContract.builder()
                    .id(uuid)
                    .start(playerContractCreateDTO.getStart())
                    .end(playerContractCreateDTO.getEnd())
                    .number(playerContractCreateDTO.getNumber())
                    .player(player.get())
                    .club(club.get())
                    .description(playerContractCreateDTO.getDescription())
                    .salary(playerContractCreateDTO.getSalary())
                    .build();
            playerContractRepository.save(tmp);
            return modelMapper.map(tmp, PlayerContractResponse.class);
        }else {
            throw new ExistException(PlayerContractFailMessage.PLAYER_CONTRACT_EXIST);
        }
    }
    public PlayerContractResponse getPlayerContractById(UUID id){
        PlayerContract playerContract = playerContractRepository.findPlayerContractById(id).orElseThrow(()-> new NotFoundException(PlayerContractFailMessage.PLAYER_CONTRACT_NOT_FOUND));
        return modelMapper.map(playerContract,PlayerContractResponse.class);
    }
    public String deletePlayerContract(UUID id){
        boolean exists = playerContractRepository.existsById(id);
        if(exists){
            playerContractRepository.deleteById(id);
            return PlayerContractSuccessMessage.REMOVE_PLAYER_CONTRACT_SUCCESSFUL;
        }else{
            return PlayerContractFailMessage.DELETE_PLAYER_CONTRACT_FAIL;
        }

    }
    public PlayerContractResponse updatePlayerContract(UUID id, PlayerContractUpdateDTO playerContractUpdateDTO){
        PlayerContract playerContract = playerContractRepository.findPlayerContractById(id).orElseThrow(()-> new NotFoundException(PlayerContractFailMessage.PLAYER_CONTRACT_NOT_FOUND));
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        if(playerContract.getStart().before(playerContractUpdateDTO.getEnd())){
            throw new NotValidException(ValidationMessage.DATE_VALID_MESSAGE);
        }
        String dateStartString = dateUtil.formatDate(playerContract.getStart());
        String dateEndString = dateUtil.formatDate(playerContractUpdateDTO.getEnd());
        List<PlayerContract> playerContractList = playerContractRepository
                .findAllByStartGreaterThanEqualAndEndLessThanEqualAndPlayerId(
                        dateStartString,
                        dateEndString,
                        playerContract.getPlayer().getId()) ;
      if(playerContractList.size() == 1 ) {
          if(playerContractList.contains(playerContract)){
              throw new NotFoundException(PlayerContractFailMessage.PLAYER_CONTRACT_NOT_FOUND);
          }
          boolean numberValid = playerContractRepository.countAllByStartGreaterThanEqualAndEndLessThanEqual(dateStartString,dateEndString,playerContractUpdateDTO.getNumber()) == 0;
          if(!numberValid){
              throw new NotValidException(ValidationMessage.NUMBER_DUPLICATE_MESSAGE);
          }
          playerContract.setEnd(playerContractUpdateDTO.getEnd());
          playerContract.setDescription(playerContractUpdateDTO.getDescription());
          playerContract.setSalary(playerContractUpdateDTO.getSalary());
          playerContract.setNumber(playerContractUpdateDTO.getNumber());
          playerContractRepository.save(playerContract);
          return modelMapper.map(playerContract, PlayerContractResponse.class);
        }else {
            throw new ExistException(PlayerContractFailMessage.PLAYER_CONTRACT_DATE_END_);
        }
    }
}
