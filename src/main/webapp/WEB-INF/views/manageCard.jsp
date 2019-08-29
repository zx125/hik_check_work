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
    <jsp:include page="../header5.jsp"></jsp:include>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <div class="layui-row">
                <div class="layui-col-xs6">
                    <div class="layui-inline layui-form" lay-filter="test1">
                        <div class="layui-input-inline">
                            <select id="select3" name="departmentId" lay-filter="test2" lay-verify="required"
                                    lay-search="">
                                <option value="">请选择人员所属部门</option>
                            </select>
                        </div>
                    </div>
                    <div id="test3" class="demo-transfer"></div>
                    <div style="width: 675px; margin-top:10px">
                        <button type="button" class="layui-btn layui-btn-fluid">全部已选人员</button>
                    </div>
                    <div style="width: 675px;">
                        <table id="demo" lay-filter="test" class="layui-hide"></table>
                    </div>
                </div>
                <div class="layui-col-xs6">
                    <div class="layui-inline layui-form" lay-filter="test3">
                        <div class="layui-input-inline">
                            <select id="select4" name="departmentId" lay-filter="test4" lay-verify="required"
                                    lay-search="">
                                <option value="">请选择设备所属部门</option>
                            </select>
                        </div>
                    </div>
                    <div id="test4" class="demo-transfer"></div>
                    <div style="width: 675px; margin-top:10px">
                        <button type="button" class="layui-btn layui-btn-fluid">全部已选设备</button>
                    </div>
                    <div style="width: 675px;">
                        <table id="demo1" lay-filter="test" class="layui-hide"></table>
                    </div>
                </div>
            </div>
        </div>
        <div style="text-align: center">
                <button style="width: 500px" type="button" lay-submit class="layui-btn layui-btn-fluid layui-bg-orange" lay-filter="*">保存</button>
        </div>
    </div>
</div>


