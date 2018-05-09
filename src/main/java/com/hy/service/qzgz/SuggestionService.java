package com.hy.service.qzgz;

import com.hy.dto.SuggestionDto;
import com.hy.dto.SuggestionWithTotalPageDto;

public interface SuggestionService {
    boolean addSuggestion(SuggestionDto suggestionDto);
    SuggestionWithTotalPageDto getSuggestion(int page, int number, String state);
    int getTotalPageOfSuggestion(int number, String state);
    boolean setStateOfSuggestion(int id, String state);
}

