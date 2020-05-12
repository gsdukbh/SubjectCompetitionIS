<template>
    <div>
        <div class="top content" v-if="rest===false">
            <el-form :model="myInfo" ref="myInfo" :rules="rules" status-icon label-position="left" abel-width="120px">

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="学号:" v-if="myInfo.role==='学生'">
                            <el-input v-model="myInfo.login" style="width: 80%" disabled readonly></el-input>
                        </el-form-item>
                        <el-form-item label="工号:" v-if="myInfo.role==='教师'|| myInfo.role==='管理员'" prop="name">
                            <el-input v-model="myInfo.login" style="width: 80%" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="名字" prop="name">
                            <el-input v-model="myInfo.name" style="width: 80%"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="手机:">
                            <el-input v-model="myInfo.phone" style="width: 80%"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">

                        <el-form-item label="邮箱:" prop="email">
                            <el-tooltip placement="top" content="填写邮箱，以便找回密码">
                                <el-input v-model="myInfo.email" style="width: 80%"></el-input>
                            </el-tooltip>

                        </el-form-item>

                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="性别:" prop="sex">
                            <el-select v-model="myInfo.sex" placeholder="请选择" style="width: 80%">
                                <el-option label="男" value="男"></el-option>
                                <el-option label="女" value="女"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="身份证号码:" prop="identity">
                            <el-input v-model="myInfo.identity" style="width: 70%"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-row v-if="myInfo.role==='教师'">
                    <el-col :span="24">
                        <el-form-item label="学院:">
                            <el-input v-model="myInfo.college.name" disabled style="width: 40%"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="myInfo.role==='学生'">
                    <el-col :span="24">
                        <el-form-item label="学院:">
                            <el-input v-model="myInfo.scisClass.major.college.name" disabled
                                      style="width: 40%"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="myInfo.role==='学生'">
                    <el-col :span="12">
                        <el-form-item label="专业:">
                            <el-input v-model="myInfo.scisClass.major.name" disabled style="width: 80%"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="班级:">
                            <el-input v-model="myInfo.scisClass.name" disabled style="width: 80%"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>


            </el-form>

            <div style="text-align: center">
                <el-button type="primary" @click="save('myInfo')" style="width: 20%" :loading="buttonLoading">
                    保存
                </el-button>
                <el-button type="success" style="width: 20%" icon="el-icon-thumb"
                           @click="rest=true">
                    重置密码
                </el-button>
            </div>
        </div>
        <div class="top " v-if="rest===true">
            <div class="center ">
                <el-form :model="resetPwd" :rules="resetRules" ref="resetPwd" status-icon label-position="top"
                         label-width="80px">
                    <el-form-item label="密码:" prop="password">
                        <el-input type="password" show-password v-model="resetPwd.password"></el-input>
                    </el-form-item>
                    <el-form-item prop="checkPassword" label="确认密码">
                        <el-input type="password" show-password v-model="resetPwd.checkPassword" autocomplete="off"
                                  placeholder="确认密码"></el-input>
                    </el-form-item>

                </el-form>
                <el-button style="width: 100%" @click="rest=false">
                    返回
                </el-button>
                <br>

                <el-button type="primary" :loading="buttonLoading" style="margin-top: 10px;width: 100%"
                           @click="savePwd('resetPwd')">
                    保存
                </el-button>
            </div>
        </div>
    </div>
</template>

<script>
    import {getJson, postFrom, postJson} from "../../api/api";
    import {mapGetters} from "vuex";


    export default {
        name: "info",

        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            let validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    callback();
                }
            };
            let validatePass3 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.resetPwd.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            let validatePass = (rule, value, callback) => {
                const info = {
                    email: value
                };
                postFrom('/i/email/repeat', info)
                    .then(response => {
                        let result = response.data.data === 'true';
                        if (this.myInfo.login === response.data.SID && result) {
                            callback()
                        } else if (result) {
                            callback(new Error('此邮箱已经绑定'));
                        } else {
                            callback()
                        }
                    });
            };
            return {
                resetPwd: {
                    id: '',
                    password: '',
                    checkPassword: '',
                },
                rest: false,
                myInfo: {
                    id: '',
                    name: '',
                    login: '',
                    email: '',
                    college: {
                        id: '',
                        name: ''
                    },
                    level: '',
                    scisClass: {
                        id: '',
                        name: '',
                        major: {
                            id: '',
                            name: '',
                            college: {
                                id: '',
                                name: '',
                            }
                        },
                    },
                    identity: '',
                    status: false,
                    phone: '',
                    role: '',
                },
                buttonLoading: false,
                resetRules: {
                    password: [
                        {validator: validatePass2, trigger: 'blur'},
                        {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'},
                        {
                            required: true,
                            pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{6,16}$/,
                            message: '包括至少1个大写字母，1个小写字母，1个数字，',
                            trigger: 'blur'
                        },
                    ],
                    checkPassword: [
                        {validator: validatePass3, trigger: 'blur'}
                    ],
                },
                rules: {
                    sex: [
                        {required: true, message: '请输入', trigger: 'change'},
                    ],
                    name: [
                        {required: true, message: '请输入', trigger: 'change'},
                    ],
                    email: [
                        {required: true, message: '请检查你的邮箱', trigger: 'blur'},
                        {
                            required: true,
                            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
                            message: '请输入正确的邮箱',
                            trigger: 'blur'
                        },
                        {validator: validatePass, trigger: 'blur'}
                    ],

                }
            }
        },
        created() {
            getJson('/i/findMyInfo/' + this.userId)
                .then(response => {
                    this.myInfo = response.data.data;
                }).catch(error => {
                this.$message.error("出现了一些问题" + error)
            });
        },
        methods: {
            savePwd(form) {
                this.resetPwd.id = this.userId;
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/i/rest/info', this.resetPwd)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '提交成功',
                                    })

                                } else {
                                    this.$notify.error({
                                        title: '错误',
                                        message: '服务器拒绝了请求'
                                    })
                                }
                                this.buttonLoading = false;
                            })
                            .catch(error => {
                                this.buttonLoading = false;
                                this.$message.error("出现一些错误:" + error)
                            });
                    } else {
                        this.buttonLoading = false;
                        this.$message.warning("请检查你填写的内容")
                    }
                })
            },
            save(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/i/upData/info', this.myInfo)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '提交成功',
                                    })

                                } else {
                                    this.$notify.error({
                                        title: '错误',
                                        message: '服务器拒绝了请求'
                                    })
                                }
                                this.buttonLoading = false;
                            })
                            .catch(error => {
                                this.buttonLoading = false;
                                this.$message.error("出现一些错误:" + error)
                            });
                    } else {
                        this.buttonLoading = false;
                        this.$message.warning("请检查你填写的内容")
                    }
                })

            }
        }

    }
</script>

<style scoped>
    .top {
        margin-top: 10px;
    }

    .content {

        width: 50%;
        margin-left: 25%;
        margin-right: 25%;
    }


    .center {
        text-align: center;
        align-content: center;
        width: 20%;
        margin-left: 40%;
        margin-right: 40%;
    }
</style>
