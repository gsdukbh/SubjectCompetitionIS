<template>
    <div id="apply" v-loading="loading">
        <div class="main">
            <div class="title">
                <h1>{{competition.name}}</h1>
            </div>
            <el-divider><i class="el-icon-trophy-1"></i></el-divider>

            <el-card shadow="always" class="apply">
                <span>
                    比赛时间:{{formatTimeA(competition.startTime)}} - {{formatTimeA(competition.endTime)}}
                </span>
            </el-card>

            <el-card class="apply top" v-if="isApplyTime " shadow="always">
                距离报名开始还有：{{applyTime}}
            </el-card>
            <el-card class="apply top" v-if="isEnd " shadow="always">
                比赛已经结束！！！
            </el-card>
            <div v-if="!competition.team" class="top">

                <el-button type="primary" round v-if="!isEnd" @click="applyIn()">
                    》》 立刻报名 《《
                </el-button>

                <router-link v-if="isEnd" to="/competition/user">
                    <el-button type="primary" round>
                        》》 查看更多比赛 《《
                    </el-button>
                </router-link>

                <!--查找团队-->

                <el-card class="top a" shadow="hover">
                    查找团队
                </el-card>
                
                <el-card class="top a" shadow="hover">
                    创建
                </el-card>

            </div>
            <el-form :model="apply" ref="apply" :rules="rules">

            </el-form>
        </div>
    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import {getJson} from "../../api/api";
    import {parseTime} from '../../utils/index'
    import {getDuration} from "../../utils";

    export default {
        name: "apply",
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                date: new Date(),
                loading: true,
                PageLoading: null,
                id: '',
                competition: {},
                apply: {
                    scisUser: {
                        id: ''
                    },
                    competition: {
                        id: ''
                    }
                },
                rules: {},
                tempRoute: {},
                applyTime: null,
                isApplyTime: true,
                isEnd: false,
                isTeam:false,

            }
        },
        created() {
            /*competition id*/
            this.id = this.$route.params.id;
            this.tempRoute = Object.assign({}, this.$route);
            this.fetchData(this.id);

        },
        mounted() {
            clearTimeout(this.timeout);
            this.timeout = setInterval(() => {
                this.date = new Date();
                this.applyTime = getDuration(new Date(this.competition.applyTime) - new Date());
                this.isApplyTime = (new Date() < new Date(this.competition.applyTime));
                this.isEnd = (new Date() > new Date(this.competition.endTime))
            }, 1000);
        },
        beforeDestroy() {
            clearInterval(this.timeout)
        },
        methods: {
            applyIn() {

            },
            fetchData(id) {
                getJson('/public/competition/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.competition = response.data.data;
                            this.setTagsViewTitle();
                            this.setPageTitle();
                            this.loading = false;
                        } else if (response.data.status === 404) {
                            this.loading = false;
                            this.$notify.warning({
                                title: '警告',
                                message: '资源不存在'
                            })
                        }
                        this.loading = false;
                    })
                    .catch(() => {
                        this.loading = false;
                    });
            },
            setTagsViewTitle() {
                const title = this.competition.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 报名`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.competition.name;
                document.title = `${title} - 报名`
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },

        }
    }
</script>

<style lang="scss" scoped>
    .main {
        text-align: center;
        align-content: center;
        background-color: rgba(255, 255, 255, 0.1)
    }

    #apply {
        width: auto;
        height: 100vh;
        background-color: #ffffff; /* 浏览器不支持时显示 */
        background-image: linear-gradient(#ffffff, #b6fffb);
        /*background-image: url("http://data.werls.top/img/wallhaven-j5jjw5.jpg");*/
    }

    .title {
        color: orange;
        font-size: 20px;
        text-shadow: 2px 2px #1482f0;
        letter-spacing: 12px;
    }

    .apply {
        width: 25%;
        margin-left: 37.5%;

    }

    .top {
        margin-top: 10px;
    }

    .a {
        width: 40%;
        margin-left: 30%;
        margin-right: 30%;
    }

</style>
