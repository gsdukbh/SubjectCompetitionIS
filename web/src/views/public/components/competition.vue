<template>
    <div>
        <div class="main">

            <div>
                <el-card class="top left " shadow="always">
                    <div style="margin-top: 10px;">

                        <el-input placeholder="名称" size="mini" style="width: 200px;margin-left: 10px;"
                                  v-model="page.name"/>

                        <el-select style="margin-left: 10px;" size="mini" v-model="page.organizer" filterable
                                   placeholder="请选择承办单位">
                            <el-option
                                    v-for="item in college"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.name">
                            </el-option>
                        </el-select>


                        <el-button round class="filter-item" size="mini" type="primary" style="margin-left: 10px;"
                                   icon="el-icon-search"
                                   @click="handleFilter">
                            搜索
                        </el-button>
                        <el-button round class="filter-item" size="mini" type="primary" style="margin-left: 10px;"
                                   icon="el-icon-refresh-left"
                                   @click="handleRefresh">
                            重置搜索
                        </el-button>
                    </div>

                    <div style="margin-top: 20px">
                        <el-card class="at" shadow="hover" v-for="(item,index) in tableData" :key="item.id">
                            <el-row>
                                <el-col :span="1">
                                    <span class="index">{{index+1}}</span>
                                </el-col>
                                <el-col :span="21">
                                    <router-link :to="'/public/competition/detail/'+item.id">
                                        <el-link type="primary">
                                            <p style="font-size: 20px;">{{item.name}}</p>
                                        </el-link>
                                    </router-link>
                                    <br>
                                    <br>
                                    <span style="color: #5a5e66;margin-left: 10px">
                                     报名时间<i class="el-icon-time"></i>   {{formatTimeA(item.applyTime)}}
                                     </span>



                                    <span style="color: #5a5e66;margin-left: 10px">
                                      结束时间<i class="el-icon-time"></i>   {{formatTimeA(item.endTime)}}
                                     </span>
                                    <span style="color: #5a5e66;margin-left: 10px">
                                              <svg-icon icon-class="college"></svg-icon>  {{item.organizer}}
                                    </span>


                                </el-col>
                            </el-row>
                        </el-card>
                    </div>


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
            </div>
            <el-tooltip placement="top" content="返回顶部">
                <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
            </el-tooltip>

        </div>
    </div>
</template>

<script>
    import {getData, getJson, postFrom} from "../../../api/api";

    import BackToTop from "../../../components/BackTop/index";
    import {Myformat} from "../../../utils";

    export default {
        name: "competition",
        components: {BackToTop},
        data() {
            return {
                competition: [],
                tableData: null,
                loading: true,
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
            rowClick(row, column, event) {
                console.log(row, column, event)
            },
            formatTimeA(time) {
                // return parseTime(time, '{y}-{m}-{d} {h}:{i}')
                return Myformat("yyyy年MM月dd日hh小时mm分", new Date(time))
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
    .main {
        padding-left: 10%;
        padding-right: 10%;
    }



    .top {
        margin-top: 20px;
    }

    .center {
        margin-top: 20px;
        text-align: center;
    }

    .left {
        margin-left: 20px;
    }

    .right {
        margin-right: 10px;
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

    .at {
        margin-top: 20px;
    }
</style>
