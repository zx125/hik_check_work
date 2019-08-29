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
        <div style="padding: 15px;">
            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-sm" lay-event="add">添加出差</button>
                    <button class="layui-btn layui-btn-sm" lay-event="add">批量审核</button>
                </div>
            </script>
            <table id="demo" lay-filter="test" class="layui-hide"></table>
            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-xs" lay-event="edit">审核</a>
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
            $("#li1").addClass("layui-this");
            $("#d4").addClass("layui-this");
        });
        table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,url: 'egetAll' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'evectionId', title: '编号', width:'10%', sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名', width:'10%'}
                ,{field: 'dname', title: '部门', width:'10%'}
                ,{field: 'estart', title: '出差开始时间', width:'15%'}
                ,{field: 'eend', title: '出差结束时间', width:'15%'}
                ,{field: 'stateName', title: '审核状态', width:'10%'}
                ,{field: 'edescribe', title: '出差说明', width:'25%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'5%'}
            ]]
        });
    });
</script>
</body>
</html>