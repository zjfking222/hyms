package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.QzgzNoticeDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qzgz")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    //region 通知公告admin后台

    /**
     * 获取通知公告列表
     *
     * @param map 查询参数
     * @return
     */
    @RequestMapping(value = "/admin/notice/get", method = RequestMethod.POST)
    public ResultObj getAdminnoticeList(@RequestBody Map<String, Object> map) {
        if (!map.containsKey("page") || !map.containsKey("pageSize"))
            return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", noticeService.getTotal((String)map.get("filters")));
        resultMap.put("data", noticeService.getList((String)map.get("filters"), (int) map.get("page"), (int) map.get("pageSize")));
        return ResultObj.success(resultMap);
    }

    /**
     * 添加通知公告信息
     *
     * @param QzgzNoticeDto 菜单对象
     * @return
     */
    @RequestMapping(value = "/admin/notice/add", method = RequestMethod.POST)
    public ResultObj addAdminnotice(@RequestBody QzgzNoticeDto QzgzNoticeDto) {
        try {
            if (QzgzNoticeDto.getTitle() == null || "".equals(QzgzNoticeDto.getTitle()))
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            QzgzNoticeDto = noticeService.add(QzgzNoticeDto);
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED, e.getMessage());
        }
        return ResultObj.success(QzgzNoticeDto);
    }

    /**
     * 更新通知公告
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/admin/notice/update", method = RequestMethod.POST)
    public ResultObj updateAdminnotice(@RequestBody QzgzNoticeDto qzgzNoticeDto) {
        try {
            if (qzgzNoticeDto.getId() == null || qzgzNoticeDto.getTitle() == null || "".equals(qzgzNoticeDto.getTitle()))
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            int count = noticeService.update(qzgzNoticeDto);
            if (count == 0)
                return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
            else
                qzgzNoticeDto = noticeService.getNotice(qzgzNoticeDto.getId());
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED, e.getMessage());
        }
        return ResultObj.success(qzgzNoticeDto);
    }

    /**
     * 删除通知公告
     *
     * @param QzgzNoticeDto
     * @return
     */
    @RequestMapping(value = "/admin/notice/delete", method = RequestMethod.POST)
    public ResultObj deleteAdminnotice(@RequestBody QzgzNoticeDto QzgzNoticeDto) {
        try {
            if (QzgzNoticeDto.getId() == null)
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            noticeService.deleteById(QzgzNoticeDto.getId());
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_DELETE_FAILED, e.getMessage());
        }
        return ResultObj.success(QzgzNoticeDto);
    }
    //endregion

    /**
     * 获取生效的通知公告
     *
     * @param map 查询参数
     * @return
     */
    @RequestMapping(value = "/web/notice/get", method = RequestMethod.POST)
    public ResultObj getWebnoticeList(@RequestBody Map<String, Object> map) {
        if (!map.containsKey("page") || !map.containsKey("pageSize"))
            return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", noticeService.getTotal(null));
        List<QzgzNoticeDto> list = noticeService.getEffecttList((int) map.get("page"), (int) map.get("pageSize"));
        for (QzgzNoticeDto dto : list) {
            dto.setContent(null);
            dto.setCreated(null);
            dto.setModifiername(null);
        }
        resultMap.put("data", list);
        return ResultObj.success(resultMap);
    }

    /**
     * 获取通知公告
     *
     * @param id 查询主键
     * @return
     */
    @RequestMapping(value = "/web/notice/get", method = RequestMethod.GET)
    public ResultObj getWebnotice(@RequestParam("id") int id) {
        QzgzNoticeDto dto = noticeService.getNotice(id);
        dto.setCreated(null);
        dto.setModifiername(null);
        return ResultObj.success(dto);
    }
}

