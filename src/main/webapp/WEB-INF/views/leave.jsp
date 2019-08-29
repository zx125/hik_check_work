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
            <div class="layui-inline" id="zx">
                <button class="layui-btn">时间范围选择</button>
                <div class="layui-inline" style="margin-top:0px">
                    <input name="vstime" type="text" class="layui-input" id="test10" placeholder=" - ">
                </div>
            </div>
            <div class="layui-input-inline layui-form">
                <select id="select" lay-filter="test">
                    <option value="">请选择部门</option>
                    <c:forEach var="name" items="${names}">
                        <option value="${name.departmentid}">${name.dname}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="layui-inline layui-form" lay-filter="test1">
                <%--                <label class="layui-form-label">搜索选择框</label>--%>
                <div class="layui-input-inline">
                    <select id="select1" name="modules" lay-filter="test2" lay-verify="required" lay-search="">
                        <option value="">请选择姓名</option>
                    </select>
                </div>
            </div>
            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
                </div>
            </script>
            <table id="demo" lay-filter="test" class="layui-hide"></table>
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
            $("#d7").addClass("layui-this");
        });
        table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,url: 'lgetAll' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'leaveId', title: '序号', width:'5%', sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名', width:'5%'}
                ,{field: 'dName', title: '部门', width:'10%'}
                ,{field: 'ltype', title: '类型', width:'10%'}
                ,{field: 'lstart', title: '开始时间', width:'15%'}
                ,{field: 'lend', title: '结束时间', width:'15%'}
                ,{field: 'ldescribe', title: '说明', width:'40%'}
            ]]
        });
    });
</script>
</body>
</html>