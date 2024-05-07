<template>
  <div>
        <el-row class="search">
            <el-row class="tag">位置:</el-row>
            <el-col :span="2">
               <el-input v-model="place"  placeholder="请输入.."></el-input>
            </el-col>
          
            <div class="tag">业主姓名:</div>
            <el-col :span="2">
               <el-input v-model="name"  placeholder="请输入.."></el-input>
            </el-col>
            <div class="tag">车牌号:</div>
            <el-col :span="2">
               <el-input v-model="carLicense"  placeholder="请输入.."></el-input>
            </el-col>
            <div class="tag">状态:</div>
            <el-select v-model="status" placeholder="全部">
                <el-option
                  v-for="item in opStatus"
                  :key="item.status"
                  :label="item.label"
                  :value="item.status">
                </el-option>
            </el-select>
            <el-col :span="2">
                <el-button class="seBT" type="success" @click="search">搜索</el-button>
            </el-col>
        </el-row>
  
      <el-table
      class="table"
      :data="this.vo"
      style="width: 100%"
      :row-class-name="tableRowClassName"
      max-height="700px">
      <el-table-column
        prop="place"
        label="位置"
        width="280">
      </el-table-column>
      <el-table-column
        prop="name"
        label="业主姓名"
        width="280">
      </el-table-column>
      <el-table-column
        prop="carLicense"
        label="车牌号"
        width="293">
      </el-table-column>
      <el-table-column
        prop="rentMonth"
        label="月租费用"
        width="280">
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="280">
        <template slot-scope="scope">  
          <span v-if="scope.row.status === 0">未出租</span>  
          <span v-else>已出租</span>  
        </template> 
      </el-table-column>
  
      <el-table-column
        label="操作"
        width="230">
        <template slot-scope="scope">
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
            <h1>姓名:{{userDetails.name}}</h1>
            <h1>用户名:{{userDetails.username}}</h1>
            <h1>手机号:{{userDetails.phone}}</h1>
            <h1>性别:{{userDetails.sex}}</h1>
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
        // deleteRow(index, rows) {
        //   rows.splice(index, 1);
        // },
        details(index, rows) {
          console.log("查看详情:", index, rows)
          var sex = rows[index].sex === 0 ? '女' : '男';
          if(rows[index].status === 0){
            sex = '';
          }
          this.userDetails.name = rows[index].name;
          this.userDetails.username = rows[index].username;
          this.userDetails.phone = rows[index].phone;
          this.userDetails.sex = sex;
        },
  
        load(){
          console.log('发请求前页数与大小', this.page, this.pageSize)
          let stopPageDTO = {
            page: this.page,
            pageSize: this.pageSize,
            name: this.name,
            place: this.place,
            carLicense: this.carLicense,
            status: this.status
          }
          axios({
          url: '/api/admin/stop/page',
          method: 'get',
          params: stopPageDTO,
          headers: {
            token: this.$store.state.admin.token
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
  
        search(){
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
          place: '',
          name: '',
          carLicense: '',
          status: -1,
          
          total: 0,
          dialogVisible: false,
          userDetails:{
            name: '',
            username: '',
            phone: '',
            sex: ''
          },
  
          opStatus: [{
            status: -1,
            label: '全部'
          }, {
            status: 0,
            label: '未出租'
          }, {
            status: 1,
            label: '已出租'
          }],
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
      background-color: #E9EEF3;
      border-radius: 20px;
    }
    .el-table .warning-row {
      background: oldlace;
    }

    .el-table .success-row {
     background: #f0f9eb;
    }
  </style>