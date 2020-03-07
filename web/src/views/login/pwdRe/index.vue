<template>
    <div id="center">

        <el-card class="recover">
            <h2> 密码重置</h2>

            <!--分割线-->
            <el-divider class="bg"><i class="el-icon-lollipop"/></el-divider>

            <el-steps :active="active" finish-status="success" align-center>
                <el-step title="步骤 1"></el-step>
                <el-step title="步骤 2"></el-step>
                <el-step title="步骤 3"></el-step>
            </el-steps>

            <el-divider class="bg"></el-divider>

            <el-card class="box-card" v-if="card === 0">
                <div slot="header" class="clearfix">
                    <span>找回密码</span>


                </div>

                <!--内容-->
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

                    <el-form-item class="item" label="账号" prop="name">
                        <el-input class="input" v-model="ruleForm.name" placeholder="请输入学号/工号/身份号/手机/邮箱"></el-input>
                    </el-form-item>


                    <el-form-item class="button">

                        <!--完成验证码 Popover 弹出框-->
                        <el-popover
                                placement="right"
                                width="400"
                                trigger="click">

                            <slide-verify :l="50"
                                          :r="10"
                                          :w="310"
                                          :h="155"
                                          :imgs="imgs"
                                          @success="onSuccess"
                                          @fail="onFail"
                                          @refresh="onRefresh"
                                          :slider-text="text"
                                          class="span"
                            ></slide-verify>

                            <el-button type="primary" slot="reference" @click="submitForm('ruleForm')">立即找回</el-button>

                        </el-popover>

                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>

                </el-form>

            </el-card>
            <el-card class="box-card" v-if="card === 1">
                <div slot="header" class="clearfix">
                    <span>验证方式</span>
                </div>
                <el-form label-width="50px" class="demo-ruleForm">

                    <el-form-item class="item" prop="delivery">
                        <el-switch
                                v-model="value1"
                                active-text="手机验证"
                                inactive-text="邮箱验证">
                        </el-switch>
                    </el-form-item>

                </el-form>
                <div>
                    <el-divider></el-divider>
                    <span v-if="value1 ===false ">你绑定的邮箱账号：{{getData.email}}</span>

                    <span v-if="value1 ===true">少量的邪恶足以抵消全部高贵的品质, 害得人声名狼藉</span>
                </div>
                <el-row>
                    <el-col :span="12">
                        <el-input v-model="input" class="center "  placeholder="请输入内容"></el-input>
                    </el-col>
                    <el-col :span="12">
                        <el-button type="primary" class="button1" round>主要按钮</el-button>
                    </el-col>

                </el-row>
            </el-card>
        </el-card>
    </div>

</template>

<script>

    import Vue from 'vue';
    import SlideVerify from 'vue-monoplasty-slide-verify';
    import {postFrom} from '../../../api/api'

    Vue.use(SlideVerify);
    export default {
        name: "password",
        data() {
            return {
                text: '向右滑完成验证',
                active: 0,
                card: 1,
                value1: false,
                dialogVisible: false,
                loding: true,
                imgs: [],
                ruleForm: {
                    name: '1361404576@qq.com',
                    delivery: false,

                },
                getData: {
                    email: '',
                    phone: ''
                },
                rules: {
                    name: [
                        {required: true, message: '请输入账号信息', trigger: 'blur'},
                        {min: 3, message: '长度在 3 个字符 以上', trigger: 'blur'}
                    ],

                }
            }
        },
        mounted() {
            if (this.ruleForm.name === '') {
                this.$refs.name.focus()
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        return true
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            onSuccess() {
                this.$message({
                    message: '验证成功',
                    type: 'success'
                });
                postFrom('/public/password/recover/find', this.ruleForm)
                    .then(response => {
                        if (response.data.code === 200) {
                            this.getData.email = response.data.email || '';
                            this.getData.phone = response.data.phone || '';
                            this.card = 1;
                            this.active = 1
                        } else {
                            this.$message.error(response.data.message);
                        }
                    })
                    .catch(error => {
                        this.$message.error("出现一些问题" + error)
                    })

            },
            onFail() {

            },
            onRefresh() {

            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            handleClose(done) {
                this.$confirm('放弃验证？')
                    .then(() => {
                        done();
                    })
                    .catch(() => {
                    });
            }
        }


    }
</script>

<style scoped>

    #center {
        align-content: center;
        text-align: center;
        width: auto;
        height: 100vh;
        background-image: url("https://w.wallhaven.cc/full/q6/wallhaven-q68mxl.png");
        padding-top: 50px;
    }

    .recover {
        background-color: #ffffff;
        align-content: center;
        text-align: center;
        height: 750px;
        width: 80%;
        margin: 0 auto;
        background-color: rgba(255, 255, 255, 0.7)
    }

    .box-card {
        text-align: center;
        width: 60%;
        margin: 0 auto;
        background-color: rgba(255, 255, 255, 0.8)
    }


    .button {
        padding-top: 80px;
    }
    .button1{
       align-content: center;

    }

    .input {
        width: 400px;
    }

    .bg {
        background-color: rgba(0, 0, 0, 0.8)
    }

    .span {
        text-align: center;
        left: auto;
    }
</style>
