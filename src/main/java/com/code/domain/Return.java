package com.code.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by MaJian on 18/2/18.
 * map 返回的参数
 */
@ApiModel(value = "全局返回类型")
public class Return<Object> implements java.io.Serializable{

    @ApiModelProperty(value = "状态码")
    public long code;

    @ApiModelProperty(value = "返回数据")
    public Object data;

    @ApiModelProperty(value = "返回信息")
    public String message;





    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
