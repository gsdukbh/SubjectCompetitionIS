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
                <span>
                 距离报名开始还有：{{applyTime}}
                 </span>
            </el-card>
            <el-card class="apply top" v-if="!isApplyTime " shadow="always">
                <span>
                 距离比赛开始还有：{{startTime}}
                 </span>
            </el-card>
            <el-card class="apply top" v-if="isEnd" shadow="always">
                比赛已经结束！！！
            </el-card>
            <div v-if="!competition.team" class="top">

                <el-button type="primary" :loading="buttonLoading" round
                           v-if=" !isEnd && isApplyTime && !competition.team " @click="applyIn()">
                    》》 立刻报名 《《
                </el-button>

                <router-link v-if="isEnd" to="/competition/user">
                    <el-button type="primary" round>
                        》》 查看更多比赛 《《
                    </el-button>
                </router-link>

                <!--查找团队-->

                <el-card class="top a" shadow="hover">
                    <!--搜索框-->
                    <div style="margin-top: 10px;">

                        <el-input placeholder="名称" style="width: 200px;margin-left: 10px;" v-model="page.name"/>

                        <el-button round size="small" class="filter-item" type="primary" style="margin-left: 10px;"
                                   icon="el-icon-search"
                                   @click="handleFilter">
                            搜索
                        </el-button>

                        <el-button round size="small" class="filter-item" type="primary" style="margin-left: 10px;"
                                   icon="el-icon-refresh-left"
                                   @click="handleRefresh">
                            重置
                        </el-button>
                    </div>

                    <el-table
                            v-loading="loading1"
                            :data="applyTeam"
                            style="width: 100%"
                            height="250">
                        <el-table-column
                                prop="name"
                                label="队名">
                        </el-table-column>
                        <el-table-column
                                prop="captain"
                                label="队长">
                        </el-table-column>
                        <el-table-column
                                sortable
                                prop="scisUserList.length"
                                label="人数">
                            <template slot-scope="{row}">
                                <span>{{row.scisUserList.length}}/{{row.number}}</span>
                            </template>
                        </el-table-column>

                        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                            <template slot-scope="{row}">
                                <el-button @click="showJoin(row)" size="mini" type="primary" round>
                                    加入
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>


                    <div class="center">
                        <el-pagination
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                @next-click="handleCurrentChange"
                                @prev-click="handleCurrentChange"
                                :current-page="page.page"
                                :page-sizes="[20,50,100]"
                                :page-size="page.size"
                                background
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="page.totalElements">
                        </el-pagination>
                    </div>
                </el-card>

                <el-card class="top a" shadow="hover">
                    创建
                </el-card>
                <el-button class="top" type="primary" :loading="buttonLoading" round
                           v-if=" !isEnd && isApplyTime && !competition.team " @click="applyIn()">
                    》》 立刻报名 《《
                </el-button>
            </div>

            <el-dialog title="成员详情" center :visible.sync="dialogVisible">
                <el-table v-loading="loading2"
                          :data="teamList"
                          style="width: 100%"
                >
                    <el-table-column prop="user.login"
                                     label="学号">
                    </el-table-column>
                    <el-table-column prop="user.name"
                                     label="名字">
                        <template slot-scope="{row}">
                            <span>{{row.name}}</span>
                            <el-tag size="small" v-if="row.captain">队长</el-tag>
                            <el-tag type="success" style="margin-left: 10px" size="small" v-if="row.user.id === userId">
                                我
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="user.scisClass.name"
                            label="班级">
                    </el-table-column>
                    <el-table-column
                            prop="user.scisClass.major.college.name"
                            label="学院">
                    </el-table-column>
                </el-table>

                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false" round>取 消</el-button>
                    <el-button type="primary" round @click="join()" :loading="buttonLoading1">
                        加入他们
                    </el-button>
                    <el-button type="primary" @click="dialogVisible = false" round>确 定</el-button>
                     </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import {getJson, postFrom, postJson} from "../../api/api";
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
                loading2: false,
                loading1: false,
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
                applyJoinTeam: {
                    id: null,
                    scisUserList: [],
                    competition: {
                        id: 1
                    }
                },
                applyTeam: [],
                rules: {},
                tempRoute: {},
                applyTime: null,
                isApplyTime: true,
                isEnd: false,
                isTeam: false,
                startTime: null,
                isApplyTimeStop: false,
                buttonLoading: false,
                page: {
                    size: 20,
                    page: 0,
                    name: null,
                    totalElements: 100,
                },
                dialogVisible: false,
                teamList: [],
                buttonLoading1: false,
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
                this.isApplyTimeStop = (new Date() > new Date(this.competition.startTime));
                this.startTime = getDuration(new Date(this.competition.startTime) - new Date());
            }, 1000);
        },
        beforeDestroy() {
            clearInterval(this.timeout)
        },
        methods: {
            join() {
                this.buttonLoading1 = true
                const user = {
                    id: this.userId
                }
                this.applyJoinTeam.scisUserList.push(user)
                postJson('/student/team/upData', this.applyJoinTeam)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$message.success("加入成功")
                        } else {
                            this.$notify.error({
                                title: '失败',
                                message: response.data.message,
                            })
                        }
                        this.buttonLoading1 = false;
                    }).catch(error => {
                    this.buttonLoading1 = false;
                    this.$notify.error({
                        title: "错误",
                        message: '出现了一些错误' + error,
                    })
                })
            },
            async showJoin(row) {
                this.dialogVisible = true;
                this.loading2 = true;
                await getJson('/student/team/find/member/' + row.id)
                    .then(response => {
                        this.teamList = response.data.data;
                        this.loading2 = false;
                    })
                    .catch(error => {
                        this.loading2 = false;
                        this.$notify.error({
                            title: '错误',
                            message: error
                        })
                    })
                this.applyJoinTeam.id = row.id;
            },

            handleSizeChange(val) {
                this.page.size = val;
                this.loading = true;
                this.getDataPage();
            },
            /*当前页数*/
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.page = val;
                this.getDataPage()
            },

            handleFilter() {
                // this.loading = true;
                this.getDataPage();
            },
            handleRefresh() {
                this.page.name = null;
                this.getDataPage();
            },
            async getDataPage() {
                this.loading1 = true;
                await postFrom('/student/team/findAll/' + this.id, this.page)
                    .then(response => {
                        this.applyTeam = response.data.data;
                        this.page.totalElements = response.data.totalElements;
                        this.loading1 = false;
                    })
                this.loading1 = false;
            },
            applyIn() {
                this.buttonLoading = true;
                this.apply.scisUser.id = this.userId;
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
                this.getDataPage();
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

    .center {
        text-align: center;
        align-content: center;
    }
</style>
