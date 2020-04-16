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
                    <el-button type="primary" size="mini" icon="el-icon-info"
                               @click="showTeamInfo(row)"
                    >
                        管理团队
                    </el-button>

                    <el-button style="margin-left: 10px" type="danger" :loading="buttonLoading" size="mini"
                               icon="el-icon-delete"
                               @click="disbandTeam(row)">
                        解散队伍
                    </el-button>
                </template>

            </el-table-column>

        </el-table>
        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="60%"
        >
            <!-- v-if="applyTeam.length===0"-->
            <div>
                <el-card
                        shadow="hover"
                        v-for="(row,index) in applyTeam "
                        :key="row.userId"
                >
                    <el-row>
                        <el-col :span="2">
                            <el-avatar icon="el-icon-user-solid" v-if="row.avatar===null"></el-avatar>
                            <el-avatar
                                    v-if="row.avatar!==null"
                                    :src="row.avatar">
                            </el-avatar>

                        </el-col>
                        <el-col :span="22">
                            <span style="font-size: larger">{{row.name}}  </span>
                            <span style="margin-left: 20px;color: #5a5e66"> 时间：{{formatTimeA(row.applyTeamTime)}} </span>
                            <br>
                            <svg-icon icon-class="college"/>
                            <span>{{row.ClassInfo.major.college.name}}</span>
                            <span style="margin-left: 10px">{{row.ClassInfo.name}}</span>
                            <span style="margin-left: 10px;color: #2b2f3a;font-size: large">申请加入你的团队 </span>

                            <el-button style="margin-left: 30%" :loading="loading1" type="primary"
                                       @click="agreeApply(row,index)">
                                同意
                            </el-button>
                            <el-button type="danger" @click="refuseApply(row,index)">
                                拒绝
                            </el-button>
                        </el-col>
                    </el-row>
                </el-card>
            </div>

            <el-table
                    :data="teamMember"
                    style="width: 100%;margin-top: 5px"
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
                <el-table-column label="操作" align="center" width="180px" class-name="small-padding fixed-width">
                    <template slot-scope="{row}">
                        <el-button style="margin-left: 10px" type="danger" :loading="buttonLoading" size="mini"
                                   icon="el-icon-delete"
                                   @click="escApply(row)">
                            请他离开队伍
                        </el-button>
                    </template>

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
                    :current-page="page.page"
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
    import {getJson, postFrom, postJson} from "../../../api/api";
    import BackToTop from "../../../components/BackTop/index";
    import {parseTime} from '../../../utils/index'

    export default {
        name: "myCreatedApply",
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
                loading1: false,
                dialogVisible: false,
                teamMember: [],
                page: {
                    size: 20,
                    page: 0,
                    teamName: '',
                    competitionName: '',
                    isCaptain: true,
                    totalElements: 100,
                },
                buttonLoading: false,
                applyTeam: [],
                upDate: {
                    isCaptain: false,
                    isApply: false,
                    isRead: true,
                    teamId: null,
                    userId: null,
                }
            }
        },
        created() {
            this.getDataPage()
        },
        methods: {
            agreeApply(value, index) {
                this.upDate.isApply = true;
                this.upDate.teamId = value.teamId
                this.upDate.userId = value.userId;
                postJson('/student/team/update', this.upDate)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$notify.success({
                                title: '成功',
                            })
                            this.applyTeam.splice(index, 1);
                            this.getDataPage()
                        } else {
                            this.$notify.error({
                                title: '错误',
                                message: response.data.message
                            })
                        }
                    })
            },
            refuseApply(value, index) {
                this.$confirm('确定拒绝？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    getJson('/student/Competition/team/esc/' + value.userId + '/' + value.teamId)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '成功',
                                })
                                this.applyTeam.splice(index, 1);
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
                    })
                })
            },
            escApply(value) {
                this.buttonLoading = true;
                this.$confirm('此操作将取消, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    getJson('/student/Competition/esc/' + value.applyId)
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
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                })
            },
            tableRowClassName({row}) {
                if (row.isCaptain) {
                    return 'success-row';
                } else {
                    return '';
                }
            },

            disbandTeam(value) {
                this.buttonLoading = true;
                this.$confirm('此操作将解散队伍, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    getJson('/student/Competition/team/esc/' + value.teamId)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '成功',
                                    message: '队伍解散成功'
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
                        this.buttonLoading = false;
                        this.$message.error(error)
                    })
                }).catch(() => {
                    this.buttonLoading = false;
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
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
                getJson('/student/team/find/Team/Member/' + value.teamId + '/' + false)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.applyTeam = response.data.content;
                        }
                    }).catch(error => {

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
                this.page.page = val;
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
