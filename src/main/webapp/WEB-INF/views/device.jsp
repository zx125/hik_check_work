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
    <jsp:include page="../header2.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <div style="padding: 15px;">
                <form class="layui-form" action="">
                    <div class="demoTable">
                        <div class="layui-inline">
                            <input class="layui-input" name="id" id="demoReload" placeholder="设备名称/设备ip" autocomplete="off">
                        </div>
                        <button class="layui-btn" data-type="reload">搜索</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </form>
                <table id="demo" lay-filter="test" class="layui-hide"></table>
                <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="add">添加设备</button>
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
            $("#li2").addClass("layui-this");
            $("#d1").addClass("layui-this");
        });
        var tablezx=table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            ,height: 'full-200'
            ,url: 'selectDeviceAll' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'deviceId', title: '序号', width:'4%', sort: true, fixed: 'left'}
                ,{field: 'departmentName', title: '使用部门', width:'10%'}
                ,{field: 'dname', title: '设备名称', width:'10%'}
                ,{field: 'dtype', title: '设备类型', width:'10%'}
                ,{field: 'dip', title: '设备IP', width:'10%'}
                ,{field: 'dnetMask', title: '子网掩码', width:'10%'}
                ,{field: 'dgateWay', title: '网关', width:'8%'}
                ,{field: 'duser', title: '用户名', width:'10%'}
                ,{field: 'dpassWord', title: '密码', width:'10%'}
                ,{field: 'dposition', title: '安装位置', width:'13%'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'7%'}
            ]]
        });
        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;//获取当前行的数据
            if(obj.event === 'del'){
                layer.confirm('真的删除行么'+data.deviceId, function (index) {
                    $.ajax({
                        url: "/delDevice",
                        type: "POST",
                        data: {"id":data.deviceId},
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
            }
            else if(obj.event === 'detail'){
            }
        });
        //监听事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.msg('添加');
                    window.location.href='goAddDevice';
                    break;
                case 'delete':
                    var data = checkStatus.data;
                    layer.confirm('真的删除所选行么', function (index) {
                        $.ajax({
                            url: "/delSomeDevice",
                            type: "POST",
                            contentType: "application/json;charset=UTF-8",//指定消息请求类型
                            data:JSON.stringify(data),
                            success: function () {
                                tablezx.reload({
                                    method: 'post'
                                    , page: {
                                        curr: 1
                                    }
                                });
                                layer.msg("删除成功", {icon: 6});
                            },
                            error:function() {
                                layer.alert("删除失败");
                            },
                        });
                        return false;
                    });
                    break;
            }
        });
    });
</script>
</body>
</html>