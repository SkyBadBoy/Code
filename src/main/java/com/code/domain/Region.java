package com.code.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by MaJian on 18/2/20.
 */
@ApiModel("地区")
public class Region implements Serializable{

    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("code码")
    private String code;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("父编号")
    private String parentid;
    @ApiModelProperty("等级")
    private String level;
    @ApiModelProperty("英文名字")
    private String nameen;
    @ApiModelProperty("短英文名字")
    private String shortnameen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNameen() {
        return nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen;
    }

    public String getShortnameen() {
        return shortnameen;
    }

    public void setShortnameen(String shortnameen) {
        this.shortnameen = shortnameen;
    }
}
