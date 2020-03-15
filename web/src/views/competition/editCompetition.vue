<template>
    <div class="center">
        <el-card shadow="hover" class="card">
            <!--card 头-->
            <div slot="header" class="clearfix">
                <span>填写信息</span>

            </div>

            <el-steps :active="item" align-center>
                <el-step title="基本信息" icon="el-icon-edit"></el-step>
                <el-step title="详情描述" icon="el-icon-edit-outline"></el-step>
                <el-step title="公告信息" icon="el-icon-finished"></el-step>
            </el-steps>
            <el-divider></el-divider>

            <div v-if="item ===1">
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="20%" class="demo-ruleForm">

                    <el-form-item label="竞赛名称" prop="name">

                        <el-tooltip class="item" effect="dark" content="为了避免重复，请使用第**届，作为前缀命名" placement="top-start">
                            <el-input v-model="ruleForm.name" style="width: 30%"></el-input>
                        </el-tooltip>
                    </el-form-item>

                    <el-form-item label="竞赛级别" prop="level">
                        <el-select v-model="ruleForm.level" placeholder="请选择竞赛级别">
                            <el-option label="校级" value="校级"></el-option>
                            <el-option label="院级" value="院级"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="承办单位" prop="organizer">

                        <el-select v-model="ruleForm.organizer" placeholder="请选择举办单位">
                            <div v-for="item in college" v-bind:key="item.id">
                                <el-option :label=item.collegeName :value=item.collegeName></el-option>
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

                    <el-form-item label="状态" prop="status">
                        <el-select v-model="ruleForm.status" placeholder="请选择">
                            <el-option label="草稿" value="草稿"></el-option>
                            <el-option label="公开" value="公开"></el-option>
                            <el-option label="进行中" disabled value="进行中"></el-option>
                            <el-option label="已结束" disabled value="已结束"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="举办形式" prop="type">
                        <el-radio-group v-model="ruleForm.type">
                            <el-radio label="offline">线下比赛</el-radio>
                            <el-radio label="online">线上比赛（提交作品）</el-radio>
                        </el-radio-group>
                    </el-form-item>

                    <el-form-item label="举办地点" prop="place">
                        <el-input
                                placeholder="请输入内容"
                                v-model="ruleForm.place"
                                style="width: 30%">
                        </el-input>
                    </el-form-item>

                    <el-form-item label="团队比赛">
                        <el-switch
                                v-model="teamValue"
                                active-text="团队比赛"
                                inactive-text="个人比赛">
                        </el-switch>
                    </el-form-item>

                    <el-form-item label="人数限制" prop="type" v-if="teamValue">
                        <el-input-number v-model="ruleForm.numLimit" :min="1" :max="10" label="描述文字"></el-input-number>
                    </el-form-item>

                    <el-form-item label="负责人" prop="author">
                        <el-input
                                placeholder="请输入内容"
                                v-model="ruleForm.author"
                                style="width: 30%">
                        </el-input>
                    </el-form-item>

                    <el-form-item>

                        <el-button type="primary" @click="next('ruleForm')">下一步</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div v-if="item === 2">
                <el-button style="float: right; padding: 3px " round  icon="el-icon-check" type="primary" @click="submit()">提交</el-button>

                <el-tag style="font-size: medium">详细内容</el-tag>
                <span style="font-size: 14px"> 使用markdown编辑  <el-link target="_blank"
                                                                      href="https://github.com/nhn/tui.editor"
                                                                      type="info">详情</el-link> </span>
                <markdown-editor ref="markdownEditor" title="请输入详细内容 " placeholder="wd" height="600px"/>

            </div>
            <!--公告-->
            <div v-if="item === 3">


            </div>

        </el-card>

    </div>
</template>

<script>
    import MarkdownEditor from '@/components/MarkdownEditor'
    import {getJson, postFrom} from "../../api/api";

    export default {
        name: "editCompetition",
        components: {MarkdownEditor},
        data() {
            return {
                ruleForm: {
                    name: '',
                    level: '',
                    startTime: '',
                    endTime: '',
                    organizer: '',
                    status: '',
                    type: '',
                    author: '',
                    content: '',
                    numLimit: '',
                    place: '',
                },

                item: 2,
                teamValue: false,
                college: {
                    id: '',
                    collegeName: '',
                },
                rules: {
                    name: [
                        {required: true, message: '请输入活动名称', trigger: 'blur'}
                    ],
                    level: [
                        {required: true, message: '请选择竞赛级别', trigger: 'change'}
                    ],
                    startTime: [
                        {required: true, message: '请选择日期', trigger: 'change'}
                    ],
                    endTime: [
                        {required: true, message: '请选择结束日期', trigger: 'change'}
                    ],
                    organizer: [
                        {required: true, message: '请选择举办单位', trigger: 'change'}
                    ],
                    status: [{required: true, message: '请选择', trigger: 'change'}],
                    author: [
                        {required: true, message: '请输入负责人', trigger: 'blur'},
                    ],
                    type:[{
                        required: true, message: '请选择', trigger: 'change'
                    }],
                    place:[{
                        required: true, message: '请输入', trigger: 'blur'
                    }]
                }
            };
        },
        created() {//初始化后
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })
        },
        methods: {
            submit(){
                this.ruleForm.content = this.$refs.markdownEditor.getMarkdown();
                postFrom('/tea/competition/save', this.ruleForm)
                .then(response=>{
                    if (response.data.code === 200) {
                        this.$message({
                            message: '提交成功',
                            type: 'success'
                        });
                        this.item=3;
                    }
                })
                .catch( error=>{
                    this.$message.error("出现了一些问题" + error)
                })
            },
            next(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.item = 1;
                        console.log(this.ruleForm.type)
                    } else {
                        this.$notify.error({
                            message: '请检查你的输入',
                            title: '错误',
                        });
                        console.log(this.ruleForm.type)
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
