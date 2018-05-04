function success(text){
    swal({
        title: "福来了",
        text: text,
        type: "success"
    });
}
function error(text){
    swal({
        title: "福来了",
        text: text,
        type: "error"
    });
}
/**
 * 提示框
 * @param that
 * @param url
 * @param w
 * @param h
 * @param title
 */
function modifyShow(that,url,w,h,title){
    if(w==null){
        w="100%";
    }
    if(h==null){
        h="100%";
    }
    if(title==""||title==null){
        title=""
    }
    that.index = layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: 0.6,
        maxmin: false, //开启最大化最小化按钮
        area: [w, h],
        content: url
    });
}

/**
 * 监测是否为空
 * @param content
 * @returns {boolean}
 */
function chechIsUnll(content){
    if(content==null || content == "null" || content == undefined || content == "undefined"){
        return false;
    }else{
        return true;
    }
}
/**
 * 状态头部
 * @returns {string}
 */
function getStatus(){
    var  html='<div class="dropdown profile-element">' +
        '<a data-toggle="dropdown" class="dropdown-toggle" href="#">' +
        '<span class="text-muted text-xs block">状态<b class="caret"></b></span>' +
        ' </span>' +
        '</a>' +
        '<ul class="dropdown-menu  m-t-xs">' +
        '<li><a class="J_menuItem" onclick="changStatus(88)">删除</a>' +
        ' </li>' +
        ' <li><a class="J_menuItem" onclick="changStatus(1)">正常</a>' +
        '</li>' +
        '<li class="divider"></li>' +
        '<li><a  class="J_menuItem" onclick="changStatus(-1)">全部</a>' +
        '</li>' +
        ' </ul>' +
        '</div>'
    return html;
}
/**
 * 状态格式
 * @param val
 */
function statusFormatter(value) {
    if(value==88 || value=="88") {
        return '<small class="label label-danger">已删除</small>'
    }else{
        return '<small class="label label-primary"> 正常</small>'
    }
}
/**
 * 行样式
 * @param val
 */
function dispErrorRow(row, index) {
    //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
    var strclass = "";
    if (row.status == 88 || row.status == '88') {
        strclass = 'danger';//还有一个active
    }else  if (row.status == 8 || row.status == '8') {
        strclass = 'warning';//还有一个active
    }else {
        return {};
    }
    return {
        classes : strclass
    }
}
/**
 * 状态改变
 * @param val
 */
function changStatus(val){
    tempApp.status=val;
    $('.bootstrap-table [name=refresh]').click()
}
/*表格相关操作*/
var geticons=
{
    refresh : "glyphicon-repeat",
    toggle : "glyphicon-list-alt",
    columns : "glyphicon-list"
};
function responseHandler(res) {

    if (res.Status==1) {
        return {
            "rows" : res.data?res.data:res.Data,
            "total" : res.total
        };
    } else {
        return {
            "rows" : [],
            "total" : 0
        };
    }

}

/*获取记录ID*/
function getRecordIDS(obj){
    var wid = obj.bootstrapTable("getSelections");
    var wids = "";

    for ( var idobj = 0; idobj < wid.length; idobj++) {
        wids += "," + wid[idobj].id
    }
    if(wids!=""){
        wids=wids.substr(1);
    }
    return wids;
}

function getUrlKey(name){
    return decodeURIComponent((new RegExp('[?|&]'+name+'='+'([^&;]+?)(&|#|;|$)').exec(location.href)||[,""])[1].replace(/\+/g,'%20'))||null;
}

function getFootInfo(){
    var date=new Date;
    var year=date.getFullYear();
    var str="";
    str+="&copy 1995-"+year.toString()+" 杭州xxxx(code)有限公司";
    return str;
}
/**
 * 表格是否被选中了,选中就说明都不反,不选中提示信息
 * @returns {boolean}
 */
function checkIsSelection(){
    if ($('#exampleTableEvents').bootstrapTable("getSelections").length <= 0) {
        success("亲,请选择一行哦")
        return true;
    }
}

/**
 * 删除请求
 * @param that
 * @param status
 * @param url
 */
function setAdminStatus(that,status,url) {
    if(checkIsSelection()){return true}
    $("#overlay").show()
    var data = {
        ID:getRecordIDForFastJson($('#exampleTableEvents')),
        status: status
    }
    $.ajax({
        url: url,
        type: "POST",
        data: {data: JSON.stringify(data)},
        success: function (json) {
            $("#overlay").hide()
            if (json.code == 0) {
                success(json.message)
                that.status = status;
                $('.bootstrap-table [name=refresh]').click()
            } else {
                error(json.message);
            }
        },
        error: function () {
            $("#overlay").hide();
            error("网络异常哦,请稍后重试");
        }
    })
}


/**
 * 封装表格 对象 地址 列 参数
 * @param obj
 * @param url
 * @param columns
 */
function getDataTable(obj,url,columns,queryParams){
    obj.bootstrapTable({
        url: url,
        search: true,
        pagination: true,
        showRefresh: true,
        showToggle: false,
        striped:true,//间隔色
        showColumns: true,//是否显示切换视图（table/card）按钮
        sidePagination : "server", //服务端请求
        rowStyle : dispErrorRow,
        queryParams : queryParams,
        //              responseHandler : responseHandler,// 这边是 服务器返回的参数设置 可看工具类有些
        iconSize: 'outline',
        toolbar: '#exampleTableEventsToolbar',
        icons: geticons,
        columns: columns,
        undefinedText:"-",
        clickToSelect:true,//设置 true 将在点击行时，自动选择 rediobox 和 checkbox。
        height:window.screen.availHeight-34*4-40-100,//自动识别高度
        paginationPreText:"上一页",
        paginationNextText:"下一页"


    });
    obj.on('click-row.bs.table', function(e, row, $element) {
       //$($element).find("input[type='checkbox']").click()
    })
}
/**
 * layer 关闭方法
 * @param message
 */
function closes(message) {
    layer.close(tempApp.index);
    success(message)
    $('.bootstrap-table [name=refresh]').click()
}

/**
 * 查找单条数据
 * @param that
 * @param id
 * @param url
 */
function findByID(that,id,url){
    $("#overlay").show()
    $.ajax({
        url: url+id,
        success: function (json) {
            $("#overlay").hide()
            console.log("获取查找数据",json)
            if (json.code == 0) {
                that.data=json.data;
            } else {
                error(json.message);
            }
        },
        error: function () {
            $("#overlay").hide();
            error("网络异常哦,请稍后重试");
        }
    })
}

/**
 * 修改的方法
 * @param that
 * @param url
 */
function savaData(that,url) {
    $("#overlay").show()
    $.ajax({
        url: url,
        type: "POST",
        data: {data: JSON.stringify(that.data)},
        success: function (json) {
            console.log("获取到数据",json)
            $("#overlay").hide()
            if (json.code == 0) {
                parent.closes(json.message)
            } else {
                error(json.message);
            }
        },
        error: function () {
            $("#overlay").hide();
            error("网络异常哦,请稍后重试");
        }
    })
}