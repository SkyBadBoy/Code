/**
 * Created by MaJian on 18/2/4.
 */
CheckLogin()
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
function CheckLogin(){
    var Login = sessionStorage.getItem('Login');
    if(Login !=1 && !check()){
        location.href=loginUrlView();
    }
}

function check(){
    var varCts = location.href;
    if(varCts.indexOf("login") >= 0 ){
        return true;
    }else{
        return false;
    }
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
    str+="&copy 2015-"+year.toString()+" 杭州互办网络科技有限公司";
    return str;
}

