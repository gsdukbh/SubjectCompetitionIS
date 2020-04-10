<template>
    <div class="top content">
        <el-form :model="major" :rules="rules" label-position="top" status-icon ref="major">
            <el-form-item label="专业名称" prop="name">
                <el-input v-model="major.name" style="width: 100%"></el-input>
            </el-form-item>
            <el-form-item label="培养层次" prop="level">
                <el-select v-model="major.level" placeholder="请选择" style="width: 100%">
                    <el-option label="本科" value="本科"></el-option>
                    <el-option label="专科" value="专科"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="所属学院" prop="college.id">
                <el-select v-model="major.college.id" filterable placeholder="请选择" style="width: 100%"
                >
                    <el-option
                            v-for="item in college"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-button style="width: 30%" type="primary" :loading="buttonLoading" @click="upMajor('major')">
                提交
            </el-button>

            <el-button style="width: 30%" @click="resetForm('major')">
                重置
            </el-button>
        </el-form>
    </div>
</template>

<script>
    import {getJson, postFrom, postJson} from "../../../api/api";

    export default {
        name: "major",
        data() {
            let validatePass = (rule, value, callback) => {
                const info = {
                    name: value
                };
                postFrom('/admin/college/repeat', info)
                    .then(response => {
                        let result = response.data.data === 'true';
                        if (result) {
                            callback(new Error('已经存在该信息'));
                        } else {
                            callback()
                        }
                    });
            };
            return {
                buttonLoading: false,
                major: {
                    name: '',
                    level: '',
                    college: {
                        id: ''
                    }
                },
                rules: {
                    name: [
                        {required: true, message: '请输入', trigger: 'change'},
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    level: [
                        {required: true, message: '请选择', trigger: 'change'}
                    ],
                    id: [
                        {required: true, message: '请选择', trigger: 'change'}
                    ]
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
        },
        methods: {
            upMajor(form) {
                this.buttonLoading = true;

                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/admin/major/save', this.major)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '提交成功'
                                    })
                                } else if (response.data.status === 0) {
                                    this.$notify.warning({
                                        title: '警告',
                                        message: response.data.message,
                                    })
                                }
                                this.buttonLoading = false;
                            }).catch(error => {
                            this.$message.error("出现一些错误:" + error);
                            this.buttonLoading = false;
                        });
                    }
                });

            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>

    .content {
        align-content: center;
        text-align: center;
        width: 30%;
        margin-left: 35%;
        margin-right: 35%;
    }
</style>
