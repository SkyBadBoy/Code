package com.code.until;

import com.code.domain.*;
import com.code.service.read.*;
import com.code.service.write.*;
import com.code.service.write.WeChatInfoService;
import com.code.until.wechat.WXPayUtil;
import com.code.until.wechat.XMLUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by MaJian on 18/2/3.
 */
@Log4j
public class CommonUntil {

    private CommonUntil(){}

    private static CommonUntil instance = new CommonUntil();

    public static CommonUntil getInstance(){
        return instance;
    }

    public static Return ReturnMap(int code, String message, Object object){
        Return returnMap=new Return();
        returnMap.setCode(code);
        returnMap.setData(object);
        returnMap.setMessage(message);
        return returnMap;
    }
    public static Return ReturnMapWithToken(int code, String message, Object object,String Token){
        Return returnMap=new Return();
        returnMap.setCode(code);
        returnMap.setData(object);
        returnMap.setMessage(message);
        returnMap.setToken(Token);
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

    public String CreateNewID(){
        // new IdWorker(1, new Random().nextInt(31)).nextId()+""
        return IdWorker.getInstance().nextId(1,1)+"";
    }

    public static WeChatInfo getWeChatInfo(WeChatInfoService WeChatInfoServices ){
        WeChatInfo weChatInfo=WeChatInfoServices.findById("1");
        return weChatInfo;
    }

    public static boolean getWithdraw(User user, float price, HttpServletResponse response, HttpServletRequest request, WeChatInfoService WeChatInfoServices){
       if(CommonStatus.IsGetMoney){//提现功能
            String orderNo = String.valueOf(CommonUntil.getInstance().CreateNewID());
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
        if(requestUrl.contains("druid")){
            return true;
        }
        List<String> pass= Arrays.asList("js","css","jpg","png","jpeg","ico","mp3","pdf","mp4","html","json","ttf","woff2","map","woff","svg");
        if(requestUrl.contains(".")){
            if(pass.contains(requestUrl.split("\\.")[requestUrl.split("\\.").length-1])){
                f=true;
            }
        }
        return f;
    }
    public Online CreateOnline(String sessionID, String UserID, int Type,ReadOnlineService ReadOnlineService,OnlineService OnlineService){
        Map<String,Object> queryMap=new HashMap<String, Object>();
        queryMap.put(Online.COLUMN_Status,CommonStatus.Status.Ectivity.getid());
        queryMap.put(Online.COLUMN_Type,Type);
        queryMap.put(Online.COLUMN_UserID,UserID);
        PageInfo<Online> onliness=ReadOnlineService.queryPage(queryMap,0,1);
        List<Online> onlines=onliness.getList();
        Online temp=null;
        if(onlines.size()>0){
            onlines.get(0).setSession(sessionID);
            temp=OnlineService.update(onlines.get(0));
        }else{
            Online online=new Online();
            online.setID(CommonUntil.getInstance().CreateNewID());
            online.setType(Type);
            online.setUserID(UserID);
            online.setSession(sessionID);
            online.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            temp=OnlineService.insert(online);
        }
        return temp;
    }

    public Operation CreateOperation(String token, String message,ReadOnlineService ReadOnlineService,OperationService OperationService,ReadUserService userService){
        String userID="0";
        int type=1;
        if(CommonUntil.CheckIsNull(token)&&!"0".equals(token)){
            try {
                long uid=Long.parseLong(token);
                User user=userService.findById(token);
                userID=String.valueOf(uid);
                if(user==null){
                    type=0;
                }
            } catch (NumberFormatException e) {
                Map map=new HashMap();
                map.put(Online.COLUMN_Session,token);
                map.put(Online.COLUMN_Status, CommonStatus.Status.Ectivity.getid());
                PageInfo<Online> onlines=ReadOnlineService.queryPage(map,1,1);
                if(onlines.getList().size()>0){
                    userID=onlines.getList().get(0).getUserID();
                    type=onlines.getList().get(0).getType();
                }
            }
        }
        Operation operation=Operation.getOperation(message,userID,type);
        operation=OperationService.insert(operation);
        return operation;
    }

    public boolean CheckToken(String Token,HttpServletRequest request,ReadOnlineService ReadOnlineService){
        boolean f=false;
        if(CheckIsNull(Token)){
            if(request.getSession().getId().equals(Token)){
                f=true;
            }else{
                Map queryMap=new HashMap();
                queryMap.put(Online.COLUMN_Status,CommonStatus.Status.Ectivity.getid());
                queryMap.put(Online.COLUMN_Session,Token);
                int OnlineCount=ReadOnlineService.queryCount(queryMap);
                if(OnlineCount>0){
                    f=true;
                }
            }
        }
        return f;
    }
    public  String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //**过滤不需要的地址
    public  static Boolean CheckWebLog(String s) {
        boolean f=true;
        if(CommonUntil.CheckIsNull(s)){
            if(s.contains("errorToken")){
                f=false;
            }
        }else{
            f=false;
        }

        return f;
    }

    public  void CheckPower(String menuID,String RoleID,ReadPowerService ReadPowerService,PowerService PowerService,ReadMenuService ReadMenuService) {
        Map queryMap=new HashMap();
        queryMap.put(Power.COLUMN_RoleID,RoleID);
        queryMap.put(Power.COLUMN_MenuID,menuID);
        PageInfo<Power> powerList=ReadPowerService.queryPage(queryMap,0,0);
        if(powerList.getSize()>0){
            Power power=powerList.getList().get(0);
            power.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            PowerService.update(power);
        }else{
            Menu menu=ReadMenuService.findById(menuID);
            String ParentID="0";
            int Type=0;
            if(menu.getParentMenu()!=null){
                queryMap.clear();
                queryMap.put(Power.COLUMN_RoleID,RoleID);
                queryMap.put(Power.COLUMN_MenuID,menu.getParentID());
                PageInfo<Power> ParentList=ReadPowerService.queryPage(queryMap,0,0);
                if(ParentList.getSize()>0){
                    ParentID=ParentList.getList().get(0).getID();
                }
            }
            Power power=new Power();
            power.setID(CommonUntil.getInstance().CreateNewID());
            power.setMenuID(menuID);
            power.setOrder(menu.getOrder());
            power.setRoleID(RoleID);
            power.setParentID(ParentID);
            power.setAdminID("0");
            power.setType(menu.getType());
            power.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            PowerService.insert(power);
        }

    }
    public static String getProjectBaseUrlNoProject(HttpServletRequest req){

        String scheme=req.getHeader("X-Forwarded-Scheme");
        if(scheme==null || scheme.equals("")){
            scheme=req.getScheme();
        }
        String port="";
        if(req.getServerPort()!=80 && req.getServerPort()!=443){
            port = ":"+req.getServerPort();
        }
        String basePath=req.getScheme() + "://"
                + req.getServerName() +port+ "/";

        return basePath;
    }

    public static String getProjectBaseUrl(HttpServletRequest request){
        String ContextPath = request.getContextPath();
        if(ContextPath.length()>1&& ContextPath.substring(0,1).equals("/")){
            ContextPath = ContextPath.substring(1,ContextPath.length())+"/";
        }
        String basePath=getProjectBaseUrlNoProject(request)+ ContextPath  ;
        return basePath;
    }

}
