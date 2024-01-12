package com.hungnln.vleague.response;

import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private UUID id;
    private String title;
    private String thumbnailImageURL;
    private String content;
    private Date createdAt;
//    private String abc;
//    private List<Club> xyz;

//    private List<UUID> playerIds;
//    private List<UUID> clubIds;
    private Collection<Player> players;
    private Collection<Club> clubs;
}
