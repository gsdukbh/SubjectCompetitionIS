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

            <!--查找账号-->
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
            <!--发送验证码-->
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
                <el-divider></el-divider>

                <el-row v-if="value1 === false">
                    <el-col :span="8">
                        <span class="grid-content">输入完整邮箱</span>
                    </el-col>
                    <el-col :span="6">
                        <el-input v-model="getData.email" class="center " placeholder="请输入"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-button type="primary" class="button1" @click="sendEmail" round>发送验证码</el-button>
                    </el-col>
                </el-row>
                <el-row v-if="value1 === true">
                    <el-col :span="8">
                        <span class="grid-content">输入完整手机</span>
                    </el-col>
                    <el-col :span="6">
                        <el-input v-model="getData.phone" class="center " placeholder="请输入"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-button type="primary" class="button1" @click="sendPhone" round>发送验证码</el-button>
                    </el-col>
                </el-row>
                <el-button :loading="loading" type="primary" style="width:30%;margin-bottom:30px;"
                           @click.native.prevent="next">下一步
                </el-button>
            </el-card>
            <!--密码重置-->
            <el-card class="box-card" v-if="card === 2">
                <div slot="header" class="clearfix">
                    <span>完成</span>
                </div>

                <el-form :model="recover" status-icon :rules="rules" ref="recover" label-width="100px"
                         class="demo-ruleForm">
                    <el-form-item label="密码" prop="pass">
                        <el-input type="password" v-model="recover.password" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="checkPass">
                        <el-input type="password" v-model="recover.checkPass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="age">
                        <el-input v-model.number="recover.code"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="recoverForm('recover')">提交</el-button>
                        <el-button native-type="reset">重置</el-button>
                    </el-form-item>
                    <el-button  type="primary" style="width:30%;margin-bottom:30px;"
                               @click.native.prevent="re">没有收到验证码?
                    </el-button>
                </el-form>

            </el-card>
        </el-card>
    </div>

</template>

<script>

    import Vue from 'vue';
    import SlideVerify from 'vue-monoplasty-slide-verify';
    import {postFrom} from '../../../api/api'
    import NProgress from 'nprogress'
    import 'nprogress/nprogress.css'
    Vue.use(SlideVerify);
    export default {
        name: "password",
        data() {
            const checkCode = (rule, value, callback) => {
                if (!value) {
                    callback(new Error('验证码不能为空'));
                }else {
                    callback()
                }
            };
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.recover.checkPass !== '') {
                        this.$refs.recover.validateField('checkPassword');
                    }
                    callback();
                }
            };
            const validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.recover.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                text: '向右滑完成验证',
                active: 0,
                card: 0,
                value1: false,
                dialogVisible: false,
                loading: true,
                imgs: [],
                ruleForm: {
                    name: '1361404576@qq.com',
                    delivery: false,

                },
                getData: {
                    email: '',
                    phone: ''
                },
                recover:{
                    password:'',
                    email:'',
                    checkPass:'',
                    code: ''

                },
                rules: {
                    name: [
                        {required: true, message: '请输入账号信息', trigger: 'blur'},
                        {min: 3, message: '长度在 3 个字符 以上', trigger: 'blur'}
                    ],
                    password: [{ validator: validatePass, trigger: 'blur' }],
                    checkPass: [{ validator: validatePass2, trigger: 'blur' }],
                    code: [{ validator: checkCode, trigger: 'blur' }]
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
                NProgress.start()
                postFrom('/public/password/recover/find', this.ruleForm)
                    .then(response => {
                        if (response.data.code === 200) {
                            this.getData.email = response.data.email || '';
                            this.getData.phone = response.data.phone || '';
                            this.card = 1;
                            this.active = 1;
                            this.$notify({
                                title: '查询成功',
                                message: response.data.message,
                                type: 'success'
                            });
                            NProgress.done()
                        } else {
                            this.$message.error(response.data.message);
                        }
                    })
                    .catch(error => {
                        this.$message.error("出现了一些问题" + error)
                    })
                NProgress.done()
            },
            onFail() {

            },
            onRefresh() {

            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            next() {
                this.active = 2;
                this.card = 2;
            },
            re(){
                this.card = 1;
                this.active = 1;
            },
            sendPhone() {
                this.$notify.info({
                    title: '抱歉',
                    message: '手机验证暂时无法提供'
                });
            },
            async sendEmail() {
                NProgress.start()
                await postFrom('/public/password/recover/email', this.getData)
                    .then(response => {
                        if (response.data.code === 200) {
                            this.$notify({
                                title: '发送成功',
                                message: response.data.message,
                                type: 'success'
                            });
                            this.recover.email = this.getData.email;
                            this.loading = false;
                            NProgress.done()
                        } else if (response.data.code === 404) {
                            this.$notify({
                                title: '警告',
                                message: response.data.message,
                                type: 'warning'
                            });
                        }
                    })
                    .catch(error => {
                        this.$message.error("出现了一些问题" + error)
                    })
                NProgress.done()
            },
           recoverForm(formName) {
                this.$refs[formName].validate((valid) => {
                    NProgress.start()
                    if (valid) {
                        postFrom('/public/password/recover/email/code',this.recover)
                        .then(response=>{
                            if (response.data.code === 200) {
                                this.$notify({
                                    title: '成功',
                                    message: response.data.message,
                                    type: 'success'
                                });
                                this.$notify.info({
                                    title: '前往登录中',
                                    message: ''
                                });
                                setTimeout(() => {
                                    this.$router.push({path: '/login' || '/'})
                                }, 3000 * Math.random());
                                NProgress.done()
                            }else if (response.data.code === 404){
                                this.$notify.error({
                                    title: '超时',
                                    message: response.data.message,
                                });
                            }
                        }).catch(error=>{
                            this.$message.error("出现了一些问题" + error)
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    NProgress.done()
                });
            },

        }


    }
</script>

<style lang="scss" scoped>

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

    .button1 {
        align-content: center;

    }

    .grid-content {
        border-radius: 4px;
        min-height: 36px;
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

    .el-row {
        margin-bottom: 20px;

        &:last-child {
            margin-bottom: 0;
        }
    }
</style>
