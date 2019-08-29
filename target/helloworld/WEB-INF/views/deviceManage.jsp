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
            <button class="layui-btn layui-bg-orange">注册设备<span id="out" class="layui-badge layui-bg-gray">1</span></button>
            <button class="layui-btn layui-bg-orange">为连接<span class="layui-badge layui-bg-gray">1</span></button>
            <button class="layui-btn layui-bg-orange">早退人员<span class="layui-badge layui-bg-gray">1</span></button>
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
            ,element=layui.element;
        var tableIns;
        //执行一个laydate实例
        laydate.render({
            elem: '#test1' //指定元素
            ,value: new Date()
            ,min: '2017-8-11 12:30:00'
            ,max: '2019-8-18 12:30:00'
            ,done: function(value, date, endDate){
                //value+date+endDate得到日期生成的值，如：2017-08-18 得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0} 得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                $.ajax({
                    url: "/notDayIn",
                    type: "POST",
                    data: {"day":value},
                    dataType:"text",
                    success: function (data) {
                        $("#out").text(data);
                        layer.msg(data);
                    },
                    error:function() {
                        layer.alert("删除失败");
                    },
                });
            }
        });
        // 执行搜索，表格重载
        $('#do_search').on('click', function () {
            // 搜索条件
            var send_name = $('#send_name').val();
            var send_data = $('#send_data').val();
            tableIns.reload( {
                method: 'post'
                , where: {
                    'csrfmiddlewaretoken': token,
                }
                , page: {
                    curr: 1
                }
            });
        tableIns=table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 800
            ,url: 'getAll' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'sId', title: 'ID', width:'5%', sort: true, fixed: 'left'}
                ,{field: 'name', title: '用户名', width:'10%'}
                ,{field: 'sex', title: '性别', width:'10%', sort: true}
                ,{field: 'dName', title: '部门', width:'15%'}
                ,{field: 'position', title: '岗位', width:'15%'}
                ,{field: 'date', title: '签到时间', width:'20%'}
                ,{field: 'state', title: '签到状态', width:'10%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'15%'}
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
            else {
                location.href="timeLine";
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
    });
</script>
</body>
</html>