package com.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 后台主页的接口和页面
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController{

    /**
     * 主页的转发 请求
     * @return
     */
    @GetMapping("")
    public String index(){
        return "index";
    }

    @GetMapping("test")
    @ResponseBody
    public String indexs(){

        return "index"+1/0;
    }
    /**
     * 主页的数据接口
     * @return
     */
    @GetMapping("/getHomeInfo")
    @ResponseBody
    public Map<String,Object> getHomeInfo(HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<String,Object>();
//        returnMap.put("boxMoney", boxService.getAllMoney());
//        returnMap.put("boxCount", boxService.getAllCount());
//        returnMap.put("userCount", userService.getAllCount());
//        returnMap.put("boxCountDay", boxService.getAllCountDay());
//        returnMap.put("boxCountByDay", boxService.queryCountDay());
//        returnMap.put("userCountByDay", userService.queryCountDay());
        return returnMap;
    }



}
