/**
 * 加载js css
 * @type {*}
 *
 *
 */
var version="1.0.6";
var Head = document.getElementsByTagName('head')[0],style = document.createElement('style');
linkScriptDOMLoaded([
    "css/font-awesome.css",
    "css/bootstrap.min.css",
    "css/plugins/bootstrap-table/bootstrap-table.min.css",
    "css/animate.css",
    "css/style.css",
    "css/plugins/sweetalert/sweetalert.css",
    "css/plugins/summernote/summernote-bs3.css",
    "css/plugins/summernote/summernote.css",
    "css/plugins/switchery/switchery.css",
    "css/plugins/cropper/cropper.min.css",
    "js/plugins/layer/skin/layer.css",
    "css/plugins/toastr/toastr.min.css",
    "css/plugins/treeview/bootstrap-treeview.css",
    "css/plugins/iCheck/custom.css",
    "css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css",
    "css/plugins/clockpicker/clockpicker.css",
    "js/plugins/layer/skin/layer.css",

    "js/jquery.min.js",
    "js/bootstrap.min.js",
    "js/plugins/bootstrap-table/bootstrap-table.min.js",
    "js/plugins/bootstrap-table/bootstrap-table-mobile.min.js",
    "js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js",
    "js/plugins/bootstrap-table/bootstraptable-treeview.js",
    "js/plugins/fancybox/jquery.fancybox.js",
    "js/plugins/sweetalert/sweetalert.min.js",
    "js/vue.js",
    "js/plugins/cropper/cropper.min.js",
    "js/plugins/iCheck/icheck.min.js",
    "js/plugins/switchery/switchery.js",
    "js/plugins/layer/layer.min.js",
    "js/plugins/summernote/summernote.min.js",
    "js/plugins/summernote/summernote-zh-CN.js",
    "js/plugins/iCheck/icheck.min.js",
    "js/plugins/toastr/toastr.min.js",
    "js/plugins/suggest/bootstrap-suggest.min.js",
    "js/plugins/iCheck/icheck.min.js",
    "js/plugins/clockpicker/clockpicker.js",
    "js/plugins/datapicker/bootstrap-datepicker.js",
    "js/plugins/layer/laydate/laydate.js",
    "js/web/commonUrl.js",
    "js/web/commonUtil.js",
    "js/web/AdminUtil.js",
    "js/web/md5.js"
])


//文件全部加载完成显示DOM
function linkScriptDOMLoaded(parm){
    style.innerHTML = 'body{display:none}';//动态加载文件造成样式表渲染变慢，为了防止DOM结构在样式表渲染完成前显示造成抖动，先隐藏body，样式表读完再显示
    Head.insertBefore(style,Head.firstChild)
    var linkScript, linckScriptCount = parm.length, currentIndex = 0;

    for ( var i = 0 ; i < parm.length; i++ ){
        if(/\.css[^\.]*$/.test(parm[i])) {
            linkScript = document.createElement("link");
            linkScript.type = "text/" + ("css");
            linkScript.rel = "stylesheet";
            linkScript.href = ""+parm[i]+"?v="+version;
            linkScript.async=false;
        } else {
            linkScript = document.createElement("script");
            linkScript.type = "text/" + ("javascript");
            linkScript.src = ""+parm[i]+"?v="+version;
            linkScript.async=false;
        }
        Head.insertBefore(linkScript, Head.lastChild)
        linkScript.onload = linkScript.onerror = function(){
            currentIndex++;
            if(linckScriptCount == currentIndex){
                style.innerHTML = 'body{display:block}';
                Head.insertBefore(style,Head.lastChild)
            }
        }
    }

}


