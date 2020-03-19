<template>
    <div class="main">

        <sticky :z-index="10" class-name="sub-navbar">
            <el-button style="margin-left: 10px;" type="success">
                publish
            </el-button>
        </sticky>
        <!--标题-->
        <div class="title">
            <span>{{showData.name}}</span>
        </div>
        <div class="el-row">

            <div class="leftColumn">

                <el-card class="box-card top left">
                    左边详细内容
                </el-card>

            </div>
            <div class="rightColumn">

                <el-card class="box-card top right">
                    <el-collapse>
                        <el-collapse-item title="比赛进程" name="1">
                            <div class="block">

                                <el-timeline>

                                    <el-timeline-item :timestamp="formatTimeA(showData.applyTime)" placement="top">
                                        <el-card>
                                            <h4>开始报名</h4>
                                            <p>参赛者可以进行报名</p>
                                        </el-card>
                                    </el-timeline-item>

                                    <el-timeline-item :timestamp="formatTimeA(showData.startTime)" placement="top">
                                        <el-card>
                                            <h4>比赛开始</h4>
                                            <p>王小虎 提交于 2018/4/3 20:46</p>
                                        </el-card>
                                    </el-timeline-item>
                                    <el-timeline-item timestamp="2018/4/2" placement="top">
                                        <el-card>
                                            <h4>更新 Github 模板</h4>
                                            <p>王小虎 提交于 2018/4/2 20:46</p>
                                        </el-card>
                                    </el-timeline-item>

                                </el-timeline>
                            </div>
                        </el-collapse-item>
                        <el-collapse-item title="反馈 Feedback" name="2">
                            <div>控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；</div>
                            <div>页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。</div>
                        </el-collapse-item>
                        <el-collapse-item title="效率 Efficiency" name="3">
                            <div>简化流程：设计简洁直观的操作流程；</div>
                            <div>清晰明确：语言表达清晰且表意明确，让用户快速理解进而作出决策；</div>
                            <div>帮助用户识别：界面简单直白，让用户快速识别而非回忆，减少用户记忆负担。</div>
                        </el-collapse-item>
                        <el-collapse-item title="可控 Controllability" name="4">
                            <div>用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；</div>
                            <div>结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。</div>
                        </el-collapse-item>
                    </el-collapse>
                </el-card>



                <el-card class="box-card top right">
                    报名信息
                </el-card>

                <el-card class="box-card top right">
                    问题
                </el-card>

            </div>
        </div>


    </div>
</template>

<script>

    import Sticky from '@/components/Sticky'
    import {getJson} from "../../api/api";
    import {parseTime} from '../../utils/index'
    export default {
        name: "detail",
        components: {Sticky},
        data() {
            return {
                id: '',
                showData: {},
                loading: false,
                tempRoute: {},

            }
        },
        created() {

            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);
        },
        methods: {
            async fetchData(id) {
                getJson('/public/competition/findById/' + id)
                    .then(response => {
                        this.showData = response.data;
                        this.setTagsViewTitle();
                        this.setPageTitle();
                    })
                    .catch(() => {
                    });
            },
            setTagsViewTitle() {
                const title = this.showData.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title}-${this.id}`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.showData.name;
                document.title = `${title} - ${this.id}`
            },
            formatTimeA(time){
                return parseTime(time,'{y}-{m}-{d} {h}:{i}')
            },
        }

    }
</script>

<style scoped>

.main{
    background-color: #83c9ba;
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
</style>
