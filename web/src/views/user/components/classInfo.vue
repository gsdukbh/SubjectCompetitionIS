<template>
    <div class="top content">
        <el-tabs type="card" @tab-click="handleClick" value="1">
            <el-tab-pane label="创建班级" name="1">

                <el-form :model="classInfo"
                         class="elForm"
                         ref="classInfo"
                         status-icon label-position="top"
                         :rules="rules">
                    <el-form-item label="名称" prop="name">
                        <el-input v-model="classInfo.name"></el-input>
                    </el-form-item>
                    <el-form-item label="所属专业" prop="major">
                        <el-select v-model="classInfo.major.id" filterable placeholder="请选择"
                                   style="width: 100%">
                            <el-option
                                    v-for="item in major"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-button style="width: 20%" type="primary"
                               :loading="buttonLoading"
                               @click="upClass('classInfo')">
                        提交
                    </el-button>

                    <el-button style="width: 20%" @click="resetForm('classInfo')">
                        重置
                    </el-button>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="班级管理" name="2">
                <div style="margin-top: 10px;">

                    <el-input placeholder="名称" style="width: 200px;" v-model="page.name"/>

                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-search"
                               @click="handleFilter">
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
                </div>


                <el-table
                        :data="classes"
                        @selection-change="handleSelectionChange"
                        v-loading="loading"
                        style="width: 100%;margin-top: 10px;">

                    <el-table-column
                            type="selection"
                            width="55">
                    </el-table-column>

                    <!--                    <el-table-column-->
                    <!--                            prop="id"-->
                    <!--                            label="ID"-->
                    <!--                            width="100"-->
                    <!--                            sortable-->
                    <!--                    >-->
                    <!--                    </el-table-column>-->

                    <el-table-column
                            prop="name"
                            sortable
                            label="班级名称"
                    >
                    </el-table-column>

                    <el-table-column
                            prop="major.name"
                            sortable
                            label="所属专业"
                    >
                    </el-table-column>

                    <el-table-column
                            prop="major.college.name"
                            sortable
                            label="所属学院"
                    >
                    </el-table-column>

                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="{row}">

                            <el-button type="primary"
                                       size="mini"
                                       @click="edit(row)"
                                       icon="el-icon-edit">
                                修改
                            </el-button>

                            <el-button style="margin-left: 10px;"
                                       size="mini"
                                       type="danger"
                                       :loading="buttonLoading"
                                       @click="handleDelete(row)">
                                删除
                            </el-button>
                        </template>

                    </el-table-column>

                </el-table>


                <el-dialog
                        title="提示"
                        :visible.sync="dialogVisible"
                        width="30%"
                >
                    <el-form :model="classUp"
                             :rules="rules"
                             status-icon
                             label-position="top"
                             ref="classUp">

                        <el-form-item label="班级名称" prop="name">
                            <el-input v-model="classUp.name"></el-input>
                        </el-form-item>
                        <el-form-item label="所属专业" prop="id">
                            <el-select v-model="classUp.major.id" filterable placeholder="请选择"
                                       style="width: 100%">
                                <el-option
                                        v-for="item in major"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>


                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="updata()" :loading="buttonLoading">确 定</el-button>
                    </div>
                </el-dialog>

                <div class="center">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            @next-click="handleCurrentChange"
                            @prev-click="handleCurrentChange"
                            :current-page="page.pages"
                            :page-sizes="[5,10,20,50,100]"
                            :page-size="page.size"
                            background
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="page.totalElements">
                    </el-pagination>

                </div>

                <el-tooltip placement="top" content="返回顶部">
                    <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
                </el-tooltip>

            </el-tab-pane>
        </el-tabs>

    </div>
</template>

