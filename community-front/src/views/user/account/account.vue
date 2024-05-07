<template>

    

    <div>


        <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose"> 

            <el-form :label-position="'left'" label-width="100px">
            <el-form-item label="姓名">
            <el-input v-model="name" placeholder=name></el-input>
            </el-form-item>
            <el-form-item label="用户名">
            <el-input v-model="username" placeholder=username></el-input>
            </el-form-item>

            <el-form-item label="性别">
            <el-select v-model="sexNum" placeholder=sex>
                <el-option
                    v-for="item in opSex2"
                    :key="item.sex"
                    :label="item.label"
                    :value="item.sex">
                </el-option>
            </el-select>
        </el-form-item>
        </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateMsg">确 定</el-button>
        </span>
    </el-dialog>

    <!-- 修改密码  -->
    <el-dialog title="修改密码" :visible.sync="dialogVisible2" width="30%" :before-close="handleClose"> 
            <el-form :label-position="'left'" label-width="100px">
                <el-form-item label="新密码">
                <el-input v-model="password" type="password"></el-input>
                </el-form-item>
            </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible2 = false">取 消</el-button>
                    <el-button type="primary" @click="updatePassword">确 定</el-button>
                </span>
            </el-dialog>

      <el-card>
        <el-descriptions class="margin-top" title="个人信息" :column="1" border>
          <template slot="extra">
            <el-button  class="bt" type="primary" size="small" @click="dialogVisible = true">修改信息</el-button>
            <el-button  class="bt" type="success" size="small" @click="dialogVisible2 = true">修改密码</el-button>
          </template>     
          <el-descriptions-item >
            <template slot="label">
              <i class="el-icon-user"></i>
              账号
            </template>
            {{ username }}
          </el-descriptions-item>
               
          <el-descriptions-item >
            <template slot="label">
              <i class="el-icon-s-custom"></i>
              姓名
            </template>
            {{ name }}
          </el-descriptions-item>
        
          <el-descriptions-item>
            <template slot="label">
              <i v-if="sexNum===1"  class="el-icon-male" ></i>
              <i v-else  class="el-icon-female"></i>
              性别
            </template>
            <el-tag size="small" >{{ sex }}</el-tag>
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号码
            </template>
            {{ phone }}
          </el-descriptions-item>
        </el-descriptions>

        <el-descriptions  title="房屋信息" :column="1" border>   
          <el-descriptions-item >
            <template slot="label">
              <i class="el-icon-discount"></i>
              楼栋号
            </template>
            {{ house.building }}栋 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </el-descriptions-item>
               
          <el-descriptions-item >
            <template slot="label">
              <i class="el-icon-receiving"></i>
              单元号
            </template>
            {{ house.cell }}单元
          </el-descriptions-item>

          <el-descriptions-item >
            <template slot="label">
              <i class="el-icon-coin"></i>
              楼层
            </template>
            {{ house.floor }}楼
          </el-descriptions-item>

          <el-descriptions-item >
            <template slot="label">
              <i class="el-icon-postcard"></i>
              门牌号
            </template>
            {{ house.doorplate }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>
  </template>
  
  <script>
import axios from 'axios';
  export default {
    data() {
      return {
        username: '',
        name: '',
        phone: '',
        sex: '',
        password: '',
        sexNum: 0,
        dialogVisible: false,
        dialogVisible2: false,
        opSex2: [{
            sex: 0,
            label: '女'
          }, {
            sex: 1,
            label: '男'
          }],
          house:{
            id: 0,
            building: 0,
            cell: 0,
            floor: 0,
            doorplate: ''
          }
      };
    },
    mounted() {
      this.load();
    },
    methods: {
      load() {
        this.username = this.$store.state.user.username;
        this.name = this.$store.state.user.name;
        this.phone = this.$store.state.user.phone;
        this.sex = this.$store.state.user.sex==1 ? '男' : '女';
        this.sexNum = this.$store.state.user.sex

        let id = this.$store.state.user.id;

        axios({
              url: `/api/user/property/house/${id}`,
              method: 'get',
              headers: {
                token: this.$store.state.user.token
              }
            }).then(res => { // 成功回调函数
              if(res.data.code == 1){ // 成功
                this.house = res.data.data;
                this.$store.dispatch('asyncSetHouse', this.house); // vuex更新
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

       isEmptyStr(s) {
          if (s == null || s === '') {
            return true
          }
          return false
        },

       updateMsg(){
        if(this.isEmptyStr(this.name)){
            this.$message("姓名不能为空")
            return;
          }
          if(this.isEmptyStr(this.username)){
            this.$message("用户名不能为空")
            return;
          }
          var userUpdateDTO2 = {
            id : this.$store.state.user.id,
            name: this.name,
            username: this.username,
            sex: this.sexNum
          }
          axios({
              url: '/api/user/account/updateMsg',
              method: 'post',
              data: userUpdateDTO2,
              headers: {
                token: this.$store.state.user.token
              }
            }).then(res => { // 成功回调函数
              if(res.data.code == 1){ // 成功
                let newData = {
                    id : this.$store.state.user.id,
                    name : this.name,
                    username: this.username,
                    sex: this.sexNum,
                    phone : this.phone,
                    token : this.$store.state.user.token
                }
                this.$message("修改成功");
                this.$store.dispatch('userLogin', newData); // vuex更新
                this.dialogVisible = false;
                this.load()
              }else{
                this.$message(res.data.msg)
              }
            })
       },

       updatePassword(){
          if(this.isEmptyStr(this.password)){
            this.$message("密码不能为空")
            return;
          }
          var userUpdateDTO = {
            id : this.$store.state.user.id,
            password : this.password
          }
          axios({
              url: '/api/user/account/update',
              method: 'post',
              data: userUpdateDTO,
              headers: {
                token: this.$store.state.user.token
              }
            }).then(res => { // 成功回调函数
              if(res.data.code == 1){ // 成功
                this.$message("修改成功");
                this.dialogVisible2 = false;
              }else{
                this.$message(res.data.msg)
              }
            })
       }

    },
  };
  </script>
  
  <style scoped>
  .el-descriptions{
    margin-top: 30px;
    margin-left: 50px;
    margin-right: 50px;
    padding-top: 30px;
    padding-left: 50px;
    padding-right: 50px;
    padding-bottom: 50px;
    background-color:#F0F9EB;
    border-radius: 20px;
    text-align: center;
  }
  .el-card{
    height: 800px;
    /* background-color:#E8EDF2; */
  }
  .bt{
    width: 100px;
    height: 35px;
    border-radius: 10px;
    text-align: center;
  }

  </style>
  