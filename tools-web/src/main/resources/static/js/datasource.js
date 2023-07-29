$(function () {

});

var dataTableVue = new Vue({
    el: "#dataTable",
    data: function () {
        return {
            modes: ['multi', 'single', 'range'],
            q: {
                dbName: null
            },
            fields: [
                {
                    key: 'selected',
                    label: '选择'
                },
                {
                    key: 'dbName',
                    label: '数据源名称'
                },
                {
                    key: 'driverClassName',
                    label: '驱动名称'
                },
                {
                    key: 'jdbcUrl',
                    label: '数据库链接'
                },
                {
                    key: 'username',
                    label: '用户名称'
                },
                {
                    key: 'password',
                    label: '用户密码'
                }],
            items: [
                /*{
                    "dbName": "zhuanjia_yaoqing_jilu2",
                    "engine": "InnoDB",
                    "tableComment": "专家邀请记录表",
                    "createTime": "2022-09-25 15:44:11"
                }*/
            ],
            selectMode: 'single',
            selected: [],
            currentDbName:''
        }
    },
    methods: {
        created() {
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
            this.$refs.selectableTable.selectRow(2)
        },
        unselectThirdRow() {
            this.$refs.selectableTable.unselectRow(2)
        },
        query: function () {
            var that = this;
            axios({
                method: 'get',
                url: '/tools/db/list',
                data: {},
                params: {'dbName': this.q.dbName}
            }).then(function (response) {
                $.each(response.data,function (index, item) {
                    if (item.selected) {
                        that.currentDbName = item.dbName;
                    }
                });
                that.items = response.data;

            }).catch(function (error) {
                console.log(error);
            });
            console.log(this);
        },
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
                url: '/tools/db/change',
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
        console.log(this);
        var that = this;
        axios({
            method: 'get',
            url: '/tools/db/list',
            data: {}
        }).then(function (response) {
            that.items = response.data;
            $.each(response.data,function (index, item) {
                if (item.selected) {
                    that.currentDbName = item.dbName;
                }
            });
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

