<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>layout 后台大布局 - Layui</title>
    <link rel="stylesheet" href="/css/layui.css" media="all">
    <style>
        #zx{
            width: 450px;
        }
        #test10{
            width: 300px;
        }
    </style>
</head>
<body class="layui-layout-body">

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
            <table id="demo" lay-filter="test" class="layui-hide"></table>
            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
            </script>
        </div>
    </div>
</div>
<script src="/js/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use(['table','laydate','element','form'], function() {
        var table = layui.table
            , $ = layui.jquery
            ,form=layui.form
            , laydate = layui.laydate
            ,element = layui.element;
        $(document).ready(function(){
            $("#li1").addClass("layui-this");
            $("#d2").addClass("layui-this");
        });
        var tablezx=table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,url: 'getstatment' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'staffId', title: 'ID', width:'5%', sort: true, fixed: 'left'}
                ,{field: 'name', title: '用户名', width:'10%'}
                ,{field: 'sex', title: '性别', width:'10%', sort: true}
                ,{field: 'dname', title: '部门', width:'20%'}
                ,{field: 'pname', title: '岗位', width:'10%'}
                ,{field: 'lateCount', title: '迟到', width:'5%'}
                ,{field: 'leaveCount', title: '早退', width:'5%'}
                ,{field: 'overtime', title: '加班（小时）', width:'10%'}
                ,{field: 'absenteeism', title: '旷工(天）', width:'10%'}
                ,{field: 'unswiped', title: '未刷卡次数', width:'10%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'5%'}
            ]]
        });
        //日期时间范围
        laydate.render({
            elem: '#test10'
            ,type: 'datetime'
            ,range: true
            ,done: function(value, date, endDate){
                var departmentId=$(" #select ").val();
                var name=$(" #select1 ").val();
                var arr=new Array();
                //可以用字符或字符串分割
                arr=value.split(' - ');
                tablezx.reload({
                    method: 'post'
                    , where: {
                        'departmentId': departmentId
                        ,'mTime':arr[0]
                        ,'eTime':arr[1]
                        ,'name':name
                    }
                    , page: {
                        curr: 1
                    }
                });
            }
        });
        form.on('select(test)', function (data) {
            $.ajax({
                url: "/selectStaffNameByDepartmentId",
                type: "POST",
                data: {"departmentId": data.value},
                dataType: "json",
                success: function (data) {
                    $('#select1').html("");
                    var content = '<option value="">请选择姓名</option>';
                    $.each(data, function (i, val) {
                        content += '<option value="' + val + '">' + val + '</option>';
                    });
                    $('#select1').html(content);
                    form.render('select', 'test1');
                },
                error:function () {
                    $('#select1').html("");
                    var content1 = '<option value="">请选择姓名</option>';
                    $('#select1').html(content1);
                    form.render('select','test1');
                }
            });
            var time=$(" #test10 ").val();
            var name=$(" #select1 ").val();
            var arr=new Array();
            //可以用字符或字符串分割
            arr=time.split(' - ');
            tablezx.reload({
                method: 'post'
                , where: {
                    'departmentId': data.value
                    ,'mTime':arr[0]
                    ,'eTime':arr[1]
                    ,'name':null
                }
                , page: {
                    curr: 1
                }
            });
        });
        form.on('select(test2)', function (data) {
            var time=$(" #test10 ").val();
            var arr=new Array();
            //可以用字符或字符串分割
            arr=time.split(' - ');
            var departmentId=$(" #select ").val();
            tablezx.reload({
                method: 'post'
                , where: {
                    'departmentId': departmentId
                    ,'mTime':arr[0]
                    ,'eTime':arr[1]
                    ,"name":data.value
                }
                , page: {
                    curr: 1
                }
            });
        });
    });
</script>
</body>
</html>