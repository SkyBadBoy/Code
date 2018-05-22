package com.code.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.io.Serializable;
/**
 * <p> Entity Class</p>
 *
 * @author majian
 */
@Getter
@Setter
@ApiModel(value = "Power")
public class Power implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_ParentID = "ParentID";

    public static final String COLUMN_Order = "Order";

    public static final String COLUMN_AdminID = "AdminID";

    public static final String COLUMN_End = "End";

    public static final String COLUMN_MenuID = "MenuID";

    public static final String COLUMN_RoleID = "RoleID";

    public static final String COLUMN_Type = "Type";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "修改时间")
    private String ModifyTime;

    @ApiModelProperty(value = "状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty(value = "父编号")
    private String ParentID;

    @ApiModelProperty(value = "排序")
    private int Order;

    @ApiModelProperty(value = "操作者")
    private String AdminID;

    @ApiModelProperty(value = "是否是最低级 【不是低级 0  低级1 】")
    private int End;

    @ApiModelProperty(value = "菜单的编号")
    private String MenuID;

    @ApiModelProperty(value = "角色的编号")
    private String RoleID;

    @ApiModelProperty(value = "菜单的类型")
    private int Type;

    @ApiModelProperty(value = "菜单信息")
    private Menu Menu;

    @ApiModelProperty(value = "子数据")
    private List<Power> SubPower;

	@ApiModelProperty(value = "['ID','编号']['CreateTime','创建时间']['ModifyTime','修改时间']['Status','状态 【正常：1   删除：88】']['ParentID','父编号']['Order','排序']['AdminID','操作者']['End','是否是最低级 【不是低级 0  低级1 】']['MenuID','菜单的编号']['RoleID','角色的编号']")
	@JSONField(serialize = false)
	public String PowerField;
}
