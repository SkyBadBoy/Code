package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Article;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import com.code.config.rabbit.RabbitUtil;
/**
 * <p> 控制器 Class</p>
 *
 * @author majian 自动构建脚本
 */
@Api("Article")
@RestController
@RequestMapping("/Article")
public class ArticleController extends BaseController {


    @GetMapping("/queryArticlePage")
    @ApiOperation(value = "获取列表")
    public Map queryArticlePage(int status,String search,int pageNumber, int pageSize,long baseinfoID,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        if(baseinfoID!=-1){
            queryMap.put(Article.COLUMN_BaseInfoID, baseinfoID);
        }
        PageInfo<Article> page = this.ReadArticleService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Article-queryArticlePage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setArticleStatus")
    @ApiOperation(value = "设置状态")
    public Map setArticleStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Article temp=JSON.parseObject(data,Article.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                ArticleService.recoverByID(id);
            }else{
                ArticleService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Article-setArticleStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findArticle/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findArticle(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Article temp=ReadArticleService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Article-findArticle-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyArticle")
    @ApiOperation(value = "修改")
    public Map modifyArticle(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Article temp = JSON.parseObject(data, Article.class);
        Article  obj=new Article();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadArticleService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setName(temp.getName());
        obj.setMemo(temp.getMemo());
        obj.setBaseInfoID(temp.getBaseInfoID());
        //obj.setUserID(temp.getUserID());
        obj.setContent(temp.getContent());
        obj.setCover(temp.getCover());
        obj.setAuthor(temp.getAuthor());
        obj.setProvinceID(temp.getProvinceID());
        obj.setAreaID(temp.getAreaID());
        obj.setCityID(temp.getCityID());


        Article tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=ArticleService.insert(obj);
        }else{
            tempObj=ArticleService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Article-modifyArticle-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
