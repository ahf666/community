<template>
  <el-container>
    <el-header>
      <!-- <h1>社区物业管理系统-管理端</h1> -->
      <h1 class="head">社区物业管理系统-用户端     欢迎您！{{name}}</h1>
    </el-header>
    <el-container>
        <el-aside width="200px">
          <el-col :span="12">
            <el-menu
              default-active="1"
              class="el-menu"
              @open="handleOpen"
              @close="handleClose"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b">
              <el-menu-item index="1"  @click="account">
              <i class="el-icon-user"></i>
              <span slot="title">个人中心</span>
              </el-menu-item>
   
              <el-menu-item index="3" @click="car">
                <i class="el-icon-truck"></i>
                <span slot="title">我的车辆</span>
              </el-menu-item>
              <el-menu-item index="4" @click="stop">
                <i class="el-icon-map-location"></i>
                <span slot="title">我的车位</span>
              </el-menu-item>
              <el-menu-item index="5" @click="fee">
                <i class="el-icon-money"></i>
                <span slot="title">我的缴费</span>
              </el-menu-item>
              <el-menu-item index="6" @click="repair">
                <i class="el-icon-setting"></i>
                <span slot="title">我的报修</span>
              </el-menu-item>
              <el-menu-item index="7" @click="complain">
                <i class="el-icon-phone-outline"></i>
                <span slot="title">我的投诉</span>
              </el-menu-item>
            </el-menu>
          </el-col>
        </el-aside>
        <el-main>
            <router-view/>
        </el-main>
    </el-container>
    </el-container>
</template>

<script>
export default {
  data: {
    name : 'xxxx'
  },
  created() {    
    this.load();       
  },
  methods: {
    load(){
      this.name = this.$store.state.user.name;
    },
    account(){
      this.$router.push({path: '/user/account'});
    },
    stop(){
      this.$router.push({path: '/user/stop'});
    },
    car(){
      this.$router.push({path: '/user/car'});
    },
    fee(){
      this.$router.push({path: '/user/fee'});
    },
    repair(){
      this.$router.push({path: '/user/repair'});
    },
    complain(){
      this.$router.push({path: '/user/complain'});
    }
  }
}
</script>

<style>
    .el-header{
    background-color: #B3C0D1;
    color: #333;
    /* text-align: top; */
    border-radius: 10px;
  }
  
  .el-aside {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 200px;
    border-radius: 10px;
    height:850px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
  }
  
  /* body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  } */
  .el-menu {
    width: 200px;
    height: max-content;
    border-radius: 10px;
    margin-top: 20px;
  }
  .el-menu-item {
    width: 200px;
    height: 134px;
    font-size: 23px;
    text-align: center;
    line-height: 125px;
    border-radius: 10px;
  }
  .head {
    text-align: center;
    margin-top: 10px;
  }
</style>