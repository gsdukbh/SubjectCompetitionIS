<template>
    <div>
        <!--搜索-->
        <div>

            <el-input placeholder="Title" style="width: 200px;"/>

            <el-select v-model="value" filterable placeholder="请选择">
                <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>

            <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
                Search
            </el-button>
            <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit"
                       @click="handleCreate">
                Add
            </el-button>
            <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download"
                       @click="handleDownload">
                Export
            </el-button>
        </div>

        <!--表格-->

        <el-table
                :data="tableData"
                height="auto"
                border
                style="width: 100%">

            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>

            <el-table-column
                    prop="date"
                    label="日期"
                    sortable
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="name"
                    label="姓名"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="address"
                    label="地址">
            </el-table-column>

        </el-table>

    </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                html: null,
            }
        },
        mounted() {

        },
        methods: {}

    }
</script>

<style scoped>

</style>
