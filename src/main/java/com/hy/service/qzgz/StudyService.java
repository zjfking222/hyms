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
     * 获取学习通知数量
     * @return 学习通知列表
     */
    int getTotal();

    /**
     * 添加学习通知
     * @param dto
     * @return
     */
    QzgzStudyDto add(QzgzStudyDto dto);

    /**
     * 更新学习通知
     * @param dto
     * @return
     */
    int update(QzgzStudyDto dto);

    /**
     * 删除学习
     * @param id 需要删除学习通知的id
     * @return 删除数量
     */
    int  deleteById(int id);
}
