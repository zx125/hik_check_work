<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <form class="layui-form layui-form-pane" id="form1" name="form1" action="">
            <div class="layui-form-item">
                <button class="layui-btn">上班迟到</button>
                <div class="layui-inline" style="margin-top:5px">
                    <div class="layui-input-inline">
                        <input type="text" name="1" autocomplete="off" class="layui-input" value="${rules[0].value}">
                    </div>
                    <button class="layui-btn">分钟以内不算迟到</button>
                </div>
                <button class="layui-btn">上班迟到</button>
                <div class="layui-inline" style="margin-top:5px">
                    <div class="layui-input-inline">
                        <input type="text" name="2" autocomplete="off" class="layui-input" value="${rules[1].value}">
                    </div>
                    <button class="layui-btn">分钟作旷工</button>
                </div>
                <div class="layui-input-inline layui-form" lay-filter="test1" style="float: right;margin-top:5px;margin-right: 600px">
                    <select name="3" id="select1">
                        <c:if test="${rules[2].value eq '0.5'}">
                            <option selected="selected" value="0.5">半天</option>
                            <option value="0">0天</option>
                            <option value="1">1天</option>
                        </c:if>
                        <c:if test="${rules[2].value eq '0'}">
                            <option value="0.5">半天</option>
                            <option selected="selected" value="0">0天</option>
                            <option value="1">1天</option>
                        </c:if>
                        <c:if test="${rules[2].value eq '1'}">
                            <option value="0.5">半天</option>
                            <option value="0">0天</option>
                            <option selected="selected" value="1">1天</option>
                        </c:if>
                    </select>
                </div>
            </div>
                <div class="layui-form-item">
                    <button class="layui-btn">下班早退</button>
                    <div class="layui-inline" style="margin-top:5px">
                        <div class="layui-input-inline">
                            <input type="text" name="4" autocomplete="off" class="layui-input" value="${rules[3].value}">
                        </div>
                        <button class="layui-btn">分钟以内不算迟到</button>
                    </div>
                    <button class="layui-btn">下班早退</button>
                    <div class="layui-inline" style="margin-top:5px">
                        <div class="layui-input-inline">
                            <input type="text" name="5" autocomplete="off" class="layui-input" value="${rules[4].value}">
                        </div>
                        <button class="layui-btn">分钟作旷工</button>
                    </div>
                    <div class="layui-input-inline layui-form" lay-filter="test1" style="float: right;margin-top:5px;margin-right: 600px">
                        <select name="6" id="select2">
                            <c:if test="${rules[5].value eq '0.5'}">
                                <option selected="selected" value="0.5">半天</option>
                                <option value="0">0天</option>
                                <option value="1">1天</option>
                            </c:if>
                            <c:if test="${rules[5].value eq '0'}">
                                <option value="0.5">半天</option>
                                <option selected="selected" value="0">0天</option>
                                <option value="1">1天</option>
                            </c:if>
                            <c:if test="${rules[5].value eq '1'}">
                                <option value="0.5">半天</option>
                                <option value="0">0天</option>
                                <option selected="selected" value="1">1天</option>
                            </c:if>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn">下午下班</button>
                    <div class="layui-inline" style="margin-top:5px">
                        <div class="layui-input-inline">
                            <input type="text" name="7" autocomplete="off" class="layui-input" value="${rules[6].value}">
                        </div>
                        <button class="layui-btn">分钟后刷卡算加班</button>
                    </div>

                </div>
                <div class="layui-form-item">
                <div class="layui-inline" style="margin-top:5px">
                    <label class="layui-form-label layui-bg-green">上下班时间</label>
                    <div class="layui-input-inline">
                        <input name="8" type="text" class="layui-input" id="test9" value="${rules[7].value}">
                    </div>
                </div>
                </div>
                <div class="layui-inline" style="margin-top:5px">
                    <label class="layui-form-label layui-bg-orange" style="width: 150px">周六上班时间</label>
                    <div class="layui-input-inline">
                        <input name="9" type="text" class="layui-input" id="test10" value="${rules[8].value}">
                    </div>
                </div>
                <div class="layui-inline" style="margin-top:5px">
                    <label class="layui-form-label layui-bg-orange" style="width: 150px">周日上班时间</label>
                    <div class="layui-input-inline">
                        <input name="10" type="text" class="layui-input" id="test11" value="${rules[9].value}">
                    </div>
                </div>
                <span class="layui-badge">全0为休息</span>
                <div class="layui-form-item" style="margin-top: 25px">
                    <button class="layui-btn layui-bg-blue" lay-submit="" lay-filter="demo2">保存规则</button>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="js/layui.js"></script>
<script>
    layui.use(['form','table','element','laydate'], function(){
        var table = layui.table
            ,form=layui.form
            ,$=layui.jquery
            ,laydate = layui.laydate
            ,element=layui.element;
        $(document).ready(function(){
            $("#li1").addClass("layui-this");
            $("#d5").addClass("layui-this");
        });
        //时间范围
        laydate.render({
            elem: '#test9'
            ,type: 'time'
            ,range: true
        });
        laydate.render({
            elem: '#test10'
            ,type: 'time'
            ,range: true
        });
        laydate.render({
            elem: '#test11'
            ,type: 'time'
            ,range: true
        });
        //监听提交
        form.on('submit(demo2)', function (data) {
            $.ajax({
                url: "/updateRule",
                type: "POST",
                async: true,//异步
                contentType : 'application/json;charset=UTF-8',
                data:JSON.stringify($("#form1").serializeArray()),
                dataType: "text",
                success: function () {
                    layer.alert("更新成功");
                },
                error:function () {
                    layer.alert(JSON.stringify($("#form1").serializeArray()));
                }
            });
            return false;
        });
    });
</script>
</body>
</html>