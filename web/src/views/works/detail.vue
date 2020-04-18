<template>
    <div class="bg">
        <div class="main" v-if="Page404 ===false">
            <sticky :z-index="10" class-name="sub-navbar">
                <router-link :to="'/works/edit/'+this.worksId">

                    <el-button style="margin-left: 10px;" type="primary">
                        更改
                    </el-button>

                </router-link>
            </sticky>

            <div class="leftColumn">
                <el-card class="left top" shadow="hover">


                    <h1 class="title">{{works.name}}</h1>
                    <p class="info">
                        <span>作者：{{works.author}}</span>
                        <span style="margin-left: 10px">上传时间：{{formatTimeA(works.upTime)}} </span>
                    </p>

                    <el-divider></el-divider>

                    <markdown-viewer v-bind:content="works.description"></markdown-viewer>

                </el-card>


            </div>

            <div class="rightColumn">

                <el-card class="right top" shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>作品
                            <svg-icon icon-class="annex"></svg-icon>
                        </span>
                    </div>
                    <li>

                        <el-link type="primary" @click="dl()">
                            {{works.objectName.substr(33,works.objectName.length)}}
                        </el-link>
                    </li>
                </el-card>


                <el-card class="right top"
                         v-if="works.img!==null"
                         shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>作品图片
                            <i class="el-icon-picture-outline"></i>
                        </span>
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


            <el-tooltip placement="top" content="返回顶部">
                <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
            </el-tooltip>

        </div>

        <Page404 v-if="Page404 ===true" class="top"></Page404>
    </div>
</template>

<script>
    import {parseTime} from '../../utils'
    import {mapGetters} from "vuex";
    import Sticky from "../../components/Sticky/index";
    import Page404 from "../error-page/404";
    import {getJson} from "../../api/api";
    import BackToTop from "../../components/BackTop/index";
    import MarkdownViewer from "../../components/MarkdownViewer/index";
    import qs from 'qs';

    export default {
        name: "detail works",
        data() {
            return {
                worksId: '',
                Page404: null,
                works: {},
                // works:{
                //     img:'',
                //     competitionId:'',
                //     userId:''
                // },
                PageLoading: null,
            }
        },
        components: {MarkdownViewer, BackToTop, Page404, Sticky},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        created() {
            this.worksId = this.$route.params.id;
            this.tempRoute = Object.assign({}, this.$route);
            this.getWorks(this.worksId);
            this.PageLoading = this.$loading({
                lock: true,
                text: '拼命加载中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        },
        methods: {
            dl() {
                const download = {
                    bucketName: this.works.bucketName,
                    objectName: this.works.objectName,
                }
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(download);
                a.download = download.objectName.substr(33, download.objectName.length);
                a.target = "_blank";
                a.click();
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
            async getWorks(id) {
                getJson('/student/works/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.Page404 = false;
                            this.works = response.data.data;
                            this.works.competitionId = response.data.competition.id;
                            this.setTagsViewTitle();
                            this.setPageTitle();
                            this.PageLoading.close();
                            console.log(this.works, this.works.d)
                        } else {
                            this.Page404 = true;
                            this.PageLoading.close();
                            this.$notify.error({
                                title: '错误',
                                message: response.data.message
                            })
                        }
                    }).catch(() => {
                    this.PageLoading.close();
                })
            },
            setTagsViewTitle() {
                const title = this.works.name;
                console.log(title)
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 详情`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.works.name;
                document.title = `${title} - 详情`
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

    .title {
        text-align: center;
    }

    .info {
        margin-top: 10px;
        align-content: center;
        text-align: center;
        font-size: 13px
    }

    .bg {
        background-color: rgba(255, 255, 255, 0.1)
    }
</style>
