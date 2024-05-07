<template>
    <div>  
        <el-table
        class="table"
        :data="this.vo"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        max-height="700px">
  
        <el-table-column
          prop="typeId"
          label="缴费类型"
          width="410">
          <template slot-scope="scope">  
            <span v-if="scope.row.typeId === 1">电费</span>  
            <span v-else-if="scope.row.typeId === 2">水费</span>
            <span v-else-if="scope.row.typeId === 3">临时停车费</span> 
            <span v-else>租借车位费</span>
            
          </template> 
        </el-table-column>
  
        <el-table-column
          prop="amount"
          label="费用"
          width="403">
        </el-table-column>
      
        <el-table-column
          prop="status"
          label="状态"
          width="410">
          <template slot-scope="scope">  
            <span v-if="scope.row.status === 0">未缴费</span>  
            <span v-else>已缴费</span>  
          </template> 
        </el-table-column>
    
        <el-table-column
          label="操作"
          width="420">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="buy(scope.$index, vo)"
              type="text"
              size="small">
              缴费
            </el-button>
            <el-button
              @click.native.prevent="details(scope.$index, vo)"
              @click="dialogVisible = true"
              type="text"
              size="small">
              详情
            </el-button>
            <el-dialog
              title="详情"
              :visible.sync="dialogVisible"
              width="30%"
              :before-close="handleClose">   
              <!-- <h1 v-if="houseDetails.building != 0">楼栋号:{{houseDetails.building}}</h1>
              <h1 v-if="houseDetails.cell != 0">单元号:{{houseDetails.cell}}</h1>
              <h1 v-if="houseDetails.floor != 0">楼层号:{{houseDetails.floor}}</h1>
              <h1 v-if="houseDetails.doorplate != ''">门牌号:{{houseDetails.doorplate}}</h1>
              <h1 v-if="stopDetails.place != ''">车位信息:{{stopDetails.place}}</h1> -->
           

              <el-descriptions :column="1" border>   
          <el-descriptions-item v-if="houseDetails.building != 0">
            <template slot="label">
              <i class="el-icon-discount"></i>
              楼栋号
            </template>
            {{ houseDetails.building }}栋
          </el-descriptions-item>
               
          <el-descriptions-item v-if="houseDetails.cell != 0">
            <template slot="label">
              <i class="el-icon-receiving"></i>
              单元号
            </template>
            {{ houseDetails.cell }}单元
          </el-descriptions-item>

          <el-descriptions-item v-if="houseDetails.floor != 0">
            <template slot="label">
              <i class="el-icon-coin"></i>
              楼层
            </template>
            {{ houseDetails.floor }}楼
          </el-descriptions-item>

          <el-descriptions-item v-if="houseDetails.doorplate != 0">
            <template slot="label">
              <i class="el-icon-postcard"></i>
              门牌号
            </template>
            {{ houseDetails.doorplate }}
          </el-descriptions-item>

          <el-descriptions-item v-if="stopDetails.place != ''">
            <template slot="label">
              <i class="el-icon-postcard"></i>
              车位信息
            </template>
            {{ stopDetails.place }}
          </el-descriptions-item>
        </el-descriptions>

              <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
              </span>
            </el-dialog>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="[5, 10, 15, 20]"
          :page-size=this.pageSize
          :current-page=this.page
          layout="total, sizes, prev, pager, next"
          :total= this.total>
        </el-pagination>
      
    </div>
    
    </template>
    
    <script>
    import axios from 'axios'
      export default {
    
        created(){
          this.load();
        },
    
        methods: {
          freshHouse(){
            this.houseDetails.building = 0;
            this.houseDetails.cell = 0;
            this.houseDetails.floor = 0;
            this.houseDetails.doorplate = '';
          },
          freshStop(){
            this.stopDetails.place = '';
          },
          buy(index, rows){
            if(rows[index].status === 1){
                this.$message("已缴费，无需缴费")
                return
            }else{
                let id = rows[index].id;
                axios({
                    url: `/api/user/fee/${id}`,
                    method: 'post',
                    headers: {
                        token: this.$store.state.user.token
                    }
                    }).then(res => { // 成功回调函数
                    if(res.data.code == 1){ // 成功
                        this.$message("缴费成功")
                        this.load();
                    }else{
                        console.log(res.data.msg)
                    }
                })
            }

          },
          details(index, rows) {
            console.log("查看详情:", index, rows)
            if(rows[index].houseId === 0){ // 是车位费
              this.freshHouse();
              axios({
                url: '/api/user/fee/details/stop',
                method: 'get',
                params: {
                  stopId: rows[index].stopId
                },
                headers: {
                  token: this.$store.state.user.token
                }
                }).then(res => { // 成功回调函数再发请求
                  if(res.data.code == 1){ // 成功
                    this.stopDetails = res.data.data;
                  }else{
                    console.log(res.data.msg)
                  }
              })
            }else{ // 是物业费
              this.freshStop();
              axios({
                url: '/api/user/fee/details/house',
                method: 'get',
                params: {
                  houseId: rows[index].houseId
                },
                headers: {
                  token: this.$store.state.user.token
                }
                }).then(res => { // 成功回调函数再发请求
                  if(res.data.code == 1){ // 成功
                    this.houseDetails = res.data.data;
                  }else{
                    console.log(res.data.msg)
                  }
              })
            }
          },
    
          load(){
            this.name = this.$store.state.user.name;
            this.userId = this.$store.state.user.id; 
            console.log('发请求前页数与大小', this.page, this.pageSize)
            var feePageDTO = {
              page: this.page,
              pageSize: this.pageSize,
              userId: this.userId
            }
            axios({
            url: '/api/user/fee/page',
            method: 'get',
            params: feePageDTO,
            headers: {
              token: this.$store.state.user.token
            }
            }).then(res => { // 成功回调函数再发请求
              if(res.data.code == 1){ // 成功
                console.log("这是数据", res.data.data)
                this.total = res.data.data.total;
                this.vo = res.data.data.records;
                console.log("这是f数据", this.total, this.vo)
              }else{
                console.log(res.data.msg)
              }
            })
          },
    
          handleSizeChange(val) {
            this.pageSize = val;
            // console.log(`每页 ${val} 条`);
            console.log('每页', this.pageSize);
            this.load();
          },
    
          handleCurrentChange(val) {
            this.page = val;
            console.log('页数', this.page);
            // console.log(`当前页: ${val}`);
            this.load();
          },
    
          handleClose(done) {
            this.$confirm('确认关闭？')
              .then(_ => {
                done();
              })
              .catch(_ => {});
          },
          tableRowClassName({row, rowIndex}) {
            if (row.status === 0) {
              return 'warning-row';
            } else if (row.status === 1) {
              return 'success-row';
            }
            return '';
          }
        },
    
    
        data() {
          return {
            page : 1,
            pageSize: 10,
            userId: 0,
            name: '',
            total: 0,
            dialogVisible: false,


            houseDetails:{
              building: 0,
              cell: 0,
              floor: 0,
              doorplate: ''
            },

            stopDetails:{
                place: ''
            },
            vo: [],
          }
        }
      }
    </script>
    
    <style>
      .search {
        display: flex;
        flex-wrap: wrap
      }
      .table{
        padding-top: 10px;
        margin-bottom: 10px;
        margin-top: 10px;
        min-height: 700px;
        max-height: 700px
      }
      .seTag{
        /*垂直居中 */
        vertical-align: middle;
        /*水平居中*/
        text-align: center;
      }
      .tag{
        font-size: 18px;
        padding-top: 18px;
        padding-left: 20px;
        padding-right: 5px;
      }
      .seBT{
        margin-top: 10px;
      }
      .el-table .cell {  
        text-align: center;  
      }
      .el-dialog{
        /* background: transparent;
        background-image: url('@/assets/login.png');
        background-size: 100% 100%; */
        background-color: #F0F9EB;
        border-radius: 20px;
      }
      .el-table .warning-row {
        background: oldlace;
      }
  
      .el-table .success-row {
       background: #f0f9eb;
      }
      .el-descriptions{
        /* height: 200px; */
        /* margin-top: 30px; */
        /* margin-left: 50px;
        margin-right: 50px; */
        /* padding-top: 30px; */
        /* padding-left: 50px;
        padding-right: 50px;
        padding-bottom: 50px; */
        background-color:#F0F9EB;
        border-radius: 20px;
        text-align: center;
    }
    </style>