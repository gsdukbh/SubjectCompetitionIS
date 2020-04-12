<template>
    <div>
        <!--搜索-->

        <div style="margin-top: 10px;">

            <el-input placeholder="名称" style="width: 200px;margin-left: 10px;" v-model="page.name"/>

            <el-select style="margin-left: 10px;" v-model="page.organizer" filterable placeholder="请选择承办单位">
                <el-option
                        v-for="item in college"
                        :key="item.id"
                        :label="item.name"
                        :value="item.name">
                </el-option>
            </el-select>

            <el-select style="margin-left: 10px;" v-model="page.level" filterable placeholder="请选择竞赛级别">
                <el-option label="" value=""></el-option>
                <el-option label="校级" value="校级"></el-option>
                <el-option label="院级" value="院级"></el-option>
                <el-option label="市级" value="市级"></el-option>
                <el-option label="区级/省级" value="区级/省级"></el-option>
                <el-option label="国级" value="国级"></el-option>
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
                :data="tableData"
                border
                v-loading="loading"
                style="width: 100%;margin-top: 10px;">
            <el-table-column
                    prop="startTime"
                    label="开始日期"
                    sortable
                    width="150">
                <template slot-scope="{row}">
                    <span>{{formatTimeA(row.startTime) }}</span>
                </template>
            </el-table-column>

            <el-table-column
                    prop="applyTime"
                    label="报名时间"
                    sortable
                    width="150">
                <template slot-scope="{row}">
                    <span>{{formatTimeA(row.applyTime) }}</span>
                </template>
            </el-table-column>

            <el-table-column
                    prop="name"
                    sortable
                    label="名称"
                    width="180">
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

            <el-table-column

                    label="赛事级别"
                    width="100"
            >
                <template slot-scope="{row}">
                    <el-tag
                            :type="row.level === '校级' ? 'primary' : 'success'"
                            disable-transitions>{{row.level}}
                    </el-tag>
                </template>

            </el-table-column>

            <el-table-column label="操作" align="center" width="350px" class-name="small-padding fixed-width">
                <template slot-scope="{row}">

                    <router-link :to="'/competition/detailStu/'+row.id">
                        <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-reading">
                            详情
                        </el-button>
                    </router-link>

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


    </div>
</template>

<script>
    import {getData, getJson, postFrom} from "../../api/api";
    import {parseTime} from '../../utils/index'

    export default {
        name: "studentCompetition",
        data() {
            return {
                loading: true,
                loading1: false,
                loading2: false,
                tableData: [
                    {
                        id: null,
                        name: null,
                        status: null,
                        startTime: null,
                        endTime: null,
                        applyTime: null,
                        content: null,
                        author: null,
                        level: null,
                        organizer: null,
                        place: null,
                        numLimit: null,
                        type: null,
                        team: null
                    }
                ],
                college: null,
                page: {
                    size: 20,
                    page: 0,
                    organizer: null,
                    name: null,
                    level: null,
                    totalElements: 100,
                },
            }
        },
        created() {
            getData('/public/competition/findAll/s')
                .then(response => {
                    this.tableData = response.data.content;
                    this.page.totalElements = response.data.totalElements;
                    this.loading = false;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                });
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })
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
                this.getDataPage()
            },


            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
            },
            handleRefresh() {
                this.page.level = null;
                this.page.organizer = null;
                this.page.name = null;
                this.getDataPage();
            },
            getDataPage() {
                this.loading = true;
                postFrom('/public/competition/findAll/s', this.page)
                    .then(response => {
                        this.tableData = response.data.content;
                        this.page.totalElements = response.data.totalElements;
                        this.loading = false;
                    }).catch(error => {
                    this.loading = false;
                    this.$message.error("出现了一些问题" + error)
                })
            }

        }
    }
</script>

<style scoped>
    .center {
        text-align: center;
        align-content: center;
    }
</style>
