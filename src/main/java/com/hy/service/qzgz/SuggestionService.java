package com.hy.service.qzgz;

import com.hy.dto.SuggestionDto;
import org.apache.ibatis.annotations.Param;

import java.sql.Blob;
import java.util.List;

public interface SuggestionService {
    Integer insertSuggestion(String contactInfo, String department, String content, String creater);
}
