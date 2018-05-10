package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.SuggestionDto;
import com.hy.dto.SuggestionWithTotalPageDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qzgz")
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/web/handInSuggestion")
    public ResultObj handInSuggestion(SuggestionDto suggestionDto)
    {
        return suggestionService.addSuggestion(suggestionDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/getSuggestion")
    public ResultObj getSuggestion(int page, int number,
                                   @RequestParam(required = false) String state)
    {
        SuggestionWithTotalPageDto suggestionWithTotalPageDto = suggestionService.getSuggestion(page,number,state);
        return suggestionWithTotalPageDto != null ?
                ResultObj.success(suggestionWithTotalPageDto):
                ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
    }
    @PostMapping("/admin/setStateOfSuggestion")
    public ResultObj setStateOfSuggestion(int id, String state)
    {
        return suggestionService.setStateOfSuggestion(id,state)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}

