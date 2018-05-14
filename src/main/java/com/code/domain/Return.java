package com.code.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by MaJian on 18/2/18.
 * map 返回的参数
 */
@ApiModel(value = "全局返回类型")
@Getter
@Setter
public class Return<Object> implements java.io.Serializable{

    @ApiModelProperty(value = "状态码【正常:0   错误:-1   Token:1】 ")
    public long code;

    @ApiModelProperty(value = "返回数据")
    public Object data;

    @ApiModelProperty(value = "返回信息")
    public String message;

    @ApiModelProperty(value = "token  首次登陆会有")
    public String token;

}
