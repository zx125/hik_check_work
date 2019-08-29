<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>layout 后台大布局 - Layui</title>
    <link rel="stylesheet" href="/css/layui.css" media="all">
</head>
<body class="layui-layout-body">

<div class="layui-layout layui-layout-admin">
    <jsp:include page="../header.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <button id="button1" class="layui-btn layui-bg-orange">迟到人员<span id="count1" class="layui-badge layui-bg-gray">${count1}</span></button>
            <button id="button2" class="layui-btn layui-bg-orange">异常进出<span id="count2" class="layui-badge layui-bg-gray">${count2}</span></button>
            <button id="button3" class="layui-btn layui-bg-orange">早退人员<span id="count3" class="layui-badge layui-bg-gray">${count3}</span></button>
            <div class="layui-inline">
                <label class="layui-form-label">开始日期</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" id="test1" placeholder="yyyy-MM-dd">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">结束日期</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" id="test2" placeholder="yyyy-MM-dd">
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
    layui.use(['table','laydate','element'], function(){
        var table = layui.table
            ,$=layui.jquery
            ,laydate = layui.laydate
            element = layui.element;
        $(document).ready(function(){
            $("#li1").addClass("layui-this");
            $("#d1").addClass("layui-this");
        });
        $.ajax({
            url:"godevice",
            type:"post",
            dataType:"json",
            success:function(data){
                layer.alert("初始化设备");
            }
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#test1' //指定元素
            ,value: new Date()
            ,type: 'datetime'
            ,min: '2017-8-11 12:30:00'
            ,max: '2019-8-18 12:30:00'
            ,done: function(value, date, endDate){
                 //value+date+endDate得到日期生成的值，如：2017-08-18 得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0} 得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                var day2 = $('#test2').val();
                $.ajax({
                    url: "/countDayState",
                    type: "POST",
                    data: {"day":value,"day2":day2,"state":2},
                    dataType:"text",
                    success: function (data) {
                        $("#count1").text(data);
                    },
                });
                $.ajax({
                    url: "/countDayState",
                    type: "POST",
                    data: {"day":value,"day2":day2,"state":4},
                    dataType:"text",
                    success: function (data) {
                        $("#count2").text(data);
                    },
                });
                $.ajax({
                    url: "/countDayState",
                    type: "POST",
                    data: {"day":value,"day2":day2,"state":3},
                    dataType:"text",
                    success: function (data) {
                        $("#count3").text(data);
                    },
                });
                // 搜索条件
                var day2 = $('#test2').val();
                laydate.render();
                tablezx.reload({
                    method: 'post'
                    , where: {
                        'day': value
                        ,'day2':day2
                    }
                    , page: {
                        curr: 1
                    }
                });
            }
        });
        //执行第二个laydate实例
        laydate.render({
            elem: '#test2' //指定元素
            ,type: 'datetime'
            ,value: new Date()
            ,min: '2017-8-11 12:30:00'
            ,max: '2019-8-18 12:30:00'
            ,done: function(value, date, endDate){
                //value+date+endDate得到日期生成的值，如：2017-08-18 得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0} 得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                // 搜索条件
                var day = $('#test1').val();
                var day2 = $('#test2').val();
                $.ajax({
                    url: "/countDayState",
                    type: "POST",
                    data: {"day":day,"day2":day2,"state":2},
                    dataType:"text",
                    success: function (data) {
                        $("#count1").text(data);
                    },
                });
                $.ajax({
                    url: "/countDayState",
                    type: "POST",
                    data: {"day":day,"day2":day2,"state":4},
                    dataType:"text",
                    success: function (data) {
                        $("#count2").text(data);
                    },
                });
                $.ajax({
                    url: "/countDayState",
                    type: "POST",
                    data: {"day":day,"day2":day2,"state":3},
                    dataType:"text",
                    success: function (data) {
                        $("#count3").text(data);
                    },
                });

                tablezx.reload({
                    method: 'post'
                    , where: {
                        'day': day
                        ,'day2':day2
                    }
                    , page: {
                        curr: 1
                    }
                });
            }
        });
        var tablezx=table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,where: {
                'day': "${today}",
                'day2':"${today}"
            }
            ,url: 'getAll' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'signInId', title: '序号', width:'5%', sort: true, fixed: 'left'}
                ,{field: 'name', title: '用户名', width:'10%'}
                ,{field: 'sex', title: '性别', width:'10%', sort: true}
                ,{field: 'dname', title: '部门', width:'20%'}
                ,{field: 'pname', title: '岗位', width:'15%'}
                ,{field: 'date', title: '签到时间', width:'25%'}
                ,{field: 'stateName', title: '签到状态', width:'10%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'5%'}
            ]]
        });
        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;//获取当前行的数据
            if(obj.event === 'del'){
                layer.confirm('真的删除行么'+data.sId, function (index) {
                    $.ajax({
                        url: "/delById",
                        type: "POST",
                        data: {"id":data.sId},
                        success: function () {
                            //删除这一行
                            obj.del();
                            //关闭弹框
                            layer.close(index);
                            layer.msg("删除成功", {icon: 6});
                        },
                        error:function() {
                            layer.alert("删除失败");
                        },
                    });
                    return false;
                });
            } else if(obj.event === 'edit'){
                layer.prompt({
                    formType: 2
                    ,value: data.email
                }, function(value, index){
                    obj.update({
                        email: value
                    });
                    layer.close(index);
                });
            }
            else if(obj.event === 'detail'){
                var day = $('#test1').val();
                location.href="timeLine?"+"signInId="+data.signInId+"&day="+day;
            }
        });
        //监听事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.msg('添加');
                    break;
                case 'delete':
                    layer.msg('删除');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            };
        });
        // 执行搜索，表格重载
        $('#button1').on('click', function () {
            // 搜索条件
            var day = $('#test1').val();
            var day2 = $('#test2').val();
            tablezx.reload({
                method: 'post'
                , where: {
                    'day': day
                    ,'day2':day2,
                    'state': 2,
                }
                , page: {
                    curr: 1
                }
            });
        });
        $('#button2').on('click', function () {
            // 搜索条件
            var day = $('#test1').val();
            var day2 = $('#test2').val();
            tablezx.reload({
                method: 'post'
                , where: {
                    'day': day
                    ,'day2':day2,
                    'state': 4,
                }
                , page: {
                    curr: 1
                }
            });
        });
        $('#button3').on('click', function () {
            // 搜索条件
            var day = $('#test1').val();
            var day2 = $('#test2').val();
            tablezx.reload({
                method: 'post'
                , where: {
                    'day': day
                    ,'day2':day2,
                    'state': 3,
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