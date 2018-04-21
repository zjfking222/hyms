package com.hy.service.ms.impl;

import com.hy.service.ms.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "Suggestion")
public class SuggestionServiceImpl implements SuggestionService {
    @Override
    public Integer insertSuggestion(String contactInfo, String department, String content, String creater) {
        return null;
    }
}
