<template>
    <div>
        <!--顶部-->
        <!--        <sticky :z-index="10" class-name="sub-navbar">-->


        <!--            <el-button style="margin-left: 10px;" type="primary" @click="dl()">-->
        <!--                导出申请信息-->
        <!--            </el-button>-->


        <!--        </sticky>-->

        <!--标题-->
        <div class="title">
            <span> {{competition.name}}--报名数据</span>
        </div>


        <!--左边信息-->
        <div class="leftColumn ">

            <el-card shadow="hover" class="left top">
                <div slot="header" class="clearfix">
                    <span>报名信息</span>
                    <el-tag style="margin-left: 10px"> 一共{{page.totalElements}}人</el-tag>
                    <!--                    <el-tag style="margin-left: 10px" v-if="!competition.team"> 一共{{page.totalElements}}人</el-tag>-->
                    <!--                    <el-tag style="margin-left: 10px" v-if="competition.team"> 一共{{page.totalElements *-->
                    <!--                        competition.numLimit }}人-->
                    <!--                    </el-tag>-->
                    <el-button @click="dl()" style="float: right; padding: 3px 0" type="text" icon="el-icon-download">
                        导出申请信息
                    </el-button>
                </div>


                <div style="margin-top: 10px;">

                    <el-input placeholder="名称" style="width: 200px;margin-left: 10px;" v-model="page.name"/>

                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-search"
                               @click="handleFilter">
                        搜索
                    </el-button>
                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                               @click="handleRefresh">
                        重置搜索
                    </el-button>

                </div>
                <!--团队赛-->
                <!--                <el-table-->
                <!--                        v-if="competition.team"-->
                <!--                        ref="table"-->
                <!--                        border stripe-->
                <!--                        highlight-current-row-->
                <!--                        :data="teamData"-->
                <!--                        v-loading="loading1"-->
                <!--                        style="width: 100%;margin-top: 10px">-->
                <!--                    <el-table-column label="队伍号码" prop="id" width="100">-->
                <!--                    </el-table-column>-->
                <!--                    <el-table-column label="队伍名称" prop="name">-->
                <!--                    </el-table-column>-->
                <!--                    <el-table-column label="申请时间" prop="applyTime">-->
                <!--                        <template slot-scope="{row}">-->
                <!--                            <span>{{formatTimeA(row.applyTime)}}</span>-->
                <!--                        </template>-->
                <!--                    </el-table-column>-->
                <!--                    <el-table-column label="人数" prop="number">-->
                <!--                        <template slot-scope="{row}">-->
                <!--                            <span>{{row.scisUserList.length}} / {{row.number}}</span>-->
                <!--                        </template>-->
                <!--                    </el-table-column>-->
                <!--                    <el-table-column label="操作" width="100">-->
                <!--                        <template slot-scope="scope">-->
                <!--                            <el-button type="text" @click="toogleExpand(scope.row)">查看成员</el-button>-->
                <!--                        </template>-->
                <!--                    </el-table-column>-->
                <!--                    <el-table-column type="expand" width="1">-->
                <!--                        <template slot-scope="props">-->
                <!--                            <el-form label-position="left" inline class="demo-table-expand">-->
                <!--                                <div v-for="item in props.row.scisUserList" :key="item.id">-->
                <!--                                    <el-row>-->
                <!--                                        <el-col :span="6">-->
                <!--                                            <el-form-item label="学号：">-->
                <!--                                                <span>{{item.login}}</span>-->
                <!--                                            </el-form-item>-->
                <!--                                        </el-col>-->
                <!--                                        <el-col :span="6">-->
                <!--                                            <el-form-item label="名字：">-->
                <!--                                                <span>{{item.name}}</span>-->
                <!--                                            </el-form-item>-->
                <!--                                        </el-col>-->
                <!--                                        <el-col :span="6">-->
                <!--                                            <el-form-item label="班级">-->
                <!--                                                <span>{{item.scisClass.name}}</span>-->
                <!--                                            </el-form-item>-->
                <!--                                        </el-col>-->
                <!--                                        <el-col :span="6">-->
                <!--                                            <el-form-item label="学院">-->
                <!--                                                <span>{{item.scisClass.major.college.name}}</span>-->
                <!--                                            </el-form-item>-->
                <!--                                        </el-col>-->
                <!--                                    </el-row>-->
                <!--                                </div>-->
                <!--                            </el-form>-->
                <!--                        </template>-->
                <!--                    </el-table-column>-->
                <!--                </el-table>-->

                <el-table
                        v-loading="loading"
                        :data="personalData"
                        style="width: 100%;margin-top: 10px"
                >
                    <el-table-column
                            sortable
                            prop="applyTime"
                            label="申请时间"
                            width="180">
                        <template slot-scope="{row}">
                            <span>{{formatTimeA(row.applyTime)}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="学号">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.login}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="名字">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.name}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="班级">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.scisClass.name}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="学院">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.scisClass.major.name}}</span>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="center">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            @next-click="handleCurrentChange"
                            @prev-click="handleCurrentChange"
                            :current-page="page.pages"
                            :page-sizes="[20,50,100]"
                            :page-size="page.size"
                            background
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="page.totalElements">
                    </el-pagination>
                </div>

            </el-card>


        </div>
        <!--右边信息-->


        <!--返回-->
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import {getJson, postFrom} from "../../api/api";
    import {parseTime} from '../../utils/index'
    import BackToTop from "../../components/BackTop/index";

    export default {
        name: "admin",
        components: {BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        props: {},
        data() {
            return {
                loading1: true,
                loading2: false,
                competitionId: '',
                competition: [],
                isTeam: false,
                loading: true,
                teamData: [],
                personalData: [],
                page: {
                    size: 20,
                    page: 0,
                    pages: 0,
                    name: '',
                    totalElements: 1,
                },
            }
        },
        created() {
            this.competitionId = this.$route.params.id;
            this.fetchData(this.competitionId);
            this.tempRoute = Object.assign({}, this.$route);
            this.PageLoading = this.$loading({
                lock: true,
                text: '拼命加载中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            this.getDataPage()
        },
        methods: {
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
            dl() {
                let a = document.createElement('a');
                a.href = "/api/tea/apply/downloadApply/" + this.competitionId;
                // a.download = download.objectName;
                a.target = "_blank";
                a.click();
            },
            toogleExpand(row) {
                const $table = this.$refs.table;
                this.personalData.forEach(item => {
                    if (row.id !== item.id) {
                        $table.toggleRowExpansion(item, false);
                    }
                });
                $table.toggleRowExpansion(row);
            },
            getTeam() {
                this.loading1 = false;
                postFrom('/student/team/findAll/' + this.competitionId, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.teamData = response.data.data;
                            this.page.totalElements = response.data.totalElements;
                            this.loading1 = false;
                        }
                    }).catch(error => {
                    this.loading1 = false;
                    this.$message.error("出现了一些问题:" + error)
                })
            },
            getPersonal() {
                this.loading = true;
                postFrom('/tea/apply/personal/' + this.competitionId, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.personalData = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        }
                    }).catch(error => {
                    this.loading = false;
                    this.$message.error("出现了一些问题:" + error)
                })
            },
            handleSizeChange(val) {
                this.page.size = val;
                this.loading = true;
                this.getDataPage();
            },
            /*当前页数*/
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.page = val - 1;
                this.loading = true;
                this.getDataPage()
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
                console.log(this.page)
            },
            handleRefresh() {
                this.page.title = null;
                this.page.from = null;
                this.page.type = null;
                this.loading = true;
                this.getDataPage();

            },
            getDataPage() {
                // this.getTeam()
                this.getPersonal()
            },
            async fetchData(id) {
                await getJson('/public/competition/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.competition = response.data.data;
                            this.loading = false;
                            this.setTagsViewTitle();
                            this.setPageTitle();
                            this.PageLoading.close();
                        } else if (response.data.status === 404) {
                            this.loading = false;
                            this.getDataPage()
                            this.PageLoading.close();
                            this.$notify.warning({
                                title: '警告',
                                message: '资源不存在'
                            })
                        }
                    })
                    .catch(() => {
                    });
                this.getPersonal()
            },
            setTagsViewTitle() {
                const title = this.competition.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 报名信息`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.competition.name;
                document.title = `${title} - 报名信息`
            }
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
        letter-spacing: 12px;
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
        margin-top: 20px;
    }

    .item {
        margin-top: 10px;
        margin-right: 40px;
    }
</style>
