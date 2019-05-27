<!doctype html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="webjars/angular-material/1.1.0/angular-material.min.css">
    <style type="text/css">
        html, body {
            font-family: "Consolas", "Microsoft Yahei", Arial, monospace;
            min-width: 420px;
            min-height: 680px;
        }
        .container{
            width: 100%;
            height: 100%;
        }

        .container .bg{
            position: absolute;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
        
        .bg .t{
            /*position: absolute;
            top: 0;
            background: #46B6EC;
            width: 100%;
            height: 40%;
            box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.15);*/

            background: #46B6EC;
            position: absolute;
            left: -5%;
            top: -20%;
            width: 120%;
            height: 58%;
            transform: rotate(355deg);
            box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.15);
        }
        .bg .c{
            position: absolute;
            top: 30%;
            background: url("image/u0.png") no-repeat;
            background-size: cover;
            width: 100%;
            height: 42%;
        }
        .bg .b{
            /*position: absolute;
            width: 100%;
            height: 40%;
            bottom: 0;
            background: #3A589B;
            box-shadow: -5px -5px 10px rgba(0, 0, 0, 0.15);*/

            position: absolute;
            width: 120%;
            height: 58%;
            bottom: -20%;
            left: -5%;
            background: #3A589B;
            transform: rotate(355deg);
            box-shadow: -5px -5px 10px rgba(0, 0, 0, 0.15);
        }

        md-content{
            background-color: transparent!important;
            height: 100%;
        }

        .c-h{
            height: 100%;
        }

        .logo{
            color: #fff;
        }
        .logo img, .logo .t{
            float: left;
        }
        .logo .t{
            font-style: normal;
        }
        .logo p{
            font-weight: normal;
            font-style: normal;
            margin: 0 10px 10px;
        }
        .logo .t1{
            font-size: 28px;
        }
        .logo .t2{
            font-size: 14px
        }
        .footer{
            color: #ffffff;
            font-size: 13px;
            padding-left: 80px;
        }
        .md-headline{
            font-size: 18px;
            color: #46B6EC;
        }
        md-card{
            width: 320px;
            height: 380px;
            /*box-shadow:0 4px 4px 8px rgba(0,0,0,0.15);*/
        }
        md-card-title{
            padding-left: 0!important;
        }
        md-card-title-text{
            padding-top: 5px;
            font-weight: bold !important;
        }
        md-card-content{
            padding: 10px 30px !important;
        }
        .tl{
            width: 6px;
            height: 42px;
            background: #46B6EC;
            display: block;
            margin-right: 30px;
        }
        .md-primary{
            background: #46B6EC!important;
        }
        .w{
            color: #D7A1E9;
        }

        
    </style>
</head>
<body ng-app="loginApp" ng-cloak>
<div class="container">
    <div class="bg">
        <div class="c"></div>
        <div class="t"></div>
        <div class="b"></div>
    </div>

    <md-content>
        <div layout="row" class="c-h">
            <div flex="10"></div>
            <div flex="80">
                <div layout="column" class="c-h">
                    <div flex="20" layout="row" layout-align="start end">
                        <div class="logo">
                            <img src="${brand.logo!}">
                            <div class="t">
                                <p>
                                    <span class="t1">${brand.title!}</span>
                                </p>
                                <p>
                                    <span class="t2">${brand.subTitle!}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div flex="60" layout-align="center end"  layout="column">
                        <div flex-lg flex-lg="50"  layout="column">
                            <md-card md-whiteframe="6">
                                <form  role="form" action="login" method="post">
                                    <md-card-title>
                                        <div class="tl"></div>
                                        <md-card-title-text>
                                            <span class="md-headline">登录</span>
                                        </md-card-title-text>

                                    </md-card-title>
                                    <md-card-content>
                                        <md-input-container class="md-icon-float md-block">
                                            <label>账户名称</label>
                                            <input ng-model="user.name" type="text" name="username">
                                        </md-input-container>
                                        <md-input-container class="md-icon-float md-block">
                                            <label>账户密码</label>
                                            <input ng-model="user.password" type="password" name="password">
                                        </md-input-container>
                                        <#if _csrf??>
                                            <input type="hidden" id="csrf_token" name="${_csrf.parameterName!}" value="${_csrf.token!}"/>
                                        </#if>

                                    <#if RequestParameters['error']??>
                                        <div class="w">
                                            登陆有误
                                        </div>
                                    </#if>
                                    </md-card-content>

                                    <md-card-actions layout="row" layout-align="center center">
                                        <md-button class="md-raised md-primary" type="submit">登录</md-button>
                                    </md-card-actions>

                                </form>

                            </md-card>
                        </div>
                    </div>
                    <div flex="20" class="footer">
                        <p>
                            <span>版权所有©南京国图信息产业有限公司 </span>
                        </p>
                        <p>
                            <span>Copyright©2017 All Rights Reserved</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </md-content>

</div>

<script src="webjars/angularjs/1.5.8/angular.min.js"></script>
<script src="webjars/angularjs/1.5.8/angular-animate.min.js"></script>
<script src="webjars/angularjs/1.5.8/angular-aria.min.js"></script>
<script src="webjars/angularjs/1.5.8/angular-messages.min.js"></script>
<script src="webjars/angular-material/1.1.0/angular-material.min.js"></script>
<script type="text/javascript">
    angular.module('loginApp', ['ngMaterial']);
</script>
</body>
</html>