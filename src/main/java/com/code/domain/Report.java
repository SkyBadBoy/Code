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
@ApiModel("Report")
public class Report implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_UserID = "UserID";

    public static final String COLUMN_Anonymity = "Anonymity";

    public static final String COLUMN_Content = "Content";

    public static final String COLUMN_SrcID = "SrcID";

    public static final String COLUMN_Type = "Type";

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

    @ApiModelProperty("是否匿名 【不匿名：0   匿名：1】")
    private int Anonymity;

    @ApiModelProperty("反馈内容")
    private String Content;

    @ApiModelProperty("举报内容的编号")
    private String SrcID;

    @ApiModelProperty("举报类型【用户：0   新闻资讯：1    评论：2   】")
    private int Type;





}
