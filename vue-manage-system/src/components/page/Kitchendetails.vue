<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8"   v-if="seeDetails.base">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">

                        <div class="user-info-cont">
                            <div class="user-info-name">{{baseData.name}}</div>

                        </div>
                    </div>
                    <div class="user-info-list">
                        餐厅地址：
                        <span>{{baseData.add}}</span>
                    </div>
                </el-card>
                <el-card shadow="hover" style="height:252px;">
                    <div slot="header" class="clearfix">
                        <span>更改信息</span>
                    </div>
                    <el-form ref="form" :model="changeAdd" label-width="80px">
                        <el-form-item label="名称" >
                            <el-input v-model="changeAdd.name" placeholder="名称" ></el-input>
                        </el-form-item>
                        <el-form-item label="地址" >
                            <el-input v-model="changeAdd.location" placeholder="地址" ></el-input>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">更改</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col >
            <el-col :span="16"   >
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="12" v-if="seeDetails.video">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-lx-people grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{countVideos}}</div>
                                    <div>餐厅历史视频数量</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col     >
                    <el-col :span="12" v-if="seeDetails.alarm">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-lx-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{countAlarms}}</div>
                                    <div>违规记录</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-card shadow="hover" style="height:403px;" v-if="seeDetails.alarm">
                    <div slot="header" class="clearfix">
                        <span>违规记录</span>
                        <el-button style="float: right; padding: 3px 0" type="text">添加</el-button>
                    </div>
                    <el-table :show-header="true" :data="alarmData" style="width:100%;">
                        <el-table-column prop="type" label="类型"  ></el-table-column>
                        <el-table-column prop="datetime" label="时间"></el-table-column>
                        <el-table-column label="截图地址"  >
                            <template slot-scope="scope">
                                <a :href="scope.row.picURL" target="_blank" class="buttonText">{{scope.row.picURL}}</a>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="pagination">
                        <el-pagination
                                background
                                layout="total, prev, pager, next"
                                :current-page="alarmQuery.pageIndex"
                                :page-size="alarmQuery.pageSize"
                                :total="alarmQuery.pageTotal"
                                @current-change="handlePageChange1"
                        ></el-pagination>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20" v-if="seeDetails.video">
            <el-card shadow="hover" style="height:403px;">
                <div slot="header" class="clearfix">
                    <span>历史视频</span>
                    <el-button style="float: right; padding: 3px 0" type="text">添加</el-button>
                </div>
                <el-table :show-header="true" :data="videoData" style="width:100%;">
                    <el-table-column prop="videoId" label="视频Id"  ></el-table-column>
                    <el-table-column prop="date" label="时间Date"></el-table-column>
                    <el-table-column label="视频地址"  >
                        <template slot-scope="scope">
                            <a :href="scope.row.videoURL" target="_blank" class="buttonText">{{scope.row.videoURL}}</a>
                        </template>
                    </el-table-column>
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
                            :current-page="videoQuery.pageIndex"
                            :page-size="videoQuery.pageSize"
                            :total="videoQuery.pageTotal"
                            @current-change="handlePageChange2"
                    ></el-pagination>
                </div>
                <!-- 编辑弹出框 -->
                <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
                    <el-form ref="form" :model="form" label-width="70px">
                        <el-form-item label="视频地址">
                            <el-input v-model="form.videoURL"></el-input>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
                </el-dialog>
            </el-card>
        </el-row>

    </div>
</template>

