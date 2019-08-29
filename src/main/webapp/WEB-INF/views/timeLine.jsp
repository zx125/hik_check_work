<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/29
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div style="padding: 15px;">
            <button class="layui-btn layui-bg-orange">本月迟到<span class="layui-badge layui-bg-gray">${count1}</span></button>
            <button class="layui-btn layui-bg-orange">异常进出<span class="layui-badge layui-bg-gray">${count2}</span></button>
            <button class="layui-btn layui-bg-orange">本月早退<span class="layui-badge layui-bg-gray">${count3}</span></button>
            <div class="layui-inline">
                <label class="layui-form-label">选择日期</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" id="test1" placeholder="${day}">
                </div>
            </div>
            <hr class="layui-bg-red">
            <table class="layui-table">
                <colgroup>
                    <col width="150">
                    <col width="200">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>名称</th>
                    <th>性别</th>
                    <th>生日</th>
                    <th>部门</th>
                    <th>职位</th>
                    <th>电话</th>
                    <th>e-mail</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${signInDetail.staffId}</td>
                    <td>${signInDetail.name}</td>
                    <td>${signInDetail.sex}</td>
                    <td>${signInDetail.birth}</td>
                    <td>${signInDetail.DName}</td>
                    <td>${signInDetail.PName}</td>
                    <td>${signInDetail.telphone}</td>
                    <td>${signInDetail.EMail }</td>
                </tr>
                </tbody>
            </table>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend>${day}签到记录</legend>
            </fieldset>
            <ul class="layui-timeline">
            <c:forEach items="${signInDetails}" var="signday">
                <c:if test="${signday.state==1}">
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis"></i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">${signday.date}  正常上班签到</div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${signday.state==2}">
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">${signday.date}  上班迟到</div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${signday.state==4}">
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">${signday.date}  异常出入</div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${signday.state==3}">
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis"></i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">${signday.date} 早退</div>
                        </div>
                    </li>
                </c:if>
                <c:if test="${signday.state==6}">
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis"></i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">${signday.date} 正常下班</div>
                        </div>
                    </li>
                </c:if>
            </c:forEach>
            </ul>
        </div>
    </div>
</div>
<script src="js/layui.js"></script>
<script>
    layui.use(['laydate','element'], function(){
        var laydate = layui.laydate
            ,element=layui.element;
        //执行一个laydate实例
        laydate.render({
            elem: '#test1' //指定元素
        });
    });
</script>
</body>
</html>