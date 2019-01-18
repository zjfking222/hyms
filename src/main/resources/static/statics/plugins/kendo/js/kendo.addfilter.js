//获取当前grid的过滤条件
var getFilters = function (grid) {
    var allMap;
    var source = grid.dataSource;
    var data = source.filter();
    if(data && data.filters && data.filters.length >0){
        //kendoGrid的filter数据存在bug，有2种形式，需要判断后再去取值
        allMap = {};
        //循环每一列的过滤条件
        for(var i=0;i<data.filters.length;i++){
            var map;
            var filters;
            var f = data.filters[i];
            if(f.field){//当前数据为在f这一层下
                //取当前字段类型的map，不存在则新建
                map = allMap[f.field];
                if(!map){
                    map = {};
                    map.logic = data.logic;
                }
                //取当前map下的过滤条件，若不存在则新建
                filters = map.filters;
                if(!filters){
                    filters = [];
                }
                //生成当前过滤条件数据
                var filter = {};
                filter.operator = f.operator;
                //取当前字段类型
                var fields = source.options.schema.model.fields;
                filter.type = fields[f.field].type;
                if("date" === filter.type){
                    var date = getDateString(f.value);
                    filter.value = date;
                }else{
                    //防止出现非字符串格式
                    filter.value = f.value + "";
                }
                //保存到相应位置
                filters.push(filter);
                map.filters = filters;
                allMap[f.field] = map;
            }else{//当前数据为在f下一层
                var ff = f.filters;
                //循环过滤条件列表
                for(var j=0;j<ff.length;j++){
                    //取当前字段类型的map，不存在则新建
                    map = allMap[ff[j].field];
                    if(!map){
                        map = {};
                        map.logic = f.logic;
                    }
                    //取当前map下的过滤条件，若不存在则新建
                    filters = map.filters;
                    if(!filters){
                        filters = [];
                    }
                    //生成当前过滤条件数据
                    var filter = {};
                    filter.operator = ff[j].operator;
                    //取当前字段类型
                    var fields = source.options.schema.model.fields;
                    filter.type = fields[ff[j].field].type;
                    if("date" === filter.type){
                        var date = getDateString(ff[j].value);
                        filter.value = date;
                    }else{
                        //防止出现非字符串格式
                        filter.value = ff[j].value + "";
                    }
                    //保存到相应位置
                    filters.push(filter);
                    map.filters = filters;
                    allMap[ff[j].field] = map;
                }
            }
        }
    }
    return allMap;
};
//取当前日期的yyyy-MM-dd格式字符串
function getDateString(date){
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    if(month <10){
        month = '0' + month;
    }
    var d = date.getDate();
    if (d <10){
        d = '0' + d;
    }
    return year + '-' + month + '-' + d;
}