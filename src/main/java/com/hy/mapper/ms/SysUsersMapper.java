package com.hy.mapper.ms;

import com.hy.model.SysUsers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUsersMapper extends BaseMapper<Integer,SysUsers> {

    List<SysUsers> selectUsers();
    List<SysUsers> selectByUid(@Param("oauserid")int oauserid);
    List<SysUsers> selectByLoginid(@Param("oaloginid")String oauserid);
    List<SysUsers> selectUsersByLike(@Param("name")String name);
    List<SysUsers> selectAllUsers(@Param("filters")String filters, @Param("value") String value, @Param("sort") String sort,
                                  @Param("dir") String dir);
    Integer selectTotalUsers(@Param("filters")String filters, @Param("value")String value);
    SysUsers selectByEmpnum(@Param("employeenumber")String employeenumber);

}