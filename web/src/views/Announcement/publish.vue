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

            <el-card class="top left" shadow="hover">
                <el-row>
                    <el-col :span="24">
                        <el-form ref="announcement" :model="announcement" :rules="rules">
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

            <el-card class="right top" shadow="hover">
                <el-row>
                    <el-col :span="24">
                        <el-form ref="announcement" status-icon :model="announcement" :rules="rules">
                            <el-form-item prop="author" label="作者：">
                                {{name}}
                            </el-form-item>
                        </el-form>
                        <el-form label-position="top" ref="announcement" status-icon label-width="80px" :rules="rules"
                                 :model="announcement">
                            <el-form-item label="类型" prop="type">
                                <!--                                <el-select v-model="announcement.type" placeholder="请选择" style="width: 100%">-->
                                <!--                                    <el-option label="考试信息" value="考试信息"></el-option>-->
                                <!--                                    <el-option label="竞赛新闻" value="竞赛新闻"></el-option>-->
                                <!--                                </el-select>-->
                                <el-autocomplete
                                        style="width: 100%"
                                        v-model="announcement.type"
                                        :fetch-suggestions="querySearchAsync"
                                        placeholder="请输入内容"
                                ></el-autocomplete>

                            </el-form-item>
                            <el-form-item label="来源" prop="from">
                                <el-select v-model="announcement.from" placeholder="请选择" style="width: 100%">
                                    <div v-for="item in college" v-bind:key="item.id">
                                        <el-option :label=item.name :value=item.name></el-option>
                                    </div>
                                </el-select>
                            </el-form-item>

                        </el-form>

                    </el-col>
                </el-row>

            </el-card>


            <el-card class="right top" shadow="hover">

                <div slot="header" class="clearfix">
                    <h3>上传附件
                        <svg-icon icon-class="annex"></svg-icon>
                    </h3>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="submitUpload">上 传</el-button>
                </div>

                <el-upload

                        drag
                        class="upload-demo"
                        ref="upload"
                        action="/api/i/upFile/annex"
                        :on-success="upSuccess"
                        :auto-upload="false">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>

                </el-upload>
            </el-card>
            <el-card class="right top" shadow="hover">
                <div slot="header" class="clearfix">
                    <h3>首页图片
                        <i class="el-icon-picture-outline"></i>
                    </h3>
                    <el-upload
                            style="float: right; padding: 3px 0"
                            class="upload-demo"
                            action="/api/i/upFile/img"
                            :on-success="onSuccess"
                            list-type="picture">
                        <el-button style="float: right; padding: 3px 0" type="text">设置首页图片</el-button>
                    </el-upload>
                </div>
                <div class="demo-image__preview">
                    <el-image
                            style="width: 100px; height: 100px"
                            :src="announcement.img"
                            :preview-src-list="[announcement.img]">
                    </el-image>
                </div>
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
            /*https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Operators/Spread_syntax*/
            /*https://vuex.vuejs.org/zh/guide/getters.html*/
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
                temType: null,
                announcement: {
                    id: null,
                    title: '',
                    content: '',
                    author: this.name,
                    from: '',
                    status: '',
                    type: '',
                    objectName: '',
                    bucketName: '',
                    img: '',
                    scisUser: {
                        id: null,
                    }
                },
                college: {
                    id: '',
                    collegeName: '',
                },
                rules: {
                    title: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {validator: validateRequire}
                    ],
                    from: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {required: true, message: '请输入', trigger: 'blur'}
                    ],
                    type: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {required: true, message: '请输入', trigger: 'blur'}
                    ]
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
        mounted() {
            // getJson('/public/announcement/findType')
            //     .then(response => {
            //         this.temType = response.data.type;
            //     });
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
            submitUpload() {
                this.$refs.upload.submit();
            },
            upSuccess(response) {
                if (response.status === 200) {
                    this.$notify.success({
                        title: '成功',
                        message: '上传成功'
                    })
                    this.announcement.objectName = response.objectName;
                    this.announcement.bucketName = response.bucketName;
                } else {
                    this.$message.error("上传失败");
                }
            },
            /*https://element.eleme.cn/#/zh-CN/component/input*/
            querySearchAsync(queryString, cb) {
                getJson('/public/announcement/findType')
                    .then(response => {
                        this.temType = response.data.data;
                    });
                const temType = this.temType;
                const res = queryString ? temType.filter(this.createTypeFilter(queryString)) : temType;

                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(res);
                }, 3000 * Math.random());
            },
            createTypeFilter(queryString) {

                return (temType) => {

                    return (temType.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
                };
            },

            /*直接发布*/
            submitPublish(formName) {
                this.announcement.content = this.$refs.markdownEditor.getMarkdown();
                this.announcement.author = this.name;
                this.announcement.status = 0;
                this.announcement.scisUser.id = this.userId;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        postJson('/tea/announcement/save', this.announcement)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.announcement = response.data.result;
                                    console.log(this.announcement)
                                    this.$notify.success({
                                        title: '成功',
                                        message: '发布成功'
                                    })
                                } else {
                                    this.$notify.error({
                                        title: '错误',
                                        message: '发生了一些错误' + response.data.message
                                    })
                                }
                            })
                            .catch(error => {
                                this.$notify.error({
                                    title: '错误',
                                    message: error
                                })
                            })
                    }
                });

            },
            /*草稿保存  status 1 为草稿 */
            draftForm(formName) {
                this.announcement.content = this.$refs.markdownEditor.getMarkdown();
                this.announcement.author = this.name;
                this.announcement.status = 1;
                this.announcement.scisUser.id = this.userId;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        postJson('/tea/announcement/save', this.announcement)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.announcement = response.data.result;
                                    this.$notify.success({
                                        title: '成功',
                                        message: '发布成功'
                                    })
                                } else {
                                    this.$notify.error({
                                        title: '错误',
                                        message: '发生了一些错误'
                                    })
                                }
                            })
                            .catch(error => {
                                this.$notify.error({
                                    title: '错误',
                                    message: error
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
