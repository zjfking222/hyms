package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.CanteenService;
import com.hy.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/qzgz")
public class CanteenController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${upload.location}")
    private String location;
    private final String UPLOAD_URL;

    public CanteenController() {
        UPLOAD_URL = "/qzgz/upload/canteen_excel/";
    }

    @Autowired
    private CanteenService canteenService;

    @PostMapping("/web/getCanteenToday")
    public ResultObj getCanteenToday(int meal) {
        return ResultObj.success(canteenService.getCanteenToday(meal));
    }

    @PostMapping("/web/setCanteenZan")
    public ResultObj setCanteenZan(int zan, int id) {
        return canteenService.updateCanteenZan(zan, id) ? ResultObj.success() : ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/admin/getCanteen")
    public ResultObj getCanteen(int page, int pageSize, @RequestParam(required = false) String value, @RequestParam(required = false) String date,
                                @RequestParam(required = false) String meal,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String dir) throws ParseException {
        HashMap<String, Object> map = new HashMap<>();
        String time;
        Integer classify;
        if (date == null || date.equals("")) {
            time = sdf.format(new Date());
        } else {
            time = date;
        }
        if (meal == null || meal.equals("")) {
            classify = null;
        } else {
            classify = Integer.parseInt(meal);
        }

        map.put("data", canteenService.getCanteen(page, pageSize, value, time, classify, sort, dir));
        map.put("total", canteenService.getCanteenTotal(value, time, classify));
        return ResultObj.success(map);
    }

    @PostMapping("/admin/deleteCanteen")
    public ResultObj deleteCanteen(int id) {
        return canteenService.deleteCanteen(id) ? ResultObj.success() : ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/admin/addCanteen")
    public ResultObj insertCanteen(@RequestParam("file") MultipartFile[] files) {
        String reStr = FileUtil.upload(files, UPLOAD_URL, location);
        return reStr != null ? ResultObj.success(reStr) : ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
    }

    @PostMapping("/admin/submit")
    public ResultObj submitCanteen(String filename) {
        int check = canteenService.insertCanteenList(filename);
        switch (check) {
            case 0:
                return ResultObj.error(ResultCode.ERROR_DATA_FAILED);
            case -1:
                return ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
            case -2:
                return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
            default:
                return ResultObj.success();
        }
    }
    //    @PostMapping("/web/getCanteen")
//    public ResultObj getCanteen(int page, int number, @RequestParam(required = false) String state)
//    {
//        CanteenWithTotalPageDto canteenWithTotalPageDto =
//                canteenService.getCanteen(page, number, state);
//
//        return canteenWithTotalPageDto == null ?
//                ResultObj.error(ResultCode.ERROR_NO_RESOURCE):
//                ResultObj.success(canteenWithTotalPageDto);
//    }
//    @PostMapping("/admin/addCanteen")
//    public ResultObj addCanteen(String name, String type)
//    {
//        return canteenService.addCanteen(name, type) ?
//                ResultObj.success():
//                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
//    }
//    @PostMapping("/admin/updateCanteen")
//    public ResultObj updateCanteen(String name, String type, int id)
//    {
//        return canteenService.updateCanteen(name, type, id) ?
//                ResultObj.success():
//                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
//    }
//    @PostMapping("/admin/updateCanteenState")
//    public ResultObj updateCanteenState(String state, int id)
//    {
//        return canteenService.updateCanteenState(state, id) ?
//                ResultObj.success():
//                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
//    }
//    @PostMapping("/admin/getCanteenByName")
//    public ResultObj getCanteenByName(String name, @RequestParam(required = false) String state)
//    {
//        return ResultObj.success(canteenService.getCanteenBySearchName(name, state));
//    }
}
