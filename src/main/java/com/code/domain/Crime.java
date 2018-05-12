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
@ApiModel("Crime")
public class Crime implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_UserID = "UserID";

    public static final String COLUMN_AdminID = "AdminID";

    public static final String COLUMN_Content = "Content";

    public static final String COLUMN_SrcType = "SrcType";

    public static final String COLUMN_SrcID = "SrcID";

    public static final String COLUMN_Time = "Time";

    public static final String COLUMN_Type = "Type";

    public static final String COLUMN_StartTime = "StartTime";

    public static final String COLUMN_EndTime = "EndTime";

    @ApiModelProperty("编号")
    private String ID;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty("用户编号")
    private String UserID;

    @ApiModelProperty("处理人")
    private String AdminID;

    @ApiModelProperty("处理内容")
    private String Content;

    @ApiModelProperty("类型【用户：0   新闻资讯：1    评论：2   】")
    private int SrcType;

    @ApiModelProperty("资源编号")
    private String SrcID;

    @ApiModelProperty("时效 【永久：0     时效：1】")
    private int Time;

    @ApiModelProperty("处理类型【提醒：0    警告：1    禁止/下架：2     管理员取消处罚：3】")
    private int Type;

    @ApiModelProperty("开始时间")
    private String StartTime;

    @ApiModelProperty("结束时间")
    private String EndTime;

    @ApiModelProperty("用户")
    private User User;



}
