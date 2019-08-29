<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/5
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui.form小例子</title>
    <link rel="stylesheet" href="css/layui.css" media="all">
    <style>
        .layui-form-pane .layui-form-label {
            width: 200px;
        }

        #textear {
            width: 90%;
        }

        #zx {
            width: 900px;
        }

        #test10 {
            width: 300px;
        }
    </style>
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <jsp:include page="../header.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>添加假期或者上班信息</legend>
            </fieldset>
            <form class="layui-form layui-form-pane" name="form1" action="">
                <div class="layui-form-item">
                    <label class="layui-form-label">名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="vname" lay-verify="required" placeholder="请输入" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">描述</label>
                    <div class="layui-input-block">
                        <input id="textear" type="text" name="descripe" autocomplete="off" placeholder="请输入假期描述" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">假期或者上班</label>
                    <div class="layui-input-inline">
                        <select name="state">
                            <option value="1">假期</option>
                            <option value="2" selected="">上班</option>
                        </select>
                    </div>
                    <div class="layui-inline" id="zx">
                        <label class="layui-form-label">日期时间范围</label>
                        <div class="layui-input-inline">
                            <input name="time" type="text" class="layui-input" id="test10" placeholder=" - ">
                        </div>
                    </div>
                    <div style="height: 20px;"></div>
                    <div class="layui-form-item">
                        <button class="layui-btn" lay-submit="" lay-filter="demo2">添加</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="js/layui.js"></script>
<script>
    layui.use(['form', 'element', 'laydate', 'upload'], function () {
        var form = layui.form
            , laydate = layui.laydate
            , $ = layui.jquery
            , upload = layui.upload
            , element = layui.element;
        //日期时间范围
        laydate.render({
            elem: '#test10'
            , type: 'datetime'
            , range: true
        });
        //监听提交
        form.on('submit(demo2)', function (data) {
            $.ajax({
                url: "/addVacation",
                type: "POST",
                async: false,//同步
                contentType: 'application/json',
                data: JSON.stringify(data.field),
                dataType: "text",
                success: function () {
                    window.location.href = 'govacation';
                },
                error: function () {
                    layer.alert("数据添加失败");
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
