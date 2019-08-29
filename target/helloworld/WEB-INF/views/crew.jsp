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
            <table id="demo" lay-filter="test" class="layui-hide"></table>
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
        table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,url: 'cgetAll' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'cid', title: 'ID', width:'10%', sort: true, fixed: 'left'}
                ,{field: 'cmstart', title: '早签到时间：开始', width:'10%'}
                ,{field: 'cmend', title: '早签到时间：结束', width:'10%'}
                ,{field: 'cnstart', title: '晚签到时间：开始', width:'10%'}
                ,{field: 'cnend', title: '晚签到时间：结束', width:'10%'}
                ,{field: 'cstart', title: '签到有效时间：开始', width:'10%'}
                ,{field: 'cend', title: '签到有效时间：结束', width:'10%'}
                ,{field: 'cdescribe', title: '说明', width:'20%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'10%'}
            ]]
        });
    });
</script>
</body>
</html>