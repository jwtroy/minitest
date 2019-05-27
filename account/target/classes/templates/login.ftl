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
<html lang="en" class="no-js">
<!--<![endif]-->

<head>
    <meta charset="UTF-8" />
    <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
    <title>统一登录平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="统一登录平台" />
    <meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class" />
    <meta name="author" content="Codrops" />
    <!--<link rel="shortcut icon" href="../favicon.ico">-->
    <link rel="stylesheet" type="text/css" href="css/login.css" />
    <#-- <link rel="stylesheet" type="text/css" href="css/demo.css" /> -->
    <#-- <link rel="stylesheet" type="text/css" href="css/style.css" /> -->
    <link rel="stylesheet" type="text/css" href="lib/layui/css/layui.css" />
    <style>
      

    </style>
</head>

<body>
    <div class="container">
        <section class="login">
            <article class="banner">
                <div class="logo">
                 <#--  style="width: 421px; height: 100px; display: block"  -->
                    <img src="image/login_logo.png" alt="">
                </div>
                <#--  <div class="intro">
                    <span>我来自偶然，像一粒尘埃</span>
                </div>  -->
            </article>
            <article class="form">
                <form class="layui-form login-form" name="confirmationForm" action="login" method="post">
                    <h2 class="login_title">用户登录</h2>
                    <div class="item">
                        <i class="layui-icon layui-icon-user"></i> 
                        <input type="text" name="username" required lay-verify="required" placeholder="用户名" autocomplete="off"
                            class="layui-input">
                    </div>
                    <div class="item">
                        <i class="layui-icon layui-icon-password"></i> 
                        <input type="password" name="password" required lay-verify="required" placeholder="密码" autocomplete="off"
                        class="layui-input">
                    </div>
                    
                  
                    
                    <#if RequestParameters['error']??>
                        <div class="eror">
                            用户名或密码错误
                        </div>
                    </#if>
                    <button type="submit" class="login-btn layui-btn layui-btn-normal">登录</button>
                </form>
            </article>
        </section>
    </div>
    <script src="webjars/jquery/3.1.0/jquery.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script>
        layui.use(['layer', 'form'], function () {
            var form = layui.form;
        })
    </script>
</body>


</html>