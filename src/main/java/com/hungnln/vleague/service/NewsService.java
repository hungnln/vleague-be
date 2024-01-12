package com.hungnln.vleague.service;

import com.hungnln.vleague.DTO.NewsCreateDTO;
import com.hungnln.vleague.DTO.NewsUpdateDTO;
import com.hungnln.vleague.constant.club.ClubFailMessage;
import com.hungnln.vleague.constant.news.NewsFailMessage;
import com.hungnln.vleague.constant.news.NewsSuccessMessage;
import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.News;
import com.hungnln.vleague.entity.Player;
import com.hungnln.vleague.exceptions.ListEmptyException;
import com.hungnln.vleague.exceptions.NotFoundException;
import com.hungnln.vleague.helper.NewsSpecification;
import com.hungnln.vleague.helper.SearchCriteria;
import com.hungnln.vleague.helper.SearchOperation;
import com.hungnln.vleague.repository.*;
import com.hungnln.vleague.response.NewsResponse;
import com.hungnln.vleague.response.PaginationResponse;
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

import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsPlayerRepository newsPlayerRepository;
    @Autowired
    private NewsClubRepository newsClubRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ClubRepository clubRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ClubService clubService;

    public ResponseWithTotalPage<NewsResponse> getAllNews(int pageNo, int pageSize, List<UUID> playerIds, List<UUID> clubIds, String title){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));

        List<Specification<News>> specificationList = new ArrayList<>();
        if (playerIds != null && !playerIds.isEmpty()) {
            List<Player> playerList = playerService.getAllPlayersByPlayerIds(playerIds);
            for (Player player : playerList) {
                NewsSpecification specPlayerIds = new NewsSpecification(new SearchCriteria("players", SearchOperation.ISMEMBER, player));
                specificationList.add(specPlayerIds);
            }
        }
        if (clubIds != null && !clubIds.isEmpty()) {
            List<Club> clubList = clubService.getAllClubsByClubIds(clubIds);
            for (Club club : clubList) {
                NewsSpecification specClubIds = new NewsSpecification(new SearchCriteria("clubs", SearchOperation.ISMEMBER, club));
                specificationList.add(specClubIds);
            }
        }
        if (title != null && !title.isEmpty()) {
            NewsSpecification specTitle = new NewsSpecification(new SearchCriteria("title",SearchOperation.CONTAINS,title));
            specificationList.add(specTitle);
        }
        Page<News> pageResult = newsRepository.findAll(Specification.allOf(specificationList),pageable);
        ResponseWithTotalPage<NewsResponse> response = new ResponseWithTotalPage<>();
        List<NewsResponse> newsList = new ArrayList<>();
        if(pageResult.hasContent()) {
            for (News news :
                    pageResult.getContent()) {
                NewsResponse newsResponse = modelMapper.map(news, NewsResponse.class);
                newsList.add(newsResponse);
            }
            response.setData(newsList);
            PaginationResponse paginationResponse = PaginationResponse.builder()
                    .pageIndex(pageResult.getNumber())
                    .pageSize(pageResult.getSize())
                    .totalCount((int) pageResult.getTotalElements())
                    .totalPage(pageResult.getTotalPages())
                    .build();
            response.setPagination(paginationResponse);
        }else{
            throw new ListEmptyException(ClubFailMessage.LIST_CLUB_IS_EMPTY);
        }
        return response;
    }
    public NewsResponse addNews(NewsCreateDTO newsCreateDTO){
        UUID uuid = UUID.randomUUID();
        List<Club> clubList = clubRepository.findAllById(newsCreateDTO.getClubIds());
        List<Player> playerList =playerRepository.findAllById(newsCreateDTO.getPlayerIds());
        Date currentDate = new Date();
        News news =News.builder()
                .id(uuid)
                .clubs(clubList)
                .players(playerList)
                .title(newsCreateDTO.getTitle())
                .content(newsCreateDTO.getContent())
                .thumbnailImageURL(newsCreateDTO.getThumbnailImageURL())
                .createdAt(currentDate)
                .build();
        newsRepository.save(news);
        return modelMapper.map(news,NewsResponse.class);
    }
    public NewsResponse updateNews(UUID newsId,NewsUpdateDTO newsUpdateDTO){
        News news = newsRepository.findNewsById(newsId).orElseThrow(()->new NotFoundException(NewsFailMessage.NEWS_NOT_FOUND));
        List<Club> clubList = clubRepository.findAllById(newsUpdateDTO.getClubIds());
        List<Player> playerList =playerRepository.findAllById(newsUpdateDTO.getPlayerIds());
        news.setClubs(clubList);
        news.setPlayers(playerList);
        news.setContent(newsUpdateDTO.getContent());
        news.setTitle(newsUpdateDTO.getTitle());
        news.setThumbnailImageURL(newsUpdateDTO.getThumbnailImageURL());
        newsRepository.save(news);
        return modelMapper.map(news,NewsResponse.class);
    }
    public NewsResponse findNewsById(UUID newsId){
        News news = newsRepository.findNewsById(newsId).orElseThrow(()->new NotFoundException(NewsFailMessage.NEWS_NOT_FOUND));
        return modelMapper.map(news,NewsResponse.class);
    }
    public String deleteNewsById(UUID newsId){
        boolean exist = newsRepository.existsById(newsId);
        if (exist){
            newsRepository.deleteById(newsId);
            return NewsSuccessMessage.DELETE_NEWS_SUCCESSFULL;
        }else {
            return NewsFailMessage.DELETE_NEWS_FAIL;
        }
    }
}
