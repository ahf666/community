<template>
    <el-container>
      <el-header>
        <h1 class="head">社区物业管理系统-管理端     欢迎您！{{username}}</h1>
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
                <el-menu-item index="1"  @click="user">
                <i class="el-icon-user"></i>
                <span slot="title">用户管理</span>
                </el-menu-item>
                <el-menu-item index="2" @click="house">
                  <i class="el-icon-house"></i>
                  <span slot="title">房屋管理</span>
                </el-menu-item>
                <el-menu-item index="3" @click="stop">
                  <i class="el-icon-map-location"></i>
                  <span slot="title">车位管理</span>
                </el-menu-item>
                <el-menu-item index="4" @click="fee">
                  <i class="el-icon-money"></i>
                  <span slot="title">缴费管理</span>
                </el-menu-item>
                <el-menu-item index="5" @click="repair">
                  <i class="el-icon-setting"></i>
                  <span slot="title">报修管理</span>
                </el-menu-item>
                <el-menu-item index="6" @click="complain">
                  <i class="el-icon-phone-outline"></i>
                  <span slot="title">投诉管理</span>
                </el-menu-item>
                <el-menu-item index="7" @click="userCar">
                  <i class="el-icon-truck"></i>
                  <span slot="title">车辆管理</span>
                </el-menu-item>
                <el-menu-item index="8" @click="currentCar">
                  <i class="el-icon-camera-solid"></i>
                  <span slot="title">车辆监督</span>
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
      username : 'xxxx'
    },
    created() {    
      this.load();       
    },
    methods: {
      load(){
        this.username = this.$store.state.admin.username;
      },
      user(){
        this.$router.push({path: '/admin/user'});
      },
      house(){
        this.$router.push({path: '/admin/house'});
      },
      stop(){
        this.$router.push({path: '/admin/stop'});
      },
      fee(){
        this.$router.push({path: '/admin/fee'});
      },
      repair(){
        this.$router.push({path: '/admin/repair'});
      },
      complain(){
        this.$router.push({path: '/admin/complain'});
      },
      userCar(){
        this.$router.push({path: '/admin/userCar'});
      },
      currentCar(){
        this.$router.push({path: '/admin/currentCarRedis'});
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
    }
    .el-menu-item {
      width: 200px;
      height: 100px;
      font-size: 20px;
      text-align: center;
      line-height: 85px;
      border-radius: 10px;
    }
    .head {
      text-align: center;
      margin-top: 10px;
    }
  </style>