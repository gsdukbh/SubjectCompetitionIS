<template>
    <div>

        <el-table
                v-if="competition.team"
                ref="table"
                border stripe
                highlight-current-row
                :data="teamData"
                v-loading="loading1"
                style="width: 100%;margin-top: 10px">
            <el-table-column label="队伍号码" prop="id" width="100">
            </el-table-column>
            <el-table-column label="队伍名称" prop="name">
            </el-table-column>
            <el-table-column label="申请时间" prop="applyTime">
                <template slot-scope="{row}">
                    <span>{{formatTimeA(row.applyTime)}}</span>
                </template>
            </el-table-column>
            <el-table-column label="人数" prop="number">
                <template slot-scope="{row}">
                    <span>{{row.scisUserList.length}} / {{row.number}}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template slot-scope="scope">
                    <el-button type="text" @click="toogleExpand(scope.row)">查看成员</el-button>
                </template>
            </el-table-column>
            <el-table-column type="expand" width="1">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <div v-for="item in props.row.scisUserList" :key="item.id">
                            <el-row>
                                <el-col :span="6">
                                    <el-form-item label="学号：">
                                        <span>{{item.login}}</span>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-form-item label="名字：">
                                        <span>{{item.name}}</span>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-form-item label="班级">
                                        <span>{{item.scisClass.name}}</span>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-form-item label="学院">
                                        <span>{{item.scisClass.major.college.name}}</span>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </el-form>
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
    export default {
        name: "teamFrom",
        props: {
            competitionId: null
        },
        data() {
            return {
                teamData: [],
                competition: [],
                loading: true,
                page: {
                    size: 20,
                    page: 0,
                    name: '',
                    totalElements: 100,
                },
            }
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
                this.page.title = null;
                this.page.from = null;
                this.page.type = null;
                this.loading = true;
                this.getDataPage();

            },
            getDataPage() {
            },
        }
    }
</script>

<style scoped>

</style>
