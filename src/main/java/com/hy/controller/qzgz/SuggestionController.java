package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.SuggestionDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/getSuggestion")
    public ResultObj getSuggestion(){
        return  ResultObj.success(suggestionService.getSuggestion());

    }
    @PostMapping("/insertSuggestion")
    public ResultObj insertSuggestion(SuggestionDto suggestionDto){
        return suggestionService.insertSuggestion(suggestionDto)
                ? ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);

    }
    @PostMapping("/deleteSuggestion")
    public ResultObj deleteSuggestion(@RequestParam(required = true) int id){
        return suggestionService.deleteSuggestion(id)
                ?ResultObj.success():
                ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
    }
    @PostMapping("/updateSuggestion")
    public ResultObj updateSuggestion(SuggestionDto suggestionDto){
        return suggestionService.updateSuggestion(suggestionDto)
                ?ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }

};

