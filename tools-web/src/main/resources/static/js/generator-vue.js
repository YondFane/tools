$(function () {

});


var dataTableVue = new Vue({
    el: "#dataTable",
    data: function () {
        return {
            modes: ['multi', 'single', 'range'],
            q: {
                tableName: null
            },
            fields: [
                {
                    key: 'selected',
                    label: '选择'
                },
                {
                    key: 'tableName',
                    label: '数据表名'
                },
                {
                    key: 'tableComment',
                    label: '数据表注释'
                },
                {
                    key: 'engine',
                    label: '数据库引擎'
                },
                {
                    key: 'createTime',
                    label: '创建时间'
                }],
            items: [
                /*{
                    "tableName": "zhuanjia_yaoqing_jilu2",
                    "engine": "InnoDB",
                    "tableComment": "专家邀请记录表",
                    "createTime": "2022-09-25 15:44:11"
                }*/
            ],
            selectMode: 'multi',
            selected: []
        }
    },
    methods: {
        created() {

            console.log(123);
        },
        onRowSelected(items) {
            this.selected = items
        },
        selectAllRows() {
            this.$refs.selectableTable.selectAllRows()
        },
        clearSelected() {
            this.$refs.selectableTable.clearSelected()
        },
        selectThirdRow() {
            // Rows are indexed from 0, so the third row is index 2
            this.$refs.selectableTable.selectRow(2)
        },
        unselectThirdRow() {
            // Rows are indexed from 0, so the third row is index 2
            this.$refs.selectableTable.unselectRow(2)
        },
        query: function () {
            var that = this;
            axios({
                method: 'get',
                url: '/tools/generator/list',
                data: {},
                params: {'tableName': this.q.tableName}
            }).then(function (response) {
                that.items = response.data;
            }).catch(function (error) {
                console.log(error);
            });
            console.log(this);
        },
        generator: function () {
            var tableNames = [];
            $.each(this.selected, function (index, item) {
                tableNames.push(item.tableName);
            })
            if (tableNames.length == 0) {
                $.alert({
                    title: '提示',
                    content: '请选择至少一条记录'
                });
                return;
            }
            location.href = "tools/generator/code?tables=" + tableNames.join();
        }
    },
    // 初始化前，注意在 beforeCreate 生命周期函数执行的时候，data 和 methods 中的 属性与方法定义都还没有没初始化
    beforeCreate: function () {
        console.log('初始化前');
        console.log(this);
    },
    // 初始化，data 和 methods 都已经被初始化好了
    created: function () {
        console.log('初始化');
        console.log(this);
        var that = this;
        axios({
            method: 'get',
            url: '/tools/generator/list',
            data: {}
        }).then(function (response) {
            that.items = response.data;
        }).catch(function (error) {
            console.log(error);
        });
        console.log(this);
    },
    // 挂载前
    beforeMount: function () {
        console.log('挂载前');
        console.log(this);
    },
    // 挂载
    mounted: function () {
        console.log('挂载');
        console.log(this)
    },
    // 界面还没有被更新
    beforeUpdate: function () {
        console.log('界面还没有被更新');
        console.log(this);
    },
    // 界面更新
    updated: function () {
        console.log('界面更新');
        console.log(this);
    },
    // 销毁之前执行，当beforeDestroy函数执行时，表示vue实例已从运行阶段进入销毁阶段，vue实例身上所有的方法与数据都处于可用状态
    beforeDestroy: function () {
        console.log('销毁之前执行');
        console.log(this);
    },
    // 当destroy函数执行时，组件中所有的方法与数据已经被完全销毁，不可用
    destroyed: function () {
        console.log('销毁');
        console.log(this);
    },
    // 页面出现的时候执行 activated生命周期函数，跟 监听 watch 有类似的作用
    activated: function () {
        console.log('监听');
        console.log(this);
    },
    // 页面消失的时候执行
    deactivated: function () {
        console.log('页面消失的时候执行');
        console.log(this);
    }

})

