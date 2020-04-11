<template>
    <div>
        <div v-if="Page404 ===false" class="main">

            <sticky :z-index="10" class-name="sub-navbar">

                <router-link :to="'/apply/apply/'+showData.id">
                    <el-button style="margin-left: 10px;" type="success">
                        立刻参赛
                    </el-button>
                </router-link>

            </sticky>

            <!--标题-->
            <div class="title">
                <span>{{showData.name}}</span>
            </div>


            <div class="el-row">

                <div class="leftColumn">

                    <el-card class="box-card top left" shadow="hover">
                        <h1>内容详情
                            <svg-icon icon-class="content"></svg-icon>
                            <el-tag style="margin-left: 10px">{{showData.level}}</el-tag>
                            <el-tag style="margin-left: 10px" v-if="showData.type === 'online'">线上比赛</el-tag>

                        </h1>

                        <el-divider content-position="right">昨夜星辰昨夜风</el-divider>
                        <!--markdown-->

                        <el-row v-loading="loading">

                            <markdown-viewer v-bind:content="showData.content"/>

                        </el-row>

                    </el-card>

                </div>


                <div class="rightColumn">
                    <el-card class="top right" shadow="hover">
                        <h3>
                            基本信息 <i class="el-icon-info"></i>
                        </h3>
                        <el-divider content-position="right"></el-divider>
                        <el-row>
                            <el-col :span="24">
                                <span>负责人：  {{showData.principal}}</span>
                            </el-col>
                            <el-col :span="24">
                                <span>发布者：{{showData.author}}</span>
                            </el-col>
                        </el-row>
                    </el-card>

                    <el-card class="box-card top right" shadow="hover">
                        <h3>承办单位
                            <svg-icon icon-class="college"></svg-icon>
                        </h3>
                        <el-divider content-position="right">画楼西畔桂堂东</el-divider>

                        <router-link to="#">

                            <el-tooltip class="item" effect="dark" :content="'查看更多'+showData.organizer+'举办的竞赛'"
                                        placement="bottom">
                                <span><router-link to="/competition/user"><el-link
                                        type="primary">{{showData.organizer}} </el-link></router-link> </span>
                            </el-tooltip>

                        </router-link>

                    </el-card>

                    <el-card class="box-card top right" shadow="hover" v-if="showData.place !==''">
                        <h3>举办地点</h3>
                        <el-divider content-position="right">身无彩凤双飞翼</el-divider>
                        <span><el-link type="primary">比赛地点 {{showData.place}}</el-link></span>
                    </el-card>

                    <el-card class="box-card top right" shadow="hover">

                        <h3>比赛进程
                            <svg-icon icon-class="match"></svg-icon>

                        </h3>

                        <el-divider content-position="right">隔座送钩春酒暖</el-divider>
                        <div class="block">

                            <el-timeline>

                                <el-timeline-item :timestamp="formatTimeA(showData.applyTime)" placement="top">
                                    <el-card>
                                        <h4>开始报名</h4>
                                        <p>参赛者可以进行报名 时间：{{formatTimeA(showData.applyTime)}}</p>
                                    </el-card>
                                </el-timeline-item>

                                <el-timeline-item :timestamp="formatTimeA(showData.startTime)" placement="top">
                                    <el-card>
                                        <h4>比赛开始</h4>
                                        <p>比赛开始于： {{formatTimeA(showData.startTime)}} </p>
                                    </el-card>
                                </el-timeline-item>
                                <el-timeline-item :timestamp="formatTimeA(showData.endTime)" placement="top">
                                    <el-card>
                                        <h4>比赛结束</h4>
                                        <p>比赛于：{{formatTimeA(showData.endTime)}} 结束</p>
                                    </el-card>

                                </el-timeline-item>

                            </el-timeline>
                        </div>
                    </el-card>


                    <el-card class="box-card top right" shadow="hover" v-if="showData.objectName!=null">
                        <h3>相关附件
                            <svg-icon icon-class="annex"></svg-icon>
                        </h3>
                        <el-divider></el-divider>
                        <el-link type="primary" @click="dl()"><i class="el-icon-download"></i>下载</el-link>
                    </el-card>

                </div>

            </div>


            <el-tooltip placement="top" content="返回顶部">
                <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
            </el-tooltip>
        </div>

        <page404 v-if="Page404 ===true"></page404>
    </div>
</template>

<script>
    import qs from 'qs';
    import Sticky from '@/components/Sticky';
    import MarkdownViewer from "../../components/MarkdownViewer/index";
    import BackToTop from "../../components/BackTop/index";
    import {mapGetters} from "vuex";
    import Page404 from '../error-page/404';
    import {getJson} from "../../api/api";
    import {parseTime} from '../../utils/index'

    export default {
        name: "detailStu",
        components: {BackToTop, MarkdownViewer, Sticky, Page404},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                download: {
                    bucketName: '',
                    objectName: '',
                },
                id: '',
                Page404: null,
                showData: {},
                loading: false,
                loading1: true,
                PageLoading: null,
                tempRoute: {},
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
                console.log("sss");
                this.download.bucketName = this.showData.bucketName;
                this.download.objectName = this.showData.objectName;
                let a = document.createElement('a');
                a.href = "/api/i/upFile/getFile?" + qs.stringify(this.download);
                a.download = this.download.objectName;
                a.target = "_blank";
                a.click();
            },
            async fetchData(id) {
                await getJson('/public/competition/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.showData = response.data.data;
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
                const title = this.showData.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 详情`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.showData.name;
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
        height: auto;
        background-color: #ffffff; /* 浏览器不支持时显示 */
        background-image: linear-gradient(#ffffff, #b6fffb);
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
        margin-top: 20px;
        font-size: 50px;
        text-align: center;
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

    #h1 {

    }

    .content {
        float: left;
        width: 100%;
    }

    .right2 {
        margin-right: 20px;
    }

    .center {
        text-align: center;
        align-content: center;
    }

    .item {
        margin-top: 10px;
        margin-right: 40px;
    }
</style>
