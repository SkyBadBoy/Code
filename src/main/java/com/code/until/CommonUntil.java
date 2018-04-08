package com.code.until;

import com.code.domain.Return;
import com.code.domain.User;
import com.code.domain.WeChatInfo;
import com.code.service.WeChatInfoService;
import com.code.until.wechat.WXPayUtil;
import com.code.until.wechat.XMLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by MaJian on 18/2/3.
 */
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
}
