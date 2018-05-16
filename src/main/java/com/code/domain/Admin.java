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
@ApiModel(value = "Admin")
public class Admin  implements Serializable{

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_HeadImg = "HeadImg";

    public static final String COLUMN_LoginName = "LoginName";

    public static final String COLUMN_PassWord = "PassWord";

    public static final String COLUMN_Phone = "Phone";

    public static final String COLUMN_ProvinceID = "ProvinceID";

    public static final String COLUMN_AreaID = "AreaID";

    public static final String COLUMN_CityID = "CityID";

    public static final String COLUMN_LongAddress = "LongAddress";

    public static final String COLUMN_Address = "Address";

    public static final String COLUMN_RoleID = "RoleID";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "名字")
    private String Name;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "修改时间")
    private String ModifyTime;

    @ApiModelProperty(value = "状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty(value = "头像")
    private String HeadImg;

    @ApiModelProperty(value = "登录名")
    private String LoginName;

    @ApiModelProperty(value = "登录密码")
    private String PassWord;

    @ApiModelProperty(value = "手机号")
    private String Phone;

    @ApiModelProperty(value = "省份")
    private String ProvinceID;

    @ApiModelProperty(value = "区")
    private String AreaID;

    @ApiModelProperty(value = "市")
    private String CityID;

    @ApiModelProperty(value = "长地址")
    private String LongAddress;

    @ApiModelProperty(value = "短地址")
    private String Address;

    @ApiModelProperty(value = "角色编号")
    private String RoleID ;

    @ApiModelProperty(value = "用户")
    private Role Role ;


}
