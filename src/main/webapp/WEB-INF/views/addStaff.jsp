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
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <jsp:include page="../header3.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>添加人员信息</legend>
            </fieldset>
            <form class="layui-form layui-form-pane" name="form1" action="">
                <%--                <div class="layui-form-item">--%>
                <%--                    <label class="layui-form-label">长输入框</label>--%>
                <%--                    <div class="layui-input-block">--%>
                <%--                        <input type="text" name="title" autocomplete="off" placeholder="请输入标题" class="layui-input">--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <div class="layui-form-item">
                    <label class="layui-form-label">编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="staffId" lay-verify="required" placeholder="请输入编号" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" lay-verify="required" placeholder="请输入姓名" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item" pane="">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="sex" value="男" title="男" checked="">
                        <input type="radio" name="sex" value="女" title="女">
                        <%--                        <input type="radio" name="sex" value="禁" title="禁用" disabled="">--%>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">出生日期</label>
                        <div class="layui-input-block">
                            <input type="text" name="birth" id="date1" autocomplete="off" class="layui-input" placeholder="请选择生日">
                        </div>
                    </div>
                    <%--                    <div class="layui-inline">--%>
                    <%--                        <label class="layui-form-label">行内表单</label>--%>
                    <%--                        <div class="layui-input-inline">--%>
                    <%--                            <input type="text" name="number" autocomplete="off" class="layui-input">--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">部门岗位</label>
                    <div class="layui-input-inline">
                        <select name="departmentId" lay-filter="test">
                            <option value="">请选择部门</option>
                            <c:forEach var="name" items="${names}">
                                <option value="${name.departmentid}">${name.dname}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="layui-input-inline layui-form" lay-filter="test1">
                        <select id="select1">
                            <option value="">请选择职位</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式</label>
                    <div class="layui-input-inline">
                        <input type="text" name="telphone" lay-verify="required" placeholder="请输入联系方式"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">QQ</label>
                    <div class="layui-input-inline">
                        <input type="text" name="qq" lay-verify="required" placeholder="请输入QQ"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-inline">
                        <input type="text" name="eMail" lay-verify="required" placeholder="请输入邮箱" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>

                <%--                <div class="layui-form-item">--%>
                <%--                    <label class="layui-form-label">密码</label>--%>
                <%--                    <div class="layui-input-inline">--%>
                <%--                        <input type="password" name="password" placeholder="请输入密码" autocomplete="off"--%>
                <%--                               class="layui-input">--%>
                <%--                    </div>--%>
                <%--                    <div class="layui-form-mid layui-word-aux">请务必填写用户名</div>--%>
                <%--                </div>--%>

                <%--                <div class="layui-form-item">--%>
                <%--                    <div class="layui-inline">--%>
                <%--                        <label class="layui-form-label">范围</label>--%>
                <%--                        <div class="layui-input-inline" style="width: 100px;">--%>
                <%--                            <input type="text" name="price_min" placeholder="￥" autocomplete="off" class="layui-input">--%>
                <%--                        </div>--%>
                <%--                        <div class="layui-form-mid">-</div>--%>
                <%--                        <div class="layui-input-inline" style="width: 100px;">--%>
                <%--                            <input type="text" name="price_max" placeholder="￥" autocomplete="off" class="layui-input">--%>
                <%--                        </div>--%>
                <%--                    </div>--%>
                <%--                </div>--%>

                <%--                <div class="layui-form-item">--%>
                <%--                    <label class="layui-form-label">单行选择框</label>--%>
                <%--                    <div class="layui-input-block">--%>
                <%--                        <select name="interest" lay-filter="aihao">--%>
                <%--                            <option value=""></option>--%>
                <%--                            <option value="0">写作</option>--%>
                <%--                            <option value="1" selected="">阅读</option>--%>
                <%--                            <option value="2">游戏</option>--%>
                <%--                            <option value="3">音乐</option>--%>
                <%--                            <option value="4">旅行</option>--%>
                <%--                        </select>--%>
                <%--                    </div>--%>
                <%--                </div>--%>

                    <div class="layui-form-item">
                        <label class="layui-form-label">在职状态</label>
                        <div class="layui-input-inline layui-form" lay-filter="test1">
                            <select id="select3">
                                <option value="">请选择在职状态</option>
                                <option value="">离职</option>
                                <option value="">就职</option>
                            </select>
                        </div>
                    </div>
<%--                <div class="layui-form-item">--%>
<%--                    <label class="layui-form-label">排班计划</label>--%>
<%--                    <div class="layui-input-inline">--%>
<%--                        <select name="cid" lay-filter="test2">--%>
<%--                            <option value="">请选择计划</option>--%>
<%--                            <c:forEach var="des" items="${crew}">--%>
<%--                                <option value="${des.cid}">${des.cdescribe}</option>--%>
<%--                            </c:forEach>--%>
<%--                        </select>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <%--                <div class="layui-form-item" pane="">--%>
                <%--                    <label class="layui-form-label">开关-开</label>--%>
                <%--                    <div class="layui-input-block">--%>
                <%--                        <input type="checkbox" checked="" name="open" lay-skin="switch" lay-filter="switchTest"--%>
                <%--                               title="开关">--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <%--                <div class="layui-form-item layui-form-text">--%>
                <%--                    <label class="layui-form-label">说明</label>--%>
                <%--                    <div class="layui-input-block">--%>
                <%--                        <textarea name="" placeholder="请输入内容" class="layui-textarea"></textarea>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <div class="layui-upload">
                    <button type="button" class="layui-btn" id="test1">请选择照片</button>
                    <input type="text" id="photo" name="photo" style="display:none">
                    <div class="layui-upload-list">
                        <img class="layui-upload-img" id="demo1">
                        <p id="demoText"></p>
                    </div>
                </div>
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
    layui.use(['form', 'element', 'laydate', 'upload'], function () {
        var form = layui.form
            , laydate = layui.laydate
            , $ = layui.jquery
            , upload = layui.upload
            , element = layui.element;
        $(document).ready(function () {
            $("#li5").addClass("layui-this");
            $("#d2").addClass("layui-this");
        });
        laydate.render({
            elem: '#date1'
        });
        //监听提交
        form.on('submit(demo2)', function (data) {
            $.ajax({
                url: "/addStaff",
                type: "POST",
                async: false,//同步
                contentType: 'application/json',
                data: JSON.stringify(data.field),
                dataType: "text",
                success: function () {
                    window.location.href = 'goStaff';
                },

            });
            return false;
        });
        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            , url: '/upload'
            , accept: 'images' //允许上传的文件类型
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                }
                //上传成功
                $('#photo').val(res.data.src);
                layer.alert(res.data.src);
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
        form.on('select(test)', function (data) {
            $.ajax({
                url: "/selectPosition",
                type: "POST",
                data: {"id": data.value},
                dataType: "json",
                success: function (data) {
                    $('#select1').html("");
                    var content = '<option value="">请选择职位</option>';
                    $.each(data, function (i, val) {
                        content += '<option value="' + val.positionId + '">' + val.pname + '</option>';
                    });
                    $('#select1').html(content);
                    form.render('select', 'test1');
                },
            });
        });
    });
</script>
</body>
</html>