<script src="js/layui.js"></script>
<script>
    function submit() {
        alert("sa");
    }
    layui.use(['table', 'element', 'transfer', 'util', 'layer', 'form'], function () {

        var table = layui.table
            , transfer = layui.transfer
            , $ = layui.jquery
            , form = layui.form
            , util = layui.util
            , layer = layui.layer
            , element = layui.element;
        var jsondata = [];
        var postdata=[];
        var jsondata2 = [];
        var postdata2=[];
        form.on('submit(*)', function(){
            alert(JSON.stringify({cards:postdata,devices:postdata2}));
            $.ajax({
                url: "/addCardDevice",
                type: "POST",
                contentType: "application/json;charset=UTF-8",//指定消息请求类型
                data:JSON.stringify({cards:postdata,devices:postdata2}),
                dataType: "text",
                success: function (data) {
                    alert("sa");
                    location.href="goCard";
                },
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
        $(document).ready(function () {
            $("#li6").addClass("layui-this");
            $("#d2").addClass("layui-this");
        });
        form.on('select(test2)', function (data) {
            var getData = transfer.getData('transfer1'); //获取右侧数据
            for (var i = 0, l = getData.length; i < l; i++) {
                if (jsondata.length == 0) {
                    jsondata.push(getData[i].value);
                }
                else {
                    var u=0;
                    for (var j = 0,z = jsondata.length; j < z; j++,u++) {
                        if (getData[i].value == jsondata[j]) {
                            break;
                        }
                    }
                    if (u==jsondata.length) {
                        jsondata.push(getData[i].value);
                    }
                }
            }
            alert(JSON.stringify(jsondata));
            $.ajax({
                url: "/selectStaffNameByDepartmentIdCard",
                type: "POST",
                data: {"departmentId": data.value},
                dataType: "json",
                success: function (data) {
                    transfer.render({
                        elem: '#test3'
                        , data: data
                        , value: jsondata
                        , width: 300
                        , height: 300
                        , title: ['当前部门未选人员', '当前部门已选人员']
                        , id: 'transfer1'
                        , onchange: function (obj, index) {
                            if(index==0){
                                for(var i=0;i<obj.length;i++){
                                    postdata.push(obj[i]);
                                }
                            }
                            if (index==1){
                                for(var i=0;i<obj.length;i++){
                                    for (var j=0;j<postdata.length;j++){
                                        if (obj[i].value==postdata[j].value){
                                            postdata[j]="";
                                            break;
                                        }
                                    }
                                }
                                for(var i=0;i<obj.length;i++){
                                    for(var j=0;j<=jsondata.length;j++){
                                        if(obj[i].value==jsondata[j]){
                                            jsondata[j]="";
                                            break;
                                        }
                                    }
                                }
                            }
                            alert(JSON.stringify(postdata));
                            table1.reload({
                                data:postdata
                            });
                            var arr = ['左边', '右边'];
                            layer.alert('来自 <strong>' + arr[index] + '</strong> 的数据：' + JSON.stringify(obj)); //获得被穿梭时的数据
                        }
                    });
                },
            });
        });
        form.on('select(test4)', function (data) {
            var getData = transfer.getData('transfer2'); //获取右侧数据
            for (var i = 0, l = getData.length; i < l; i++) {
                if (jsondata2.length == 0) {
                    jsondata2.push(getData[i].value);
                }
                else {
                    var u=0;
                    for (var j = 0,z = jsondata2.length; j < z; j++,u++) {
                        if (getData[i].value == jsondata2[j]) {
                            break;
                        }
                    }
                    if (u==jsondata2.length) {
                        jsondata2.push(getData[i].value);
                    }
                }
            }
            alert(JSON.stringify(jsondata2));
            $.ajax({
                url: "/selectDeviceByDepartmentIdCard",
                type: "POST",
                data: {"departmentId": data.value},
                dataType: "json",
                success: function (data) {
                    transfer.render({
                        elem: '#test4'
                        , data: data
                        , value: jsondata2
                        , width: 300
                        , height: 300
                        , title: ['当前部门未选设备', '当前部门已选设备']
                        , id: 'transfer2'
                        , onchange: function (obj, index) {
                            if(index==0){
                                for(var i=0;i<obj.length;i++){
                                    postdata2.push(obj[i]);
                                }
                            }
                            if (index==1){
                                for(var i=0;i<obj.length;i++){
                                    for (var j=0;j<postdata2.length;j++){
                                        if (obj[i].value==postdata2[j].value){
                                            // delete postdata[j];
                                            postdata2[j]="";
                                            break;
                                        }
                                    }
                                }
                                for(var i=0;i<obj.length;i++){
                                    for(var j=0;j<=jsondata2.length;j++){
                                        if(obj[i].value==jsondata2[j]){
                                            jsondata2[j]="";
                                            break;
                                        }
                                    }
                                }
                            }
                            alert(JSON.stringify(postdata2));
                            table2.reload({
                                data:postdata2
                            });
                            var arr = ['左边', '右边'];
                            layer.alert('来自 <strong>' + arr[index] + '</strong> 的数据：' + JSON.stringify(obj)); //获得被穿梭时的数据
                        }
                    });
                },
            });
        });
        var table1=table.render({
            elem: '#demo'
            , height: '300px'
            , data:postdata
            , cols: [[ //表头
                {field: 'value', title: '序号', width: '20%', sort: true, fixed: 'left'}
                , {field: 'title', title: '人员姓名', width: '40%'}
                , {field: 'departmentName', title: '部门', width: '40%'}
            ]]
        });
        var table2=table.render({
            elem: '#demo1'
            , height: '300px'
            , data:postdata2
            , cols: [[ //表头
                {field: 'value', title: '序号', width: '20%', sort: true, fixed: 'left'}
                , {field: 'title', title: '机器名称', width: '40%'}
                , {field: 'departmentName', title: '部门', width: '40%'}
            ]]
        });
        $.ajax({
            url: "/selectCascadeDepartmentName",
            type: "POST",
            dataType: "json",
            success: function (data) {
                $('#select3').html("");
                $('#select4').html("");
                var content = '<option value="">请选择使用部门</option>';
                $.each(data, function (i, val) {
                    content += '<option value="' + val.rootId + '">' + val.title + '</option>';
                });
                $('#select3').html(content);
                $('#select4').html(content);
                form.render('select', 'test1');
                form.render('select', 'test3');
            },
        });
        //显示搜索框
        transfer.render({
            elem: '#test3'
            , id: 'transfer1'
            , data: ""
            , width: 300
            , height: 300
            , title: ['当前部门未选人员', '当前部门已选人员']
            , showSearch: true
            , onchange: function (obj, index) {
                var arr = ['左边', '右边'];
                layer.alert('来自 <strong>' + arr[index] + '</strong> 的数据：' + JSON.stringify(obj)); //获得被穿梭时的数据
            }
        });
        //显示搜索框
        transfer.render({
            elem: '#test4'
            , id: 'transfer2'
            , data: ""
            , width: 300
            , height: 300
            , title: ['当前部门未选设备', '当前部门已选设备']
            , showSearch: true
            , onchange: function (obj, index) {
                var arr = ['左边', '右边'];
                layer.alert('来自 <strong>' + arr[index] + '</strong> 的数据：' + JSON.stringify(obj)); //获得被穿梭时的数据
            }
        });
    });
</script>
</body>
</html>