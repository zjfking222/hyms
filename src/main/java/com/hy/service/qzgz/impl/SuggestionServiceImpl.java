package com.hy.service.qzgz.impl;

import com.hy.service.qzgz.SuggestionService;
import org.springframework.stereotype.Service;

@Service(value = "Suggestion")
public class SuggestionServiceImpl implements SuggestionService {
    @Override
    public Integer insertSuggestion(String contactInfo, String department, String content, String creater) {
        return null;
    }
}
