package com.hy.service.qzgz;

import com.hy.dto.QzgzStudyDto;

import java.util.List;

public interface StudyService {

    /**
     * 分页获取学习通知列表
     *
     * @param pageNum  当前页
     * @param pageSize 当前页面展示数目
     * @return 菜单列表
     */
    List<QzgzStudyDto> getList(int pageNum, int pageSize);

    /**
     * 根据主键获取学习通知信息
     *
     * @param id  学习通知主键
     * @return 学习通知信息
     */
    QzgzStudyDto getStudy(int id );

    /**
     * 获取菜单数量
     * @return 菜单列表
     */
    int getTotal();

    /**
     * 添加菜单
     * @param dto
     * @return
     */
    QzgzStudyDto add(QzgzStudyDto dto);

    /**
     * 更新菜单
     * @param dto
     * @return
     */
    int update(QzgzStudyDto dto);

    /**
     * 删除菜单
     * @param id 需要删除菜单的id
     * @return 删除数量
     */
    int  deleteById(int id);
}
