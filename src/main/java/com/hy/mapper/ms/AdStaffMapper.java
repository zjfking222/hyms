package com.hy.mapper.ms;

import com.hy.model.AdStaff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdStaffMapper {
   int insertStaff(List<AdStaff> adStaff);
   List<AdStaff> selectAdStaff(@Param("page") int page,@Param("limit") int limit,@Param("date")String date,@Param("time")String time);
   Integer getCount(@Param("date")String date,@Param("time")String time);
   Integer updateOperator(@Param ("loginId") String LoginId, @Param("id") String Id);
}
