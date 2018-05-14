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
@ApiModel("Admin")
public class Admin implements Serializable {

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

    @ApiModelProperty("编号")
    private String ID;

    @ApiModelProperty("名字")
    private String Name;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty("头像")
    private String HeadImg;

    @ApiModelProperty("登录名")
    private String LoginName;

    @ApiModelProperty("登录密码")
    private String PassWord;

    @ApiModelProperty("手机号")
    private String Phone;

    @ApiModelProperty("省份")
    private String ProvinceID;

    @ApiModelProperty("区")
    private String AreaID;

    @ApiModelProperty("市")
    private String CityID;

    @ApiModelProperty("长地址")
    private String LongAddress;

    @ApiModelProperty("短地址")
    private String Address;

    @ApiModelProperty("角色编号")
    private String RoleID ;

    @ApiModelProperty("用户")
    private Role Role ;


}
