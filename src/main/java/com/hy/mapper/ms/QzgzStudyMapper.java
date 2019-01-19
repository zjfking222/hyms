package com.hy.mapper.ms;

import com.hy.model.QzgzStudy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzStudyMapper extends BaseMapper<Integer, QzgzStudy> {
    List<QzgzStudy> selectStudys(@Param("filters") String filters);
    int  selectStudysTotal(@Param("filters") String filters);
}