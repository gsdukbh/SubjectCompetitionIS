<template>
    <div class="top main">
        <el-form label-position="top" :model="college" :rules="rulesA" status-icon ref="college">
            <el-form-item label="学院名称 " prop="name">
                <el-input v-model="college.name"></el-input>
            </el-form-item>

            <el-button style="width: 20%" type="primary" :loading="buttonLoading" @click="upCollegeInfo('college')">
                提交
            </el-button>

            <el-button style="width: 20%" @click="resetForm('college')">
                重置
            </el-button>

        </el-form>
    </div>
</template>

<script>
    import {postFrom, postJson} from "../../../api/api";

    export default {
        name: "college",
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
                college: {
                    name: ''
                },
                rulesA: {
                    name: [

                        {validator: validatePass, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            upCollegeInfo(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/admin/college/save', this.college)
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
                            })
                            .catch(error => {
                                this.$message.error("出现一些错误:" + error)
                            });
                        this.buttonLoading = false;
                    }
                })
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>

</style>
