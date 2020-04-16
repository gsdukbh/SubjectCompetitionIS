<template>
    <div>
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

        <el-table

                v-loading="loading"
                :data="teamApply"
                style="width: 100%;margin-top: 20px">
            <el-table-column label="团队信息">
                <el-table-column prop="teamId"
                                 label="团队编号"
                                 width="100px"
                >
                </el-table-column>
                <el-table-column
                        prop="applyTime"
                        label="申请时间"
                        width="180">
                    <template slot-scope="{row}">
                        <span>{{formatTimeA(row.applyTime)}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="队名"
                        prop="name">
                    <template slot-scope="{row}">
                        <router-link to="/apply/team/">
                            <el-link v-if="row.name.length>15">{{row.name.substr(0,20)}}...</el-link>
                            <el-link v-if="row.name.length<15" style="color: #2b2f3a"> {{row.name}}</el-link>
                        </router-link>
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column label="竞赛信息">
                <el-table-column
                        prop="name"
                        label="竞赛名称"
                >
                    <template slot-scope="{row}">
                        <router-link :to="'/competition/detailStu/'+row.competitionId">

                            <el-link v-if="row.competitionName.length>15">{{row.competitionName.substr(0,20)}}...
                            </el-link>
                            <el-link v-if="row.competitionName.length<15">{{row.competitionName}}</el-link>
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="startTime"
                        label="竞赛开始时间">
                    <template slot-scope="{row}">
                     <span>
                       {{formatTimeA(row.startTime)}}
                    </span>
                    </template>
                </el-table-column>

                <el-table-column
                        prop="place"
                        label="举办地点">
                    <template slot-scope="{row}">
                     <span>
                        <el-tag type="info" v-if="row.type ==='online'">线上比赛</el-tag>
                        <span v-if="row.type ==='offline'">{{row.place}}</span>
                    </span>
                    </template>
                </el-table-column>
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

        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import {postFrom} from "../../../api/api";
    import {parseTime} from '../../../utils/index'
    import BackToTop from "../../../components/BackTop/index";

    export default {
        name: "teamApply",
        components: {BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                teamApply: null,
                loading: true,
                buttonLoading: false,
                page: {
                    size: 20,
                    page: 0,
                    name: '',
                    totalElements: 100,
                },
            }
        },
        created() {
            this.getDataPage();
        },
        methods: {
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
                console.log(this.page)
            },
            handleRefresh() {
                this.page.name = null;
                this.loading = true;
                this.getDataPage();

            },
            async getDataPage() {
                await postFrom('/student/Competition/findTeam/' + this.userId, this.page)
                    .then(response => {
                        this.teamApply = response.data.content;
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
