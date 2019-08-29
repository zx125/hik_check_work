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
        <div style="padding: 15px">
            <div style="width: 840px;float: left">
                <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="add">添加假期</button>
                        <button class="layui-btn layui-btn-sm layui-bg-orange" style="width: 150px;margin-left: 250px">节假日表格</button>
                    </div>
                </script>
                <table id="demo" lay-filter="test" class="layui-hide"></table>
                <script type="text/html" id="barDemo">
                    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                </script>
            </div>
            <div style="width: 840px;float: left">
                <script type="text/html" id="toolbarDemo1">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="add">添加上班日</button>
                        <button class="layui-btn layui-btn-sm layui-bg-orange" style="width: 150px;margin-left: 250px">必须上班日</button>
                    </div>
                </script>
                <table id="demo1" lay-filter="test" class="layui-hide"></table>
                <script type="text/html" id="barDemo1">
                    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                </script>
            </div>
        </div>
    </div>
</div>


<script src="js/layui.js"></script>
<script>
    layui.use(['form','table','element','laydate'], function(){
        var table = layui.table
            ,$=layui.jquery;
        $(document).ready(function(){
            $("#li1").addClass("layui-this");
            $("#d6").addClass("layui-this");
        });
        table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: '600px'
            ,url: 'selectVacationAll' //数据接口
            ,where: {
                'state': "1"
            }
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'vacationId', title: '序号', width:'10%', sort: true, fixed: 'left'}
                ,{field: 'vstime', title: '假期开始时间', width:'20%'}
                ,{field: 'vetime', title: '假期结束时间', width:'20%'}
                ,{field: 'vname', title: '假期名称', width:'15%'}
                ,{field: 'descripe', title: '假期说明', width:'20%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo1', width:'15%'}
            ]]
        });
        table.render({
            elem: '#demo1'
            ,toolbar: '#toolbarDemo1'
            ,height: '600px'
            ,url: 'selectVacationAll' //数据接口
            ,where: {
                'state': "2"
            }
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'vacationId', title: '序号', width:'10%', sort: true, fixed: 'left'}
                ,{field: 'vstime', title: '必须上班开始时间', width:'20%'}
                ,{field: 'vetime', title: '必须上班结束时间', width:'20%'}
                ,{field: 'vname', title: '上班名称', width:'15%'}
                ,{field: 'descripe', title: '上班说明', width:'20%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo1', width:'15%'}
            ]]
        });
        //监听事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.msg('添加');
                    window.location.href='addVacationView';
                    break;
            };
        });
        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;//获取当前行的数据
            if(obj.event === 'del'){
                layer.confirm('真的删除行么'+data.vacationId, function (index) {
                    $.ajax({
                        url: "/delVacationById",
                        type: "POST",
                        data: {"vacationId":data.vacationId},
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

            }
        });
    });
</script>
</body>
</html>