package com.hungnln.vleague.service;


import com.hungnln.vleague.DTO.PlayerCreateDTO;
import com.hungnln.vleague.DTO.PlayerUpdateDTO;
import com.hungnln.vleague.constant.player.PlayerFailMessage;
import com.hungnln.vleague.constant.player.PlayerSuccessMessage;
import com.hungnln.vleague.entity.Player;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.helper.PlayerSpecification;
import com.hungnln.vleague.helper.SearchCriteria;
import com.hungnln.vleague.helper.SearchOperation;
import com.hungnln.vleague.repository.PlayerRepository;
import com.hungnln.vleague.response.MatchResponse;
import com.hungnln.vleague.response.PaginationResponse;
import com.hungnln.vleague.response.PlayerResponse;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public ResponseWithTotalPage<PlayerResponse> getAllPlayers(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Player> pageResult = playerRepository.findAll(pageable);
        ResponseWithTotalPage<PlayerResponse> response = new ResponseWithTotalPage<>();
        List<PlayerResponse> playerList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Player player :
                    pageResult.getContent()) {
                PlayerResponse playerResponse = modelMapper.map(player, PlayerResponse.class);
                playerList.add(playerResponse);
            }
        }
        response.setData(playerList);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageIndex(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalCount((int) pageResult.getTotalElements())
                .totalPage(pageResult.getTotalPages())
                .build();
        response.setPagination(paginationResponse);
        return response;
    }
    public List<Player> getAllPlayersByPlayerIds(List<UUID> playerIds){
        List<Specification<Player>> listSpec = new ArrayList<>();
        for(UUID id : playerIds){
            PlayerSpecification specification = new PlayerSpecification(new SearchCriteria("id", SearchOperation.EQUALITY,id));
            listSpec.add(specification);
        }
        List<Player> list = playerRepository.findAll(Specification.allOf(listSpec));
        return list;
    }

    public PlayerResponse addPlayer(PlayerCreateDTO playerCreateDTO) {
        Optional<Player> player = playerRepository.findPlayerByName(playerCreateDTO.getName());
        if(player.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            Player tmp = Player.builder()
                    .id(uuid)
                    .dateOfBirth(playerCreateDTO.getDateOfBirth())
                    .imageURL(playerCreateDTO.getImageURL())
                    .name(playerCreateDTO.getName())
                    .heightCm(playerCreateDTO.getHeightCm())
                    .weightKg(playerCreateDTO.getWeightKg())
                    .build();
            playerRepository.save(tmp);
            return modelMapper.map(tmp, PlayerResponse.class);
        }else {
            throw new ExistException(PlayerFailMessage.PLAYER_EXIST);
        }
    }
    public PlayerResponse getPlayerById(UUID id){
        Player player = playerRepository.findPlayerById(id).orElseThrow(()-> new NotFoundException(PlayerFailMessage.PLAYER_NOT_FOUND));
        return modelMapper.map(player,PlayerResponse.class);
    }
    public String deletePlayer(UUID id){
        boolean exists = playerRepository.existsById(id);
        if(exists){
            playerRepository.deleteById(id);
            return PlayerSuccessMessage.REMOVE_PLAYER_SUCCESSFULL;
        }else{
            return PlayerFailMessage.DELETE_PLAYER_FAIL;
        }

    }
    public PlayerResponse updatePlayer(UUID id, PlayerUpdateDTO playerUpdateDTO){
        Player player = playerRepository.findPlayerById(id).orElseThrow(()-> new NotFoundException(PlayerFailMessage.PLAYER_NOT_FOUND));
            player.setName(playerUpdateDTO.getName());
            player.setWeightKg(playerUpdateDTO.getWeightKg());
            player.setHeightCm(playerUpdateDTO.getHeightCm());
            player.setImageURL(playerUpdateDTO.getImageURL());
            player.setDateOfBirth(playerUpdateDTO.getDateOfBirth());
            playerRepository.save(player);
            return  modelMapper.map(player,PlayerResponse.class);
    }

}