<script>
    import {getJson, postFrom, postJson} from "../../../api/api";
    import BackToTop from "../../../components/BackTop/index";

    export default {
        name: "classInfo",
        components: {BackToTop},
        data() {
            let validatePass = (rule, value, callback) => {
                const info = {
                    name: value
                };
                postFrom('/admin/class/repeat', info)
                    .then(response => {
                        let result = response.data.data === 'true';
                        if (result) {
                            callback(new Error('重复的班级名称'));
                        } else {
                            callback()
                        }
                    });
            };
            return {
                buttonLoading: false,
                dialogVisible: false,
                classes: [],
                loading: true,
                loading2: false,
                multipleSelection: [],
                classUp: {
                    id: '',
                    name: '',
                    major: {
                        id: '',
                    }
                },
                page: {
                    size: 20,
                    page: 0,
                    pages: 0,
                    name: null,
                    totalElements: 0,
                },
                classInfo: {
                    name: '',
                    major: {
                        id: '',
                    }
                },
                major: [],
                rules: {
                    name: [
                        {required: true, message: '请输入', trigger: 'change'},
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    id: [
                        {required: true, message: '请选择对应专业', trigger: 'change'}
                    ]
                }
            }
        },
        created() {
            getJson("/public/major/find")
                .then(response => {
                    this.major = response.data.data;

                }).catch(error => {
                this.$message.error("出现了一些问题" + error)
            });
        },
        methods: {
            updata() {
                this.buttonLoading = true;
                postJson('/admin/class/save', this.classUp)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$notify.success({
                                title: '成功',
                                message: '修改成功'
                            })
                            this.buttonLoading = false;
                            this.getDataPage();
                        }
                    }).catch(error => {
                    this.buttonLoading = false;
                    this.getDataPage();
                    this.$message.error('出现一些错误' + error)
                })
            },
            edit(value) {
                this.classUp = value;
                this.dialogVisible = true;
            },
            handleClick(tab) {

                if (tab.index === '1') {
                    this.getDataPage();
                }
            },
            handleSizeChange(val) {
                this.page.size = val;
                this.loading = true;
                this.getDataPage();
            },
            /*当前页数*/
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.page = val - 1;
                this.getDataPage()
            },

            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
            },
            handleRefresh() {
                this.page.name = null;
                this.getDataPage();
            },
            deleteList() {
                this.loading2 = true;
                postJson('/admin/class/delAll', this.multipleSelection)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.loading2 = false;
                            this.$notify.success({
                                title: '成功',
                                message: '删除成功'
                            })
                            this.getDataPage();
                        } else {
                            this.loading2 = false;
                            this.$notify.error({
                                title: '警告',
                                message: response.data.message
                            })
                        }
                    }).catch(error => {
                    this.loading2 = false;
                    this.$message.error('出现了一些错误' + error);
                })
            },
            handleDelete(value) {
                postJson('/admin/class/del', value)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$notify.success({
                                title: '成功',
                                message: '删除成功'
                            });
                            this.getDataPage();
                        } else {
                            this.$notify.error({
                                title: '警告',
                                message: response.data.message
                            })
                        }
                    })
            },
            getDataPage() {
                this.loading = true;
                postFrom('/admin/class/find', this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.classes = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        }

                    }).catch(error => {
                    this.loading = false;
                    this.$message.error('出现了一些问题：' + error);
                })

            },
            upClass(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/admin/class/save', this.classInfo)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '提交成功'
                                    })
                                } else if (response.data.status === 0) {
                                    this.$notify.warning({
                                        title: '警告',
                                        message: response.data.message,
                                    })
                                }
                                this.buttonLoading = false;
                            })
                    }
                    this.buttonLoading = false;
                });

            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
    .content {
        align-content: center;
        text-align: center;
        width: 80%;
        margin-left: 10%;
        margin-right: 10%;
    }

    .center {
        align-content: center;
        text-align: center;
        margin-top: 20px;
    }

    .elForm {
        text-align: center;
        width: 50%;
        margin-right: 25%;
        margin-left: 25%;
    }
</style>
