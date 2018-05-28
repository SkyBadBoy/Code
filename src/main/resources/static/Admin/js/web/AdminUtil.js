function success(text) {
    toastr.success(text,"操作提示");
}
function error(text){
    toastr.error(text,"操作提示");
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
    if(content==null || content == "null" || content == undefined || content == "undefined" || content == ""){
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
 * 点击的链接
 * @param value
 * @returns {string}
 */
function clickUrlFormatter(value) {
    if(chechIsUnll(value)) {
        return '<a href="'+value+'" target="_blank" >'+value+'</a>'
    }
}
/**
 * 状态格式
 * @param val
 */
function headImgFormatter(value) {
    if(chechIsUnll(value)){
        return "<image src='"+value+"' style='height:70px;width: 70px;'>";
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
/**
 * 状态类型
 * @param val
 */
function changType(val){
    tempApp.type=val;
    tableRefresh();
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
        headers: {
            Token: getToken()
        },
        data: {data: JSON.stringify(data)},
        success: function (json) {
            $("#overlay").hide()
            if (json.code == 1) {
                top.location.href="../Admin/login.html"
            }
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
        paginationNextText:"下一页",
        ajaxOptions:{
            headers: {"Token": getToken()},
        },
        onLoadSuccess: function(){  //加载成功时执行
            bindImgError();
            $("#overlay").hide();
        },

    });
    obj.on('click-row.bs.table', function(e, row, $element) {
       //$($element).find("input[type='checkbox']").click()
    })
}

function getToken(){
    var Token=sessionStorage.getItem("Token");
    if(chechIsUnll(Token)){
        return Token;
    }
    location.href="../Admin/login.html";
}
function tableRefresh(){
    $('.bootstrap-table [name=refresh]').click()
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
        headers: {
            Token: getToken()
        },
        success: function (json) {
            $("#overlay").hide()
            console.log("获取查找数据",json)
            if (json.code == 1) {
                top.location.href="../Admin/login.html"
            }
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

function findByIDWithSummernote(that,id,url){
    $("#overlay").show()
    $.ajax({
        url: url+id,
        headers: {
            Token: getToken()
        },
        success: function (json) {
            $("#overlay").hide()
            console.log("获取查找数据",json)
            if (json.code == 1) {
                top.location.href="../Admin/login.html"
            }
            if (json.code == 0) {
                that.data=json.data;
                $('.summernote').code(that.data.content)
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
    scrollTo(0,0)
    $("#overlay").show()
    $.ajax({
        url: url,
        type: "POST",
        headers: {
            Token: getToken()
        },
        data: {data: JSON.stringify(that.data)},
        success: function (json) {
            console.log("获取到数据",json)
            $("#overlay").hide()
            if (json.code == 1) {
                top.location.href="../Admin/login.html"
            }
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

/**
 * 默认图片处理
 */
function bindImgError(){
    setTimeout(function(){
        $('img').each(function() {
            if (!this.complete || typeof this.naturalWidth == "undefined" || this.naturalWidth == 0) {
                this.src = "img/errorpic.jpg";
            }
        })
    },500);
}

function getRegionHead(that,type,ParentID){
    if(ParentID!=0&&ParentID!="0"){
        $.ajax({
            headers: {
                Token: getToken()
            },
            url:"../Region/queryRegionByParentID?ParentID="+ParentID,
            success:function(json){
                if (json.code == 1) {
                    top.location.href="../Admin/login.html"
                }
                if(type==1){
                    that.ProvinceList=json.data;
                    that.AreaID=0;that.CityID=0;
                }else if(type==2){
                    that.CityList=json.data;
                    that.AreaID=0;that.CityID=0;
                }else if(type==3){
                    that.AreaList=json.data;
                }
                tableRefresh();
            }
        })
    }else{tableRefresh()}
}

function getRegionHeadWithEdit(that,type,ParentID,f){
    if(ParentID!=0&&ParentID!="0"){
        $.ajax({
            headers: {
                Token: getToken()
            },
            async: false,
            url:"../Region/queryRegionByParentID?ParentID="+ParentID,
            success:function(json){
                if (json.code == 1) {
                    top.location.href="../Admin/login.html"
                }
                if(type==1){
                    that.ProvinceList=json.data;
                    if(f==0){
                        that.data.areaID=0;that.data.cityID=0;
                    }else{
                        that.getCity(f)
                    }
                }else if(type==2){
                    that.CityList=json.data;
                    if(f==0){
                        that.data.areaID=0;that.data.cityID=0;
                    }else{
                        that.getArea(f);
                    }
                }else if(type==3){
                    that.AreaList=json.data;
                }
            }
        })
    }
}
function initSwitch(){
    var elem = document.querySelector('.js-switch');
    var switchery = new Switchery(elem, {
        color: '#1AB394'
    });
}

function initDatepicker(){
    $('#datepicker').datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
}
/**
 * 时间轴格式化  yyyy--mm--dd
 * @param obj
 * @returns {string}
 */
function fmtDate(obj){
    var date =  new Date(obj);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}
/**
 * 时间格式化  yyyy--mm--dd
 * @param obj
 * @returns {string}
 */
function fmtDate2Data(obj){
    var date =  obj;
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}

/**
 * 几天之后的日期
 * @param time
 * @returns {Date}
 */
function laterTime(time){
    var myDate=new Date()
    myDate.setDate(myDate.getDate()+time)
    return myDate;
}

/**
 * 获取URL 前缀
 */
function getUrlPrefix(){
    var pathnames=location.pathname.split("/");
    var project="/"
    if(pathnames.length>=2&&pathnames[0]==""){
        project+=pathnames[1];
    }
    return location.origin+project;
}

/**
 * 文章分类编号
 * @returns {string}
 */
function getArticleType(){
    return "999914071648243712";
}
/**
 * 帮助分类
 * @returns {string}
 */
function getHelpType(){
    return "115080258167902208";
}
function sendFile(file, editor,welEditable) {
    var data = new FormData();
    data.append("file", file);
    console.log(data);
    $.ajax({
        data: data,
        type: "POST",
        url: Picture,
        cache: false,
        contentType: false,
        processData: false,
        success: function(json) {
            console.log(json);
            if(json.code==0){
                editor.insertImage(welEditable, json.data.url);//比较老得版本的做法
            }else{
                error(json.message);
            }

        }
    });
}

/**
 * 生成6位数字
 * @returns {string}
 * @constructor
 */
function MathRand()
{
    var Num="";
    for(var i=0;i<6;i++)
    {
        Num+=Math.floor(Math.random()*10);
    }
  return Num;
}

function initSummernote(H){
    $('.summernote').summernote({
        lang: 'zh-CN',
        height: H,
        //callbacks: { //版本太低智能用这个东西
        onImageUpload: function(files,editor, $editable) { //the onImageUpload API
            img = sendFile(files[0],editor,$editable);
        },
        //  }
        //toolbar:[["style",["style"]],["font",["bold","italic","underline","clear"]],["fontname",["fontname"]],["color",["color"]],["para",["ul","ol","paragraph"]],["height",["height"]],["table",["table"]],["insert",["link"]],["view",["fullscreen","codeview"]],["help",["help"]]]
    });

}
