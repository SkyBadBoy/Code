package com.code.until.wechat;



import com.code.domain.User;
import com.code.domain.WeChatInfo;
import com.code.until.CommonStatus;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;



public class WXPayUtil {
    private static WXPayUtil instance;

    public static WXPayUtil sharedInstance() {
        if (instance == null) {
            instance = new WXPayUtil();
        }

        return instance;
    }

    /**
     *
     *spbill_create_ip 可能得修改才能使用体现
     *
     * @param request
     * @param response
     * @param user
     * @param money
     * @param orderNo
     * @param weChatInfo
     * @param appid
     * @return
     * @throws Exception
     */
    public static String pushClient(HttpServletRequest request, HttpServletResponse response, User user, int money, String orderNo, WeChatInfo weChatInfo, String appid) throws Exception {
        String result="";
        WeChatPayRequestHandler queryReq = new WeChatPayRequestHandler(request,response);
        queryReq.init();
        queryReq.setKey(weChatInfo.getApikey());
        queryReq.setGateUrl(CommonStatus.WXPay_TansfersUrl);
        queryReq.setParameter("amount",String.valueOf(money));
        queryReq.setParameter("check_name", "NO_CHECK");
        queryReq.setParameter("desc", "活动返利!");
        queryReq.setParameter("mch_appid",weChatInfo.getAppid());
        queryReq.setParameter("mchid",String.valueOf(weChatInfo.getMchid()));
        queryReq.setParameter("nonce_str",WXUtil.getNonceStr());
        queryReq.setParameter("openid", user.getOpenID());
        queryReq.setParameter("partner_trade_no",orderNo);
        queryReq.setParameter("re_user_name", user.getName());
        queryReq.setParameter("spbill_create_ip", "114.215.201.156");
        queryReq.getSign();

        String WXParam=queryReq.getParamXML();
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setTimeOut(5);
        httpClient.setPostData(WXParam);

        String path =request.getRealPath("/WEB-INF/classes/certificate/"+ appid +"/rootca.pem");
        httpClient.setCaInfo(new File(path));

        path =request.getRealPath("/WEB-INF/classes/certificate/"+ appid +"/apiclient_cert.p12");
        httpClient.setCertInfo(new File(path),String.valueOf(weChatInfo.getMchid()));
        //设置请求内容
        httpClient.setReqContent(queryReq.getRequestURL());
       // System.out.println("queryReq:" + queryReq.getRequestURL());
        //后台调用
        if(httpClient.call()) {
            ClientResponseHandler queryRes = new ClientResponseHandler();
            //设置结果参数
            queryRes.setContent(httpClient.getResContent());
            result=queryRes.getContent();
            System.out.println("queryRes:" + queryRes.getContent());
        }
        return result;
    }
    
    
    

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
