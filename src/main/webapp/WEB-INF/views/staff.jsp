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
    <jsp:include page="../header3.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <form class="layui-form" action="">
            <div class="demoTable">
                <div class="layui-inline">
                    <input class="layui-input" name="id" id="demoReload" placeholder="姓名/编号/卡号" autocomplete="off">
                </div>
                <button class="layui-btn" data-type="reload">搜索</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
            </form>
            <table id="demo" lay-filter="test" class="layui-hide"></table>
            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-sm" lay-event="add">添加人员</button>
                    <button class="layui-btn layui-btn-sm" lay-event="delete">批量删除</button>
                    <button class="layui-btn layui-btn-sm" lay-event="update">批量导入</button>
                </div>
            </script>
            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
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
            $("#li5").addClass("layui-this");
            $("#d1").addClass("layui-this");
        });
        table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,url: 'selectStaffDetailAll' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'staffId', title: '编号', width:'5%', sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名', width:'10%'}
                ,{field: 'sex', title: '性别', width:'5%'}
                ,{field: 'birth', title: '生日', width:'10%'}
                ,{field: 'cardNumber', title: '卡号', width:'5%'}
                ,{field: 'dname', title: '部门', width:'10%'}
                ,{field: 'pname', title: '职务', width:'5%'}
                ,{field: 'telphone', title: '联系方式', width:'10%'}
                ,{field: 'qq', title: 'QQ', width:'10%'}
                ,{field: 'email', title: '邮箱', width:'10%'}
                ,{field: 'workState', title: '职位状态', width:'10%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'10%'}
            ]]
        });
        //监听事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.msg('添加');
                    window.location.href='addStaffView';
                    break;
                case 'delete':
                    layer.msg('删除');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            };
        });
    });
</script>
</body>
</html>