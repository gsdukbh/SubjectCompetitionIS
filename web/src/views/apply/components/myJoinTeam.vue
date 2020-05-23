<template>
    <div>
        <el-table
                border
                style="width: 100%"
                v-loading="loading"
                :data="team">
            <el-table-column label="团队信息">
                <el-table-column label="队伍编号" prop="teamId">
                </el-table-column>
                <el-table-column label="申请时间">
                    <template slot-scope="{row}">
                        <span>{{formatTimeA(row.applyTime)}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="队名" prop="teamName">
                </el-table-column>
                <el-table-column label="成员">
                    <template slot-scope="{row}">
                        <el-link @click="showTeamInfo(row)">查看</el-link>
                    </template>
                </el-table-column>

            </el-table-column>
            <el-table-column label="竞赛信息">
                <el-table-column
                        label="名称"
                        prop="competitionName">
                </el-table-column>
                <el-table-column label="开始比赛时间">
                    <template slot-scope="{row}">
                        <span> <span>{{formatTimeA(row.startTime)}}</span></span>
                    </template>
                </el-table-column>

                <el-table-column label="结束时间">
                    <template slot-scope="{row}">
                        <span> <span>{{formatTimeA(row.endTime)}}</span></span>
                    </template>
                </el-table-column>
                <el-table-column label="比赛地点">
                    <template slot-scope="{row}">
                        <span v-if="row.type==='online'">
                        <el-tag>线上比赛 </el-tag>
                        </span>
                        <span v-if="row.type==='offline'">{{row.place}}</span>
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column label="状态">
                <template slot-scope="{row}">
                    <el-tag type="success" v-if="row.isApply">已加入团队</el-tag>
                    <el-tag type="warning" v-if="!row.isApply">申请中</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作" align="center" width="350px" class-name="small-padding fixed-width">
                <template slot-scope="{row}">
                    <el-button type="info" :loading="buttonLoading" size="mini" icon="el-icon-info"
                               @click="showTeamInfo(row)"
                    >
                        查看成员
                    </el-button>

                    <el-button style="margin-left: 10px" type="danger" :loading="buttonLoading" size="mini"
                               icon="el-icon-delete"
                               @click="escApply(row)">
                        取消报名
                    </el-button>
                </template>

            </el-table-column>

        </el-table>
        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="50%"
        >
            <el-table
                    :data="teamMember"
                    style="width: 100%"
                    :row-class-name="tableRowClassName">
                <el-table-column
                        prop="name"
                        label="名字"
                        width="180px">
                    <template slot-scope="{row}">
                        <span>{{row.name}}
                            <el-tag v-if="row.isCaptain" type="success">队长</el-tag>
                            <el-tag v-if="row.userId===userId" type="info" style="margin-left: 10px">我</el-tag>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="login"
                        label="学号"
                        width="100">
                </el-table-column>
                <el-table-column
                        width="180"
                        prop="email"
                        label="邮箱">
                </el-table-column>

                <el-table-column
                        label="学院"
                        prop="ClassInfo.major.college.name">
                </el-table-column>
                <el-table-column label="班级" prop="ClassInfo.name">
                </el-table-column>

            </el-table>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
        </el-dialog>

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

        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import {getJson, postFrom} from "../../../api/api";
    import {parseTime} from '../../../utils/index'
    import BackToTop from "../../../components/BackTop/index";

    export default {
        name: "myJoinTeam",
        components: {BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                team: [],
                loading: true,
                dialogVisible: false,
                teamMember: [],
                page: {
                    size: 20,
                    page: 0,
                    pages: 0,
                    teamName: '',
                    competitionName: '',
                    isCaptain: false,
                    totalElements: 100,
                },
                buttonLoading: false,

            }
        },
        created() {
            this.getDataPage();
        },
        methods: {

            tableRowClassName({row}) {
                if (row.isCaptain) {
                    return 'success-row';
                } else {
                    return '';
                }
            },
            escApply(value) {
                this.buttonLoading = true;
                this.$confirm('此操作将退出队伍, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    getJson('/student/Competition/team/esc/' + this.userId + '/' + value.teamId)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '成功',
                                    message: '退出成功'
                                })
                                this.getDataPage()
                            } else {
                                this.$notify.error({
                                    title: '错误',
                                    message: response.data.message
                                })
                            }
                            this.buttonLoading = false;
                        }).catch(error => {
                        this.$message.error(error)
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                })
            },
            async showTeamInfo(value) {
                await getJson('/student/team/find/Team/Member/' + value.teamId + '/' + true)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.teamMember = response.data.content;
                        }
                    })
                    .catch(error => {

                        this.$message.error("出现了一些问题:" + error)
                    })
                this.dialogVisible = true;
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
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
            },
            handleRefresh() {
                this.page.teamName = null;
                this.page.competitionName = null;
                this.loading = true;
                this.getDataPage();

            },

            async getDataPage() {
                await postFrom('/student/team/find/My/Team/Captain/' + this.userId, this.page)
                    .then(response => {
                        this.team = response.data.content;
                        this.page.totalElements = response.data.totalElements;
                        this.loading = false;
                    })
                    .catch(error => {
                        this.loading = false;
                        this.$message.error("出现了一些问题:" + error)
                    })
            },
        }

    }
</script>

<style scoped>
    .center {
        text-align: center;
        margin-top: 20px;
    }
</style>
