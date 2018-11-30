package com.hy.mapper.ms;

import com.hy.dto.AdDepartmentDto;
import com.hy.model.AdDepartment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface AdDepartmentMapper {
    int insertAdDepartment(List<AdDepartment> adDepartment);
    List<AdDepartment> selectAdDepartment(@Param("date")String date, @Param("time") String time);
    List<AdDepartment> getTime(@Param("date") String date);
    List<AdDepartment> getChangeDep(@Param("date")String date, @Param("time") String time);
    Integer updateOperator(@Param ("loginId") int LoginId, @Param("id") String Id);
}
