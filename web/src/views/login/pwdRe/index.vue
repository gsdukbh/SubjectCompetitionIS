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

            <el-card class="box-card" v-show="card === 0">
                <div slot="header" class="clearfix">
                    <span>找回密码</span>

                    <!--   <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->

                </div>

                <!--内容-->
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

                    <el-form-item class="item" label="账号" prop="name">
                        <el-input class="input" v-model="ruleForm.name" placeholder="请输入学号/工号/身份号/手机/邮箱"></el-input>
                    </el-form-item>

                    <el-form-item class="item" label="手机验证" prop="delivery">

                        <el-switch class="sw" v-model="ruleForm.delivery"></el-switch>

                    </el-form-item>

                    <el-form-item class="button">

                        <el-button type="primary"  @click="submitForm('ruleForm')">立即创建</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>

                </el-form>
                <!--完成验证码 Popover 弹出框-->
                <el-dialog
                        title="提示"
                        :visible.sync="dialogVisible"
                        width="30%"
                        :before-close="handleClose">
                    <span>这是一段信息</span>

                </el-dialog>

                <el-button type="primary" round style="margin-top: 12px;" @click="next">下一步</el-button>


            </el-card>

        </el-card>
    </div>

</template>

<script>

    import Vue from 'vue';
    import SlideVerify from 'vue-monoplasty-slide-verify';

    Vue.use(SlideVerify);
    export default {
        name: "password",
        data() {
            return {
                text: '向右滑完成验证',
                active: 0,
                card: 0,
                dialogVisible: false,
                ruleForm: {
                    name: '',
                    delivery: false,

                },
                rules: {
                    name: [
                        {required: true, message: '请输入活动名称', trigger: 'blur'},
                        {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                    ],

                }
            }
        },
        methods: {
            next() {

                if (this.active++ > 2)
                    this.active = 0;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(() => {
                        done();
                    })
                    .catch(() => {});
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

    .item {

        position: relative;
        text-align: center;
        float: left;
        display: inline-block;
    }

    .sw {
        margin: 0 0 0 0;
        float: none;
        display: inline-block;
        padding: 10px 5px;
        position: absolute;
        top: 0;
    }

    .button {
        padding-top: 80px;
    }

    .input {
        width: 400px;
    }

    .bg {
        background-color: rgba(0, 0, 0, 0.8)
    }
</style>
