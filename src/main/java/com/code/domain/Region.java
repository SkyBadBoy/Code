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
@ApiModel(value = "Region")
public class Region implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_Code = "Code";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_ParentID = "ParentID";

    public static final String COLUMN_Level = "Level";

    public static final String COLUMN_Order = "Order";

    public static final String COLUMN_NameEn = "NameEn";

    public static final String COLUMN_ShortNameEn = "ShortNameEn";

    public static final String COLUMN_ProvinceID = "ProvinceID";

    public static final String COLUMN_ProvinceName = "ProvinceName";

    public static final String COLUMN_CityID = "CityID";

    public static final String COLUMN_CityName = "CityName";

    public static final String COLUMN_AreaID = "AreaID";

    public static final String COLUMN_AreaName = "AreaName";

    public static final String COLUMN_Status = "Status";


    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "code")
    private String Code;

    @ApiModelProperty(value = "名字")
    private String Name;

    @ApiModelProperty(value = "父ID")
    private String ParentID;

    @ApiModelProperty(value = "等级 【国家：0  省：1   市：2   区：3   学校：4  】")
    private int Level;

    @ApiModelProperty(value = "排序")
    private int Order;

    @ApiModelProperty(value = "英文名")
    private String NameEn;

    @ApiModelProperty(value = "短英文名")
    private String ShortNameEn;

    @ApiModelProperty(value = "省份编号")
    private String ProvinceID;

    @ApiModelProperty(value = "省份名字")
    private String ProvinceName;

    @ApiModelProperty(value = "城市编号")
    private String CityID;

    @ApiModelProperty(value = "城市名字")
    private String CityName;

    @ApiModelProperty(value = "区域编号")
    private String AreaID;

    @ApiModelProperty(value = "区域名字")
    private String AreaName;

    @ApiModelProperty(value = "状态 【正常：1  删除：88】")
    private int Status;



}
