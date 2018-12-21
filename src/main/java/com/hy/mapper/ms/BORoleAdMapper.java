package com.hy.mapper.ms;

import com.hy.model.BORoleAd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/13 16:50
 * @Description:角色、AD员工号关系表增删查mapper
 */
@Repository
public interface BORoleAdMapper {
    List<BORoleAd> selectRoleAd(@Param("rid") int rid);
    Integer insertRoleAd(List<BORoleAd> boRoleAd);
    Integer deleteRoleAd(int[] array);
}
