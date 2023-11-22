package com.hungnln.vleague.service;


import com.hungnln.vleague.DTO.PlayerCreateDTO;
import com.hungnln.vleague.DTO.PlayerUpdateDTO;
import com.hungnln.vleague.constant.player.PlayerFailMessage;
import com.hungnln.vleague.constant.player.PlayerSuccessMessage;
import com.hungnln.vleague.entity.Player;
import com.hungnln.vleague.exceptions.ExistException;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.repository.PlayerRepository;
import com.hungnln.vleague.response.PlayerResponse;
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
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public List<PlayerResponse> getAllPlayers(int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Player> pageResult = playerRepository.findAll(pageable);
        List<PlayerResponse> playerList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (Player player :
                    pageResult.getContent()) {
                PlayerResponse playerResponse = modelMapper.map(player, PlayerResponse.class);
                playerList.add(playerResponse);
            }

        }else
            throw new ListEmptyException(PlayerFailMessage.LIST_PLAYER_IS_EMPTY);
        return playerList;
    }

    public PlayerResponse addPlayer(PlayerCreateDTO playerCreateDTO) {
        Optional<Player> player = playerRepository.findPlayerByName(playerCreateDTO.getName());
        if(player.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            Player tmp = Player.builder()
                    .id(String.valueOf(uuid))
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
    public PlayerResponse getPlayerById(String id){
        Player player = playerRepository.findPlayerById(id).orElseThrow(()-> new NotFoundException(PlayerFailMessage.PLAYER_NOT_FOUND));
        return modelMapper.map(player,PlayerResponse.class);
    }
    public String deletePlayer(String id){
        boolean exists = playerRepository.existsById(id);
        if(exists){
            playerRepository.deleteById(id);
            return PlayerSuccessMessage.REMOVE_PLAYER_SUCCESSFULL;
        }else{
            return PlayerFailMessage.DELETE_PLAYER_FAIL;
        }

    }
    public PlayerResponse updatePlayer(String id, PlayerUpdateDTO playerUpdateDTO){
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
