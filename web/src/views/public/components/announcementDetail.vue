<template>
    <div class="main">

        <div class="main" v-if="Page404 ===false"
             :style="{'background-image': 'url('+announcement.img+')'}">
            <!--左边主要内容-->
            <div class="leftColumn">
                <el-card class="left top " shadow="hover">
                    <!--标题-->
                    <h1 class="title">{{announcement.title}}</h1>


                    <p class="info">
                        <span v-if="announcement.status==='0'">发布时间: {{formatTimeA(announcement.time)}} </span>
                        <span v-if="announcement.status==='1'">最后修改时间: {{formatTimeA(announcement.time)}} </span>
                        <span style="margin-left: 15px"></span>
                        发布单位: {{announcement.from}}
                        <span style="margin-left: 15px"></span>
                        作者: {{announcement.author}}
                        <span style="margin-left: 15px"></span>
                        信息类型: {{announcement.type}}
                    </p>
                    <el-divider></el-divider>
                    <!--主要内容-->

                    <markdown-viewer v-bind:content="announcement.content"></markdown-viewer>
                </el-card>

            </div>
            <!--右边信息栏-->
            <div class="rightColumn">
                <el-card class="right top" shadow="hover" v-if="announcement.bucketName!=null">

                    <div slot="header" class="clearfix">
                        <h3>相关附件
                            <svg-icon icon-class="annex"></svg-icon>
                        </h3>
                    </div>
                    <el-link type="primary" @click="dl()"><i class="el-icon-download"></i>下载：{{announcement.objectName.substr(33,
                        announcement.objectName.length)}}
                    </el-link>
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
    import BackToTop from "../../../components/BackTop/index";
    import Page404 from "../../error-page/404";
    import {getJson} from "../../../api/api";
    import {parseTime} from '../../../utils/index'
    import MarkdownViewer from "../../../components/MarkdownViewer/index";
    import qs from 'qs';

    export default {
        name: "announcementDetail",
        components: {Page404, BackToTop, MarkdownViewer},
        data() {
            return {
                Page404: null,
                announcement: null,
                id: '',
                tempRoute: null,
                download: {
                    bucketName: '',
                    objectName: ''
                }
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);
        },
        methods: {
            dl() {
                this.$notify.info({
                    title: '提示',
                    message: '服务器下载文件中...'
                })
                this.download.bucketName = this.announcement.bucketName;
                this.download.objectName = this.announcement.objectName;
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(this.download);
                a.target = "_blank";
                a.click();
            },
            async fetchData(id) {
                getJson('/public/announcement/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.announcement = response.data.data;
                            this.Page404 = false;
                            this.loading = false;
                            this.setTagsViewTitle();
                            this.setPageTitle();
                        } else if (response.data.status === 404) {
                            this.loading = false;
                            this.Page404 = true;
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
                const title = this.announcement.title;
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 详情`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.announcement.title;
                document.title = `${title} - 详情`
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },

        }
    }
</script>

<style scoped>
    .main {
        margin-left: 10%;
        margin-right: 10%;
        background-color: rgba(255, 255, 255, 0.1);
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

    .title {
        text-align: center;
    }

    .info {
        margin-top: 10px;
        align-content: center;
        text-align: center;
        font-size: 13px
    }

</style>
