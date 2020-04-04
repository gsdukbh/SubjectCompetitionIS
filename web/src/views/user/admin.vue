<template>
    <div>
        <!--顶栏-->
        <sticky :z-index="10" class-name="sub-navbar">

            <el-select style="margin-left: 10px;" v-model="page.college" filterable placeholder="学院">
                <el-option
                        v-for="item in college"
                        :key="item.id"
                        :label="item.name"
                        :value="item.name">
                </el-option>
            </el-select>
            <el-select style="margin-left: 10px;" v-model="page.major" filterable placeholder="专业">
                <el-option
                        v-for="item in major"
                        :key="item.id"
                        :label="item.majorName"
                        :value="item.majorName">
                </el-option>
            </el-select>
            <el-select style="margin-left: 10px;" v-model="page.className" filterable placeholder="年级">
                <el-option
                        v-for="item in classList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.name">
                </el-option>
            </el-select>
            <el-input v-model="page.name" placeholder="姓名/学号/工号/身份证号"  style="margin-left: 10px;width: 10%">
            </el-input>

            <el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter()">
                搜索
            </el-button>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                       @click="handleRefresh">
                重置搜索
            </el-button>

            <el-tooltip content="请选择要导出的信息，默认当前页" placement="bottom">
                <el-button :loading="downloadLoading" style="margin-left: 10px;" class="filter-item" type="primary"
                           icon="el-icon-download"
                           @click="getExcel">
                    导出
                </el-button>

            </el-tooltip>
        </sticky>

        <!--内容-->

        <el-table
                :data="tableData"
                border
                @selection-change="handleSelectionChange"
                v-loading="loading"
                style="width: 100%;margin-top: 10px;">

            <el-table-column

                    type="selection"
                    width="55">
            </el-table-column>

            <el-table-column
                    prop="id"
                    label="ID"
                    sortable
                    width="60">
            </el-table-column>

            <el-table-column
                    prop="startTime"
                    label="开始日期"
                    sortable
                    width="150">

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
                <template slot-scope="{row,$index}">
                    <router-link :to="'/competition/detail/'+row.id">
                        <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-reading">
                            详情
                        </el-button>
                    </router-link>

                    <el-button style="margin-left: 10px;" v-if="row.status!=='deleted'" size="mini" type="danger"
                               @click="handleDelete(row.id,$index)">
                        删除
                    </el-button>
                </template>

            </el-table-column>

        </el-table>


        <!--分页-->
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

        <!--返回顶部-->
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>
    </div>
</template>

<script>
    import Sticky from "../../components/Sticky/index";
    import {getJson} from "../../api/api";

    export default {
        name: "admin",
        components: {Sticky},
        data() {
            return {
                multipleSelection: [],
                classList: [],
                college: [],
                tableData: [],
                major: [],
                downloadLoading: false,
                loading: false,
                page: {
                    size: 20,
                    page: 0,
                    college: null,
                    major: null,
                    name: null,
                    className: null,
                    totalElements: 100,
                },
            }
        },
        mounted() {
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                });
            getJson('/public/class/find/info')
                .then(response => {
                    this.classList = response.data.data;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                });
            getJson("/public/major/find")
                .then(response => {
                    this.major = response.data.data;

                }).catch(error => {
                this.$message.error("出现了一些问题" + error)
            })
        },
        methods: {
            getExcel() {

            },
            handleDelete() {

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
                this.page = {
                    size: 20,
                    page: 0,
                    college: null,
                    major: null,
                    name: null,
                    className: null,
                };
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
