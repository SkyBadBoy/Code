package com.code.domain;

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
@ApiModel(value = "Error")
public class Error implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_UserID = "UserID";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_Message = "Message";

    public static final String COLUMN_ClassName = "ClassName";

    public static final String COLUMN_Status = "Status";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "用户编号")
    private String UserID;

    @ApiModelProperty(value = "报错类")
    private String Name;

    @ApiModelProperty(value = "报错时间")
    private String CreateTime;

    @ApiModelProperty(value = "报错信息")
    private String Message;

    @ApiModelProperty(value = "报错信息标题")
    private String ClassName;

    @ApiModelProperty(value = "状态")
    private int Status;





}
