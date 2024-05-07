<template>
  <div>
        <el-row class="search">
            <el-row class="tag">姓名:</el-row>
            <el-col :span="2">
               <el-input v-model="name"  placeholder="请输入.."></el-input>
            </el-col>
          
            <div class="tag">用户名:</div>
            <el-col :span="2">
               <el-input v-model="username"  placeholder="请输入.."></el-input>
            </el-col>
            <div class="tag">手机号:</div>
            <el-col :span="2">
               <el-input v-model="phone"  placeholder="请输入.."></el-input>
            </el-col>
            <div class="tag">性别:</div>
            <el-select v-model="sex" placeholder="全部">
                <el-option
                  v-for="item in opSex"
                  :key="item.sex"
                  :label="item.label"
                  :value="item.sex">
                </el-option>
            </el-select>
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
            <el-col :span="2">
                <el-button class="seBT" type="primary"  @click="dialogVisible = true">添加</el-button>
            </el-col>


            <el-dialog title="用户信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose"> 
              <el-form :label-position="'left'" label-width="100px" :model="addUserData">
                <el-form-item label="姓名">
                  <el-input v-model="addUserData.name"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                  <el-input v-model="addUserData.phone"></el-input>
                </el-form-item>

                <el-form-item label="性别">
                  <el-select v-model="addUserData.sex" placeholder="男">
                      <el-option
                        v-for="item in opSex2"
                        :key="item.sex"
                        :label="item.label"
                        :value="item.sex">
                      </el-option>
                  </el-select>
              </el-form-item>

              <el-form-item label="楼栋号">
                  <el-select v-model="addUserData.building" placeholder="1栋">
                      <el-option
                        v-for="item in opBuilding"
                        :key="item.building"
                        :label="item.label"
                        :value="item.building">
                      </el-option>
                  </el-select>
              </el-form-item>

              <el-form-item label="单元号">
                  <el-select v-model="addUserData.cell" placeholder="一单元">
                      <el-option
                        v-for="item in opCell"
                        :key="item.cell"
                        :label="item.label"
                        :value="item.cell">
                      </el-option>
                  </el-select>
              </el-form-item>

              <el-form-item label="楼层号">
                  <el-select v-model="addUserData.floor" placeholder="一楼">
                      <el-option
                        v-for="item in opFloor"
                        :key="item.floor"
                        :label="item.label"
                        :value="item.floor">
                      </el-option>
                  </el-select>
              </el-form-item>

              <el-form-item label="门牌号">
                <el-input v-model="addUserData.doorplate"></el-input>
              </el-form-item>
          
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="add">确 定</el-button>
              </span>
            </el-dialog>

        </el-row>
  
      <el-table
      class="table"
      :data="this.vo"
      style="width: 100%"
      max-height="700px"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="name"
        label="姓名"
        width="260">
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="303">
      </el-table-column>

      <el-table-column
        prop="phone"
        label="手机号"
        width="340">
      </el-table-column>

      <el-table-column
        prop="sex"
        label="性别"
        width="210">
        <template slot-scope="scope">  
          <span v-if="scope.row.sex===0">女</span>  
          <span v-else>男</span>  
        </template> 
      </el-table-column>
    
      <el-table-column
        prop="status"
        label="状态"
        width="250">
        <template slot-scope="scope">  
          <span v-if="scope.row.status === 0">禁用</span>  
          <span v-else>启用</span>  
        </template> 
      </el-table-column>

      <el-table-column
        label="操作"
        width="280">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="updateStatus(scope.$index, vo)"
            type="text"
            size="small">
            状态修改
          </el-button>
          <el-button
            @click.native.prevent="delUser(scope.$index, vo)"
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
        delUser(index, rows){
          if(rows[index].status == 1){
            this.$message("只能删除禁用状态的用户");
            return
          }
          axios({
              url: '/api/admin/user/delete',
              method: 'delete',
              params: {
                id: rows[index].id
              },
              headers: {
                token: this.$store.state.admin.token
              }
            }).then(res => { // 成功回调函数再发请求
              if(res.data.code == 1){ // 成功
                rows.splice(index, 1);
              }else{
                this.$message(res.data.msg)
              }
            })
        },
        updateStatus(index, rows){
          if(rows[index].username==='未注册'){
            this.$message("未注册用户不可修改")
          }else{
            var sta = rows[index].status==0 ? 1 : 0;
            var userStatusDTO = {
              id : rows[index].id,
              status : sta
            }
            axios({
              url: '/api/admin/user/status',
              method: 'post',
              data: userStatusDTO,
              headers: {
                token: this.$store.state.admin.token
              }
            }).then(res => { // 成功回调函数再发请求
              if(res.data.code == 1){ // 成功
                rows[index].status = sta;
              }else{
                this.$message("修改失败")
              }
            })
          }
        },
  
        load(){
          var userPageDTO = {
            page: this.page,
            pageSize: this.pageSize,
            username: this.username,
            name: this.name,
            phone: this.phone,
            sex: this.sex,
            status: this.status
          }
          console.log('发请求前页数与大小', this.page, this.pageSize)
            axios({
            url: '/api/admin/user/page',
            method: 'get',
            params: userPageDTO,
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
        isEmptyStr(s) {
          if (s == null || s === '') {
            return true
          }
          return false
        },
        add(){
          if(this.isEmptyStr(this.addUserData.name)){
            this.$message("姓名不能为空")
            return;
          }
          if(this.addUserData.phone.length != 11){
            this.$message("手机号必须为11位数字")
            return;
          }
          axios({
              url: '/api/admin/user/add',
              method: 'post',
              data: this.addUserData,
              headers: {
                token: this.$store.state.admin.token
              }
            }).then(res => { // 成功回调函数再发请求
              if(res.data.code == 1){ // 成功
                this.$message("新增成功");
                this.dialogVisible = false;
                this.load()
              }else{
                this.$message(res.data.msg)
              }
            })
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
          username: '',
          name: '',
          phone: '',
          sex: -1,
          status: -1,
          total: 0,
          dialogVisible: false,

          addUserData: {
            name: '',
            phone: '',
            sex: 1,
            building: 1,
            cell: 1,
            floor: 1,
            doorplate: ''
          },
  
          opStatus: [{
            status: -1,
            label: '全部'
          }, {
            status: 0,
            label: '未出售'
          }, {
            status: 1,
            label: '已出售'
          }],

          opSex: [{
            sex: -1,
            label: '全部'
          },{
            sex: 0,
            label: '女'
          }, {
            sex: 1,
            label: '男'
          }],
          opSex2: [{
            sex: 0,
            label: '女'
          }, {
            sex: 1,
            label: '男'
          }],
          opBuilding: [{
            building: 1,
            lable: '1栋'
          },{
            building: 2,
            lable: '2栋'
          },{
            building: 3,
            lable: '3栋'
          },{
            building: 4,
            lable: '4栋'
          },{
            building: 5,
            lable: '5栋'
          }
        ],
        opCell: [{
          cell: 1,
          lable: '一单元'
        },{
          cell: 2,
          lable: '二单元'
        },{
          cell: 3,
          lable: '三单元'
        }
        ],
        opFloor: [{
          floor: 1,
          lable: '一楼'
        },{
          floor: 2,
          lable: '二楼'
        },{
          floor: 3,
          lable: '三楼'
        },{
          floor: 4,
          lable: '四楼'
        },{
          floor: 5,
          lable: '五楼'
        },{
          floor: 6,
          lable: '六楼'
        },{
          floor: 7,
          lable: '七楼'
        },{
          floor: 8,
          lable: '八楼'
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