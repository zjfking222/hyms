package com.hy.mapper.ms;

import com.hy.model.MmAgenda;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmAgendaMapper {
    Integer insertMmAgenda(MmAgenda mmAgenda);
    Integer updateMmAgenda(MmAgenda mmAgenda);
    Integer deleteMmAgenda(@Param("id") int id);
    List<MmAgenda> selectMmAgenda(@Param("mid") int mid,
                                  @Param("sort") String sort,
                                  @Param("dir") String dir);
    Integer selectMmAgendaTotal(@Param("mid")int mid);

    List<MmAgenda> selectMmAgendaByRid(@Param("rid")int rid);
}
