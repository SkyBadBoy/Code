package com.code.until;

import com.code.domain.Return;
import com.code.domain.User;
import com.code.domain.WeChatInfo;
import com.code.service.write.WeChatInfoService;
import com.code.until.wechat.WXPayUtil;
import com.code.until.wechat.XMLUtil;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.List;

/**
 * Created by MaJian on 18/2/3.
 */
@Log4j
public class CommonUntil {

    public static Return ReturnMap(int code, String message, Object object){
        Return returnMap=new Return();
        returnMap.setCode(code);
        returnMap.setData(object);
        returnMap.setMessage(message);
        return returnMap;
    }

    public static boolean CheckIsNull(String str){
        boolean flag=true;
        if (str!=null) {
            if (!str.trim().isEmpty()) {
                if (str.equals(null)||str.equals("")||str.equals("null")||str.equals("undefined")||str.equals("\"\"")) {
                    flag=false;
                }
            }else {
                flag=false;
            }}else {
            flag=false;
        }
        return flag;
    }

    public static String CreateNewID(){
        return new IdWorker(1, new Random().nextInt(31)).nextId()+"";
    }

    public static WeChatInfo getWeChatInfo(WeChatInfoService WeChatInfoServices ){
        WeChatInfo weChatInfo=WeChatInfoServices.findById("1");
        return weChatInfo;
    }

    public static boolean getWithdraw(User user, float price, HttpServletResponse response, HttpServletRequest request, WeChatInfoService WeChatInfoServices){
       if(CommonStatus.IsGetMoney){//提现功能
            String orderNo = String.valueOf(CreateNewID());
            WeChatInfo weChatInfo=getWeChatInfo(WeChatInfoServices);
            try {
                String respcontent = WXPayUtil.pushClient(request, response, user,(int)(price*100), orderNo,weChatInfo,weChatInfo.getAppid());// 提现金额写死1元钱
                Map<String, Object> mapContent = XMLUtil.doXMLParse(respcontent);
                System.err.println(mapContent);
                if(mapContent!=null){
                    if (mapContent.get("return_code").equals("SUCCESS") && mapContent.get("result_code").equals("SUCCESS")) {
                        return true;
                    } else {
                        return false;
                    }
                }else{
                    return false;
                }
            }catch (Exception e){
                return false;
            }
       }else{
           return false;
       }
    }

    public String getAbsolutePhth(HttpServletRequest request){
        String path =request.getRealPath("/WEB-INF/classes/");
        return path;
    }
    /**
     * 查看url属于哪个类型的
     * @param requestUrl
     * @return
     */
    public static int checkAuthorize(String requestUrl){
        String[] url=requestUrl.split("/");
        int Authorize=CommonStatus.AuthorizeType.Admin.seq;
        if(url.length>0){
            if(url.length>2){
                String AuthorizeName=url[1];
                if("Interface".equals(AuthorizeName)){
                    Authorize=CommonStatus.AuthorizeType.Interface.seq;
                }else{//自行扩展

                }
            }
        }
        return Authorize ;
    }

    /**
     * 模糊放行
     * @return
     */
    public static boolean dimAuthorize(String requestUrl){
        boolean f=false;
        List<String> pass= Arrays.asList("js","css","jpg","png","jpeg","ico","mp3","pdf","mp4");
        if(requestUrl.contains(".")){
            if(pass.contains(requestUrl.split("\\.")[requestUrl.split("\\.").length-1])){
                f=true;
            }
        }
        return f;
    }
}
