<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/29
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui.form小例子</title>
    <link rel="stylesheet" href="css/layui.css" media="all">
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <jsp:include page="../header.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <form class="layui-form" style="padding: 15px;"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
            <div class="layui-form-item">
                <label class="layui-form-label">下拉选择框</label>
                <div class="layui-input-block">
                    <select name="interest" lay-filter="aihao">
                        <option value="0">写作</option>
                        <option value="1">阅读</option>
                    </select>
                </div>
            </div>
            <!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
        </form>
    </div>
</div>


<script src="js/layui.js"></script>
<script>
    layui.use(['form','element'], function(){
        var form = layui.form
        element=layui.element;

        //各种基于事件的操作，下面会有进一步介绍
    });
</script>
</body>
</html>