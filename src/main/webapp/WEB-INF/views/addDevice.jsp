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
            <fieldset class="layui-elem-field layui-field-title">
                <legend>添加设备信息</legend>
            </fieldset>
            <form class="layui-form layui-form-pane" name="form" action="">
                <div class="layui-form-item">
                    <label class="layui-form-label">设备名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dname" lay-verify="required" placeholder="请输入设备名称" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">设备类型</label>
                    <div class="layui-input-inline">
                        <select name="dtype" lay-filter="test">
                            <option value="">请选择设备类型</option>
                            <option value="UNIVIEM">UNIVIEM</option>
                            <option value="DAHUA">DAHUA</option>
                            <option value="HIKVISION">HIKVISION</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">设备IP</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dip" lay-verify="required" placeholder="请输入设备IP"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">子网掩码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dnetMask" lay-verify="required" placeholder="请输入子网掩码"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">网关</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dgateWay" lay-verify="required" placeholder="请输入网关" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="duser" lay-verify="required" placeholder="请输入用户名" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dpassWord" lay-verify="required" placeholder="请输入密码" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">安装位置</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dposition" lay-verify="required" placeholder="请输入安装位置" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-inline layui-form" lay-filter="test1">
                    <label class="layui-form-label">使用部门</label>
                    <div class="layui-input-inline">
                        <select id="select3" name="departmentId" lay-filter="test2" lay-verify="required" lay-search="">
                            <option value="">请选择使用部门</option>
                        </select>
                    </div>
                </div>
                <div style="padding-top: 20px"></div>
                <div class="layui-form-item">
                    <button class="layui-btn" lay-submit="" lay-filter="demo2">保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="js/layui.js"></script>
<script>
    layui.use(['table', 'element','form'], function () {
        var table = layui.table
            , $ = layui.jquery
            ,form=layui.form
            , element = layui.element;
        $(document).ready(function () {
            $("#li2").addClass("layui-this");
            $("#d2").addClass("layui-this");
        });
        $.ajax({
            url: "/selectCascadeDepartmentName",
            type: "POST",
            dataType: "json",
            success: function (data) {
                $('#select3').html("");
                var content = '<option value="">请选择使用部门</option>';
                $.each(data, function (i, val) {
                    content += '<option value="' + val.rootId + '">' + val.title + '</option>';
                });
                $('#select3').html(content);
                form.render('select', 'test1');
            },
        });
        //监听提交
        form.on('submit(demo2)', function (data) {
            $.ajax({
                url: "/addDevice",
                type: "POST",
                async: false,//同步
                contentType: 'application/json',
                data: JSON.stringify(data.field),
                dataType: "text",
                success: function () {
                    window.location.href = 'goMachine';
                },

            });
            return false;
        });
    });
</script>
</body>
</html>