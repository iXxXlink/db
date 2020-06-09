<template>
    <el-table
            :data="tableData"
            style="width: 100%"
            :row-class-name="tableRowClassName">
        <el-table-column
                prop="currentTime"
                label="日期"
                width="180">
        </el-table-column>
        <el-table-column
                prop="username"
                label="用户"
                width="180">
        </el-table-column>
        <el-table-column
                prop="details"
                label="操作">
        </el-table-column>
    </el-table>
</template>

<style>
    .el-table .warning-row {
        background: oldlace;
    }

    .el-table .success-row {
        background: #f0f9eb;
    }
</style>

<script>
    export default {
        created() {
          this.getData();
        },
        methods: {
            tableRowClassName({row, rowIndex}) {
                if (row.details.includes("删除")) {
                    return 'warning-row';
                }
                else if(row.details.includes("添加")||row.details.includes("修改")){
                    return "success-row"
                }
                else {
                    return "error-row"
                }
                return '';
            },
            getData(){
                axios.get("http://localhost:8181/logs/findAll")
                .then(response=>{
                    this.tableData=response.data
                })
                .catch(function (err) {
                    console.log(err);
                })
            }
        },
        data() {
            return {
                tableData: []
            }
        }

    }
</script>