<template>
    <div class="login-container" >
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
                 label-position="left">

            <div class="title-container">
                <h2 class="title">登 录</h2>
            </div>

            <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
                <el-input
                        ref="username"
                        v-model="loginForm.username"
                        placeholder="学号/工号/身份号/手机/邮箱"
                        name="username"
                        type="text"
                        tabindex="1"
                        auto-complete="on"
                />
            </el-form-item>

            <el-form-item prop="password3">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
                <el-input
                        :key="passwordType"
                        ref="password"
                        v-model="loginForm.password"
                        :type="passwordType"
                        placeholder="密码"
                        name="password"
                        tabindex="2"
                        auto-complete="on"
                        @keyup.enter.native="handleLogin"
                />
                <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
            </el-form-item>

            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                       @click.native.prevent="handleLogin">Login
            </el-button>

            <div style="position:relative">

                <div class="tips">
                    <span> <el-link type="primary" @click="toHome">返回首页</el-link></span>

                    <span > <router-link to="/public/register">
                        <el-link type="primary"  >注 册</el-link>
                    </router-link></span>

                    <span style="margin-left: 10px">  <el-link type="warning" @click="passwordRecover">忘记密码</el-link></span>
                </div>

                <div class="tips">
                    <span> 默认root : root</span>
                    <span> 密码 : 123456</span>
                </div>
                <div class="tips">
                    <span> 学生测试账号 : stu</span>
                    <span> 密码 : 123456</span>
                </div>
                <div class="tips">
                    <span> 教师测试账号 : tea</span>
                    <span> 密码 : 123456</span>
                </div>

                <!--oath2 验证-->
<!--                <el-button class="thirdparty-button" type="primary" @click="showDialog=true">-->
<!--                    Or connect with-->
<!--                </el-button>-->
            </div>

        </el-form>
    </div>
</template>

<script>

    export default {
        name: "Login",
        data() {
            const validateUsername = (rule, value, callback) => {
                if (value.length < 1) {
                    callback(new Error('请输入你的用户名'))
                } else {
                    callback()
                }
            }
            const validatePassword = (rule, value, callback) => {
                if (value.length < 6) {
                    callback(new Error('密码长度不能小于6位'))
                } else {
                    callback()
                }
            }
            return {
                loginForm: {
                    username: 'root',
                    password: '123456'/*默认*/
                },

                loginRules: {
                    username: [{required: true, trigger: 'blur', validator: validateUsername}],
                    password: [{required: true, trigger: 'blur', validator: validatePassword}]
                },
                passwordType: 'password',
                capsTooltip: false,
                loading: false,
                msg:''
            }
        },

        created() {//初始化后

        },
        mounted() {//创建vm.$el之后
            /*判空*/
            if (this.loginForm.username === '') {
                this.$refs.username.focus()
            } else if (this.loginForm.password === '') {
                this.$refs.password.focus()
            }
        },
        destroyed() {//销毁之后

        },
        methods: {
            showPwd() {
                if (this.passwordType === 'password') {
                    this.passwordType = ''
                } else {
                    this.passwordType = 'password'
                }
                this.$nextTick(() => {
                    this.$refs.password.focus()
                })
            },
            handleLogin() {/*登录前检验*/

                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        this.loading = true
                        this.$store.dispatch('user/login', this.loginForm)
                            .then(response => {

                                if (response.data.status === 401) {
                                    this.$notify.error({
                                        title: '错误',
                                        message: response.data.message
                                    });

                                } else if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '登录成功',
                                        message: response.data.message
                                    });

                                 this.$router.push({path: '/home' || '/'})
                                }

                                this.loading = false
                            })
                            .catch(error => {
                                this.$message.error("出现了一些问题" + error)
                                this.loading = false
                            })
                    } else {
                        this.$notify.error({
                            title: '错误',
                            message: "错误提交"
                        });
                        return false
                    }

                })
            },
            passwordRecover(){
                this.$router.push({path:'/password/recover'});
            },
            toHome(){
                this.$router.push({path:'/'});
            }
        }
    }
</script>
<style lang="scss" >


    $bg:#283443;
    $light_gray:#fff;
    $cursor: #fff;

    @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
        .login-container .el-input input {
            color: $cursor;
        }
    }

    /* reset element-ui css */
    .login-container {
        .el-input {
            display: inline-block;
            height: 47px;
            width: 85%;

            input {
                background: transparent;
                border: 0px;
                -webkit-appearance: none;
                border-radius: 0px;
                padding: 12px 5px 12px 15px;
                color: $light_gray;
                height: 47px;
                caret-color: $cursor;

                &:-webkit-autofill {
                    box-shadow: 0 0 0px 1000px $bg inset !important;
                    -webkit-text-fill-color: $cursor !important;
                }
            }
        }

        .el-form-item {
            border: 1px solid rgba(255, 255, 255, 0.1);
            background: rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            color: #454545;
        }
    }
</style>

<style lang="scss" scoped>
    $bg:#2d3a4b;
    $dark_gray:#889aa4;
    $light_gray:#eee;

    .login-container {
        min-height: 100%;
        width: 100%;
        background-color: $bg;
        overflow: hidden;

        .login-form {
            position: relative;
            width: 520px;
            max-width: 100%;
            padding: 160px 35px 0;
            margin: 0 auto;
            overflow: hidden;
        }

        .tips {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;

            span {
                &:first-of-type {
                    margin-right: 16px;
                }
            }
        }

        .svg-container {
            padding: 6px 5px 6px 15px;
            color: $dark_gray;
            vertical-align: middle;
            width: 30px;
            display: inline-block;
        }

        .title-container {
            position: relative;

            .title {
                font-size: 26px;
                color: $light_gray;
                margin: 0px auto 40px auto;
                text-align: center;
                font-weight: bold;
            }
        }

        .show-pwd {
            position: absolute;
            right: 10px;
            top: 7px;
            font-size: 16px;
            color: $dark_gray;
            cursor: pointer;
            user-select: none;
        }
        .right{
            right: 0;

        }
        .thirdparty-button {
            position: absolute;
            right: 0;
            bottom: 6px;

        }
        @media only screen and (max-width: 470px) {
            .thirdparty-button {
                display: none;
            }
        }
    }
</style>
