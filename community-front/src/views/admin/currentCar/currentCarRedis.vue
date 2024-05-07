<template>
    <div>
          <el-row class="search">
            <el-row class="tag">24小时以内的车辆&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</el-row>
              <el-col :span="2">
                  <el-button class="seBT" type="success" @click="switchMysql">切换</el-button>
              </el-col>
          </el-row>
    
        <el-table
        class="table"
        :data="this.vo"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        max-height="700px">
        <el-table-column
          prop="carLicense"
          label="车牌号"
          width="500">
        </el-table-column>
        <el-table-column
          prop="entryTime"
          label="进入时间"
          width="600">
        </el-table-column>
    
        <el-table-column
          label="操作"
          width="543">
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
              <h1 v-if="userDetails.name.length != 0">姓名:{{userDetails.name}}</h1>
              <h1 v-if="userDetails.username.length != 0">用户名:{{userDetails.username}}</h1>
              <h1 v-if="userDetails.phone.length != 0">手机号:{{userDetails.phone}}</h1>
              <h1 v-if="userDetails.sex.length != 0">性别:{{userDetails.sex}}</h1>
              <h1 v-if="userDetails.sex.length === 0">外来车辆</h1>
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

          details(index, rows) {
            console.log("查看详情:", index, rows)
            var userId = rows[index].userId;
            if(userId === 0){ // 外来车辆
                this.userDetails.name = '';
                this.userDetails.username = '';
                this.userDetails.phone = '';
                this.userDetails.sex = '';
            }else{
                axios({
                    url: `/api/admin/currentCar/${userId}`,
                    method: 'get',
                    headers: {
                        token: this.$store.state.admin.token
                    }
                    }).then(res => { // 成功回调函数再发请求
                    if(res.data.code == 1){ // 成功
                        console.log("这是数据", res.data.data)
                        let user = res.data.data;
                        this.userDetails.name = user.name;
                        this.userDetails.username = user.username;
                        this.userDetails.phone = user.phone;
                        this.userDetails.sex = user.sex===1 ? '男': '女';      
                    }else{
                        console.log(res.data.msg)
                    }
                })
            }
          },
    
          load(){
            console.log('发请求前页数与大小', this.page, this.pageSize)
            var carPageDTO = {
              page: this.page,
              pageSize: this.pageSize,
            }
            axios({
                url: '/api/admin/currentCar/redisPage',
                method: 'get',
                params: carPageDTO,
                headers: {
                token: this.$store.state.admin.token
                }
                }).then(res => { // 成功回调函数再发请求
                if(res.data.code == 1){ // 成功
                    console.log("这是数据", res.data.data)
                    this.vo = res.data.data.cars;
                    this.total = res.data.data.total;
                    console.log("这是f数据", this.total, this.vo)
                }else{
                    console.log(res.data.msg)
                }
                })
          },

          switchMysql(){
            this.$router.push({path: '/admin/currentCarMysql'});
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
              if (row.userId === 0) {
                return 'warning-row';
              } else{
                return 'success-row';
              }
            }
        },
    
    
        data() {
          return {
            page : 1,
            pageSize: 10,
            
            dialogVisible: false,
            userDetails:{
              name: '',
              username: '',
              phone: '',
              sex: ''
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