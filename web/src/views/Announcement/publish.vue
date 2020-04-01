<template>
    <div class="main">

        <!--固定头部-->
        <sticky :z-index="10" class-name="sub-navbar">


            <el-button style="margin-left: 10px;" type="primary" @click="submitPublish('announcement')">
                发布
            </el-button>
            <el-button style="margin-left: 10px;" type="warning" @click="draftForm('announcement')">
                草稿
            </el-button>
        </sticky>

        <div class="leftColumn">

            <el-card class="top left">
                <el-row>
                    <el-col :span="24">
                        <el-form ref="announcement"   :model="announcement" :rules="rules">
                            <el-form-item style="margin-bottom: 40px;" prop="title">
                                <MdInput v-model="announcement.title" name="name" required>
                                    标题
                                </MdInput>
                            </el-form-item>
                        </el-form>
                    </el-col>

                </el-row>
                <markdown-editor ref="markdownEditor" title="请输入详细内容 " height="auto"/>
            </el-card>
        </div>

        <div class="rightColumn">

            <el-card class="right top">
                <el-row>
                    <el-col :span="24">
                        <el-form ref="announcement" status-icon :model="announcement" :rules="rules">
                            <el-form-item  prop="author" label="作者：">
                              {{name}}
                            </el-form-item>
                        </el-form>
                        <el-form label-position="top" ref="announcement" status-icon label-width="80px" :rules="rules" :model="announcement">
                            <el-form-item label="类型" prop="type">

                                <el-select v-model="announcement.type" placeholder="请选择" style="width: 100%">
                                    <el-option label="考试信息" value="考试信息"></el-option>
                                    <el-option label="竞赛新闻" value="竞赛新闻"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="来源" prop="from">
                                <el-select v-model="announcement.from" placeholder="请选择" style="width: 100%">
                                    <div v-for="item in college" v-bind:key="item.id" >
                                        <el-option :label=item.collegeName :value=item.collegeName></el-option>
                                    </div>
                                </el-select>
                            </el-form-item>

                        </el-form>

                    </el-col>

                </el-row>

            </el-card>

        </div>


        <!--返回顶部-->
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>
    </div>
</template>

<script>
    import Sticky from '@/components/Sticky'
    import {mapGetters} from "vuex";
    import BackToTop from "../../components/BackTop/index";
    import MdInput from "../../components/MDinput/index";
    import MarkdownEditor from '@/components/MarkdownEditor'
    import {getJson, postJson} from "../../api/api";

    export default {
        name: "publish",
        components: {MarkdownEditor, MdInput, BackToTop, Sticky},
        computed: {
            ...mapGetters([
                'name'
            ])
        },
        data() {
            const validateRequire = (rule, value, callback) => {
                if (value === '') {
                callback(new Error('标题' + '为必传项'))
                } else {
                    callback()
                }
            };
            return {
                announcement: {
                    id: null ,
                    title: '',
                    content: '',
                    author: this.name,
                    from: '',
                    status: '',
                    type: '',
                },
                college: {
                    id: '',
                    collegeName: '',
                },
                rules: {
                    title: [{validator: validateRequire}],
                    from:[{validator: validateRequire}],
                    type:[{validator: validateRequire}]
                }
            }
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
            /*直接发布*/
            submitPublish(formName) {
                this.announcement.content=this.$refs.markdownEditor.getMarkdown();
                this.announcement.author=this.name;
                this.announcement.status=0;
                this.$refs[formName].validate((valid) => {
                    if (valid){
                        postJson('/tea/announcement/save',this.announcement)
                        .then(response=>{
                            if (response.data.status===200){
                                this.announcement=response.data.result;
                                console.log(this.announcement)
                                this.$notify.success({
                                    title:'成功',
                                    message:'发布成功'
                                })
                            }else {
                                this.$notify.error({
                                    title:'错误',
                                    message:'发生了一些错误'
                                })
                            }
                        })
                        .catch(error=>{
                            this.$notify.error({
                                title:'错误',
                                message:error
                            })
                        })
                    }
                });

            },
            /*草稿保存*/
            draftForm(formName) {
                this.announcement.content=this.$refs.markdownEditor.getMarkdown();
                this.announcement.author=this.name;
                this.announcement.status=1;
                this.$refs[formName].validate((valid) => {
                    if (valid){
                        postJson('/tea/announcement/save',this.announcement)
                            .then(response=>{
                                if (response.data.status===200){
                                    this.announcement=response.data.result;
                                    this.$notify.success({
                                        title:'成功',
                                        message:'发布成功'
                                    })
                                }else {
                                    this.$notify.error({
                                        title:'错误',
                                        message:'发生了一些错误'
                                    })
                                }
                            })
                            .catch(error=>{
                                this.$notify.error({
                                    title:'错误',
                                    message:error
                                })
                            })
                    }
                });
            },


        }
    }
</script>

<style scoped>
    .main {
        height: auto;
        background-color: #ffffff; /* 浏览器不支持时显示 */
        background-image: linear-gradient(#ffffff, #b6fffb);
    }

    .rightColumn {
        float: left;
        width: 25%;
        padding-left: 20px;
    }

    .leftColumn {
        float: left;
        width: 75%;
    }

    .top {
        margin-top: 20px;
    }

    .left {
        margin-left: 20px;
    }

    .right {
        margin-right: 10px;
    }

</style>
