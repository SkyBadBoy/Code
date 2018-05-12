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
@ApiModel("User")
public class User implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_LoginName = "LoginName";

    public static final String COLUMN_PassWord = "PassWord";

    public static final String COLUMN_HeadImg = "HeadImg";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_NikeName = "NikeName";

    public static final String COLUMN_Signature = "Signature";

    public static final String COLUMN_Sex = "Sex";

    public static final String COLUMN_Age = "Age";

    public static final String COLUMN_Phone = "Phone";

    public static final String COLUMN_UnionID = "UnionID";

    public static final String COLUMN_OpenID = "OpenID";

    public static final String COLUMN_Email = "Email";

    public static final String COLUMN_ProvinceID = "ProvinceID";

    public static final String COLUMN_AreaID = "AreaID";

    public static final String COLUMN_CityID = "CityID";

    public static final String COLUMN_LongAddress = "LongAddress";

    public static final String COLUMN_Address = "Address";

    @ApiModelProperty("编号")
    private String ID;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty("登录名")
    private String LoginName;

    @ApiModelProperty("登录密码")
    private String PassWord;

    @ApiModelProperty("头像")
    private String HeadImg;

    @ApiModelProperty("用户名")
    private String Name;

    @ApiModelProperty("微信名字【不是微信不需要】")
    private String NikeName;

    @ApiModelProperty("个性签名")
    private String Signature;

    @ApiModelProperty("性别 【未设置：0   男：1  女：2】")
    private int Sex;

    @ApiModelProperty("生日")
    private String Age;

    @ApiModelProperty("手机号")
    private String Phone;

    @ApiModelProperty("unionid")
    private String UnionID;

    @ApiModelProperty("openid")
    private String OpenID;

    @ApiModelProperty("邮箱")
    private String Email;

    @ApiModelProperty("省")
    private String ProvinceID;

    @ApiModelProperty("区")
    private String AreaID;

    @ApiModelProperty("市")
    private String CityID;

    @ApiModelProperty("长地址")
    private String LongAddress;

    @ApiModelProperty("短地址")
    private String Address;





}
