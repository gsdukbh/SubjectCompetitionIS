<template>
    <div class="main">
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

        <div class="top">
            <el-table
                    v-loading="loading"
                    :data="competition"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="name"
                        sortable
                        label="名称"
                        width="180">
                </el-table-column>

                <el-table-column
                        prop="startTime"
                        sortable
                        label="名称"
                        width="180">
                    <template slot-scope="{row}">
                        <span>{{row.startTime}}</span>
                    </template>

                </el-table-column>

                <el-table-column
                        sortable
                        prop="organizer"
                        label="承办单位"
                        width="180">
                </el-table-column>

                <el-table-column
                        prop="place"
                        label="举办地点">
                    <template slot-scope="{row}">
                    <span>
                        <el-tag v-if="row.type ==='online'">线上比赛</el-tag>
                        <span v-if="row.type ==='offline'">{{row.place}}</span>
                    </span>
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
    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import BackToTop from "../../components/BackTop/index";
    import {postFrom} from "../../api/api";
    import {parseTime} from '../../utils/index'

    export default {
        name: "myjoin",
        components: {BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                id: '',
                loading: true,
                competition: [],
                page: {
                    size: 20,
                    page: 0,
                    name: null,
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

            handleSelectionChange(val) {
                this.multipleSelection = val
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
                this.loading = true;
                postFrom('/student/Competition/my/join/' + this.userId, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.competition = response.data.data;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        } else {
                            this.$notify.warning({
                                title: '警告',
                                message: response.data.message
                            })
                        }
                    }).catch(error => {
                    this.$message.error("错误：" + error)
                })
            }
        }
    }
</script>

<style scoped>
    .top {
        margin-top: 10px;
    }

    .center {
        text-align: center;
    }
</style>
