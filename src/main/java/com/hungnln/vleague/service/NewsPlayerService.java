package com.hungnln.vleague.service;

import com.hungnln.vleague.repository.NewsPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsPlayerService {
    @Autowired
    private NewsPlayerRepository newsPlayerRepository;
    private final ModelMapper modelMapper;

}
