<template>
    <div class="home">

        <div class="welcome">
            <!--欢迎信息！-->
            <span class="welcome-info">欢迎！</span>
            <span class="welcome-name">{{myInfo.name}}</span>
            <br>
            <span style="color: #9aaabf">现在是，{{time()}}。快看看有什么新动态把！</span>
        </div>
        <div class="rightColumn my-left">
            <el-card class=" top ">
                <div slot="header" class="clearfix">
                    <span>我的信息</span>
                    <router-link to="/me/myInfo">
                        <el-button style="float: right; padding: 3px 0" type="text">更新</el-button>
                    </router-link>
                </div>
                <li v-if="myInfo.role==='学生'">
                    <span>学号：{{myInfo.login}}</span>
                </li>
                <li v-if="myInfo.role !=='学生'">
                    <span>工号：{{myInfo.login}}</span>
                </li>
                <li>
                    <span>邮箱：{{myInfo.email}} </span>
                </li>
                <li>
                    <span>
                        手机号码：{{myInfo.phone}}
                    </span>
                </li>
                <li>
                    <span>身份:{{myInfo.role}}</span>
                </li>
                <div v-if="myInfo.role ==='学生'">
                    <li>
                        <span>院系：{{myInfo.scisClass.major.college.name}}</span>
                    </li>
                    <li>
                        <span>专业：{{myInfo.scisClass.major.name}}</span>
                    </li>
                    <li>
                        <span>班级：{{myInfo.scisClass.name}}</span>
                    </li>
                </div>
                <div v-if="myInfo.role ==='教师'">
                    <li v-if="myInfo.college">
                        <span>院系：{{myInfo.college.name}}</span>
                    </li>
                </div>
            </el-card>

        </div>
        <div v-if="myInfo.role==='学生'">
            <div class="Column">
                <el-card class="  top" v-loading="loading">
                    <div slot="header" class="clearfix">
                        <span>最新的公告</span>
                        <router-link to="/announcement/index">
                            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                        </router-link>
                    </div>
                    <div style="height: 200px">
                        <div v-for="(item,index) in announcement" :key="index">
                            <li style="margin-top: 10px">
                                <router-link :to="'/announcement/detailStu/'+item.id">
                                    <el-link type="primary" style="font-size: 16px" v-if="item.title.length < 20">
                                        {{item.title}}
                                    </el-link>
                                    <el-link type="primary" style="font-size: 16px" v-if="item.title.length > 20">
                                        {{item.title.substr(0,20)}}...
                                    </el-link>
                                    <span style="float: right; padding: 3px 0"> {{formatTimeA(item.time)}}</span>
                                </router-link>
                            </li>
                        </div>
                    </div>


                </el-card>
            </div>
            <div class="Column">
                <el-card class="  top" v-loading="loading1">
                    <div slot="header" class="clearfix">
                        <span>最新的竞赛</span>
                        <router-link to="/competition/user">
                            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                        </router-link>
                    </div>
                    <div style="height: 200px">
                        <div v-for="(item,index) in competition" :key="index">

                            <li style="margin-top: 10px">
                                <router-link :to="'/competition/detailStu/'+item.id">
                                    <el-link type="primary" style="font-size: 16px" href="/#">{{item.name}}</el-link>
                                    <span style="float: right; padding: 3px 0">{{formatTimeA(item.applyTime)}}</span>
                                </router-link>
                            </li>
                        </div>
                    </div>
                </el-card>
            </div>
        </div>


        <div v-if="myInfo.role!=='学生'">
            <div class="Column">
                <el-card class="  top" v-loading="loading">
                    <div slot="header" class="clearfix">
                        <span>最新的公告</span>
                        <router-link to="/announcement/list">
                            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                        </router-link>

                    </div>
                    <div style="height: 200px">
                        <div v-for="(item,index) in announcement1" :key="index">
                            <li style="margin-top: 10px">
                                <router-link :to="'/announcement/detail/'+item.id">
                                    <el-link type="primary" style="font-size: 16px" v-if="item.title.length < 20">
                                        {{item.title}}
                                    </el-link>
                                    <el-link type="primary" style="font-size: 16px" v-if="item.title.length > 20">
                                        {{item.title.substr(0,20)}}...
                                    </el-link>
                                    <span style="float: right; padding: 3px 0"> {{formatTimeA(item.time)}}</span>
                                </router-link>
                            </li>
                        </div>
                    </div>
                </el-card>
            </div>
            <div class="Column">
                <el-card class="  top" v-loading="loading1">
                    <div slot="header" class="clearfix">
                        <span>最新的竞赛</span>
                        <router-link to="/competition/index">
                            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                        </router-link>
                    </div>
                    <div style="height: 200px">
                        <div v-for="(item,index) in competition1" :key="index">

                            <li style="margin-top: 10px">
                                <router-link :to="'/competition/detail/'+item.id">
                                    <el-link type="primary" style="font-size: 16px" href="/#">{{item.name}}</el-link>
                                    <span style="float: right; padding: 3px 0">{{formatTimeA(item.applyTime)}}</span>
                                </router-link>
                            </li>
                        </div>
                    </div>

                </el-card>
            </div>
        </div>

    </div>

