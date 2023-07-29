$(function () {

});

var dataTableVue = new Vue({
    el: "#app",
    data: function () {
        return {
            q: {
                dbName: null
            },
            data:{}
        }
    },
    methods: {
        confirmDataSource: function () {
            var that = this;
            var dbName = "";
            $.each(this.selected, function (index, item) {
                dbName = item.dbName;
            })
            if (!dbName) {
                $.alert({
                    title: '提示',
                    content: '请选择一条记录'
                });
                return;
            }
            axios({
                method: 'post',
                url: '/yyadmin/db/change',
                data: {},
                params: {
                    dbName:dbName
                }
            }).then(function (response) {
                $.alert({
                    title: '提示',
                    content: response.data
                });
                that.currentDbName = dbName;
                this.query();
            }).catch(function (error) {
                console.log(error);
            });
        }
    },
    // 初始化前，注意在 beforeCreate 生命周期函数执行的时候，data 和 methods 中的 属性与方法定义都还没有没初始化
    beforeCreate: function () {

    },
    // 初始化，data 和 methods 都已经被初始化好了
    created: function () {
        console.log('初始化');
        var that = this;
        axios({
            method: 'get',
            url: '/yyadmin/modelsetting/queryData',
            data: {}
        }).then(function (response) {
            console.log(response);
            that.data = response.data;
        }).catch(function (error) {
            console.log(error);
        });
        console.log(this);
    },
    // 挂载前
    beforeMount: function () {
        console.log('挂载前');
    },
    // 挂载
    mounted: function () {
        console.log('挂载');
    },
    // 界面还没有被更新
    beforeUpdate: function () {
        console.log('界面还没有被更新');
    },
    // 界面更新
    updated: function () {
        console.log('界面更新');
    },
    // 销毁之前执行，当beforeDestroy函数执行时，表示vue实例已从运行阶段进入销毁阶段，vue实例身上所有的方法与数据都处于可用状态
    beforeDestroy: function () {
        console.log('销毁之前执行');
    },
    // 当destroy函数执行时，组件中所有的方法与数据已经被完全销毁，不可用
    destroyed: function () {
        console.log('销毁');
    },
    // 页面出现的时候执行 activated生命周期函数，跟 监听 watch 有类似的作用
    activated: function () {
        console.log('监听');
    },
    // 页面消失的时候执行
    deactivated: function () {
        console.log('页面消失的时候执行');
    }

})

