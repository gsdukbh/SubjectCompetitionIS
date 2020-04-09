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

            <el-select style="margin-left: 10px;" v-model="page.className" filterable placeholder="班级">
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
            <el-tooltip class="item" effect="dark" content="删除选中的用户" placement="top">
                <el-button :loading="loading2" class="filter-item" type="danger" style="margin-left: 10px;"
                           icon="el-icon-delete"
                           @click="deleteList()">
                    批量删除
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
                             width="350px"
                             fixed="right"
                             class-name="small-padding fixed-width">
                <template slot-scope="{row}">

                    <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-reading"
                               @click="showDetail(row)">
                        详情
                    </el-button>

                    <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-refresh"
                               @click="resetPwd(row)">
                        重置密码
                    </el-button>

                    <el-button style="margin-left: 10px;" v-if="row.status!=='deleted'" size="mini" type="danger"
                               @click="handleDelete(row.id)">
                        删除
                    </el-button>

                </template>

            </el-table-column>
        </el-table>


        <!--对话框-->

        <el-dialog title="详情信息" :visible.sync="dialogTableVisible">
            <el-row>
                <el-col :span="8">
                    <span class="bold">
                        学 号：
                    </span>
                    <span>
                          {{detailInfo.login}}
                   </span>
                </el-col>
                <el-col :span="8">
                    <span class="bold">
                        姓 名：
                    </span>
                    <span>
                        {{detailInfo.name}}
                    </span>
                </el-col>
                <el-col :span="8">
                    <span class="bold">
                        院 系：
                    </span>
                    <span v-if="special===1 && dialogTableVisible===true">{{detailInfo.collegeName}}</span>
                    <span v-if="special===0  && dialogTableVisible===true">{{detailInfo.scisClass.major.college.name}}</span>

                </el-col>
            </el-row>

            <el-row style="margin-top: 10px">
                <el-col :span="8">
                    <span class="bold">
                       专 业：
                    </span>
                    <span v-if="special===1  && dialogTableVisible===true">{{detailInfo.majorName}}</span>
                    <span v-if="special===0 && dialogTableVisible===true">{{detailInfo.scisClass.major.name}} ({{detailInfo.scisClass.major.level}}) </span>
                </el-col>
                <el-col :span="8">
                    <span class="bold">
                       班 别：
                    </span>
                    <span v-if="special===1 && dialogTableVisible===true">{{detailInfo.className}}</span>
                    <span v-if="special===0 && dialogTableVisible===true">{{detailInfo.scisClass.name}}</span>
                </el-col>

                <el-col :span="8">
                    <span class="bold">
                        性别：
                    </span>
                    <span>{{detailInfo.sex}}</span>
                </el-col>
            </el-row>

            <el-row style="margin-top: 10px">
                <el-col :span="8">
                    <span class="bold">
                       身份证号码：
                    </span>
                    <span>
                       {{detailInfo.identity}}
                   </span>
                </el-col>
                <el-col :span="8">
                    <span class="bold">
                     邮 箱：
                    </span>
                    <span>{{detailInfo.email}}</span>
                </el-col>

                <el-col :span="8">
                    <span class="bold">
                        手机号码：
                    </span>
                    <span>{{detailInfo.phone}}</span>
                </el-col>

            </el-row>

            <el-row>
                <el-col :span="8">
                    <span class="bold">
                        参加的比赛：
                    </span>
                    <router-link to="/home">
                        <el-button type="primary" size="small">
                            更多
                        </el-button>
                    </router-link>
                </el-col>

                <el-col :span="8">
                    <span class="bold">
                        作 品：
                    </span>
                    <router-link to="/home">
                        <el-button type="primary" size="small">
                            更多
                        </el-button>
                    </router-link>
                </el-col>

                <el-col :span="8">
                    <span class="bold">
                        成 绩：
                    </span>
                    <router-link to="/home">
                        <el-button type="primary" size="small">
                            更多
                        </el-button>
                    </router-link>
                </el-col>
            </el-row>

            <el-dialog
                    width="40%"
                    title="修改用户信息"
                    :visible.sync="innerVisible"
                    append-to-body>
                <el-form ref="form" :model="editInfo"  label-width="80px">

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="学号:" >
                                <el-input v-model="editInfo.login" size="small"  disabled :value="detailInfo.login"> </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="姓名:" >
                                <el-input style="width: 100%" v-model="editInfo.name" size="small"  :value="detailInfo.name"> </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>


                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="学院:" >

                                <el-select style="width: 100%"  v-model="editInfo.college"  filterable placeholder="学院">

                                    <el-option

                                            v-for="item in college"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.name">
                                    </el-option>

                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="专业:" >

                                <el-select style="width: 100%" v-model="editInfo.major" filterable placeholder="专业">
                                    <el-option
                                            v-for="item in major"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.name">
                                    </el-option>
                                </el-select>

                            </el-form-item>
                        </el-col>
                    </el-row>



                </el-form>

            </el-dialog>

            <span slot="footer" class="dialog-footer">

                    <el-button @click="innerVisible=true">修改用户信息</el-button>

              <el-button style="margin-left: 10px" @click="dialogTableVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogTableVisible = false">确 定</el-button>
             </span>
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

                editInfo:{
                    login:null,
                    name:null,
                    college:null,
                    major:null,

                },
                innerVisible: false,
                dialogTableVisible: false,
                multipleSelection: [],
                classList: [],
                college: [],
                tableData: [],
                detailInfo: [],
                major: [],
                loading2: false,
                special: 0,
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
            resetPwd(value) {
                this.$confirm('此操作将密码重置为：Ab1234, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {

                    postJson('/admin/user/resetPwd/', value)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '成功',
                                    message: '密码重置成功'
                                })
                            }
                        })
                }).catch(() => {

                })
            },
            showDetail(value) {
                this.detailInfo = value;
                this.dialogTableVisible = true;

            },
            deleteList() {
                this.loading2 = true;
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/admin/user/deleteAll', this.multipleSelection)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '删除成功'
                                })
                            }
                            this.loading2 = false;
                            this.getDataPage();
                        })
                }).catch(() => {
                    this.$notify.info({
                        title: '取消删除'
                    });
                    this.loading2 = false;
                    this.getDataPage();
                });
            },
            handleDelete(value) {
                this.loading = true;
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/admin/user/delete/' + value)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '删除成功'
                                })
                            }
                            this.getDataPage();
                        })
                }).catch(() => {
                    this.$notify.info({
                        title: '取消删除'
                    });
                    this.getDataPage();
                })

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
                postJson('/admin/user/find/student', this.page)
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

    .bold {
        font-weight: bold
    }

    .center {
        text-align: center;
        align-content: center;
    }
</style>
