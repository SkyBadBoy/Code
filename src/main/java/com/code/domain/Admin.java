package com.code.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by MaJian on 18/2/3.
 */
@Getter
@Setter
@ApiModel("管理员")
public class Admin implements java.io.Serializable {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("等级")
    private String level;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String modifyTime;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("头像")
    private String headimg;
    @ApiModelProperty("登录名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
