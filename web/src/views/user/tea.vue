<template>
    <div>
        <!--顶部搜索栏-->
        <sticky :z-index="10" class-name="sub-navbar">

            <el-select style="margin-left: 10px;" v-model="page.college" filterable placeholder="学院">
                <el-option
                        v-for="item in college"
                        :key="item.id"
                        :label="item.name"
                        :value="item.name">
                </el-option>
            </el-select>

            <el-tooltip class="item" effect="dark" content="请输入完整信息，暂不支持模糊查询" placement="top">
                <el-input v-model="page.name" placeholder="姓名/工号/身份证号" style="margin-left: 10px;width: 10%">
                </el-input>
            </el-tooltip>

            <el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter()">
                搜索
            </el-button>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                       @click="handleRefresh">
                重置搜索
            </el-button>
            <el-button :loading="loading2" class="filter-item" type="danger" style="margin-left: 10px;"
                       icon="el-icon-delete"
                       @click="deleteList()">
                批量删除
            </el-button>
        </sticky>

        <!--表格-->

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
                    label="工号"
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
                    <span v-if="special===0">{{row.college.name}}</span>
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
            >
            </el-table-column>

            <el-table-column
                    prop="phone"
                    label="手机号码"
            >
            </el-table-column>
            <el-table-column label="操作"
                             align="center"
                             width="350px"
                             class-name="small-padding fixed-width">
                <template slot-scope="{row}">

                    <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-coordinate"
                               @click="Endow(row)">
                        权限管理
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
        <el-dialog center title="权限赋予" :visible.sync="dialogTableVisible">

            <div style="text-align: center">
                <el-switch
                        v-model="role"
                        active-text="赋予管理员"
                        inactive-text="撤销管理员">
                </el-switch>
            </div>
            <span slot="footer" class="dialog-footer">

                <el-button style="margin-left: 10px" @click="dialogTableVisible = false">取 消</el-button>
                <el-button :loading="loading1" type="primary" @click="endow()">确 定</el-button>
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
    import BackToTop from "../../components/BackTop/index";

    export default {
        name: "stu",
        components: {BackToTop, Sticky},
        data() {
            return {
                role: false,
                roleList: [],
                detailInfo: [],
                dialogTableVisible: false,
                multipleSelection: [],
                loading: true,
                loading1: false,
                loading2: false,
                college: [],
                tableData: [],
                dataTemp: {},
                special: 0,
                page: {
                    size: 20,
                    page: 0,
                    college: null,
                    name: null,
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
            this.getDataPage();
        },
        methods: {
            async Endow(value) {
                this.dataTemp = value;
                await postJson('/admin/user/getUserInfo', value)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.roleList = response.data.data;
                            if (this.roleList.length === 2) {
                                this.role = true;
                            }
                            this.dialogTableVisible = true;
                        } else {
                            this.$message.error("该用户可能被删除了")
                        }
                    });

            },
            async endow() {
                this.loading1 = true;
                if (this.role === true) {
                    if (this.roleList.length === 1) {
                        await postJson('"/admin/user/endowRoot', this.dataTemp)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '赋予成功'
                                    });
                                    this.loading1 = false;
                                }
                            }).catch(error => {
                                this.$message.error("出现了一些问题" + error);
                            });
                        this.dialogTableVisible = false
                    }
                    this.dialogTableVisible = false
                } else {
                    await postJson('/admin/user/endowTea', this.dataTemp)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify.success({
                                    title: '撤销成功'
                                });
                                this.loading1 = false;
                            }
                        }).catch(error => {
                            this.$message.error("出现了一些问题" + error);
                        });
                    this.dialogTableVisible = false
                }
            },
            showDetail(value) {
                this.detailInfo = value;
                this.dialogTableVisible = true;
            },
            getDataPage() {
                this.loading = true;
                postJson('/admin/user/find/tea', this.page)
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
            resetPwd() {

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
        }
    }
</script>

<style scoped>
    .center {
        text-align: center;
        align-content: center;
    }
</style>