</template>

<script>

    import {Myformat} from "../../utils";
    import {getJson, postFrom} from "../../api/api";
    import {mapGetters} from "vuex";

    export default {
        name: 'home',
        computed: {
            ...mapGetters([
                'userId'
            ])
        },
        data() {
            return {
                loading: true,
                myInfo: {
                    name: ''
                },
                loading1: true,
                announcement: null,
                announcement1: null,
                competition: [],
                competition1: null,
            }
        },

        created() {
            // this.initWebSocket();
            this.getMyInfo();
            setTimeout(() => {
                this.getAnnouncement();
                this.getCompetition();
                if (this.myInfo.role !== '学生') {
                    this.getAnnouncement1();
                    this.getCompetition1()
                }
            }, 2000);


        },
        methods: {
            getAnnouncement1() {
                const info = {
                    size: 5,
                    page: 0
                }
                postFrom('/tea/announcement/findAll', info)
                    .then(response => {
                        this.announcement1 = response.data.content;
                        this.loading = false;
                    }).catch(error => {
                    this.loading = false;
                    this.$notify.error({
                        title: '错误',
                        message: '服务器异常：' + error
                    })
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
                        this.loading = false;
                    }).catch(error => {
                    this.loading = false;
                    this.$notify.error({
                        title: '错误',
                        message: '服务器异常：' + error
                    })
                })
            },
            getCompetition() {
                const info = {
                    size: 5,
                    page: 0
                };
                postFrom('/public/competition/findAll/s', info)
                    .then(response => {
                        this.competition = response.data.content;
                        this.loading1 = false;
                    }).catch(error => {
                    this.loading1 = false;
                    this.$notify.error({
                        title: '错误',
                        message: '服务器异常：' + error
                    })
                })
            },
            getCompetition1() {
                const info = {
                    size: 5,
                    page: 0
                };
                postFrom('/tea/competition/findAll', info)
                    .then(response => {
                        this.competition1 = response.data.content;
                        this.loading1 = false;
                    }).catch(error => {
                    this.loading1 = false;
                    this.$notify.error({
                        title: '错误',
                        message: '服务器异常：' + error
                    })
                })
            },
            async getMyInfo() {
                await getJson('/i/findMyInfo/' + this.userId)
                    .then(response => {
                        this.myInfo = response.data.data;
                    }).catch(error => {
                        this.$notify.error({
                            title: '异常',
                            message: '服务器异常' + error
                        })
                    })
            },
            formatTimeA(time) {
                return Myformat("yyyy年MM月dd日hh小时mm分", new Date(time))
            },
            time() {
                return Myformat("yyyy年MM月dd日hh小时mm分", new Date());
            },
        }
    }

</script>
<style scoped>

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

    .welcome-info {
        letter-spacing: 4px;
        font-size: 35px;
        font-family: '新宋体';
    }

    .rightColumn {
        float: left;
        width: 25%;
        padding-left: 20px;
    }

    .Column {
        float: left;
        width: 35%;
        padding-left: 20px;
    }

    .leftColumn {
        float: left;
        width: 75%;
    }

    .welcome {
        margin-left: 5%;
        margin-top: 40px;
    }

    .welcome-name {
        font-family: '楷体';
        font-size: 30px;
    }

    .my-left {
        /*margin-left: 5%;*/
    }
</style>
