package com.hy.controller.qzgz;


import com.hy.common.ResultObj;
import com.hy.dto.QzgzStudyDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/qzgz")
public class StudyController {

    @Autowired
    private StudyService studyService;

    //region 学习园地admin后台

    /**
     * 获取学习列表
     *
     * @param map 查询参数
     * @return
     */
    @RequestMapping(value = "/admin/study/get", method = RequestMethod.POST)
    public ResultObj getAdminStudyList(@RequestBody Map<String, Object> map) {
        if (!map.containsKey("page") || !map.containsKey("pageSize"))
            return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", studyService.getTotal((String)map.get("filters")));
        resultMap.put("data", studyService.getList((String)map.get("filters"), (int) map.get("page"), (int) map.get("pageSize")));
        return ResultObj.success(resultMap);
    }

    /**
     * 添加学习信息
     *
     * @param qzgzStudyDto 菜单对象
     * @return
     */
    @RequestMapping(value = "/admin/study/add", method = RequestMethod.POST)
    public ResultObj addAdminStudy(@RequestBody QzgzStudyDto qzgzStudyDto) {
        try {
            if (qzgzStudyDto.getTitle() == null || "".equals(qzgzStudyDto.getTitle()))
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            qzgzStudyDto = studyService.add(qzgzStudyDto);
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED, e.getMessage());
        }
        return ResultObj.success(qzgzStudyDto);
    }

    /**
     * 更新学习信息
     *
     * @param qzgzStudyDto 学习信息对象
     * @return
     */
    @RequestMapping(value = "/admin/study/update", method = RequestMethod.POST)
    public ResultObj updateAdminStudy(@RequestBody QzgzStudyDto qzgzStudyDto) {
        try {
            if (qzgzStudyDto.getId() == null || qzgzStudyDto.getTitle() == null || "".equals(qzgzStudyDto.getTitle()))
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            int count = studyService.update(qzgzStudyDto);
            if (count == 0)
                return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED, e.getMessage());
        }
        return ResultObj.success(qzgzStudyDto);
    }

    /**
     * 删除菜单
     *
     * @param qzgzStudyDto
     * @return
     */
    @RequestMapping(value = "/admin/study/delete", method = RequestMethod.POST)
    public ResultObj deleteAdminStudy(@RequestBody QzgzStudyDto qzgzStudyDto) {
        try {
            if (qzgzStudyDto.getId() == null)
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            int id = studyService.deleteById(qzgzStudyDto.getId());
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_DELETE_FAILED, e.getMessage());
        }
        return ResultObj.success(qzgzStudyDto);
    }
    //endregion

    /**
     * 获取学习列表
     *
     * @param map 查询参数
     * @return
     */
    @RequestMapping(value = "/web/study/get", method = RequestMethod.POST)
    public ResultObj getWebStudyList(@RequestBody Map<String, Object> map) {
        if (!map.containsKey("page") || !map.containsKey("pageSize"))
            return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", studyService.getTotal(null));
        List<QzgzStudyDto> list = studyService.getList(null, (int) map.get("page"), (int) map.get("pageSize"));
        for (QzgzStudyDto dto : list) {
            dto.setContent("");
        }
        resultMap.put("data", list);
        return ResultObj.success(resultMap);
    }

    /**
     * 获取学习信息
     *
     * @param id 查询主键
     * @return
     */
    @RequestMapping(value = "/web/study/get", method = RequestMethod.GET)
    public ResultObj getWebStudy(@RequestParam("id") int id) {
        QzgzStudyDto dto = studyService.getStudy(id);
        return ResultObj.success(dto);
    }
}

