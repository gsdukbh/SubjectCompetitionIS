<template>
    <div>
        <!--轮播图-->
        <div class="Carousel">
            <el-carousel :interval="4000" type="card" height="480px">

                <el-carousel-item v-for="(item,index) in announcement" :key="index">


                    <div :style="{'background-image': 'url('+item.img+')' ,'background-size':'100% 100%' }"
                         class="image">

                        <div class="title">
                            <span>{{item.title}}</span>
                        </div>
                        <p class="info" style="color: white">
                            <span><i class="el-icon-time"/>{{formatTimeA(item.time)}} </span>
                            <span style="margin-left: 15px"></span>
                            <svg-icon icon-class="college"></svg-icon>
                            {{item.from}}
                        </p>
                        <div>

                        </div>
                        <div class="info">
                            <router-link :to="'/public/announcement/detail/'+item.id">
                                <el-button round style="color: #2b2f3a; background-color: rgba(255,255,255,0.5);">
                                    <span>查看详情</span>

                                </el-button>
                            </router-link>

                        </div>


                    </div>


                </el-carousel-item>
            </el-carousel>
        </div>

        <el-divider></el-divider>

        <div class="main">
            <div class="rightColumn">
                <el-card shadow="always" class="top right">
                    <div slot="header" class="clearfix">
                        <span>学科竞赛系统介绍</span>
                    </div>

                    <li>
                        <span>
                            <el-link href="https://github.com/gsdukbh/SubjectCompetitionIS"
                                     target="_blank">项目地址</el-link>
                            <svg-icon icon-class="github"></svg-icon>
                        </span>
                    </li>

                </el-card>
            </div>

            <div class="leftColumn">
                <el-card class="left top" shadow="always">
                    <div slot="header" class="clearfix">
                        <span>最新公告</span>
                        <router-link to="/public/announcement">
                            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                        </router-link>

                    </div>

                    <div v-loading="loading" style="height: 280px">
                        <div v-for="(item,index) in announcement1" :key="index">
                            <li style="margin-top: 10px">
                                <router-link :to="'/public/announcement/detail/'+item.id">
                                    <el-link type="primary" style="font-size: 16px">{{item.title}}</el-link>

                                    <span style="float: right; padding: 3px 0"> {{formatTimeA(item.time)}}</span>
                                </router-link>
                            </li>
                        </div>
                    </div>

                </el-card>

                <el-card class="left top" shadow="always">
                    <div slot="header" class="clearfix">
                        <span>最新竞赛</span>
                        <router-link to="/public/competition">
                            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                        </router-link>
                    </div>
                    <div v-loading="loading1" style="height: 280px">
                        <div v-for="(item,index) in competition" :key="index">

                            <li style="margin-top: 10px">
                                <router-link :to="'/public/competition/detail/'+item.id">

                                    <el-link type="primary" style="font-size: 16px" href="/#">{{item.name}}</el-link>
                                    <span style="float: right; padding: 3px 0">{{formatTimeA(item.applyTime)}}</span>
                                </router-link>
                            </li>
                        </div>
                    </div>

                </el-card>

            </div>

        </div>
        <div class="end">
            <el-divider></el-divider>
            <div class="endInfo">
                <span> © 大学学科竞赛系统</span>
                <br>
                <p> {{formatTimeA(new Date().getTime())}}</p>
            </div>


        </div>
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>


<script>


    import {postFrom} from "../../api/api";

    import BackToTop from "../../components/BackTop/index";
    import {Myformat} from "../../utils";

    export default {
        name: "index",
        components: {BackToTop},
        data() {
            return {
                loading: true,
                loading1: true,
                announcement: [],
                announcement1: [],
                competition: [],
            }
        },
        created() {
            setTimeout(() => {
                this.getAnnouncement();
                this.getAnnouncement1();
                this.getCompetition()
            }, 2000);

        },
        mounted() {

        },
        methods: {
            formatTimeA(time) {
                // return parseTime(time, '{y}-{m}-{d} {h}:{i}')
                return Myformat("yyyy年MM月dd日", new Date(time))
            },
            getAnnouncement1() {
                const info = {
                    size: 10,
                    page: 0
                }
                postFrom('/public/announcement/findAllA', info)
                    .then(response => {
                        this.announcement1 = response.data.content;
                        this.loading1 = false;
                    }).catch(() => {
                    this.loading1 = false;
                    this.getAnnouncement1()
                    // this.$notify.error({
                    //     title: '错误',
                    //     message: '服务器异常：' + error
                    // })
                })
            },
            getCompetition() {
                const info = {
                    size: 10,
                    page: 0
                };
                postFrom('/public/competition/findAll/s', info)
                    .then(response => {
                        this.competition = response.data.content;
                        this.loading = false;
                    }).catch(() => {
                    this.loading = false;
                    this.getCompetition()
                    // this.$notify.error({
                    //     title: '错误',
                    //     message: '服务器异常：' + error
                    // })
                })
            },
            getAnnouncement() {
                const info = {
                    size: 5,
                    page: 0
                }
                postFrom('/public/announcement/findAllA', info)
                    .then(response => {
                        this.announcement = response.data.content;
                    }).catch(() => {
                    this.getAnnouncement()
                    // this.$notify.error({
                    //     title: '错误',
                    //     message: '服务器异常：' + error
                    // })
                })
            }
        }
    }
</script>

<style scoped>
    .el-carousel__item h3 {
        color: #080808;
        font-size: 14px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
        text-align: center;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }

    .Carousel {
        margin-top: 20px;
        height: 480px;
        width: 100%;
    }

    .main {
        padding-left: 10%;
        padding-right: 10%;
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
        padding-top: 20%;
        font-size: 50px;
        text-align: center;
        letter-spacing: 12px;
        color: aliceblue;
    }

    .end {
        float: left;
        width: 100%;
        padding-top: 20px;

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

    .image {
        height: 480px;
    }

    .info {
        align-content: center;
        text-align: center;
        padding-top: 20px;
    }

    .endInfo {
        height: 50px;
        color: #d8dce5;
        text-align: center;
        font-size: 10px;
    }
</style>
