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
                        :label="item.name"
                        :value="item.name">
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


            <el-tooltip class="item" effect="dark" content="请输入完整信息，暂不支持模糊查询" placement="top">
                <el-input v-model="page.name" placeholder="姓名/学号/身份证号" style="margin-left: 10px;width: 10%">
                </el-input>
            </el-tooltip>

            <el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter()">
                搜索
            </el-button>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                       @click="handleRefresh">
                重置搜索
            </el-button>
            <el-button v-loading="loading2" class="filter-item" type="danger" style="margin-left: 10px;"
                       icon="el-icon-delete"
                       @click="deleteList()">
                批量删除
            </el-button>
        </sticky>

        <!--内容-->


        <el-table
                :data="tableData"
                border
                @selection-change="handleSelectionChange"
                v-loading="loading"
                style="width: 100%;margin-top: 10px;">
            <el-table-column
                    fixed="left"
                    type="selection"
                    width="55">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="login"
                    label="学号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名字"
                    sortable
                    width="150">

            </el-table-column>
            <el-table-column

                    label="院系"
                    width="180">
                <template slot-scope="{row}">
                    <span v-if="special===1">{{row.collegeName}}</span>
                    <span v-if="special===0">{{row.scisClass.major.college.name}}</span>
                </template>
            </el-table-column>

            <el-table-column
                    label="专业"
                    width="180">
                <template slot-scope="{row}">
                    <span v-if="special===1">{{row.majorName}}</span>
                    <span v-if="special===0">{{row.scisClass.major.name}}({{row.scisClass.major.level}})</span>
                </template>
            </el-table-column>

            <el-table-column
                    label="班级"
                    width="180">
                <template slot-scope="{row}">
                    <span v-if="special===1">{{row.className}}</span>
                    <span v-if="special===0">{{row.scisClass.name}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    prop="sex"
                    label="性别"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="identity"
                    label="身份证号码"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="phone"
                    label="手机号码"
                    width="180">
            </el-table-column>
            <el-table-column label="操作"
                             align="center"
                             width="200px"
                             fixed="right"
                             class-name="small-padding fixed-width">
                <template slot-scope="{row,$index}">

                    <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-reading">
                        详情
                    </el-button>


                    <el-button style="margin-left: 10px;" v-if="row.status!=='deleted'" size="mini" type="danger"
                               @click="handleDelete(row.id,$index)">
                        删除
                    </el-button>
                </template>

            </el-table-column>
        </el-table>


        <!--对话框-->

        <el-dialog title="收货地址" :visible.sync="dialogTableVisible">
            <el-table>
                <el-table-column property="date" label="日期" width="150"></el-table-column>
                <el-table-column property="name" label="姓名" width="200"></el-table-column>
                <el-table-column property="address" label="地址"></el-table-column>
            </el-table>
        </el-dialog>

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
    import {getJson, postJson} from "../../api/api";

    export default {
        name: "admin",
        components: {Sticky},
        data() {
            return {
                dialogTableVisible: false,
                multipleSelection: [],
                classList: [],
                college: [],
                tableData: [],
                tableData1: [],
                major: [],
                loading2: false,
                special: 0,
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
            });
            this.getDataPage();
        },
        methods: {

            deleteList() {
                this.loading2 = true;
                this.$confirm('此操作将永久删除竞赛, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/tea/user/deleteAll', this.multipleSelection)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '删除成功'
                                })
                            }
                            this.getDataPage();
                        })
                });
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
                console.log(val)
                this.multipleSelection = val
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
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
            },
            getDataPage() {
                this.loading = true;
                postJson('/tea/user/find/student', this.page)
                    .then(response => {
                        this.tableData = response.data.data;
                        this.page.totalElements = response.data.totalElements;
                        this.loading = false;
                        this.special = response.data.special;
                    })
                    .catch(error => {
                        this.$message.error("出现了一些问题" + error);
                        this.loading = false
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
