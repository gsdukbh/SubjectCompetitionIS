<template>
    <div class="main">
        <!---->
        <div class="content">
            <el-card shadow="hover">
                <h1 style="text-align: center">注 册</h1>
                <el-divider></el-divider>


                <el-form :model="register" status-icon :rules="rules" ref="register" label-width="100px"
                         class="register">
                    <el-form-item prop="login">
                        <el-input type="text" v-model="register.login" autocomplete="off" placeholder="学号"></el-input>
                    </el-form-item>

                    <el-form-item prop="password">
                        <el-input type="password" v-model="register.password" autocomplete="off"
                                  placeholder="密码"></el-input>
                    </el-form-item>

                    <el-form-item prop="password1">
                        <el-input type="password" v-model="register.password1" autocomplete="off"
                                  placeholder="确认密码"></el-input>
                    </el-form-item>

                    <el-form-item prop="email">
                        <el-input placeholder="输入邮箱" v-model="register.email" style="width: 72%;margin-left: 0">
                        </el-input>
                        <el-button v-loading="loading" style="width: 25%;margin-left: 3%" @click="sendCode('register')">
                            发送验证码
                        </el-button>
                    </el-form-item>

                    <el-form-item prop="code" v-if="codeShow===true">
                        <el-input placeholder="输入验证码" v-model="register.code">
                        </el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button v-loading="loading1" type="primary" @click="submitForm('register')">注 册</el-button>
                        <el-button native-type="reset">重 置</el-button>
                    </el-form-item>

                </el-form>
            </el-card>
        </div>
    </div>
</template>

<script>


    import {postFrom, postJson} from "../../api/api";


    export default {
        name: "index",
        data() {
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    callback();
                }
            };
            let validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.register.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            let validatePass3 = (rule, value, callback) => {
                const post = {
                    login: value
                };
                postFrom('/public/repeat', post)
                    .then(response => {
                        let result = response.data.data === 'true';
                        if (result) {
                            callback(new Error('该学号已经注册，请前往登录'));
                        } else {
                            callback()
                        }
                    });
            };
            return {
                register: {
                    login: '',
                    password: '',
                    password1: '',
                    code: '',
                    email: '',
                },
                loading: false,
                loading1: false,
                toLogin: {
                    username: '',
                    password: '',
                },
                codeShow: false,
                rules: {
                    login: [
                        {required: true, message: '请输入学号', trigger: 'blur'},
                        {min: 4, max: 20, message: '长度在 4 到 20 个字符', trigger: 'blur'},
                        {
                            required: true,
                            pattern: /^[0-9a-zA-Z]{4,20}$/,
                            message: '学号包含0-9，26个字母',
                            trigger: 'blur'
                        },
                        {validator: validatePass3, trigger: 'blur'}

                    ],
                    password: [
                        {validator: validatePass, trigger: 'blur'},
                        {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'},
                        {
                            required: true,
                            pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{6,16}$/,
                            message: '包括至少1个大写字母，1个小写字母，1个数字，',
                            trigger: 'blur'
                        },
                    ],
                    password1: [
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '请检查你的邮箱', trigger: 'blur'},
                        {
                            required: true,
                            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
                            message: '请输入正确的邮箱',
                            trigger: 'blur'
                        },
                    ],
                    code: [
                        {required: true, message: '请输入活动名称', trigger: 'blur'},
                    ]
                },
            }
        },
        methods: {
            submitForm(formName) {
                this.loading1 = true;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        postJson('/public/register', this.register)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '注册成功',
                                        message: response.data.message,
                                    });
                                    this.toLogin.username = this.register.login;
                                    this.toLogin.password = this.register.password;
                                    this.$store.dispatch('user/login', this.toLogin)
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
                                        });
                                    this.loading1 = false;

                                } else {
                                    this.$notify.error({
                                        title: '错误',
                                        message: response.data.message,
                                    });
                                    this.loading1 = false;
                                }
                            })
                            .catch(error => {
                                this.$notify.error({
                                    title: '错误',
                                    message: '出现了一些问题：' + error
                                })
                                this.loading1 = false;
                            })
                    } else {
                        this.$notify.info({
                            title: '提示',
                            message: '请检查信息'
                        });
                        this.loading1 = false;
                    }
                });
            },
            sendCode(formName) {
                this.$refs[formName].validate((valid) => {
                    this.loading = true;
                    if (valid) {
                        postFrom('/public/register/sendEmail', this.register)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '成功',
                                        message: response.data.message,
                                    })
                                }
                                this.codeShow=true;
                                this.loading = false;
                            })
                            .catch(error => {
                                this.$notify.error({
                                    title: '错误',
                                    message: '出现了一些问题：' + error
                                })
                            })
                    } else {
                        this.loading = false;
                    }
                });
            },


        }
    }
</script>

<style scoped>
    .content {
        float: left;
        width: 80%;
        margin-left: 10%;
        margin-top: 10px;

    }

    .main {
        height: auto;
        background-color: #ffffff; /* 浏览器不支持时显示 */
        background-image: linear-gradient(#ffffff, #b6fffb);

    }

    .register {
        text-align: center;
        width: 35%;
        margin-left: 30%;
    }
</style>
