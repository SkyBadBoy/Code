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
@ApiModel("Keyword")
public class Keyword implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_KeyWord = "KeyWord";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    @ApiModelProperty("键词编号")
    private String ID;

    @ApiModelProperty("关键词语")
    private String KeyWord;

    @ApiModelProperty("创建时间")
    private String CreateTime;

    @ApiModelProperty("修改时间")
    private String ModifyTime;

    @ApiModelProperty("状态信息")
    private int Status;





}
