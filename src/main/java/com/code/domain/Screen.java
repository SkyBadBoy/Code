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
@ApiModel("Screen")
public class Screen implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_Title = "Title";

    public static final String COLUMN_Url = "Url";

    public static final String COLUMN_Type = "Type";

    public static final String COLUMN_StartTime = "StartTime";

    public static final String COLUMN_EndTime = "EndTime";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_AdminID = "AdminID";

    public static final String COLUMN_ClickUrl = "ClickUrl";

    @ApiModelProperty("编号")
    private String ID;

    @ApiModelProperty("标题")
    private String Title;

    @ApiModelProperty("图片地址")
    private String Url;

    @ApiModelProperty("类型 0是PC  1是App的启动  2是微信端   3是所有平台的启动")
    private int Type;

    @ApiModelProperty("开始时间")
    private String StartTime;

    @ApiModelProperty("结束时间")
    private String EndTime;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态 【正常：1 删除：88】")
    private int Status;

    @ApiModelProperty("创建者")
    private String AdminID;

    @ApiModelProperty("点击跳转的URL")
    private String ClickUrl;





}
