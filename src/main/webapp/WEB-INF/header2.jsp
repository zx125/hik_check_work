<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/30
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div class="layui-header">
    <div class="layui-logo">ZY考勤系统</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
        <li id="li1" class="layui-nav-item"><a href="zy">考勤管理</a></li>
        <li id="li2" class="layui-nav-item"><a href="goMachine">设备管理</a></li>
        <li id="li4" class="layui-nav-item"><a href="goDepartment">部门管理</a></li>
        <li id="li5" class="layui-nav-item"><a href="goStaff">人员管理</a></li>
        <li id="li6" class="layui-nav-item">
            <a href="goCard">权限管理</a>
<%--            <dl class="layui-nav-child">--%>
<%--                <dd><a href="">邮件管理</a></dd>--%>
<%--                <dd><a href="">消息管理</a></dd>--%>
<%--                <dd><a href="">授权管理</a></dd>--%>
<%--            </dl>--%>
        </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item">
            <a href="javascript:;">
                <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                zx
            </a>
            <dl class="layui-nav-child">
                <dd><a href="">基本资料</a></dd>
                <dd><a href="">安全设置</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item"><a href="">退出</a></li>
    </ul>
</div>

<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree"  lay-filter="test">
            <li class="layui-nav-item layui-nav-itemed">
                <a  href="javascript:;">设备管理</a>
                <dl class="layui-nav-child">
                    <dd id="d1"><a href="/goMachine">设备信息</a></dd>
                </dl>
            </li>
        </ul>
    </div>
</div>