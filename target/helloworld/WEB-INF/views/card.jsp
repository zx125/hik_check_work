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
    <jsp:include page="../header5.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <div style="padding: 15px;">
                <form class="layui-form" action="">
                    <div class="demoTable">
                        <div class="layui-inline">
                            <input class="layui-input" name="id" id="demoReload" placeholder="姓名" autocomplete="off">
                        </div>
                        <div class="layui-inline">
                            <input class="layui-input" name="id" id="demoReload1" placeholder="卡号" autocomplete="off">
                        </div>
                        <div class="layui-inline">
                            <input class="layui-input" name="id" id="demoReload2" placeholder="门权限" autocomplete="off">
                        </div>
                        <button class="layui-btn" data-type="reload">搜索</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </form>
                <table id="demo" lay-filter="test" class="layui-hide"></table>
                <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="add">添加卡号绑定</button>
                        <button class="layui-btn layui-btn-sm" lay-event="delete">批量删除</button>
                    </div>
                </script>
                <script type="text/html" id="barDemo">
                    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                </script>
        </div>
    </div>
</div>


<script src="js/layui.js"></script>
<script>
    layui.use(['table','element'], function(){
        var table = layui.table
            ,$=layui.jquery
            ,element=layui.element;
        $(document).ready(function(){
            $("#li6").addClass("layui-this");
            $("#d1").addClass("layui-this");
        });
        table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,url: 'selectAllCard' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'cardDeviceId', title: '序号', width:'10%', sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名', width:'10%'}
                ,{field: 'cardNumber', title: '卡号', width:'10%'}
                ,{field: 'dname', title: '门权限', width:'10%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'10%'}
            ]]
        });
    });
</script>
</body>
</html>