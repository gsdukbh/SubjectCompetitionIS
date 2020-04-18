<template>
    <div>
        <div v-if="Page404===false" class="main">

            <!--固定头部-->
            <sticky :z-index="10" class-name="sub-navbar">

                <el-button :loading="loading1" style="margin-left: 10px;" type="primary"
                           @click="submitWorks('works')">
                    修改
                </el-button>

            </sticky>

            <div class="leftColumn">

                <el-card class="top left" shadow="hover">
                    <el-row>
                        <el-col :span="24">
                            <el-form ref="works" :model="works" :rules="rules">
                                <el-form-item style="margin-bottom: 40px;" prop="name">
                                    <MdInput v-model="works.name" name="name" required>
                                        标题
                                    </MdInput>
                                </el-form-item>
                            </el-form>
                        </el-col>

                    </el-row>
                    <markdown-editor ref="markdownEditor" title="请输入详细内容 " v-bind:content="works.description"
                                     height="auto"/>
                </el-card>
            </div>

            <div class="rightColumn">
                <el-card shadow="hover" class="right top">
                    <el-form ref="works" :model="works" :rules="rules" label-position="top">
                        <el-form-item label="选择提交的比赛" prop="competitionId">
                            <el-select style="width: 100%" v-model="works.competitionId" filterable placeholder="请选择">
                                <el-option
                                        v-for="item in competition"
                                        :key="item.competitionId"
                                        :label="item.name"
                                        :value="item.competitionId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>

                </el-card>

                <el-card class="right top" shadow="hover">
                    <div slot="header" class="clearfix">
                        <h3>原作品
                            <svg-icon icon-class="annex"></svg-icon>
                        </h3>
                    </div>
                    <li>

                        <el-link type="primary" @click="dl()">
                            {{works.objectName.substr(33,works.objectName.length)}}
                        </el-link>
                    </li>
                </el-card>
                <el-card class="right top" shadow="hover">

                    <div slot="header" class="clearfix">
                    <span>修改作品
                        <svg-icon icon-class="annex"></svg-icon>
                    </span>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="submitUpload">上 传
                        </el-button>
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
                    </div>
                    <el-alert
                            title="设置图片,以便更好的展示你的作品"
                            type="success"
                            effect="dark">
                    </el-alert>
                    <el-upload
                            style="float: right; padding: 3px 0"
                            class="upload-demo"
                            action="/api/i/upFile/img"
                            :on-success="onSuccess"
                            list-type="picture">
                        <el-button style="float: left; padding: 3px 0" type="text">设置图片</el-button>
                    </el-upload>
                    <div class="demo-image__preview" style="margin-top: 10px">
                        <el-image
                                style="width: 100px; height: 100px"
                                :src="works.img"
                                :preview-src-list="[works.img]">
                        </el-image>
                    </div>
                </el-card>
            </div>


            <!--返回顶部-->
            <el-tooltip placement="top" content="返回顶部">
                <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
            </el-tooltip>
        </div>
        <page404 v-if="  Page404===true"></page404>
    </div>
</template>

<script>
    import Sticky from '@/components/Sticky'
    import {mapGetters} from "vuex";
    import BackToTop from "../../../components/BackTop/index";
    import MdInput from "../../../components/MDinput/index";
    import MarkdownEditor from '@/components/MarkdownEditor'
    import {getJson, postJson} from "../../../api/api";
    import Page404 from "../../error-page/404";
    import qs from 'qs';

    export default {
        name: "edit",
        components: {Page404, MarkdownEditor, MdInput, BackToTop, Sticky},
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
                Page404: null,
                id: '',
                loading1: false,
                competition: [],
                works: {
                    name: '',
                    description: '',
                    author: '',
                    bucketName: '',
                    objectName: '',
                    img: '',
                    competitionId: '',
                    userId: ''
                },
                rules: {
                    name: [
                        {required: true, message: '请填写标题', trigger: 'blur'},
                        {validator: validateRequire}
                    ],
                    competitionId: [
                        {required: true, message: '请选择上传比赛', trigger: 'blur'}
                    ]
                }
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);
            getJson('/student/Competition/findPersonal/all/' + this.userId)
                .then(response => {
                    this.competition = response.data.content;
                })
        },

        methods: {
            submitWorks(formName) {
                this.loading1 = true;
                this.works.description = this.$refs.markdown.getMarkdown()
                this.works.author = this.name;
                this.works.userId = this.userId
                if (this.works.bucketName === '') {
                    this.$message.warning({
                        title: '警告',
                        message: '请先上传作品'
                    })
                    this.loading1 = false;
                } else if (this.works.name === '') {
                    this.loading1 = false;
                    this.$message.warning("请填写作品名称")
                } else {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            postJson('/student/works/update', this.works)
                                .then(response => {
                                    if (response.data.status === 200) {
                                        this.$notify.success({
                                            title: '成功',
                                            message: '提交成功'
                                        })

                                    } else {
                                        this.$notify.error({
                                            title: '错误',
                                            message: '发生了一些错误' + response.data.message
                                        })
                                    }
                                    this.loading1 = false;
                                }).catch(error => {
                                this.loading1 = false;
                                this.$notify.error({
                                    title: '错误',
                                    message: error
                                })
                            })
                        } else {
                            this.loading1 = false;
                            this.$message.warning('检查你的输入')
                        }
                    })
                }
            },

            onSuccess(response) {
                if (response.status === 200) {
                    this.$notify.success({
                        title: '成功',
                        message: '设置成功'
                    })
                    this.works.img = response.img;
                } else {
                    this.$message.error("上传失败");
                }
            },
            dl() {
                this.$notify.info({
                    title: '提示',
                    message: '服务器下载文件中...'
                })
                const download = {
                    bucketName: this.works.bucketName,
                    objectName: this.works.objectName
                }
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(download);
                a.download = download.objectName;
                a.target = "_blank";
                a.click();
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
            async fetchData(id) {
                await getJson('/student/works/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.works = response.data.data;
                            this.Page404 = false;
                            this.loading = false;
                            this.setTagsViewTitle();
                            this.setPageTitle();
                            this.PageLoading.close();
                        } else if (response.data.status === 404) {
                            this.loading = false;
                            this.Page404 = true;
                            this.PageLoading.close();
                            this.$notify.warning({
                                title: '警告',
                                message: '资源不存在'
                            })
                        }
                    })
                    .catch(() => {
                    });

            },
            setTagsViewTitle() {
                const title = this.works.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title}-作品修改`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.works.name;
                document.title = `${title} - 作品修改`
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
