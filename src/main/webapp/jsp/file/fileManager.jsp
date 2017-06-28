<!doctype html>
<html data-ng-app="FileManagerApp">
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>文件系统</title>

    <!-- 第三方插件 -->
    <script src="webjars/angularjs/1.5.0/angular.min.js"></script>
    <script src="webjars/angular-translate/2.9.1/angular-translate.min.js"></script>
    <script src="webjars/ng-file-upload/12.0.1/ng-file-upload.min.js"></script>
    <script src="webjars/jquery/2.2.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="webjars/bootswatch-paper/3.3.5+4/css/bootstrap.min.css"/>

    <link href="dist/angular-filemanager.min.css" rel="stylesheet">
    <script src="dist/angular-filemanager.min.js"></script>

    <script type="text/javascript">
        // 重写默认Config配置
        angular.module('FileManagerApp').config(['fileManagerConfigProvider', function (config) {
            var defaults = config.$get();
            config.set({
                appName: 'angular-filemanager',
                defaultLang: 'zh',
                listUrl: '/fileManager/list/',
                uploadUrl: '/fileManager/upload/',
                downloadFileUrl: '/fileManager/preview/',
                createFolderUrl: '/fileManager/createFolder/',
                permissionsUrl: '/fileManager/changePermissions/',
                copyUrl: '/fileManager/copy/',
                moveUrl: '/fileManager/move/',
                removeUrl: '/fileManager/remove/',
                renameUrl: '/fileManager/rename/',
                getContentUrl: '/fileManager/getContent/',
                editUrl: '/fileManager/edit/',
                compressUrl: '/fileManager/compress/',
                extractUrl: '/fileManager/extract/',
                pickCallback: function (item) {
                    var msg = 'Picked %s "%s" for external use'
                            .replace('%s', item.type)
                            .replace('%s', item.fullPath());
                    window.alert(msg);
                },

                allowedActions: angular.extend(defaults.allowedActions, {
                    pickFiles: false,
                    pickFolders: false,
                    downloadMultiple: false
                }),
                tplPath: 'app/templates'
            });
        }]);
    </script>
</head>

<body class="ng-cloak">
<angular-filemanager></angular-filemanager>
</body>
</html>
