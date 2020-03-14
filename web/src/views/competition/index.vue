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
                border
                @select="handleSelectionChange"
                v-loading="loading"
                style="width: 100%">

            <el-table-column
                    type="selection"
                    @selection-change="handleSelectionChange"
                    width="55">
            </el-table-column>

            <el-table-column
                    prop="id"
                    label="ID"
                    sortable
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="startTime"
                    label="日期"
                    sortable
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="name"
                    sortable
                    label="姓名"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="address"
                    label="地址">
            </el-table-column>

            <el-table-column label="Actions" align="center" width="300px" class-name="small-padding fixed-width">
                <template slot-scope="{row,$index}">
                    <el-button type="primary" size="mini" @click="handleUpdate(row)">
                        Edit
                    </el-button>
                    <el-button v-if="row.status!='published'" size="mini" type="success" @click="handleModifyStatus(row,'published')">
                        Publish
                    </el-button>
                    <el-button v-if="row.status!='draft'" size="mini" @click="handleModifyStatus(row,'draft')">
                        Draft
                    </el-button>
                    <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
                        Delete
                    </el-button>
                </template>
            </el-table-column>

        </el-table>
        <div class="center">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[20,50,100]"
                    :page-size="page.pageSize"
                    background
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="page.totalElements">
            </el-pagination>
    </div>
    </div>
</template>

<script>
    import {getData} from "../../api/api";

    export default {
        name: "index",
        data() {
            return {
                html: null,
                loading:true,
                tableData:[],
                page:{
                    pageSize:20,
                    pageNumber:0,
                    totalElements:null,
                    totalPages:null
                },
                multipleSelection: []
            }
        },
        created() {
            getData('/public/competition/findAll')
            .then(response =>{
                this.tableData = response.data.content;

                this.loading=false;
            })
        },
        mounted() {

        },
        methods: {
                next(){
                    console.log("wwwww")
                },
            currentPage(){

            },
            handleSizeChange(){

            },
            handleCurrentChange(){

            },
            handleSelectionChange(val){
                this.multipleSelection=val
            }
        }

    }
</script>

<style scoped>
.center{
    text-align: center;
    align-content: center;
}
</style>
