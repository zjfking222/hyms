package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.QzgzNewsDto;
import com.hy.enums.ResultCode;
import com.hy.model.QzgzNews;
import com.hy.service.qzgz.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 10:28
 * @Description:衢州新闻controller
 */
@RestController
@RequestMapping("/qzgz")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/admin/getNews")
    //查询新闻并分页
    public ResultObj getNews(@RequestParam(required = false) String value, int page, int pageSize,
                             @RequestParam(required = false) String sort,
                             @RequestParam(required = false) String dir, String code){
        Map<String, Object> map = new HashMap<>();
        map.put("data", newsService.getNews(value, sort, dir, page, pageSize, code));
        map.put("total", newsService.getNewsTotal(value,code));
        return ResultObj.success(map);
    }

    @PostMapping("/admin/getNewsByTypeDel")
    //删除新闻类型时根据type查询是否有新闻
    public ResultObj getNewsByTypeDel(int type){
        System.out.println(newsService.getNewsTypeDel(type));
        return ResultObj.success(newsService.getNewsTypeDel(type));
    }

    @PostMapping("/admin/addNews")
    //新增新闻信息
    public ResultObj addNews(QzgzNewsDto qzgzNewsDto){
        return newsService.addNews(qzgzNewsDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/admin/setNews")
    //修改新闻信息
    public ResultObj setNews(QzgzNewsDto qzgzNewsDto){
        return newsService.setNews(qzgzNewsDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/admin/delNews")
    //删除新闻信息
    public ResultObj delNews(int id){
        return newsService.delNews(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/web/getNewsType")
    //查询新闻类型
    public ResultObj getNewsType(String code){
        return ResultObj.success(newsService.getNewsType(code));
    }

    @PostMapping("/web/getNewsByType")
    //根据新闻类型查询新闻并分页
    public ResultObj getNewsByType(int type, int num){
        return ResultObj.success(newsService.getNewsByType(type, num));
    }

    @PostMapping("/web/getNewsByTypeTotal")
    //根据新闻类型查询新闻总数
    public ResultObj getNewsByTypeTotal(int type){
        return ResultObj.success(newsService.getNewsByTypeTotal(type));
    }
}
