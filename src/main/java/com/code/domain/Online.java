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
@ApiModel("Online")
public class Online implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_UserID = "UserID";

    public static final String COLUMN_Session = "Session";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Version = "Version";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_Type = "Type";

    @ApiModelProperty("在线编号")
    private String ID;

    @ApiModelProperty("用户编号 或者管理员编号 ")
    private String UserID;

    @ApiModelProperty("会话编号")
    private String Session;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("登录次数")
    private int Version;

    @ApiModelProperty("状态信息【正常：1    删除：88】")
    private int Status;

    @ApiModelProperty("登录身份类型【用户:0  管理人员：1】")
    private int Type;

    private User User;

    private Admin Admin;

}
