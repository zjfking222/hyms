package com.hy.mapper.ms;

import com.hy.model.MmMeeting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmMeetingMapper {
    Integer insertMmMeeting(MmMeeting mmMeeting);
    Integer updateMmMeeting(MmMeeting mmMeeting);
    Integer deleteMmMeeting(@Param("id") int id);
    List<MmMeeting> selectMmMeeting(@Param("value") String value, @Param("sort") String sort,
                                    @Param("dir") String dir);
    Integer selectMmMeetingTotal(@Param("value") String value);

    MmMeeting selectMeetingByRid(@Param("rid")int rid);
    MmMeeting selectMeetingById(@Param("id")int id);
}
