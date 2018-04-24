$(function () {
    function dataSource() {
        return FetchData({},'POST','/getBus',false).data;
    }
    var vm = new Vue({
        el:'#app',
        data: {
            data1:dataSource(),
            data2: {
                number:'',
                start:'',
                end:''
            },
            data3: {
                id: 0,
                number:'',
                start:'',
                end:''
            }
        },
        methods: {
            onedit:function (id,number,start,end) {
                this.$data.data3.id = id;
                this.$data.data3.number = number;
                this.$data.data3.end = end;
                this.$data.data3.start = start;
            },
            ondelete:function (id) {
                FetchData({id:id},'POST','/delBus',false);
                this.$data.data1 = dataSource();
            },
            onaddsubmit:function () {
                FetchData({
                    number:this.$data.data2.number,
                    start:this.$data.data2.start,
                    end:this.$data.data2.end},
                    'POST','/addBus',false)
                this.$data.data1 = dataSource();
            },
            oneditsubmit:function () {
                FetchData({
                    id:this.$data.data3.id,
                    number:this.$data.data3.number,
                    start:this.$data.data3.start,
                    end:this.$data.data3.end},
                    'POST','/setBus',false);
                this.$data.data1 = dataSource();
            }

        }
    });
    
    layui.laydate.render({
        elem: '#adds',
        type: 'time',
        format: 'HH:mm',
        done: function(value){
            vm.$data.data2.start = value;
        }
    });
    layui.laydate.render({
        elem: '#adde',
        type: 'time',
        format: 'HH:mm',
        done: function(value){
            vm.$data.data2.end = value;
        }
    });
    layui.laydate.render({
        elem: '#edits',
        type: 'time',
        format: 'HH:mm',
        done: function(value){
            vm.$data.data3.start = value;
        }
    });
    layui.laydate.render({
        elem: '#edite',
        type: 'time',
        format: 'HH:mm',
        done: function(value){
            vm.$data.data3.end = value;
        }
    });

    $('.tr-add,.tr-edit').fadeOut(0);
    $('.btn-edit').click(function () {
        $('.tr-add').fadeOut(0);
        $('.tr-edit').fadeIn(500);
    });
    $('.btn-editCancel').click(function () {
        $('.tr-edit').fadeOut(0);
    });
    $('.btn-addCancel').click(function () {
        $('.tr-add').fadeOut(0);
    });
    $('.btn-add').click(function () {
        $('.tr-edit').fadeOut(0);
        $('.tr-add').fadeIn(500);
    });
    $('.btn-addCommit').click(function () {
        $('.tr-add').fadeOut(0);
    });
    $('.btn-editCommit').click(function () {
        $('.tr-edit').fadeOut(0);
    })

});
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};