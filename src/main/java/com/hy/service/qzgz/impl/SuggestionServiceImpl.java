package com.hy.service.qzgz.impl;

import com.hy.common.SecurityUtil;
import com.hy.dto.SuggestionDto;
import com.hy.dto.SuggestionWithTotalPageDto;
import com.hy.mapper.ms.QzgzSuggestionMapper;
import com.hy.model.QzgzSuggestion;
import com.hy.service.qzgz.SuggestionService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    private QzgzSuggestionMapper suggestionMapper;

    @Override
    public boolean addSuggestion(SuggestionDto suggestionDto) {
        QzgzSuggestion suggestion = DTOUtil.populate(suggestionDto,QzgzSuggestion.class);
        suggestion.setCreater(-1);
        suggestion.setModifier(-1);
        return suggestionMapper.insertSuggestion(suggestion) == 1;
    }

    @Override
    public SuggestionWithTotalPageDto getSuggestion(int page, int number, String state) {
        int startRow = (page - 1) * number;
        List<SuggestionDto> suggestions =
                DTOUtil.populateList(suggestionMapper.selectSuggestion(state,startRow,number),
                        new ArrayList<SuggestionDto>(),SuggestionDto.class);
        return suggestions==null?
                null: new SuggestionWithTotalPageDto(suggestions,
                getTotalPageOfSuggestion(number,state));
    }

    @Override
    public int getTotalPageOfSuggestion(int number, String state) {
        int countOfSuggest = suggestionMapper.selectCountOfSuggestion(state);
        double totalPageD = (double)countOfSuggest/(double)number;
        return (int)Math.ceil(totalPageD);
    }

    @Override
    public boolean setStateOfSuggestion(int id, String state) {
        QzgzSuggestion suggestion = new QzgzSuggestion();
        suggestion.setId(id);
        suggestion.setState(state);
        suggestion.setModifier(SecurityUtil.getUserId());
        return suggestionMapper.updateStateOfSuggestion(suggestion) == 1;
    }
}

