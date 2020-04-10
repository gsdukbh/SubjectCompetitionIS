<template>

    <div>

        <el-tabs tab-position="right" class="top" type="card">

            <el-tab-pane label="添加用户">

                <div class="top main">

                    <el-form :model="userInfo" status-icon label-position="left" :rules="rules" ref="userInfo"
                             label-width="auto">

                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="学号/工号:" prop="login">
                                    <el-input v-model="userInfo.login" style="width: 80%"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="名字: " prop="name">
                                    <el-input v-model="userInfo.name" STYLE="width: 80%"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="12">

                                <el-form-item label="密码: " prop="password">
                                    <el-input type="password" v-model="userInfo.password" STYLE="width: 80%"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="再次输入密码: " prop="checkPassword">
                                    <el-input type="password" v-model="userInfo.checkPassword"
                                              STYLE="width: 80%"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>


                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="身份" prop="role">

                                    <el-select v-model="userInfo.role" style="width: 80%">
                                        <el-option label="学生" value="学生"></el-option>
                                        <el-option label="教师" value="教师"></el-option>
                                        <el-option label="管理员" value="管理员"></el-option>
                                    </el-select>
                                </el-form-item>

                            </el-col>
                            <el-col :span="12">


                                <el-form-item label="手机号码">
                                    <el-input v-model="userInfo.phone" style="width: 80%"></el-input>
                                </el-form-item>

                            </el-col>

                        </el-row>


                        <el-row>

                            <el-col :span="12">

                                <el-form-item label="学院" prop="college"
                                              v-if="userInfo.role==='学生'||userInfo.role==='教师'">

                                    <el-select v-model="userInfo.college" filterable placeholder="请选择"
                                               style="width: 80%">
                                        <el-option
                                                v-for="item in college"
                                                :key="item.id"
                                                :label="item.name"
                                                :value="item.name">
                                        </el-option>
                                    </el-select>

                                </el-form-item>

                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="专业" v-if="userInfo.role==='学生'" prop="majorName">
                                    <el-select v-model="userInfo.majorName" filterable placeholder="请选择"
                                               style="width: 80%">
                                        <el-option
                                                v-for="item in major"
                                                :key="item.id"
                                                :label="item.name"
                                                :value="item.name">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="培养层次" prop="level" v-if="userInfo.role==='学生'">
                                    <el-select v-model="userInfo.level" placeholder="请选择" style="width: 80%">
                                        <el-option label="本科" value="本科"></el-option>
                                        <el-option label="专科" value="专科"></el-option>
                                    </el-select>

                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="班级" prop="className" v-if="userInfo.role==='学生'">
                                    <el-select v-model="userInfo.className" filterable placeholder="请选择"
                                               style="width: 80%">
                                        <el-option
                                                v-for="item in classList"
                                                :key="item.id"
                                                :label="item.name"
                                                :value="item.name">
                                        </el-option>
                                    </el-select>

                                </el-form-item>
                            </el-col>
                        </el-row>


                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="身份证号码">
                                    <el-input v-model="userInfo.identity" style="width: 80%"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="12">
                                <el-form-item label="邮箱" prop="email">
                                    <el-input v-model="userInfo.email" style="width: 80%"></el-input>
                                </el-form-item>
                            </el-col>

                        </el-row>

                        <el-button style="width: 50%" native-type="reset">
                            重置
                        </el-button>
                        <br>
                        <el-button style="width: 50%;margin-top: 10px" type="primary" @click="commit('userInfo')"
                                   :loading="buttonLoading">
                            提交
                        </el-button>

                    </el-form>

                </div>


            </el-tab-pane>


            <el-tab-pane label="添加学院信息">

                <college/>

            </el-tab-pane>


            <el-tab-pane label="添加专业信息">
                <major/>
            </el-tab-pane>

            <el-tab-pane label="添加班级信息">
                <class-info/>
            </el-tab-pane>
        </el-tabs>


    </div>

</template>

<script>
    import {getJson, postFrom, postJson} from "../../api/api";
    import College from "./components/college";
    import Major from "./components/major";
    import ClassInfo from "./components/classInfo";

    export default {
        name: "add",
        components: {ClassInfo, Major, College},
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
                } else if (value !== this.userInfo.password) {
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
                buttonLoading: false,
                item: 1,
                college: [],
                userInfo: {
                    name: '',
                    login: '',
                    password: '',
                    checkPassword: '',
                    email: '',
                    college: '',
                    majorName: '',
                    level: '',
                    className: '',
                    identity: '',
                    status: false,
                    phone: '',
                    role: '学生',
                },
                classList: [],
                major: [],
                rules: {
                    level: [
                        {required: true, message: '请输入', trigger: 'change'}
                    ],
                    className: [
                        {required: true, message: '请输入', trigger: 'change'}
                    ],
                    majorName: [
                        {required: true, message: '请输入', trigger: 'change'}
                    ],
                    college: [
                        {required: true, message: '请输入', trigger: 'change'}
                    ],
                    name: [
                        {required: true, message: '请输入', trigger: 'change'}
                    ],
                    login: [
                        {required: true, message: '请输入', trigger: 'blur'},
                        {min: 4, max: 20, message: '长度在 4 到 20 个字符', trigger: 'blur'},
                        {
                            required: true,
                            pattern: /^[0-9a-zA-Z]{4,20}$/,
                            message: '学号包含0-9，26个字母',
                            trigger: 'blur'
                        },
                        {validator: validatePass3, trigger: 'blur'}

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
                    checkPassword: [
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                }
            }
        },
        created() {
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                });
            getJson('/public/class/find/info')
                .then(response => {
                    this.classList = response.data.data;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                });
            getJson("/public/major/find")
                .then(response => {
                    this.major = response.data.data;

                }).catch(error => {
                this.$message.error("出现了一些问题" + error)
            });
        },
        methods: {
            upCollegeInfo() {

            },
            commit(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {

                        postJson('/admin/user/save', this.userInfo)
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
                                this.buttonLoading = true;
                            })
                            .catch(error => {
                                this.$message.error("出现一些错误:" + error)
                            });

                    } else {
                        this.$message.warning("请检查你填写的内容")
                    }
                });
                this.buttonLoading = true;
            }

        }
    }
</script>

<style scoped>


    .main {
        text-align: center;
        width: 50%;
        margin-left: 25%;
    }

    .top {
        margin-top: 20px;
    }


</style>
