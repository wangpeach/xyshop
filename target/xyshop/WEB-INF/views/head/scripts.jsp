<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <script src="assets/scripts/sea.js"></script>
    <script>
        seajs.config({
            alias: {
                "jquery": "/xyshop/assets/base/jquery.min",
                "jqueryUtils": "/xyshop/assets/scripts/utils/jqueryUtils",
                "localStageUtils": "/xyshop/assets/scripts/utils/localStageUtils",
                "bootstrap": "/xyshop/assets/base/bootstrap",
                "table": "/xyshop/assets/base/plugins/bootstrap-table/bootstrap-table",
                "locale": "/xyshop/assets/base/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min",
                "export": "/xyshop/assets/base/plugins/bootstrap-table/extensions/export/bootstrap-table-export",
                "bootstrapedit": "/xyshop/assets/base/plugins/bootstrap-table/extensions/editable/bootstrap-editable",
                "tableedit": "/xyshop/assets/base/plugins/bootstrap-table/extensions/editable/bootstrap-table-editable",
                "upload": "/xyshop/assets/base/plugins/webuploader/webuploader.min",
                "tableUtils": "/xyshop/assets/scripts/utils/bootstrapTableUtils",
                "uploadUtils": "/xyshop/assets/scripts/utils/uploadUtils",
                "alertUtils": "/xyshop/assets/scripts/utils/alertUtils",
                "validateUtils": "/xyshop/assets/scripts/utils/validateUtils",
                "common": "/xyshop/assets/scripts/utils/common",
                "load": "/xyshop/assets/scripts/utils/superUse",
                "icheck": "/xyshop/assets/base/plugins/iCheck/icheck.min",
                "sweet": "/xyshop/assets/base/plugins/sweetalert/sweetalert.min",
                "validate": "/xyshop/assets/base/plugins/validate/jquery.validate.min.js",
                "messages_zh": "/xyshop/assets/base/plugins/validate/messages_zh.min.js",
                "ueditor": "/xyshop/assets/base/plugins/ueditor/ueditor.all.min.js",
                "ueconfig": "/xyshop/assets/base/plugins/ueditor/ueditor.config.js",
                "laydate": "/xyshop/assets/base/plugins/layer/laydate/laydate.js",
                "contabs": "/xyshop/assets/base/contabsiframs.js",
                "imgsUtils": "/xyshop/assets/scripts/utils/imgsShowUtils.js",
                "imgTip": "/xyshop/assets/base/plugins/imgtooptip/ToolTip.js",
                "time": "/xyshop/assets/scripts/utils/timeUtils.js",
                "echarts": "/xyshop/assets/base/plugins/echarts/echarts-all.js",
                "echartsUtils": "/xyshop/assets/scripts/utils/echartsUtils.js",
                "dateformat": "/xyshop/assets/scripts/utils/dateformat.js",
                "laydateUtils": "/xyshop/assets/scripts/utils/laydateUtils.js",
                "metisMenu": "/xyshop/assets/base/plugins/metisMenu/jquery.metisMenu.js",
                "slimscroll": "/xyshop/assets/base/plugins/slimscroll/jquery.slimscroll.min.js",
                "layer": "/xyshop/assets/base/plugins/layer/layer.min.js",
                "hplus": "/xyshop/assets/base/hplus.min.js",
                "contabs_index": "/xyshop/assets/base/contabs.min.js",
                "pace": "/xyshop/assets/base/plugins/pace/pace.min.js",
                "printarea": "/xyshop/assets/base/plugins/printarea/jquery.printarea.js",
                "ztree": "/xyshop/assets/base/plugins/zTree_v3/js/jquery.ztree.all.min.js",
                "utils": "/xyshop/assets/scripts/utils/utils.js",
                "tableexport": "/xyshop/assets/base/plugins/bootstrap-table/extensions/export/tableExport",
                "AMap": "http://webapi.amap.com/maps?v=1.3&key=4d2305dc2bee0fc8cdf3c4640ebe9bce",
                "videojs": "/xyshop/assets/base/plugins/video.js/dist/video.min.js",
            },
            charset: 'utf-8',
            preload: ["jquery", "bootstrap", "table"]
        });
    </script>