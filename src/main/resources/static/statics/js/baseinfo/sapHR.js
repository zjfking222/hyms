
function getData(){
    $('#example').css('display','inline-block');
    $.ajax({  //树
        method:"post",
        dataType:"json",
        url:"/ad/department/select",//数据路径
        success:function (result) {
            var dataSource = new kendo.data.HierarchicalDataSource({ //设置数据源
                data: result.data,
                schema: {
                    model: {
                        id: "did",
                        children:"child"
                    }
                }
            });
            $("#treeView").kendoTreeView({ //配置树视图
                dataSource: dataSource,
                dataValueField: ["parentid", "did"],//实际值
                dataTextField: ["name", "name"],//显示值
                select: function(nodevariable) {
                    // console.log(nodevariable);
                    this.expand(nodevariable.node);//单击node展开子项
                }
            });
            layui.use('table', function(){
                var table = layui.table;   //员工change信息表
                table.render({
                    elem: '#demo'
                    ,method:"post"
                    ,height: 312
                    ,where:{date:$("#test1").val(),time:getTime}
                    ,url: '/ad/staff/select' //数据接口
                    ,page: true //开启分页
                    ,cols: [[ //表头
                        {field: 'sid', title: '员工ID', width:80, fixed: 'left'}
                        ,{field: 'operator', title: '操作人', width:100,sort: true}
                        ,{field: 'state', title: '状态', width:100,sort: true}
                        ,{field: 'name', title: '姓名', width:80}
                        ,{field: 'depname', title: '部门名称', width: 100, sort: true}
                        ,{field: 'duty', title: '职位', width: 100, sort: true}
                        ,{field: 'oldduty', title: '原职位', width:100}
                        ,{field: 'dn', title: '路径', width: 570}
                        ,{field: 'olddn', title: '原始路径', width: 570}
                        ,{field: 'operate',title:'操作',width:100,fixed:'right',toolbar: '#barDemo'}
                    ]]
                    ,done:function (res) {
                        $("[data-field='state']").children().each(function(){
                            if($(this).text()=='0'){
                                $(this).text("更改")
                            }else if($(this).text()=='1'){
                                $(this).text("删除")
                            }else if($(this).text()=='2'){
                                $(this).text("增加")
                            }else if($(this).text()=='3'){
                                $(this).text("岗位变动")
                            }
                        });
                        $("[data-field='operator']").children().each(function(){
                            if($(this).text()=='0'){
                                $(this).text("未操作")
                            }else if($(this).text()=='100505'){
                                $(this).text("陈亚楠")
                            }else if($(this).text()=='100791'){
                                $(this).text("王春燕")
                            }else if($(this).text()=='20102197'){
                                $(this).text("陈珍珍")
                            }
                        });
                    }
                });
                table.render({   //组织架构change信息表
                    elem: '#demo1'
                    ,method:"post"
                    ,height: 250
                    ,where:{date:$("#test1").val(),time:getTime}
                    ,url: '/ad/department/getChangeDep' //数据接口
                    ,page: false //开启分页
                    ,cols: [[ //表头
                        {field: 'did', title: '组织ID', width:100, fixed: 'left'}
                        ,{field: 'operator', title: '操作人', width:100,sort: true}
                        ,{field: 'state', title: '状态', width:100,sort: true}
                        ,{field: 'name', title: '名称', width:100}
                        ,{field: 'oldname', title: '原名称', width: 100, sort: true}
                        ,{field: 'dn', title: '路径', width: 570}
                        ,{field: 'olddn', title: '原始路径', width: 570}
                        ,{field: 'operate',title:'操作',width:100,fixed:'right',toolbar: '#barDemo'}
                    ]]
                    ,done:function (res) {
                        $("[data-field='state']").children().each(function(){
                            if($(this).text()=='0'){
                                $(this).text("更改")
                            }else if($(this).text()=='1'){
                                $(this).text("删除")
                            }else if($(this).text()=='2'){
                                $(this).text("新增")
                            }
                        });
                        $("[data-field='operator']").children().each(function(){
                            if($(this).text()=='0'){
                                $(this).text("未操作")
                            }else if($(this).text()=='100505'){
                                $(this).text("陈亚楠")
                            }else if($(this).text()=='100791'){
                                $(this).text("王春燕")
                            }else if($(this).text()=='20102197'){
                                $(this).text("陈珍珍")
                            }
                        });
                    }
                });
                table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的DOM对象
                    if(obj.data.sid==undefined){
                        var id=obj.data.did;
                        var url1="/ad/department/updateOperator";
                    }else {
                        var id=obj.data.sid;
                        var url1="/ad/staff/updateOperator";
                    }

                    if(layEvent === 'detail'){ //查看
                        // alert('点击了查看');
                        tr.addClass("layui-btn-disabled");
                        // alert("Id"+JSON.stringify(id));
                        $.ajax({
                            method:"post",
                            url:url1,
                            async:false,
                            data:{id:id},
                            success:function (res) {
                                // alert("update successful!"+JSON.stringify(res));
                                $.ajax({  //获取登录者姓名
                                    method:"post",
                                    url:"/ad/staff/getOperator",//数据路径
                                    async:false,
                                    success:function (name) {
                                        // alert("update successful!"+JSON.stringify(name));
                                        obj.update({
                                            operator: name.data
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            });
        }
    });
}



layui.use('laydate', function(){ //版本
    var laydate = layui.laydate;
    laydate.render({
        elem: '#test1' //日期
        ,done: function(value){
            // console.log(value); //得到日期生成的值，如：2017-08-18
            $.ajax({  //获取时间
                method:"post",
                url:"/ad/department/getTime",//数据路径
                async:false,
                data:{date:value},
                success:function (timeResult) {
                    //动态加载select的option
                    // console.log("现在的长度是"+timeResult.data.length);
                    $("select[name='time']").empty();
                    for(var i=0; i<timeResult.data.length; i++){
                        // console.log("获取到的时间是"+timeResult.data[i].time);
                        var option="<option value=\""+(i+1)+"\"";
                        option += ">"+timeResult.data[i].time+"</option>"; //动态添加数据
                        $("select[name='time']").append(option);
                    }
                    layui.form.render('select');
                }
            })
        }
    });
});

var getTime;
layui.use('form', function(){ //得到选中的时间
    var form = layui.form;
    form.on('select(choseTime)', function(data) {
        // console.log(data.elem[data.elem.selectedIndex].text)
        getTime= data.elem[data.elem.selectedIndex].text;
    });
});





