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
        $(document).ready(function () {
            var loginName ="${loginName}";
            var password = "${password}";
            $("#username").val(loginName);
            $("#password").val(password);
            $("#confirmationForm").submit();
        });
    </script>
</head>
<body>
<div class="container">
    <div class="loginOtherSys" style="display: none;">
        <form id="confirmationForm" name="confirmationForm"
              action="login" method="post">
            <h1>
                <img src="image/title_icon.png" class="login-icon">用户登录
            </h1>

            <div>
                <img src="image/denglukuang_04.png" class="login-user"><input class="login-input"
                                                                              id="username"
                                                                              name="username"
                                                                              required="required"
                                                                              type="text"
                                                                              placeholder="请输入用户名"/>
            </div>
            <div class="div-m">
                <img src="image/passwordkuang_07.png" class="login-user">
                <input class="login-input"
                       id="password" name="password" required="required" type="password" placeholder="请输入密码"/>
            </div>
            <input
                   id="map" name="map" type="hidden" value=""/>
        </form>
    </div>
</div>
</body>
</html>