<script>
    import Schart from 'vue-schart';
    import bus from '../common/bus';
    export default {
        name: 'dashboard',

        data() {
            return {
                seeDetails:{
                    base:this.$route.params.base,
                    alarm:this.$route.params.alarm,
                    video:this.$route.params.video,
                },
                countVideos:0,
                countAlarms:0,
                baseData:{
                    name: this.$route.params.name,
                    add:"霸王路",
                    id:0,
                },
                alarmData:[{
                    type:"没戴帽子",
                    datetime:"2018-01-12",
                    picURL:"http://localhost",
                }],
                videoData:[{
                    videoId:1,
                    date:"2018-1-12",
                    videoURL:"http://localhost"
                }],
                alarmQuery:{
                    pageIndex: 1,
                    pageSize: 10,
                    pageTotal:5
                },
                videoQuery:{
                    pageIndex: 1,
                    pageSize: 10,
                    pageTotal:5
                },
                form:{
                    videoURL:"",
                    idx:0,
                    row:{}
                },
                changeAdd:{
                    location:"",
                    name:"",
                    id:0
                },
                editVisible: false,
                dateChange:{
                    dateFrom:this.$route.params.dateFrom,
                    dateTo:this.$route.params.dateTo,
                },
            };
        },
        computed: {
            role() {
                return this.name === 'admin' ? '超级管理员' : '普通用户';
            }
        },
        // created() {
        //     this.handleListener();
        //     this.changeDate();
        // },
        // activated() {
        //     this.handleListener();
        // },
        // deactivated() {
        //     window.removeEventListener('resize', this.renderChart);
        //     bus.$off('collapse', this.handleBus);
        // },
        created() {
            this.getData();
        },
        methods: {
            getData(){
                //情况一：未限定时间范围
                if(!this.$route.params.useDate){
                    //获取基础信息
                    axios.post("http://localhost:8181/kitchen/findByName",{name:this.baseData.name})
                        .then(response=>{this.baseData.add=response.data.location,this.baseData.id=response.data.id,
                            //获取违规信息（嵌套实现同步）
                            axios.get("http://localhost:8181/alarm/findByKitchen",{params:{id:this.baseData.id}})
                                .then(response2=>(this.alarmData=response2.data,this.countAlarms=this.alarmData.length,this.alarmQuery.pageTotal= Math.ceil(this.countAlarms/this.alarmQuery.pageSize)))
                                .catch(function (error) {
                                    console.log(error);
                                    this.$router.push("/kitchenquery");
                                })})
                        .catch(function (err) {
                            console.log(err);
                            this.$router.push("/kitchenquery");
                        });
                    //获取视频信息
                    axios.post("http://localhost:8181/historyvideo/findByName",{kName:this.baseData.name})
                        .then(response=>(this.videoData=response.data,this.countVideos=this.videoData.length,this.videoQuery.pageTotal= Math.ceil(this.countVideos/this.videoQuery.pageSize)))
                        .catch(function (error) {
                            console.log(error);
                            this.$router.push("/kitchenquery");
                        });

                }else {//情况二：使用时间范围查询
                    //获取基础信息
                    axios.post("http://localhost:8181/kitchen/findByName",{name:this.baseData.name})
                        .then(response=>{this.baseData.add=response.data.location,this.baseData.id=response.data.id,
                            //获取违规信息（嵌套实现同步）
                            axios.post("http://localhost:8181/alarm/findByKitchenAndDate",{id:this.baseData.id,dateFrom:this.dateChange.dateFrom,dateTo:this.dateChange.dateTo})
                                .then(response2=>(this.alarmData=response2.data,this.countAlarms=this.alarmData.length,this.alarmQuery.pageTotal= Math.ceil(this.countAlarms/this.alarmQuery.pageSize)))
                                .catch(function (error) {
                                    console.log(error);
                                    this.$router.push("/kitchenquery");
                                }),//获取视频信息
                            axios.post("http://localhost:8181/historyvideo/findByIdAndDate",{id:this.baseData.id,dateFrom:this.dateChange.dateFrom,dateTo:this.dateChange.dateTo})
                                .then(response3=>(this.videoData=response3.data,this.countVideos=this.videoData.length,this.videoQuery.pageTotal= Math.ceil(this.countVideos/this.videoQuery.pageSize)))
                                .catch(function (error) {
                                    console.log(error);
                                    this.$router.push("/kitchenquery");
                                })})
                        .catch(function (err) {
                            console.log(err);
                            this.$router.push("/kitchenquery");
                        });
                    // //获取视频信息
                    // axios.post("http://localhost:8181/historyvideo/findByIdAndDate",{id:this.baseData.id,dateFrom:this.dateChange.dateFrom,dateTo:this.dateChange.dateTo})
                    //     .then(response=>(this.videoData=response.data,this.countVideos=this.videoData.length,this.videoQuery.pageTotal= Math.ceil(this.countVideos/this.videoQuery.pageSize)))
                    //     .catch(function (error) {
                    //         console.log(error);
                    //         this.$router.push("/kitchenquery");
                    //     });
                    console.log(this.dateChange);
                    axios.get("http://localhost:8181/alarm/date",{params:this.dateChange})
                }

            },
            handlePageChange1(val){
                this.alarmQuery.pageIndex=val;
                this.created();
            },
            handlePageChange2(val){
                this.videoQuery.pageIndex=val;
                this.getData();
            },
            handleEdit(index,row){
                this.editVisible = true;
                this.form.row=row;
                this.form.idx=index;
            },
            handleDelete(index,row){


            },
            saveEdit(){
                this.editVisible = false;
                this.$message.success(`修改第 ${this.form.idx + 1} 行成功`);
                this.form.row.videoURL=this.form.videoURL;
                this.$set(this.videoData, this.form.idx, this.form.row);
            },
            onSubmit(){
                this.changeAdd.id=this.baseData.id;
                axios.put("http://localhost:8181/kitchen/update",this.changeAdd)
                .then(this.baseData.name=this.changeAdd.name,this.baseData.add=this.changeAdd.location)
                .catch()

            },
        }
    };
</script>


<style scoped>
    .el-row {
        margin-bottom: 20px;
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 24px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }

    .todo-item {
        font-size: 14px;
    }

    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }

    .schart {
        width: 100%;
        height: 300px;
    }
</style>
