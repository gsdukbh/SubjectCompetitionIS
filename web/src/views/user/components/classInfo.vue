<template>
    <div class="top content">
        <el-form :model="classInfo" ref="classInfo" status-icon label-position="top" :rules="rules">
            <el-form-item label="名称" prop="name">
                <el-input v-model="classInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="所属专业" prop="major">
                <el-select v-model="classInfo.major.id" filterable placeholder="请选择"
                           style="width: 100%">
                    <el-option
                            v-for="item in major"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-button style="width: 20%" type="primary" :loading="buttonLoading" @click="upClass('classInfo')">
                提交
            </el-button>

            <el-button style="width: 20%" @click="resetForm('classInfo')">
                重置
            </el-button>
        </el-form>
    </div>
</template>

<script>
    import {getJson, postFrom, postJson} from "../../../api/api";

    export default {
        name: "classInfo",
        data() {
            let validatePass = (rule, value, callback) => {
                const info = {
                    name: value
                };
                postFrom('/admin/class/repeat', info)
                    .then(response => {
                        let result = response.data.data === 'true';
                        if (result) {
                            callback(new Error('重复的班级名称'));
                        } else {
                            callback()
                        }
                    });
            };
            return {
                buttonLoading: false,
                classInfo: {
                    name: '',
                    major: {
                        id: '',
                    }
                },
                major: [],
                rules: {
                    name: [
                        {required: true, message: '请输入', trigger: 'change'},
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    id: [
                        {required: true, message: '请选择对应专业', trigger: 'change'}
                    ]
                }
            }
        },
        created() {
            getJson("/public/major/find")
                .then(response => {
                    this.major = response.data.data;

                }).catch(error => {
                this.$message.error("出现了一些问题" + error)
            });
        },
        methods: {
            upClass(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/admin/class/save', this.classInfo)
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
                            })
                    }
                });
                this.buttonLoading = false;
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
