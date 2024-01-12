package com.hungnln.vleague.controller;

import com.hungnln.vleague.DTO.NewsCreateDTO;
import com.hungnln.vleague.DTO.NewsUpdateDTO;
import com.hungnln.vleague.constant.news.NewsSuccessMessage;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.helper.NewsSpecification;
import com.hungnln.vleague.helper.SearchCriteria;
import com.hungnln.vleague.repository.NewsRepository;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.NewsResponse;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import com.hungnln.vleague.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/news")
@Tag(name = "news", description = "news api")
public class NewsController {
    public static Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    NewsService newsService;

    @GetMapping("")
    @Operation(summary ="Get news list", description = "Get news list")
    ResponseEntity<ResponseDTO<ResponseWithTotalPage<NewsResponse>>> getAllNews(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") List<UUID> playerIds,
            @RequestParam(defaultValue = "") List<UUID> clubIds,
            @RequestParam(defaultValue = "") String title
    ){
        ResponseDTO<ResponseWithTotalPage<NewsResponse>> responseDTO = new ResponseDTO<>();

        ResponseWithTotalPage<NewsResponse> list = newsService.getAllNews(pageNo,pageSize,playerIds,clubIds,title);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(list);
        responseDTO.setMessage(NewsSuccessMessage.GET_NEWS_SUCCESSFULL);
        return ResponseEntity.ok().body(responseDTO);
    }


    @PostMapping("")
    @Operation(summary ="Add news", description = "Add news")
    ResponseEntity<ResponseDTO<NewsResponse>> addNews(@RequestBody @Valid NewsCreateDTO dto) throws BindException {
        ResponseDTO<NewsResponse> responseDTO = new ResponseDTO<>();
        NewsResponse newsResponse = newsService.addNews(dto);
        responseDTO.setData(newsResponse);
        responseDTO.setMessage(NewsSuccessMessage.CREATE_NEWS_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PutMapping("/{id}")
    @Operation(summary ="Update news", description = "Update news")
    ResponseEntity<ResponseDTO<NewsResponse>> updateNews(@PathVariable UUID id,@RequestBody @Valid NewsUpdateDTO dto) throws BindException {
        ResponseDTO<NewsResponse> responseDTO = new ResponseDTO<>();
        NewsResponse newsResponse = newsService.updateNews(id,dto);
        responseDTO.setData(newsResponse);
        responseDTO.setMessage(NewsSuccessMessage.UPDATE_NEWS_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    @Operation(summary ="Get news by id", description = "Get news by id")
    ResponseEntity<ResponseDTO<NewsResponse>> findNews(@PathVariable UUID id){
        ResponseDTO<NewsResponse> responseDTO = new ResponseDTO<>();
        NewsResponse newsResponse = newsService.findNewsById(id);
        responseDTO.setData(newsResponse);
        responseDTO.setMessage(NewsSuccessMessage.GET_NEWS_SUCCESSFULL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary ="Delete news by id", description = "Delete news by id")
    ResponseEntity<ResponseDTO<NewsResponse>> deleteNews(@PathVariable UUID id){
        ResponseDTO<NewsResponse> responseDTO = new ResponseDTO<>();
        String msg = newsService.deleteNewsById(id);
        responseDTO.setMessage(msg);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
