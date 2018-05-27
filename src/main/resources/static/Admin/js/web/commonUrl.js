/**
 * Created by MaJian on 18/2/4.
 *
 * view 代表视图  其他的代表接口
 */


var PictureBase64="http://www.jnrrxc.com/FileSystem/Code/UploadBase64";//图片路径 /{Project}/UploadBase64
var Picture="http://www.jnrrxc.com/FileSystem/Code/Upload";//图片路径 /{Project}/UploadBase64

var basePath="../";
var viewPath=""
function loginUrl(){//后台登录接口
    return basePath+"admin/login";
}
function userQueryUrl(){//后台登录接口
    return basePath+"user/queryPage";
}
function regionQueryUrl(){//地区接口
    return basePath+"region/queryPage";
}
function userStatus(){
    return basePath+"user/setStatus";
}
function getHomeInfo(){
    return basePath+"getHomeInfo";
}


function queryPageBox(){//盒子查询登录接口
    return basePath+"box/queryPageBox";
}
function queryPageRecord(){//盒子使用记录查询登录接口
    return basePath+"box/queryPageRecord";
}
function boxStatus(){
    return basePath+"box/setStatus";
}
function boxRecordStatus(){
    return basePath+"box/setRecordStatus";
}


function loginUrlView(){//登录地址
    return viewPath+"Admin/login.html";
}

function homeUrlView(){//首页地址
    return viewPath+"home.html";
}
function indexUrlView(){//主页地址
    return viewPath+"Admin/index.html";
}

function userUrlView(){//用户视图
    return viewPath+"Admin/user.html";
}
function adminUrlView(){//管理员视图
    return viewPath+"Admin/admin.html";
}
function boxUrlView(){//盒子视图
    return viewPath+"Admin/box.html";
}
function boxrecordUrlView(){//盒子记录视图
    return viewPath+"Admin/boxrecord.html";
}
function addressUrlView(){//盒子记录视图
    return viewPath+"Admin/region.html";
}