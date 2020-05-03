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
                :data="myApply"
                style="width: 100%">
            <el-table-column
                    prop="applyTime"
                    label="申请时间"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="竞赛名称"
            >
                <template slot-scope="{row}">
                    <router-link :to="'/competition/detailStu/'+row.competitionId">
                        <el-link>{{row.name}}</el-link>
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

            <el-table-column label="操作" align="center" width="250px" class-name="small-padding fixed-width">
                <template slot-scope="{row}">
                    <div v-if="row.status ==='online'">
                        <router-link to="/works/up" v-if="row.worksId===null">
                            <el-button type="primary" size="mini" icon="el-icon-upload2">
                                提交作品
                            </el-button>
                        </router-link>
                        <router-link :to="'/works/detail/'+row.worksId" v-if="row.worksId!==null">
                            <el-button type="primary" size="mini" icon="el-icon-view">
                                查看作品
                            </el-button>
                        </router-link>
                    </div>

                    <el-button style="margin-left: 10px" type="danger" :loading="buttonLoading" size="mini"
                               icon="el-icon-edit"
                               @click="escApply(row)">
                        取消报名
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

        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>

<script>
    import {getJson, postFrom} from "../../../api/api";
    import {mapGetters} from "vuex";
    import BackToTop from "../../../components/BackTop/index";
    import {parseTime} from '../../../utils/index'

    export default {
        name: "applyPersonal",
        components: {BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                buttonLoading: false,
                myApply: [],
                loading: true,
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
                await postFrom('/student/Competition/findPersonal/' + this.userId, this.page)
                    .then(response => {
                        this.myApply = response.data.content;
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
