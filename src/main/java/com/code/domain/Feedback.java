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
@ApiModel(value = "Feedback")
public class Feedback implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_UserID = "UserID";

    public static final String COLUMN_Anonymity = "Anonymity";

    public static final String COLUMN_Content = "Content";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "修改时间")
    private String ModifyTime;

    @ApiModelProperty(value = "状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty(value = "用户编号")
    private String UserID;

    @ApiModelProperty(value = "是否匿名 【不匿名：0   匿名：1】")
    private int Anonymity;

    @ApiModelProperty(value = "反馈内容")
    private String Content;

    @ApiModelProperty(value = "用户")
    private User User;




}
