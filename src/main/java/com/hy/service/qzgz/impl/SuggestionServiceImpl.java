package com.hy.service.qzgz.impl;

import com.hy.dto.SuggestionDto;
import com.hy.mapper.ms.QzgzSuggestionMapper;
import com.hy.model.QzgzSuggestion;
import com.hy.service.qzgz.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "Suggestion")
public class SuggestionServiceImpl implements SuggestionService {
    @Autowired
    private QzgzSuggestionMapper suggestionMapper;

    @Override
    public boolean insertSuggestion(SuggestionDto suggestionDto) {
        QzgzSuggestion suggestion = new QzgzSuggestion(
                suggestionDto.getTitle(),
                suggestionDto.getContact(),
                suggestionDto.getDepartment(),
                suggestionDto.getContent(),
                suggestionDto.getCreater()
        );
        return suggestionMapper.insertSuggestion(suggestion);
    }

    @Override
    public List<SuggestionDto> getSuggestion(int pageNum) {

        return suggestionMapper.getSuggestion(pageNum);
    }

    @Override
    public boolean deleteSuggestion(int id){

        return suggestionMapper.deleteSuggestion(id);
    }

    @Override
    public boolean updateSuggestion(SuggestionDto suggestionDto){
        QzgzSuggestion suggestion = new QzgzSuggestion(
                suggestionDto.getTitle(),
                suggestionDto.getContact(),
                suggestionDto.getDepartment(),
                suggestionDto.getContent(),
                suggestionDto.getCreater()
        );
        return suggestionMapper.updateSuggestion(suggestion);
    }
    public int totalPageS(){

        return suggestionMapper.totalPageS();
    }
}

