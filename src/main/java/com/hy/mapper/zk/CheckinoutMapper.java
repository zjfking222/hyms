package com.hy.mapper.zk;

import com.hy.model.Checkinout;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
public interface CheckinoutMapper {
    List<Checkinout> findByUserId(@Param("userid") String userid, @Param("stime")Date stime, @Param("etime") Date etime);
}