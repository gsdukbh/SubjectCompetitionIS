<template>
    <div>

        <div style="margin-top: 10px;">

            <div style="margin-left: 10%">
                <el-select style="width: 200px;margin-left: 10px;" v-model="page.competitionId" filterable
                           placeholder="请选择竞赛">
                    <el-option
                            v-for="item in competition"
                            :key="item.competitionId"
                            :label="item.name"
                            :value="item.competitionId">
                    </el-option>
                </el-select>


                <el-select style="margin-left: 10px;" v-model="type" filterable placeholder="成绩">
                    <el-option
                            label="不合格"
                            value=0>
                    </el-option>
                    <el-option
                            label="合格"
                            value=1>
                    </el-option>
                    <el-option
                            label="中等"
                            value=2>
                    </el-option>
                    <el-option
                            label="良好"
                            value=3>
                    </el-option>
                    <el-option
                            label="优秀"
                            value=4>
                    </el-option>
                </el-select>
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
                    :data="content"
                    style="width: 80%;margin-left: 10%;margin-right: 10%">
                <el-table-column
                        prop="competition.name"
                        label="竞赛名称"
                        width="180">
                    <template slot-scope="{row}">
                        <router-link :to="'/competition/detailStu/'+row.competition.id">

                            <el-link type="primary">{{row.competition.name}}</el-link>

                        </router-link>
                    </template>
                </el-table-column>

                <el-table-column
                        prop="works.name"
                        label="作品名称"
                        width="180">
                    <template slot-scope="{row}">
                        <div v-if="row.works !==null">
                            <router-link :to="'/works/detail/'+row.works.id">
                                <el-link type="primary">{{row.works.name}}</el-link>
                            </router-link>
                        </div>


                    </template>
                </el-table-column>

                <el-table-column
                        sortable
                        prop="score"
                        label="分数">
                </el-table-column>
                <el-table-column
                        sortable
                        prop="gradesanking"
                        label="排名">
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
        </div>

    </div>

</template>

<script>
    import {getJson, postFrom} from "../../../api/api";
    import {mapGetters} from "vuex";

    export default {
        name: "myScore",
        computed: {
            /*https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Operators/Spread_syntax*/
            /*https://vuex.vuejs.org/zh/guide/getters.html*/
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                competition: null,
                content: [],
                loading: true,
                type: null,
                page: {
                    size: 20,
                    page: 0,
                    pages: 0,
                    value: null,
                    left: null,
                    right: null,
                    competitionId: null,
                    totalElements: 0,
                },
            }
        },
        created() {
            getJson('/public/competition/find')
                .then(response => {
                    if (response.data.status === 200) {
                        this.competition = response.data.competition;
                    }
                })
            this.getDataPage();
        },
        methods: {
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
            async getDataPage() {
                this.loading = true;
                await postFrom('/public/score/findMy/' + this.userId, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.content = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        } else {
                            this.loading = false;
                            this.$notify.error({
                                title: '错误',
                                message: '服务器异常' + response.data.message
                            })
                        }
                    }).catch(() => {
                        this.loading = false;
                        this.$notify.error({
                            title: "错误",
                            message: '服务器异常'
                        })
                    })
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;

                if (this.type === '0') {
                    this.page.left = 0;
                    this.page.right = 59;
                } else if (this.type === '1') {
                    this.page.left = 60;
                    this.page.right = 75;
                } else if (this.type === '2') {
                    this.page.left = 76;
                    this.page.right = 85;
                } else if (this.type === '3') {
                    this.page.left = 86;
                    this.page.right = 95;
                } else if (this.type === '4') {
                    this.page.left = 96;
                    this.page.right = 100;
                }
                this.getDataPage();


            },
            handleRefresh() {
                this.page.value = null;

                this.page.left = null;
                this.page.right = null;
                this.page.competitionId = null;
                this.loading = true;

                this.getDataPage();
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
