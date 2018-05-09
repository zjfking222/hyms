package com.hy.dto;

import java.util.List;

public class SuggestionWithTotalPageDto {
    private List<SuggestionDto> suggestions;
    private int totalPage;

    public SuggestionWithTotalPageDto(List<SuggestionDto> suggestions, int totalPage) {
        this.suggestions = suggestions;
        this.totalPage = totalPage;
    }

    public List<SuggestionDto> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionDto> suggestions) {
        this.suggestions = suggestions;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
