<template>
    <div>
        <el-row>
            <el-col :span="6">
                <div id="chart" class="main" :style="{height:height,width:width}"></div>
            </el-col>
            <el-col :span="18">
                <div id="chart1" class="main" style="height: 400px;width: 800px"></div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import eCharts from 'echarts'
    import {getJson} from "../../../api/api";

    export default {
        name: "analysis",
        props: {
            width: {
                type: String,
                default: '700px'
            },
            height: {
                type: String,
                default: '400px'
            }
        },
        data() {
            return {
                chart: null,
                chart1: null,
                scoreData: [],
                collegeData: null,
                collegeName: [],
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.initCharts()
                this.initCharts1()
            })
        },
        beforeDestroy() {
            if (!this.chart) {
                return
            }
            this.chart.dispose()
            this.chart = null
            if (!this.chart1) {
                return
            }
            this.chart1.dispose()
            this.chart1 = null
        },
        methods: {
            async getInfo(value) {
                this.chart.showLoading();
                this.chart1.showLoading();
                await getJson('/tea/score/getScore/analysis/' + value.id)
                    .then(response => {
                        this.collegeData = response.data.collegeData;
                        this.scoreData = response.data.scoreData;
                        this.collegeName = response.data.collegeName;
                    }).catch(() => {
                        this.$message.error('服务器异常')
                    })
                this.chart.setOption({
                    legend: {
                        data: ['三等奖', '二等奖', '一等奖']
                    },
                    series: {
                        data: this.scoreData
                    }
                })

                this.chart1.setOption({
                    legend: {
                        type: 'scroll',
                        orient: 'vertical',
                        right: 10,
                        top: 20,
                        bottom: 20,
                        data: this.collegeName
                    },
                    series: this.collegeData
                })
                this.chart1.hideLoading();
                this.chart.hideLoading();
            },
            initCharts1() {
                this.chart1 = eCharts.init(document.getElementById('chart1'));
                this.chart1.showLoading();
                this.chart1.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} )'
                    },
                    title: {
                        text: '学院成绩分布',
                        left: 'center'
                    },

                    angleAxis: {
                        type: 'category',
                        data: ['三等奖', '二等奖', '一等奖']
                    },
                    radiusAxis: {},
                    polar: {},
                    series: [],
                    legend: {
                        show: true,
                        data: []
                    }
                })
            },
            initCharts() {
                /*https://echarts.apache.org/zh/*/
                this.chart = eCharts.init(document.getElementById('chart'));
                this.chart.showLoading();
                this.chart.setOption({
                    title: {
                        text: '成绩分布',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        left: 'center',
                        bottom: '10',
                        data: ['三等奖', '二等奖', '一等奖']
                    },
                    series: [
                        {
                            name: '成绩分布',
                            type: 'pie',
                            roseType: 'radius',
                            radius: [15, 95],
                            center: ['50%', '40%'],
                            data: this.scoreData,
                            animationEasing: 'cubicInOut',
                            animationDuration: 2600
                        }
                    ]
                })
            }
        }
    }
</script>

<style scoped>
    .main {
        text-align: center;
        margin-top: 20px;
    }


</style>
