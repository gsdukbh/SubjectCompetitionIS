<template>
    <div>
        <!--头部-->
        <sticky :z-index="10" class-name="sub-navbar">


            <el-button :loading="loading1" style="margin-left: 10px;" type="primary"
                       @click="submitWorks('works')">
                提交
            </el-button>

        </sticky>

        <div class="leftColumn">
            <el-card shadow="hover" class="left top">
                <el-form ref="works" :model="works" :rules="rules">
                    <el-form-item prop="name">
                        <MdInput v-model="works.name" required>作品名字</MdInput>
                    </el-form-item>
                </el-form>
                <el-tag>输入作品描述</el-tag>

                <markdown-editor style="margin-top: 10px" ref="markdown" title="请输入作品描述" height="auto">
                </markdown-editor>
            </el-card>
        </div>


        <div class="rightColumn">
            <el-card shadow="hover" class="right top">
                <el-form ref="works" :model="works" :rules="rules" label-position="top">
                    <el-form-item label="选择提交的比赛" prop="id">
                        <el-select style="width: 100%" v-model="works.competition.id" filterable placeholder="请选择">
                            <el-option
                                    v-for="item in competition"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>

            </el-card>


            <el-card class="right top" shadow="hover">

                <div slot="header" class="clearfix">
                    <span>上传作品
                        <svg-icon icon-class="annex"></svg-icon>
                    </span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="submitUpload">上 传</el-button>
                </div>
                <!-- drag-->
                <el-upload

                        drag
                        class="upload-demo"
                        ref="upload"
                        action="/api/i/upFile/works"
                        :on-success="upSuccess"
                        :auto-upload="false">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-card>
            <el-card class="right top" shadow="hover">
                <div slot="header" class="clearfix">
                    <span>设置展示图片
                        <i class="el-icon-picture-outline"></i>
                    </span>
                    <el-upload
                            style="float: right; padding: 3px 0"
                            class="upload-demo"
                            action="/api/i/upFile/img"
                            :on-success="onSuccess"
                            list-type="picture">
                        <el-button style="float: right; padding: 3px 0" type="text">设置图片</el-button>
                    </el-upload>
                </div>
                <el-alert
                        title="设置图片,以便更好的展示你的作品"
                        type="success"
                        effect="dark">
                </el-alert>
                <div class="demo-image__preview" style="margin-top: 10px">
                    <el-image
                            style="width: 100px; height: 100px"
                            :src="works.img"
                            :preview-src-list="[works.img]">
                    </el-image>
                </div>
            </el-card>
        </div>


    </div>
</template>

<script>
    import Sticky from "../../components/Sticky/index";
    import {mapGetters} from "vuex";
    import MdInput from "../../components/MDinput/index";
    import MarkdownEditor from "../../components/MarkdownEditor/index";
    import {getJson, postJson} from "../../api/api";

    export default {
        name: "up",
        components: {MarkdownEditor, MdInput, Sticky},
        computed: {
            ...mapGetters([
                'name',
                'userId'
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
                competition: [],
                loading1: false,
                works: {
                    name: '',
                    description: '',
                    author: '',
                    bucketName: '',
                    objectName: '',
                    img: '',
                    competition: {
                        id: ''
                    },

                },
                rules: {
                    name: [
                        {validator: validateRequire}
                    ],
                    id: [
                        {required: true, message: '请选择上传比赛', trigger: 'blur'}
                    ]

                }
            }
        },
        created() {
            getJson('/student/Competition/findPersonal/all/' + this.userId)
                .then(response => {
                    this.competition = response.data.content;
                })
        },
        methods: {
            onSuccess(response) {
                if (response.status === 200) {
                    this.$notify.success({
                        title: '成功',
                        message: '设置成功'
                    })
                    this.announcement.img = response.img;
                } else {
                    this.$message.error("上传失败");
                }
            },
            submitWorks(formName) {
                this.loading1 = true;
                this.works.description = this.$refs.markdown.getMarkdown()
                this.works.author = this.name;
                if (this.works.bucketName === '') {
                    this.$notify.warning({
                        title: '警告',
                        message: '请先上传作品'
                    })
                } else {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
postJson()
                        }
                    })
                }
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
            upSuccess(response) {
                if (response.status === 200) {
                    this.$notify.success({
                        title: '成功',
                        message: '上传成功'
                    })
                    this.works.objectName = response.objectName;
                    this.works.bucketName = response.bucketName;
                } else {
                    this.$message.error("上传失败");
                }
            },
        }
    }
</script>

<style scoped>
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
