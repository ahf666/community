<template>
    <div>
   
        <el-col :span="2">
            <el-button class="seBT" type="success" @click="dialogVisible=true">添加</el-button>
        </el-col>

        <el-dialog title="车辆信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose"> 
            <el-form :label-position="'left'" label-width="100px">

                <el-form-item label="车牌号">
                <el-input v-model="carLicense"></el-input>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="add">确 定</el-button>
        </span>
    </el-dialog>

        <el-table
        class="table"
        :data="this.vo"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        max-height="700px">
  
        <el-table-column
          prop="carLicense"
          label="车牌"
          width="650">
        </el-table-column>
    
        <el-table-column
          label="操作"
          width="443">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="deleteCar(scope.$index, vo)"
              type="text"
              size="small">
              删除
            </el-button>
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
          deleteCar(index, rows){
            this.id  = rows[index].id;
            let id = this.id;
            axios({
              url: `/api/user/property/userCar/delete/${id}`,
              method: 'delete',
              headers: {
                token: this.$store.state.user.token
              }
            }).then(res => { // 成功回调函数再发请求
              if(res.data.code == 1){ // 成功
                rows.splice(index, 1);
                this.$message("删除成功")
              }else{
                this.$message(res.data.msg)
              }
            })
          },

          add(){
            let userAddDTO = {
                userId: this.$store.state.user.id,
                carLicense: this.carLicense
            }
            axios({
              url: `/api/user/property/userCar/insert`,
              method: 'post',
              data: userAddDTO,
              headers: {
                token: this.$store.state.user.token
              }
            }).then(res => { // 成功回调函数再发请求
              if(res.data.code == 1){ // 成功
                this.$message("添加成功")
                this.dialogVisible=false;
                this.carLicense = ''
                this.load();
              }else{
                this.$message(res.data.msg)
              }
            })
          },
        
    
          load(){
            console.log('发请求前页数与大小', this.page, this.pageSize)
            var userCarPageDTO = {
              page: this.page,
              pageSize: this.pageSize,
              userId: this.$store.state.user.id
            }
            axios({
            url: '/api/user/property/userCar/page',
            method: 'get',
            params: userCarPageDTO,
            headers: {
              token: this.$store.state.user.token
            }
            }).then(res => { // 成功回调函数
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
            carLicense: '',
            dialogVisible: false,
            id: 0,
  
            vo: []
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
        background-color: #E9EEF3;
        border-radius: 20px;
      }
      .el-table .warning-row {
        background: oldlace;
      }
  
      .el-table .success-row {
       background: #f0f9eb;
      }
      .el-table{
        font-size: 21px;
      }
    </style>