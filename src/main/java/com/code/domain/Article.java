package com.code.domain;

import com.alibaba.fastjson.annotation.JSONField;
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
@ApiModel(value = "Article")
public class Article implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_Memo = "Memo";

    public static final String COLUMN_BaseInfoID = "BaseInfoID";

    public static final String COLUMN_UserID = "UserID";

    public static final String COLUMN_Content = "Content";

    public static final String COLUMN_Cover = "Cover";

    public static final String COLUMN_Author = "Author";

    public static final String COLUMN_ProvinceID = "ProvinceID";

    public static final String COLUMN_AreaID = "AreaID";

    public static final String COLUMN_CityID = "CityID";

    public static final String COLUMN_Read = "Read";

    public static final String COLUMN_Like = "Like";

    public static final String COLUMN_Comment = "Comment";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "修改时间")
    private String ModifyTime;

    @ApiModelProperty(value = "状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty(value = "名字")
    private String Name;

    @ApiModelProperty(value = "备注")
    private String Memo;

    @ApiModelProperty(value = "分类编号")
    private String BaseInfoID;

    @ApiModelProperty(value = "发布者")
    private String UserID;

    @ApiModelProperty(value = "内容")
    private String Content;

    @ApiModelProperty(value = "封面")
    private String Cover;

    @ApiModelProperty(value = "发布者")
    private String Author;

    @ApiModelProperty(value = "省")
    private String ProvinceID;

    @ApiModelProperty(value = "区")
    private String AreaID;

    @ApiModelProperty(value = "市")
    private String CityID;

    @ApiModelProperty(value = "阅读量")
    private long Read;

    @ApiModelProperty(value = "点赞")
    private long Like;

    @ApiModelProperty(value = "评论")
    private long Comment;

	@ApiModelProperty(value = "['ID','编号']['CreateTime','创建时间']['ModifyTime','修改时间']['Status','状态 【正常：1   删除：88】']['Name','名字']['Memo','备注']['BaseInfoID','分类编号']['UserID','发布者']['Content','内容']['Cover','封面']['Author','发布者']['ProvinceID','省']['AreaID','区']['CityID','市']")
	@JSONField(serialize = false)
	public String ArticleField;
}
