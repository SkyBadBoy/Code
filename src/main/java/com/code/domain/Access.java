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
@ApiModel(value = "Access")
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

    @ApiModelProperty(value = "访问量编号")
    private String ID;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "修改时间")
    private String ModifyTime;

    @ApiModelProperty(value = "状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty(value = "请求地址")
    private String RequestURL;

    @ApiModelProperty(value = "请求方式")
    private String Method;

    @ApiModelProperty(value = "访问者IP")
    private String RemoteAddr;

    @ApiModelProperty(value = "类名+方法名")
    private String ClassName;

    @ApiModelProperty(value = "参数")
    private String Args;

    @ApiModelProperty(value = "处理的时间")
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
