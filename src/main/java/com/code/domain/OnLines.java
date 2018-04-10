package com.code.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by MaJian on 18/4/10.
 */
@Getter
@Setter
@ApiModel("管理员")
public class OnLines {
    @ApiModelProperty("编号")
    private String ID;
    @ApiModelProperty("用户")
    private String UserID;
    @ApiModelProperty("token")
    private String Session;
    @ApiModelProperty("时间")
    private String CreateTime;
    @ApiModelProperty("时间")
    private String ModifyTime;
    @ApiModelProperty("更新次数")
    private String Version;
    @ApiModelProperty("状态")
    private int Status;
    @ApiModelProperty("类型【 0 是移动端  1是后端的 】")
    private int Type;

}
