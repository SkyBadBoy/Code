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
@ApiModel("Menu")
public class Menu implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_Memo = "Memo";

    public static final String COLUMN_Type = "Type";

    public static final String COLUMN_ParentID = "ParentID";

    public static final String COLUMN_Logo = "Logo";

    public static final String COLUMN_Url = "Url";

    public static final String COLUMN_Order = "Order";

    public static final String COLUMN_AdminID = "AdminID";

    public static final String COLUMN_End = "End";

    public static final String COLUMN_UrlType = "UrlType";

    @ApiModelProperty("编号")
    private String ID;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty("菜单名字")
    private String Name;

    @ApiModelProperty("备注")
    private String Memo;

    @ApiModelProperty("菜单类型【目录:0   菜单:1   操作:2】")
    private int Type;

    @ApiModelProperty("父编号")
    private String ParentID;

    @ApiModelProperty("图标地址 只允许H+里面的图标")
    private String Logo;

    @ApiModelProperty("地址")
    private String Url;

    @ApiModelProperty("排序")
    private int Order;

    @ApiModelProperty("操作者")
    private String AdminID;

    @ApiModelProperty("是否是最低级 【不是低级 0  低级1 】")
    private int End;

    @ApiModelProperty("Url 类型 【后台：0  移动端：1】")
    private int UrlType;





}
