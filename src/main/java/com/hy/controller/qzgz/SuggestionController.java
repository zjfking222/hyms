package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.SuggestionDto;
import com.hy.service.qzgz.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/getSuggestion")
    public ResultObj getSuggestion(SuggestionDto suggestionDto){
        return null;
    }
}
