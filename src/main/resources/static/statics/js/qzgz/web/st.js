var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/qzgz" + param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }
        });
    return response.responseJSON;
};

function dataSourceTodays(meal) {
    return FetchData({meal: meal}, 'POST', '/web/getCanteenToday', false).data;
}

function updateZan(zan, id) {
    return FetchData({zan: zan, id: id}, 'POST', '/web/setCanteenZan', false);
}

var vm = new Vue({
    el: '#app',
    data: {
        breakfast: '',
        lunch: '',
        dinner: '',
        night: ''
    },
    created: function () {
        this.breakfast = dataSourceTodays(1);
        this.lunch = dataSourceTodays(2);
        this.dinner = dataSourceTodays(3);
        this.night = dataSourceTodays(4)
    },
    methods: {
        forId_1: function f(id) {
            return '1_' + id
        },
        forId_2: function f(id) {
            return '2_' + id
        },
        forId_3: function f(id) {
            return '3_' + id
        },
        forId_4: function f(id) {
            return '4_' + id
        }
    }
});

function judgeDiff(data, id) {
    var zan;
    for (var i = 0; i < data.length; i++) {
        if (data[i].id === id) {
            data[i].zan--;
            zan = data[i].zan;
            updateZan(zan, id);
            return data[i].zan
        }
    }
}

function judgeSum(data, id) {
    var zan;
    for (var i = 0; i < data.length; i++) {
        if (data[i].id === id) {
            data[i].zan++;
            zan = data[i].zan;
            updateZan(zan, id);
            return data[i].zan
        }
    }
}

$(function () {
    $("p").on('click', function (e) {
        var type, id;
        var arrayId = $(this).attr("id");
        type = parseInt(arrayId.substring(0, 1));
        id = parseInt(arrayId.slice(2));
        console.log(type);
        console.log(id);
        if (arrayId !== null) {
            $(this).toggleClass('red-heart');//更改点赞图标的class
            var className = $(this).attr('class');//取到点赞图标的class
            if (className === 'glyphicon glyphicon-thumbs-up') {
                switch (type) {
                    case 1:
                        judgeDiff(vm.breakfast, id);
                        break;
                    case 2:
                        judgeDiff(vm.lunch, id);
                        break;
                    case 3:
                        judgeDiff(vm.dinner, id);
                        break;
                    case 4:
                        judgeDiff(vm.night, id);
                        break;
                }
            } else {
                switch (type) {
                    case 1:
                        judgeSum(vm.breakfast, id);
                        break;
                    case 2:
                        judgeSum(vm.lunch, id);
                        break;
                    case 3:
                        judgeSum(vm.dinner, id);
                        break;
                    case 4:
                        judgeSum(vm.night, id);
                        break;
                }
            }
        }
    })
});






