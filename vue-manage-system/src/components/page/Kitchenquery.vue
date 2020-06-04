<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 餐厅详细信息
                </el-breadcrumb-item>

            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="限定时间">
                        <el-switch v-model="form.useTime"></el-switch>
                    </el-form-item>
                    <el-form-item label="餐厅名称" >
                        <el-input v-model="form.name" placeholder="填入餐厅名称，以获得餐厅的位置信息，视频信息，违规信息" ></el-input>
                    </el-form-item>
<!--                    <el-form-item label="餐厅ID" v-if="form.useId">-->
<!--                        <el-input v-model="form.id" placeholder="填入餐厅id" ></el-input>-->
<!--                    </el-form-item>-->
                    <el-form-item label="日期时间" v-if="form.useTime">
                        <el-col :span="11">
                            <el-date-picker
                                    type="date"
                                    placeholder="DateFrom"
                                    v-model="form.date1"
                                    value-format="yyyy-MM-dd"
                                    style="width: 100%;"
                            ></el-date-picker>
                        </el-col>
                        <el-col class="line" :span="2">--</el-col>
                        <el-col :span="11">
                            <el-date-picker
                                    type="date"
                                    placeholder="DateTo"
                                    v-model="form.date2"
                                    value-format="yyyy-MM-dd"
                                    style="width: 100%;"
                            ></el-date-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="查询内容">
                        <el-checkbox-group v-model="form.type" >
                            <el-checkbox label="查询餐厅基本信息" name="base" ></el-checkbox>
                            <el-checkbox label="查询餐厅历史视频" name="video" ></el-checkbox>
                            <el-checkbox label="查询餐厅违规记录" name="alarm" ></el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>



                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">表单提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    import Kitchendetails from "./Kitchendetails";

    export default {
        name: 'baseform',
        data() {
            return {
                form: {
                    name: '',
                    date1: '',
                    date2: '',
                    useTime: false,
                    id:0,
                    type:[],
                    base:false,
                    alarm:false,
                    video:false,
                    validate:true,
                }
            };
        },
        methods: {
            onSubmit() {
                // this.$message.success('提交成功！');
                //如果餐厅名称不为空，并且不限定时间范围
                if(this.form.name!==""){
                    if(this.form.type.includes("查询餐厅基本信息")){
                        this.form.base=true;
                    }else {
                        this.form.base=false;
                    }
                    if(this.form.type.includes("查询餐厅历史视频")){
                        this.form.video=true;

                    }
                    else {
                        this.form.video=false;
                    }
                    if(this.form.type.includes("查询餐厅违规记录")){
                        this.form.alarm=true;
                    }
                    else {
                        this.form.alarm=false;
                    }
                    this.$router.push({
                        path:"/kitchendetails",
                        name:"kitchendetails",
                        params:{
                            base:this.form.base,
                            video:this.form.video,
                            alarm:this.form.alarm,
                            name:this.form.name,
                            useDate:this.form.useTime,
                            dateFrom:this.form.date1,
                            dateTo:this.form.date2,
                        }
                    });
                }


            }
        }
    };
    </script>