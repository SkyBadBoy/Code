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
@ApiModel("Region")
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


    @ApiModelProperty("编号")
    private String ID;

    @ApiModelProperty("code")
    private String Code;

    @ApiModelProperty("名字")
    private String Name;

    @ApiModelProperty("父ID")
    private String ParentID;

    @ApiModelProperty("等级 【国家：0  省：1   市：2   区：3   学校：4  】")
    private int Level;

    @ApiModelProperty("排序")
    private int Order;

    @ApiModelProperty("英文名")
    private String NameEn;

    @ApiModelProperty("短英文名")
    private String ShortNameEn;

    @ApiModelProperty("省份编号")
    private String ProvinceID;

    @ApiModelProperty("省份名字")
    private String ProvinceName;

    @ApiModelProperty("城市编号")
    private String CityID;

    @ApiModelProperty("城市名字")
    private String CityName;

    @ApiModelProperty("区域编号")
    private String AreaID;

    @ApiModelProperty("区域名字")
    private String AreaName;

    @ApiModelProperty("状态 【正常：1  删除：88】")
    private int Status;



}
