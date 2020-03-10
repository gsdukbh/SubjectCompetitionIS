<template>
    <div class="center">
        <el-card shadow="hover" class="card">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="20%" class="demo-ruleForm">

                <el-form-item label="竞赛名称" prop="name">

                    <el-tooltip class="item" effect="dark" content="为了避免重复，请使用第**届，作为前缀命名" placement="top-start">
                        <el-input v-model="ruleForm.name" style="width: 30%"></el-input>
                    </el-tooltip>
                </el-form-item>

                <el-form-item label="竞赛级别" prop="region">
                    <el-select v-model="ruleForm.region" placeholder="请选择竞赛级别">
                        <el-option label="校级" value="校级"></el-option>
                        <el-option label="院级" value="院级"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="承办单位" prop="organizer">

                    <el-select v-model="ruleForm.organizer" placeholder="请选择竞赛级别">
                        <div v-for="item in college" v-bind:key="item.id">
                            <el-option :label=item.collegeName :value=item.collegeName ></el-option>
                        </div>
                    </el-select>

                </el-form-item>

                <el-form-item label="竞赛时间" required>

                    <el-col :span="3">
                        <el-form-item prop="startTime">
                            <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.startTime"
                                            value-format="yyyy-MM-dd"
                                            style="width: 100%;"></el-date-picker>
                        </el-form-item>
                    </el-col>

                    <el-col :span="2">
                        <p class="p">结束时间： </p>
                    </el-col>

                    <el-col :span="5">
                        <el-form-item prop="endTime">
                            <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.endTime"
                                            value-format="yyyy-MM-dd"
                                            style="width: 80%;"></el-date-picker>
                        </el-form-item>

                    </el-col>

                </el-form-item>

                <el-form-item label="立即发布" prop="status">
                    <el-switch v-model="ruleForm.status"></el-switch>
                    <span>立即发布，竞赛则会公开，任何人可以看到</span>
                </el-form-item>

                <el-form-item label="人数限制" prop="type">
                        <el-input-number v-model="ruleForm.type" :min="1" :max="10" label="描述文字"></el-input-number>
                </el-form-item>

                <el-form-item label="负责人" prop="author">
                    <el-input
                            placeholder="请输入内容"
                            v-model="ruleForm.author"
                             style="width: 20%">
                    </el-input>


                </el-form-item>
                <el-tag style="font-size: medium">详细内容</el-tag>
                <span style="font-size: 14px"> 使用markdown编辑  <el-link target="_blank" href="https://github.com/nhn/tui.editor" type="info">详情</el-link> </span>
                <markdown-editor ref="markdownEditor"  title="请输入详细内容 "  placeholder="wd"/>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>

            </el-form>
        </el-card>

    </div>
</template>

<script>
    import MarkdownEditor from '@/components/MarkdownEditor'
    import {getJson, postJson} from "../../api/api";

    export default {
        name: "editCompetition",
        components: { MarkdownEditor },
        data() {
            return {
                ruleForm: {
                    name: '',
                    region: '',
                    startTime: '',
                    endTime: '',
                    organizer: '',
                    status: false,
                    type: '',
                    author: '',
                    content: '',

                },
                college: {
                    id: '',
                    collegeName: '',
                },
                rules: {
                    name: [
                        {required: true, message: '请输入活动名称', trigger: 'blur'}
                    ],
                    region: [
                        {required: true, message: '请选择竞赛级别', trigger: 'change'}
                    ],
                    startTime: [
                        {required: true, message: '请选择日期', trigger: 'change'}
                    ],
                    endTime: [
                        { required: true, message: '请选择时间', trigger: 'change'}
                    ],
                    organizer: [
                        {required: true, message: '请选择举办单位', trigger: 'change'}
                    ],
                    author: [
                        {required: true, message: '请输入负责人', trigger: 'blur'},
                    ],
                }
            };
        },
        created() {//初始化后
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
            .catch(error =>{
                this.$message.error("出现了一些问题" + error)
            })
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.content = this.$refs.markdownEditor.getMarkdown();
                        postJson('/tea/competition/save',this.ruleForm)
                        .then(response=>{
                            if (response.data.code === 200){
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                            }
                        }).catch(error=>{
                            this.$message.error("出现了一些问题" + error)
                        })

                            console.log(this.ruleForm)
                    }
                     else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

        }
    }
</script>

<style scoped>
    .card {
        align-items: center;
        width: 90%;
        margin: 0 auto;
        padding-top: 10px;
    }

    .p {
        text-align: center;
    }
</style>
