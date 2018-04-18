package com.hy.controller.ms;

import com.hy.common.ResultObj;
import com.hy.service.ms.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/getSuggestion")
    public ResultObj getSuggestion(){return ResultObj.success(suggestionService.getSuggestion());}
}
