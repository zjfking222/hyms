
function getData(){
    $.ajax({  //树
        method:"post",
        url:"/ad/department/select",//数据路径
        data:{date:$("#test1").val(),time:getTime},
        success:function (result) {
            var dataSource = new kendo.data.HierarchicalDataSource({ //设置数据源
                data: result.data,
                schema: {
                    model: {
                        id: "did",
                        children: "child"
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
                        ,{field: 'state', title: '状态', width:100,sort: true}
                        ,{field: 'name', title: '姓名', width:80}
                        ,{field: 'depname', title: '部门名称', width: 100, sort: true}
                        ,{field: 'duty', title: '职位', width: 100, sort: true}
                        ,{field: 'oldduty', title: '原职位', width:100}
                        ,{field: 'dn', title: '路径', width: 570}
                        ,{field: 'olddn', title: '原始路径', width: 570}
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
                    }
                });
                table.render({   //组织架构change信息表
                    elem: '#demo1'
                    ,method:"post"
                    ,height: 312
                    ,where:{date:$("#test1").val(),time:getTime}
                    ,url: '/ad/department/getChangeDep' //数据接口
                    ,page: false //开启分页
                    ,cols: [[ //表头
                        {field: 'did', title: 'ID', width:100, fixed: 'left'}
                        ,{field: 'state', title: '状态', width:100,sort: true}
                        ,{field: 'name', title: '名称', width:100}
                        ,{field: 'oldname', title: '原名称', width: 100, sort: true}
                        ,{field: 'dn', title: '路径', width: 570}
                        ,{field: 'olddn', title: '原始路径', width: 570}
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






