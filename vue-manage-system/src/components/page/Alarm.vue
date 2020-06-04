<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 违规信息
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                        type="primary"
                        icon="el-icon-delete"
                        class="handle-del mr10"
                        @click="delAllSelection"
                >批量删除</el-button>


            </div>



            <el-table
                    :data="tableData"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="alarmid" label="ID" width="55" align="center"></el-table-column>
                <el-table-column label="截图地址"  >
                    <template slot-scope="scope">
                        <a :href="scope.row.picURL" target="_blank" class="buttonText">{{scope.row.picURL}}</a>
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="违规类型"></el-table-column>
                <el-table-column prop="datetime" label="日期"></el-table-column>
                <el-table-column prop="kitchen.name" label="餐厅"></el-table-column>
                <el-table-column prop="kitchen.location" label="餐厅地址"></el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                        background
                        layout="total, prev, pager, next"
                        :current-page="query.pageIndex"
                        :page-size="query.pageSize"
                        :total="pageTotal"
                        @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    import { fetchData } from '../../api/index';
    export default {
        data() {
            return {
                kName:"",
                query: {
                    pageIndex: 1,
                    pageSize: 10
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 100,
                form: {},
                idx: -1,
                id: -1
            };
        },
        created() {
            this.getData();

        },
        methods: {

            // 获取数据
            getData() {
                axios.get("http://localhost:8181/alarm/findAll",{params:this.query})
                    .then(response=>(this.tableData=response.data,this.pageTotal=response.data.length))
                    .catch(function (error) {
                        console.log(error);
                    });
            },

            // 多选操作
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            delAllSelection() {
                const length = this.multipleSelection.length;
                let str = '';
                this.delList = this.delList.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += this.multipleSelection[i].name + ' ';
                }
                this.$message.error(`删除了${str}`);
                this.multipleSelection = [];
            },
            // 分页导航
            handlePageChange(val) {
                this.query.pageIndex=val;
                this.getData();
            }
        }
    };
</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .table {
        width: 100%;
        font-size: 14px;
    }
    .red {
        color: #ff0000;
    }
    .mr10 {
        margin-right: 10px;
    }
    .table-td-thumb {
        display: block;
        margin: auto;
        width: 40px;
        height: 40px;
    }
</style>
