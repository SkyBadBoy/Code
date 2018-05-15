package com.code.domain;

import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
/**
 * <p> Entity Class</p>
 *
 * @author majian
 */
@Getter
@Setter
@ApiModel("Access")
public class Access implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_RequestURL = "RequestURL";

    public static final String COLUMN_Method = "Method";

    public static final String COLUMN_RemoteAddr = "RemoteAddr";

    public static final String COLUMN_ClassName = "ClassName";

    public static final String COLUMN_Time = "Time";

    @ApiModelProperty("访问量编号")
    private String ID;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty("请求地址")
    private String RequestURL;

    @ApiModelProperty("请求方式")
    private String Method;

    @ApiModelProperty("访问者IP")
    private String RemoteAddr;

    @ApiModelProperty("类名+方法名")
    private String ClassName;

    @ApiModelProperty("参数")
    private String Args;

    @ApiModelProperty("处理的时间")
    private long Time;

    public  static Access getAccess(String RequestURL,String Method,String RemoteAddr,String ClassName,String Args,long Time){
        Access access=new Access();
        access.setID(CommonUntil.getInstance().CreateNewID());
        access.setStatus(CommonStatus.StatusEnum.Ectivity.seq);
        access.setRequestURL(RequestURL);
        access.setMethod(Method);
        access.setRemoteAddr(RemoteAddr);
        access.setClassName(ClassName);
        access.setArgs(Args);
        access.setTime(Time);
        return access;
    }



}
