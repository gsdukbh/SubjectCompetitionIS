<template>
    <div class="main">

        <sticky :z-index="10" class-name="sub-navbar">

            <router-link :to="'/competition/edit/'+showData.id">
                <el-button style="margin-left: 10px;" type="success">
                    修改
                </el-button>
            </router-link>

        </sticky>

        <!--标题-->
        <div class="title">
            <span>{{showData.name}}</span>
        </div>
        <el-tabs type="card" class="" value="1" @tab-click="handleClick">
            <el-tab-pane lazy label="竞赛详情" name="1">

                <div class="el-row">

                    <div class="leftColumn">

                        <el-card class="box-card top left" shadow="hover">
                            <h1>内容详情
                                <svg-icon icon-class="content"></svg-icon>
                                <el-tag style="margin-left: 10px">{{showData.level}}</el-tag>
                                <el-tag style="margin-left: 10px" v-if="showData.type === 'online'">线上比赛</el-tag>

                            </h1>

                            <el-divider content-position="right">昨夜星辰昨夜风</el-divider>
                            <!--markdown-->

                            <el-row v-loading="loading">

                                <markdown-viewer v-bind:content="showData.content"/>

                            </el-row>

                        </el-card>

                    </div>


                    <div class="rightColumn">

                        <el-card class="box-card top right" shadow="hover">
                            <h3>承办单位
                                <svg-icon icon-class="college"></svg-icon>
                            </h3>
                            <el-divider content-position="right">画楼西畔桂堂东</el-divider>

                            <router-link to="#">

                                <el-tooltip class="item" effect="dark" :content="'查看更多'+showData.organizer+'举办的竞赛'"
                                            placement="bottom">
                                    <span><el-link type="primary">{{showData.organizer}}</el-link></span>
                                </el-tooltip>

                            </router-link>

                        </el-card>

                        <el-card class="box-card top right" shadow="hover" v-if="showData.place !==''">
                            <h3>举办地点</h3>
                            <el-divider content-position="right">身无彩凤双飞翼</el-divider>
                            <span><el-link type="primary">比赛地点 {{showData.place}}</el-link></span>
                        </el-card>

                        <el-card class="box-card top right" shadow="hover">

                            <h3>比赛进程
                                <svg-icon icon-class="match"></svg-icon>

                            </h3>

                            <el-divider content-position="right">隔座送钩春酒暖</el-divider>
                            <div class="block">

                                <el-timeline>

                                    <el-timeline-item :timestamp="formatTimeA(showData.applyTime)" placement="top">
                                        <el-card>
                                            <h4>开始报名</h4>
                                            <p>参赛者可以进行报名 时间：{{formatTimeA(showData.applyTime)}}</p>
                                        </el-card>
                                    </el-timeline-item>

                                    <el-timeline-item :timestamp="formatTimeA(showData.startTime)" placement="top">
                                        <el-card>
                                            <h4>比赛开始</h4>
                                            <p>比赛开始于： {{formatTimeA(showData.startTime)}} </p>
                                        </el-card>
                                    </el-timeline-item>
                                    <el-timeline-item :timestamp="formatTimeA(showData.endTime)" placement="top">
                                        <el-card>
                                            <h4>比赛结束</h4>
                                            <p>比赛于：{{formatTimeA(showData.endTime)}} 结束</p>
                                        </el-card>

                                    </el-timeline-item>

                                </el-timeline>
                            </div>
                        </el-card>


                    </div>

                </div>

            </el-tab-pane>


            <el-tab-pane label="问题反馈" lazy name="2">

                <div class="content">

                    <el-card shadow="hover" class="box-card  left right2">

                        <el-table
                                v-loading="loading1"
                                :data="tableDataProblem "
                                style="width: 100%">
                            <el-table-column type="expand">
                                <template slot-scope="props">
                                    <el-form label-position="left" inline class="demo-table-expand">
                                        <el-form-item label="商品名称">
                                            <span>{{ props.row.name }}</span>
                                        </el-form-item>
                                        <el-form-item label="所属店铺">
                                            <span>{{ props.row.shop }}</span>
                                        </el-form-item>
                                        <el-form-item label="商品 ID">
                                            <span>{{ props.row.id }}</span>
                                        </el-form-item>
                                        <el-form-item label="店铺 ID">
                                            <span>{{ props.row.shopId }}</span>
                                        </el-form-item>
                                        <el-form-item label="商品分类">
                                            <span>{{ props.row.category }}</span>
                                        </el-form-item>
                                        <el-form-item label="店铺地址">
                                            <span>{{ props.row.address }}</span>
                                        </el-form-item>
                                        <el-form-item label="商品描述">
                                            <span>{{ props.row.desc }}</span>
                                        </el-form-item>
                                    </el-form>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="商品 ID"
                                    prop="id">
                            </el-table-column>
                            <el-table-column
                                    label="商品名称"
                                    prop="name">
                            </el-table-column>
                            <el-table-column
                                    label="描述"
                                    prop="desc">
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
                    </el-card>

                </div>

            </el-tab-pane>

            <el-tab-pane label="报名详情" lazy name="3">
                配置管理
            </el-tab-pane>

        </el-tabs>


        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>
    </div>
</template>

<script>

    import Sticky from '@/components/Sticky'
    import {getJson} from "../../api/api";
    import {parseTime} from '../../utils/index'
    import MarkdownViewer from "../../components/MarkdownViewer/index";
    import BackToTop from "../../components/BackTop/index";


    export default {
        name: "detail",
        components: {BackToTop, MarkdownViewer, Sticky},
        data() {
            return {
                id: '',
                showData: {},
                loading: false,
                loading1: true,
                tempRoute: {},
                markdown: null,
                poem: null,
                tableDataProblem: null,
                page: {
                    size: 20,
                    page: 0,
                    organizer: null,
                    name: null,
                    totalElements: 100,
                },
            }
        },
        created() {

            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);

        },
        mounted() {

        },
        methods: {
            async fetchData(id) {
                getJson('/public/competition/findById/' + id)
                    .then(response => {
                        this.showData = response.data.data;
                        this.setTagsViewTitle();
                        this.setPageTitle();
                    })
                    .catch(() => {
                    });
            },
            setTagsViewTitle() {
                const title = this.showData.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title}-详情`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.showData.name;
                document.title = `${title} - 修改`
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
            handleClick(tab) {
                console.log(tab.index)
            },
            loadingProblem(id) {
                getJson("/public/problem/find/competition/" + id)
                    .then(response => {
                        if (response.data.status === 200) {

                        }
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
                this.getDataPage()
            },

            handleSelectionChange(val) {
                this.multipleSelection = val
            },
        }
    }

</script>

<style scoped>

    .main {
        height: auto;
        background-color: #ffffff; /* 浏览器不支持时显示 */
        background-image: linear-gradient(#ffffff, #b6fffb);
    }

    .top {
        margin-top: 20px;
    }

    .left {
        margin-left: 20px;
    }

    .right {
        margin-right: 10px;
    }

    .title {
        margin-top: 20px;
        font-size: 50px;
        text-align: center;
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

    #h1 {

    }

    .content {
        float: left;
        width: 100%;
    }

    .right2 {
        margin-right: 20px;
    }
</style>
