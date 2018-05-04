package com.code.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@ApiModel("Activity")
public class Activity implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_Title = "Title";

    public static final String COLUMN_Content = "Content";

    public static final String COLUMN_ImageID = "ImageID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_Hot = "Hot";

    public static final String COLUMN_Weight = "Weight";

    public static final String COLUMN_WeChatID = "WeChatID";

    public static final String COLUMN_Praise = "Praise";

    public static final String COLUMN_EndTime = "EndTime";

    public static final String COLUMN_Sponsor = "Sponsor";

    public static final String COLUMN_SBrief = "SBrief";

    public static final String COLUMN_SImage = "SImage";

    public static final String COLUMN_ApplyCount = "ApplyCount";

    public static final String COLUMN_ApplyLimit = "ApplyLimit";

    public static final String COLUMN_ApplyMoney = "ApplyMoney";

    public static final String COLUMN_PayType = "PayType";

    @ApiModelProperty("编号")
    private String ID;

    @ApiModelProperty("标题")
    private String Title;

    @ApiModelProperty("内容描述")
    private String Content;

    @ApiModelProperty("图片编号")
    private long ImageID;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态")
    private int Status;

    @ApiModelProperty("热度")
    private int Hot;

    @ApiModelProperty("权值")
    private int Weight;

    @ApiModelProperty("活动所属支社,可以不填")
    private long WeChatID;

    @ApiModelProperty("点赞")
    private long Praise;

    @ApiModelProperty("结束时间")
    private String EndTime;

    @ApiModelProperty("主办方")
    private String Sponsor;

    @ApiModelProperty("主办方简介")
    private String SBrief;

    @ApiModelProperty("主办方头像")
    private long SImage;

    @ApiModelProperty("报名的人数")
    private int ApplyCount;

    @ApiModelProperty("报名人数限制")
    private int ApplyLimit;

    @ApiModelProperty("报名的钱")
    private String ApplyMoney;

    @ApiModelProperty("支付类型 14免费 15人民币 16微米")
    private String PayType;


}
