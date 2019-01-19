package com.hy.service.qzgz;

import com.hy.dto.QzgzNoticeDto;

import java.util.List;

public interface NoticeService {

    /**
     * 分页获取通知公告列表
     *
     * @param pageNum  当前页
     * @param pageSize 当前页面展示数目
     * @return 菜单列表
     */
    List<QzgzNoticeDto> getList(String filters, int pageNum, int pageSize);

    /**
     * 分页获取已生效通知公告列表
     *
     * @param pageNum  当前页
     * @param pageSize 当前页面展示数目
     * @return 菜单列表
     */
    List<QzgzNoticeDto> getEffecttList(int pageNum, int pageSize);

    /**
     * 根据主键获取通知公告信息
     *
     * @param id  学习通知主键
     * @return 学习通知信息
     */
    QzgzNoticeDto getNotice(int id );

    /**
     * 获取通知公告数量
     * @return 通知公告列表
     */
    int getTotal(String filters);

    /**
     * 添加通知公告
     * @param dto 通知公告Dto对象
     * @return
     */
    QzgzNoticeDto add(QzgzNoticeDto dto);

    /**
     * 更新通知公告
     * @param dto  通知公告Dto对象
     * @return
     */
    int update(QzgzNoticeDto dto);


    /**
     * 删除通知公告
     * @param id 需要删除通知公告的id
     * @return 删除数量
     */
    int  deleteById(int id);
}



