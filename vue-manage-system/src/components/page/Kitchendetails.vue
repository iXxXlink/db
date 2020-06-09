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
                                    <div class="grid-num">{{videoQuery.pageTotal}}</div>
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
                                    <div class="grid-num">{{alarmQuery.pageTotal}}</div>
                                    <div>违规记录</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-card shadow="hover" style="height:403px;" v-if="seeDetails.alarm">
                    <div slot="header" class="clearfix">
                        <span>违规记录</span>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="showAddAlarmV">添加</el-button>
                    </div>
                    <el-table :show-header="true" :data="alarmData.slice((alarmQuery.pageIndex-1)*alarmQuery.pageSize,(alarmQuery.pageIndex)*alarmQuery.pageSize,)" style="width:100%;">
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
            <el-card shadow="hover" style="height:600px;">
                <div slot="header" class="clearfix">
                    <span>历史视频</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="showAddVideoV">添加</el-button>
                </div>
                <el-table :show-header="true" :data="videoData.slice((videoQuery.pageIndex-1)*videoQuery.pageSize,(videoQuery.pageIndex)*videoQuery.pageSize,)" style="width:100%;">
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
            </el-card>

        </el-row>
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
        <el-dialog title="添加视频" :visible.sync="showAddVideo" width="50%">
            <el-form ref="addVideoForm" :model="addVideoForm" label-width="70px">
                <el-form-item label="视频url">
                    <el-input v-model="addVideoForm.videoURL"></el-input>
                </el-form-item>
                <el-form-item label="视频日期">
                    <el-date-picker
                            type="date"
                            placeholder="Date"
                            v-model="addVideoForm.date"
                            value-format="yyyy-MM-dd"
                            style="width: 100%;"
                    ></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showAddVideo= false">取 消</el-button>
                <el-button type="primary" @click="saveAddVideo">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog title="添加违规记录" :visible.sync="showAddAlarm" width="50%">
            <el-form ref="addAlarmForm" :model="addAlarmForm" label-width="70px">
                <el-form-item label="违规类型">
                    <el-input v-model="addAlarmForm.type"></el-input>
                </el-form-item>
                <el-form-item label="截图url">
                    <el-input v-model="addAlarmForm.picURL"></el-input>
                </el-form-item>
                <el-form-item label="日期">
                    <el-date-picker
                            type="date"
                            placeholder="Date"
                            v-model="addAlarmForm.datetime"
                            value-format="yyyy-MM-dd"
                            style="width: 100%;"
                    ></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showAddAlarm= false">取 消</el-button>
                <el-button type="primary" @click="saveAddAlarm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import Schart from 'vue-schart';
    import bus from '../common/bus';
    export default {
        name: 'dashboard',

        data() {
            return {
                user:localStorage.getItem("ms_username"),
                seeDetails:{
                    base:this.$route.params.base,
                    alarm:this.$route.params.alarm,
                    video:this.$route.params.video,
                },
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
                    pageSize: 3,
                    pageTotal:5
                },
                videoQuery:{
                    pageIndex: 1,
                    pageSize: 5,
                    pageTotal:5,
                },
                form:{
                },
                tmpURL:"",
                table2:{
                    row:0,
                    idx:0,
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
                showAddVideo:false,
                showAddAlarm:false,
                addVideoForm:{
                    id:null,
                    videoURL:"",
                    date:"",
                },
                addAlarmForm:{
                    id:null,
                    picURL:"",
                    datetime:"",
                    type:"",
                }
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
            showAddVideoV(){
                this.showAddVideo=true;
                this.addVideoForm.id=this.baseData.id+"";
            },
            showAddAlarmV(){
                this.showAddAlarm=true;
                this.addAlarmForm.id=this.baseData.id+"";
            },
            getData(){
                //情况一：未限定时间范围
                if(!this.$route.params.useDate){
                    //获取基础信息
                    axios.post("http://localhost:8181/kitchen/findByName",{name:this.baseData.name})
                        .then(response=>{this.baseData.add=response.data.location,this.baseData.id=response.data.id,
                            //获取违规信息（嵌套实现同步）
                            axios.get("http://localhost:8181/alarm/findByKitchen",{params:{id:this.baseData.id}})
                                .then(response2=>(this.alarmData=response2.data,this.alarmQuery.pageTotal=response2.data.length))
                                .catch(function (error) {
                                    console.log(error);
                                    this.logAction(error);
                                    this.$router.push("/kitchenquery");
                                })})
                        .catch(function (err) {
                            console.log(err);
                            this.logAction(error);
                            this.$router.push("/kitchenquery");
                        });
                    //获取视频信息
                    axios.post("http://localhost:8181/historyvideo/findByName",{kName:this.baseData.name})
                        .then(response=>(this.videoData=response.data,this.videoQuery.pageTotal=this.videoData.length))
                        .catch(function (error) {
                            console.log(error);
                            this.logAction(error);
                            this.$router.push("/kitchenquery");
                        });

                }else {//情况二：使用时间范围查询
                    //获取基础信息
                    axios.post("http://localhost:8181/kitchen/findByName",{name:this.baseData.name})
                        .then(response=>{this.baseData.add=response.data.location,this.baseData.id=response.data.id,
                            //获取违规信息（嵌套实现同步）
                            axios.post("http://localhost:8181/alarm/findByKitchenAndDate",{id:this.baseData.id,dateFrom:this.dateChange.dateFrom,dateTo:this.dateChange.dateTo})
                                .then(response2=>(this.alarmData=response2.data,this.alarmQuery.pageTotal= response2.data.length))
                                .catch(function (error) {
                                    console.log(error);
                                    this.logAction(error);
                                    this.$router.push("/kitchenquery");
                                }),//获取视频信息
                            axios.post("http://localhost:8181/historyvideo/findByIdAndDate",{id:this.baseData.id,dateFrom:this.dateChange.dateFrom,dateTo:this.dateChange.dateTo})
                                .then(response3=>(this.videoData=response3.data,this.videoQuery.pageTotal= response3.data.length))
                                .catch(function (error) {
                                    console.log(error);
                                    this.logAction(error);
                                    this.$router.push("/kitchenquery");
                                })})
                        .catch(function (err) {
                            console.log(err);
                            this.logAction(err);
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
            getPage(){

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
                this.table2.row=row;
                this.table2.idx=index;
            },
            handleDelete(index,row){
                axios.get("http://localhost:8181/historyvideo/deleteByVideoId",{params:{videoId:row.videoId}})
                    .then(response=>{
                        if(response.data===1){
                            this.$message.success('删除成功');
                            this.videoData.splice(index, 1);
                            this.getData();
                            this.logAction("删除了video:"+row.videoId);
                        }else {
                            this.$message.error("删除失败");
                        }
                    }).catch(function (err) {
                    console.log(err);
                    this.logAction(err);
                });
            },
            saveEdit(){
                axios.post("http://localhost:8181/historyvideo/changeURL",{videoId:this.table2.row.videoId,videoURL:this.table2.row.videoURL})
                    .then(response=>{
                        if(response.data===1){
                            this.table2.row.videoURL=this.tmpURL;
                            this.$message.success(`修改第 ${this.table2.idx + 1} 行成功`);
                            this.$set(this.videoData, this.table2.idx, this.table2.row);
                            this.logAction("修改了video："+this.table2.row.videoId+"的视频地址");
                        }
                        else{
                            this.$message.success(`修改失败`);
                        }
                    })
                    .catch(function (err) {
                    this.logAction(err);
                    })
                this.editVisible = false;

            },
            saveAddVideo(){
                this.showAddVideo=false;
                axios.put("http://localhost:8181/historyvideo/save",this.addVideoForm)
                    .then(response=>{
                        if(response.data===1){
                            this.$message.success("添加成功！");
                            this.getData();
                            this.logAction("给"+this.baseData.name+"添加了video");
                        }else{
                            this.$message.error("添加失败");
                        }
                    })

            },
            saveAddAlarm(){
                this.showAddAlarm=false;
                axios.post("http://localhost:8181/alarm/save",this.addAlarmForm)
                    .then(response=>{
                        if(response.data===1){
                            this.$message.success("添加成功！");
                            this.getData();
                            this.logAction("给"+this.baseData.name+"添加了alarm");
                        }else{
                            this.$message.error("添加失败");
                        }
                    })
            },
            onSubmit(){
                this.changeAdd.id=this.baseData.id;
                axios.put("http://localhost:8181/kitchen/update",this.changeAdd)
                    .then(this.baseData.name=this.changeAdd.name,this.baseData.add=this.changeAdd.location
                    ,this.logAction("给"+this.baseData.name+"修改了地址"))
                    .catch(function (error) {
                        this.logAction(error);
                    })


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
