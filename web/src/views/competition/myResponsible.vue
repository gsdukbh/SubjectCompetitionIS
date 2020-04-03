<template>
    <div>
        <!--顶栏-->
        <sticky :z-index="10" class-name="sub-navbar">

            <el-autocomplete
                    style="margin-left: 10px"
                    v-model="page.title"
                    :fetch-suggestions="querySearchAsyncTitle"
                    placeholder="请输入标题"
            ></el-autocomplete>

            <el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter()">
                搜索
            </el-button>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                       @click="handleRefresh">
                重置搜索
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
                <template slot-scope="{row}">
                    <span>{{formatTimeA(row.startTime) }}</span>
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
                <template slot-scope="{row,$index}">
                    <router-link :to="'/competition/edit/'+row.id">

                        <el-button type="primary" size="mini" icon="el-icon-edit">
                            编辑
                        </el-button>

                    </router-link>

                    <router-link :to="'/competition/detail/'+row.id">
                        <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-reading">
                            详情
                        </el-button>
                    </router-link>

                    <el-button :loading="loading1" style="margin-left: 10px;" v-if="row.status ==='草稿'" size="mini"
                               type="success"
                               @click="handleModifyStatus(row.id,'公开')">
                        发布
                    </el-button>

                    <el-button :loading="loading1" style="margin-left: 10px;" v-if="row.status==='公开'" size="mini"
                               @click="handleModifyStatus(row.id,'草稿')">
                        草稿
                    </el-button>

                    <el-button disabled style="margin-left: 10px;" v-if="row.status==='进行中'" size="mini">
                        进行中
                    </el-button>

                    <el-button disabled style="margin-left: 10px;" v-if="row.status==='已结束'" size="mini">
                        已结束
                    </el-button>

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
    import BackToTop from "../../components/BackTop/index";
    import {mapGetters} from "vuex";

    export default {
        name: "myResponsible",
        components: {BackToTop, Sticky},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                college: null,
                temTitle: '',
                tableData: null,
                page: {
                    size: 20,
                    page: 0,
                    title: null,
                    totalElements: 100,
                },
            }
        },
        created() {


        },
        methods: {
            querySearchAsyncTitle(queryString, cb) {
                getJson('/public/announcement/findTitle')
                    .then(response => {
                        this.temTitle = response.data.data;
                    });
                const temTitle = this.temTitle;
                const res = queryString ? temTitle.filter(this.createTitleFilter(queryString)) : temTitle;

                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(res);
                }, 3000 * Math.random());
            },
            createTitleFilter(queryString) {
                return (temTitle) => {
                    return (temTitle.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
                };
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
