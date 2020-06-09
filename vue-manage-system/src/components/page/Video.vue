<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 视频信息
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
                <el-input v-model="kName" placeholder="餐厅名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button style="float: right; padding: 3px 0" type="text" @click="showAddTable">添加</el-button>
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
                <el-table-column prop="videoId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="kitchen.name" label="餐厅名称"></el-table-column>
                <el-table-column prop="kitchen.location" label="餐厅地址"></el-table-column>
                <el-table-column label="视频地址"  >
                    <template slot-scope="scope">
                        <a :href="scope.row.videoURL" target="_blank" class="buttonText">{{scope.row.videoURL}}</a>
                    </template>
                </el-table-column>
                <el-table-column prop="date" label="录入时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.$index, scope.row)"

                        >编辑</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
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

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="视频地址">
                    <el-input v-model="tmpURL"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 添加弹出框 -->
        <el-dialog title="添加视频" :visible.sync="showAdd" width="50%">
            <el-form ref="addForm" :model="addForm" label-width="70px">
                <el-form-item label="餐厅id">
                    <el-input v-model="addForm.id"></el-input>
                </el-form-item>
                <el-form-item label="视频url">
                    <el-input v-model="addForm.videoURL"></el-input>
                </el-form-item>
                <el-form-item label="视频日期">
                    <el-date-picker
                            type="date"
                            placeholder="Date"
                            v-model="addForm.date"
                            value-format="yyyy-MM-dd"
                            style="width: 100%;"
                    ></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showAdd= false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { fetchData } from '../../api/index';
    export default {
        data() {
            return {

                showAdd:false,
                kName:"",
                query: {
                    pageIndex: 1,
                    pageSize: 2
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 100,
                form: {},
                tmpURL:"",
                idx: -1,
                id: -1,
                addForm:{
                    id:null,
                    videoURL:"",
                    date:"",
                }
            };
        },
        created() {
            this.getPage();
            this.getData();

        },
        methods: {

            //获得总页数
            getPage(){
                axios.get("http://localhost:8181/historyvideo/count")
                .then(response=>(this.pageTotal= response.data-1))
            },
            // 获取数据
            getData() {
               axios.get("http://localhost:8181/historyvideo/findAll",{params:this.query})
                .then(response=>(this.tableData=response.data))
                .catch(function (error) {
                    console.log(error);
                    this.logAction(error);
                });
            },
            // 触发搜索按钮
            handleSearch() {
                axios.post("http://localhost:8181/historyvideo/findByName",{kName:this.kName})
                .then(response=>(this.tableData=response.data))
                .catch(function (error) {
                    console.log(error);
                    this.logAction(error);
                });
            },
            // 删除操作
            handleDelete(index, row) {
                axios.get("http://localhost:8181/historyvideo/deleteByVideoId",{params:{videoId:row.videoId}})
                    .then(response=>{
                        if(response.data===1){
                            this.$message.success('删除成功');
                            this.tableData.splice(index, 1);
                            this.logAction("删除了video:"+row.videoId);
                        }else {
                            this.$message.error("删除失败");
                        }
                    }).catch(function (err) {
                        console.log(err);
                        this.logAction(error);
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
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form = row;
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                axios.post("http://localhost:8181/historyvideo/changeURL",{videoId:this.form.videoId,videoURL:this.form.videoURL})
                .then(response=>{
                    if(response.data===1){
                        this.form.videoURL=this.tmpURL;
                        this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                        this.$set(this.tableData, this.idx, this.form);
                        this.logAction("修改了video："+this.form.videoId+"的视频地址");
                    }
                    else{
                        this.$message.success(`修改失败`);

                    }
                }).catch(function (error) {
                    this.logAction(error);
                })
                this.editVisible = false;

            },
            saveAdd(){
                this.showAdd=false;
                axios.put("http://localhost:8181/historyvideo/save",this.addForm)
                .then(response=>{
                    if(response.data===1){
                        this.$message.success("添加成功！");
                        this.getData();
                        this.logAction("添加了video");
                    }else{
                        this.$message.error("添加失败");
                    }
                })

            },
            // 分页导航
            handlePageChange(val) {
                this.query.pageIndex=val;
                this.getData();
            },
            showAddTable(){
                this.showAdd=true
            },
            logAction(action){
                axios.post("http://localhost:8181/logs/save",{username:localStorage.getItem("ms_username"),details:action})
                    .catch(function (e) {
                        console.log(e)
                    })
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
