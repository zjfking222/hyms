package com.hy.mapper.ms;

import com.hy.model.AdDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdDepartmentMapper {
    int insertAdDepartment(List<AdDepartment> adDepartment);
}
