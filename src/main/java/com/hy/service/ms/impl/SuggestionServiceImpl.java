package com.hy.service.ms.impl;

import com.hy.dto.SuggestionDto;
import com.hy.mapper.ms.SuggestionMapper;
import com.hy.service.ms.SuggestionService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    @Autowired
    private SuggestionMapper suggestionMapper;
    private final int GROUNDING = 1;

    @Override
    public List<SuggestionDto> getSuggestion() {
        return suggestionMapper.selectSuggestion(GROUNDING);
    }
}
