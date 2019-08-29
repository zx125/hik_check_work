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
    <link rel="stylesheet" href="/css/layui.css" media="all">
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <jsp:include page="../header4.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <form class="layui-form" action="">
                <div class="demoTable">
                    <div class="layui-inline">
                        <input class="layui-input" name="id" id="demoReload" placeholder="部门名称" autocomplete="off">
                    </div>
                    <button class="layui-btn" data-type="reload">搜索</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </form>
            <div id="test1"></div>
        </div>
    </div>
</div>


<script src="/js/layui.js"></script>
<script>
    layui.use(['tree','util','element','layer'], function(){
        var tree = layui.tree
            ,layer = layui.layer
            ,util = layui.util
            ,$=layui.jquery
            ,element=layui.element;

        $(document).ready(function(){
            $("#li4").addClass("layui-this");
            $("#d1").addClass("layui-this");
        });
        init();
        function init(){
            $.ajax({
                url: "/DepartmentText",
                type: "POST",
                dataType:"json",
                success: function (data) {
                    //可以重载所有基础参数
                    tree.reload('demoId', {
                        data:data
                    });
                },
                error:function() {
                    layer.alert("数据加载失败");
                },
            });
        };

        //渲染
        tree.render({
            elem: '#test1'
            ,id: 'demoId'//绑定元素
            ,edit: ['add', 'update', 'del'] //操作节点的图标
            ,click: function(obj){
                layer.msg(JSON.stringify(obj.data));
            },operate: function(obj){
                var type = obj.type; //得到操作类型：add、edit、del
                var data = obj.data; //得到当前节点的数据
                var elem = obj.elem; //得到当前节点元素
                //Ajax 操作
                var id = data.rootId; //得到节点索引
                var id1=data.parentId;
                if(type === 'add'){ //增加节点
                    //返回 key 值
                    $.ajax({
                        url: "/addDepartment",
                        type: "POST",
                        data:{"rootId":id1},
                        contentType: "application/json;charset=UTF-8",//指定消息请求类型
                        dataType:"text",
                        success: function (data) {
                            //可以重载所有基础参数
                        },
                        error:function() {
                            layer.alert("数据加载失败");
                        },
                    });

                } else if(type === 'update'){ //修改节点

                } else if(type === 'del'){ //删除节点

                };
            }
        });
    });
</script>
</body>
</html>