package com.code.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
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
@ApiModel(value = "Operation")
public class Operation implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_Message = "Message";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_UserID = "UserID";

    public static final String COLUMN_Type = "Type";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "操作信息")
    private String Message;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "状态 【正常：1   删除88】")
    private int Status;

    @ApiModelProperty(value = "是哪个用户")
    private String UserID;

    @ApiModelProperty(value = "类型【用户:0   管理员：1】")
    private int Type;

    @ApiModelProperty(value = "['ID','编号']['Message','操作信息']['CreateTime','创建时间']['Status','状态 【正常：1   删除88】']['UserID','是哪个用户']['Type','类型【用户:0   管理员：1】']")
    @JSONField(serialize = false)
    public String OperationField;


    public static Operation getOperation(String message,String userID,int type){
        Operation operation=new Operation();
        operation.setID(CommonUntil.getInstance().CreateNewID());
        operation.setMessage(message);
        operation.setType(type);
        operation.setUserID(userID);
        operation.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
        return operation;
    }
}