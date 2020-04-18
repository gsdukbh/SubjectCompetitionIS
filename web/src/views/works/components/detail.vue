<template>
    <div class="content">
        <div class="main" v-if="Page404 ===false"
        >
            <!--顶部-->
            <sticky :z-index="10" class-name="sub-navbar">

                <router-link :to="'/works/edit/'+this.id">

                    <el-button style="margin-left: 10px;" type="primary">
                        更改
                    </el-button>

                </router-link>

            </sticky>

            <!--内容-->

            <!--左边主要内容-->
            <div class="leftColumn">
                <el-card class="left top " shadow="hover">
                    <!--标题-->
                    <h1 class="title">{{works.name}}</h1>


                    <p class="info">
                        <span>作者：{{works.author}}</span>
                        <span style="margin-left: 10px">上传时间：{{formatTimeA(works.upTime)}} </span>
                        <span style="margin-left: 10px">
                          <el-tooltip
                                  class="item"
                                  effect="dark"
                                  content="查看比赛信息"
                                  placement="right">
                                 <router-link :to="'/competition/detailStu/'+works.competition.id">
                                     <el-link type="primary"> 竞赛 ：{{works.competition.name}}</el-link>
                                           </router-link>
                          </el-tooltip>

                        </span>
                    </p>
                    <el-divider></el-divider>
                    <!--主要内容-->

                    <markdown-viewer v-bind:content="works.description"></markdown-viewer>
                </el-card>

            </div>
            <!--右边信息栏-->
            <div class="rightColumn">

                <el-card class="right top" shadow="hover">
                    <div slot="header" class="clearfix">
                        <h3>作品
                            <svg-icon icon-class="annex"></svg-icon>
                        </h3>
                    </div>
                    <span v-if="works.score!=null">评分：{{works.score}}</span>
                    <span v-if="works.score==null">评分：暂无</span>
                    <br>
                    <li>
                        <el-link type="primary" @click="dl()">
                            {{works.objectName.substr(33,works.objectName.length)}}
                        </el-link>
                    </li>
                </el-card>


                <el-card class="right top" shadow="hover" v-if="works.img!=null">
                    <div slot="header" class="clearfix">
                        <h3>作品图片
                            <i class="el-icon-picture-outline"></i>
                        </h3>
                    </div>
                    <div class="demo-image__preview">
                        <el-image
                                style="width: 100px; height: 100px"
                                :src="works.img"
                                :preview-src-list="[works.img]">
                        </el-image>
                    </div>
                </el-card>
            </div>

            <!--底部-->

            <el-tooltip placement="top" content="返回顶部">
                <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
            </el-tooltip>
        </div>
        <Page404 v-if="Page404 === true"></Page404>
    </div>

</template>

<script>
    import Sticky from "../../../components/Sticky/index";
    import BackToTop from "../../../components/BackTop/index";
    import Page404 from "../../error-page/404";
    import {getJson} from "../../../api/api";
    import {parseTime} from '../../../utils/index'
    import MarkdownViewer from "../../../components/MarkdownViewer/index";
    import qs from 'qs';
    import {mapGetters} from "vuex";


    export default {
        name: "detail",
        components: {MarkdownViewer, Page404, BackToTop, Sticky},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                Page404: null,
                works: null,
                id: '',
                download: {
                    bucketName: '',
                    objectName: ''
                },
                PageLoading: null
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);
            this.PageLoading = this.$loading({
                lock: true,
                text: '拼命加载中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });

        },
        methods: {
            dl() {
                this.$notify.info({
                    title: '提示',
                    message: '服务器下载文件中...'
                })
                this.download.bucketName = this.works.bucketName;
                this.download.objectName = this.works.objectName;
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(this.download);
                a.download = this.download.objectName.substr(33, this.download.objectName.length);
                a.target = "_blank";
                a.click();
            },
            async fetchData(id) {
                getJson('/student/works/findById/' + id)
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
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 作品详情`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.works.name;
                document.title = `${title} - 作品详情`
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
        }
    }
</script>

<style lang="scss" scoped>

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

    .title {
        text-align: center;
    }

    .info {
        margin-top: 10px;
        align-content: center;
        text-align: center;
        font-size: 13px
    }

    .main {
        background-color: rgba(255, 255, 255, 0.1);
    }
</style>
