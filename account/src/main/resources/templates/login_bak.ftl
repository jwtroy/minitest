<!DOCTYPE html>
<!--[if lt IE 7 ]>
<html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>
<html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>
<html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>
<html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js"> <!--<![endif]-->
<head>
    <meta charset="UTF-8"/>
    <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
    <title>统一登录平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="统一登录平台"/>
    <meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class"/>
    <meta name="author" content="Codrops"/>
    <!--<link rel="shortcut icon" href="../favicon.ico">-->
    <link rel="stylesheet" type="text/css" href="css/demo.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <script src="webjars/jquery/3.1.0/jquery.js"></script>
    <!--<link rel="stylesheet" type="text/css" href="css/animate-custom.css"/>-->
    <script>
        $(function () {
            $("#container_demo").on('click', '.change_link', function (evt, e1, e2) {
                alert("请联系管理员获得或者重置密码！");
            });
        })
    </script>
</head>
<#if stylePage == "st">
<body>
<div class="container">
    <div class="head-bg">
        <div class="codrops-top">
            <div class="head-img">
                <div>
                <#if logoEnable=='true'> <img src="image/logo.png" class="login-logo-class">
                <#else><img src="image/logo-hidden.png" class="login-logo-class">
                </#if>
                </div>
                <div class="title-img-div">
                    <div class="zn-font-class">${titleZn}</div>
                    <div class="en-font-class">${titleEn}</div>
                <#--<#if regionCode=='320000'><img src="image/title.png"><#elseif regionCode='320400'>-->
                <#--<img src="image/titleCz.png"><#elseif regionCode='320700'>-->
                <#--<img src="image/titleLYG.png"></#if>-->
                </div>
            <#--<div class="register-div">
                <button class="register-button">用户注册</button>
            </div>-->
            </div>
        </div>
    </div>
    <div class="main-contain">
        <div class="body-style">
            <div class="computer">
                <img src="image/computer.png">
            </div>
            <div class="bg-register">

            <#if regionCode=='320000'><img src="image/bg_register_st.png"><#elseif regionCode!='320400'>
                <img src="image/bg_register.png"></#if>
            </div>
            <div id="container_demo">
                <div id="wrapper">
                    <div id="login" class="animate form">
                        <form id="confirmationForm" name="confirmationForm"
                              action="login" method="post">
                            <h1>
                                <img src="image/title_icon.png" class="login-icon">用户登录
                            </h1>

                            <div>
                                <img src="image/denglukuang_04.png" class="login-user">
                                <input class="login-input"
                                       name="username"
                                       required="required"
                                       type="text"
                                       placeholder="请输入用户名"/>
                            </div>
                            <div class="div-m">
                                <img src="image/passwordkuang_07.png" class="login-user">
                                <input class="login-input"
                                       id="" name="password" required="required" type="password" placeholder="请输入密码"/>
                            </div>
                            <p class="change_link">
                                忘记密码?
                            </p>
                        <#if RequestParameters['error']??>
                            <div class="eror">
                                用户名或密码错误
                            </div>
                        </#if>
                            <p class="login button">
                                <input type="submit" value="立即登录"/>
                            </p>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <div class="foot-div"><img src="image/c.png" class="foot-img">
            <span>${copyright}</span>
        <#--<#if regionCode=='320000'><span>版权所有 江苏省国土资源信息中心</span><#elseif regionCode=='320400'>-->
        <#--<span>版权所有 常州市国土资源信息中心</span><#elseif regionCode=='320700'>-->
        <#--<span>版权所有 连云港市国土资源信息中心</span></#if>-->
        </div>
    </div>
</div>
</body>
<#elseif stylePage == "lyg">
<body class="body-lyg">
<div class="container">
    <div class="login-bg">

        <div class="title-img-div-lyg">
            <div class="zn-font-class-lyg"><img src="image/logo.png" class="lyg-title-logo"><span>${titleZn}</span>
            </div>
        </div>
        <div class="container-input">
            <img src="image/lyg-input.png">
        </div>
        <div id="container_demoLyg">
            <div id="wrapperLyg">
                <div id="loginLyg" class="animate form">
                    <form id="confirmationForm" name="confirmationForm"
                          action="login" method="post">
                        <h1>
                            <img src="image/title_icon.png" class="login-icon-lyg">用户登录
                        </h1>

                        <div>
                            <img src="image/lyg-user.png" class="login-user-lyg"><input class="login-input-lyg"
                                                                                        name="username"
                                                                                        required="required"
                                                                                        type="text"
                                                                                        placeholder="请输入用户名"/>
                        </div>
                        <div class="div-m-lyg">
                            <img src="image/lyg-password.png" class="login-user-lyg">
                            <input class="login-input-lyg"
                                   id="" name="password" required="required" type="password" placeholder="请输入密码"/>
                        </div>
                    <#--<p class="change-link-lyg">-->
                    <#--忘记密码?-->
                    <#--</p>-->
                        <#if RequestParameters['error']??>
                            <div class="eror">
                                用户名或密码错误
                            </div>
                        </#if>
                        <p class="login button">
                            <input type="submit" value="立即登录"/>
                        </p>

                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="foot-div-lyg">
        &copy;<span>${copyright}</span>
    <#--<#if regionCode=='320000'><span>版权所有 江苏省国土资源信息中心</span><#elseif regionCode=='320400'>-->
    <#--<span>版权所有 常州市国土资源信息中心</span><#elseif regionCode=='320700'>-->
    <#--<span>版权所有 连云港市国土资源信息中心</span></#if>-->
    </div>
</div>
</body>
</#if>

</html>