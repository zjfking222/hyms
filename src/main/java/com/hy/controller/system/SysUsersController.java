package com.hy.controller.system;

import com.hy.common.ResultObj;
import com.hy.dto.SysUsersNewDto;
import com.hy.enums.ResultCode;
import com.hy.service.system.SysUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/system")
public class SysUsersController {
    @Autowired
    private SysUsersService sysUsersService;

    @PostMapping("/users/get")
    public ResultObj getUsers(){
        return ResultObj.success(sysUsersService.getUsers());
    }

    @PostMapping("users/getUid")

    public ResultObj getUsersByUid(int uid){
        return ResultObj.success(sysUsersService.getUsersByUid(uid));
    }

    @PostMapping("/users/add")
    public ResultObj addUsers(@RequestBody SysUsersNewDto sysUsersNewDto){
        try {
            sysUsersService.addUsers(sysUsersNewDto);
            return ResultObj.success();
        }catch (Exception e){
        return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        }
    }

    @PostMapping("/users/getByLike")
    public ResultObj getUsersByLike(@RequestParam(required = false) String lastname){
        return ResultObj.success(sysUsersService.getUsersByLike(lastname));
    }


    @PostMapping("/users/delete")
    public ResultObj deleteUsers(int id){
        return sysUsersService.deleteUsers(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("users/getAll")
    public ResultObj getAllUsers(int page, int pageSize, @RequestParam(required = false) String value,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) String dir) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", sysUsersService.getAllUsers(page, pageSize, value, sort, dir));
        map.put("total", sysUsersService.getTotalUsers(value));
        return ResultObj.success(map);
    }

    @PostMapping("users/search")
    public ResultObj getUserSearch(@RequestParam(required = false) String value){
        try {
            return ResultObj.success(sysUsersService.getUsersBySearch(value));
        }catch (Exception e){
            return ResultObj.error(ResultCode.ERROR_UNKNOWN);
        }
    }
}
