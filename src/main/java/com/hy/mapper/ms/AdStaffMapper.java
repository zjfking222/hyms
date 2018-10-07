package com.hy.mapper.ms;

import com.hy.model.AdStaff;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdStaffMapper {
   int insertStaff(List<AdStaff> adStaff);
}
