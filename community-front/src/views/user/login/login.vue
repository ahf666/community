<style>
	.center {
		width: 1920px;
		height: 1080px;
		background-image: url('@/assets/login.png');
		background-size: 100% 100%;
		background-repeat: no-repeat;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	h1 {
		font-size: 30px;
		color: black;
	}
 
	.logon {
		background-color: #fff;
		border-radius: 10px;
		box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
		/* position: relative;
		overflow: hidden; */
		width: 768px;
		max-width: 100%;
		min-height: 480px;
		margin-top: 20px;
		display: flex;
		background: -webkit-linear-gradient(right, #4284db, #29eac4);
	}
 
	.overlaylong {
		border-radius: 10px 0 0 10px;
		width: 50%;
		height: 100%;
		background-color: #fff;
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
	.overlaylongleft {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: #fff;
		transform: translateX(100%);
		transition: transform 0.6s ease-in-out;
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
	.overlaylongright {
		border-radius: 10px 0 0 10px;
		width: 50%;
		height: 100%;
		background-color: #fff;
		transform: translateX(0%);
		transition: transform 0.6s ease-in-out;
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
	.overlaytitle {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0);
		display: flex;
		align-items: center;
		justify-content: center;
	}
 
 
	.overlaytitleH2 {
		font-size: 30px;
		color: #fff;
		margin-top: 20px;
	}
 
	.overlaytitleP {
		font-size: 15px;
		color: #fff;
		margin-top: 20px;
	}
 
	.overlaytitleleft {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0);
		display: flex;
		align-items: center;
		justify-content: center;
		transform: translateX(0%);
		transition: transform 0.6s ease-in-out;
	}
 
	.overlaytitleright {
		border-radius: 0px 10px 10px 0px;
		width: 50%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0);
		display: flex;
		align-items: center;
		justify-content: center;
		transform: translateX(-100%);
		transition: transform 0.6s ease-in-out;
	}
 
	.overlaytitle-Signin {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	.overlaytitle-Signup {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	.buttongohs {
		width: 180px;
		height: 40px;
		border-radius: 50px;
		border: 1px solid #fff;
		color: #fff;
		font-size: 15px;
		text-align: center;
		line-height: 40px;
		margin-top: 40px;
	}
 
	.overlaylongH2 {
		font-size: 25px;
		color: black;
		/* width: 250px; */
	}
 
	.overlaylong-Signin {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	.overlaylong-Signup {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
 
	input {
		background-color: #eee;
		border: none;
		padding: 12px 15px;
		margin: 10px 0;
		width: 240px;
	}
	h3{
		font-size: 10px;
		margin-top: 10px;
		cursor: pointer;
	}
	.inupbutton{
		background-color: #29eac4;
		border: none;
		width: 180px;
		height: 40px;
		border-radius: 50px;
		font-size: 15px;
		color: #fff;	
		text-align: center;
		line-height: 40px;
		margin-top: 30px;
	}
</style>
 
<template>
	<div class="center">
		<h1>登录/注册</h1>
		<div class="logon">
			<div :class="overlaylong">
				<div class="overlaylong-Signin" v-if="disfiex == 0">
					<h2 class="overlaylongH2">登录</h2>
					<input type="text" v-model="loginData.username" placeholder="账号">
					<input type="password" v-model="loginData.password" placeholder="密码">
					<!-- <h3>Forgot your password?</h3> -->
					<button class="inupbutton" @click="login">登录</button>
				</div>
				<div class="overlaylong-Signup" v-if="disfiex == 1">
					<h2 class="overlaylongH2">注册账号</h2>
                    <input type="text" v-model="liveData.phone" placeholder="手机号">
					<input type="text" v-model="liveData.username" placeholder="用户名">
					<input type="password" v-model="liveData.password" placeholder="密码">
					<button class="inupbutton" @click="live">注册</button>
				</div>
 
			</div>
			<div :class="overlaytitle">
				<div class="overlaytitle-Signin" v-if="disfiex == 0">
					<h2 class="overlaytitleH2">注册您的账号</h2>
					<p class="overlaytitleP">
						只有被管理员录入系统的用户才能注册
					</p>
					<div class="buttongohs" @click="Signin">注册</div>
				</div>
				<div class="overlaytitle-Signup" v-if="disfiex == 1">
					<h2 class="overlaytitleH2">返回登录</h2>
					<p class="overlaytitleP">已注册完毕请返回登录</p>
					<div class="buttongohs" @click="Signup">登录</div>
				</div>
			</div>
		</div>
 
	</div>
</template>
 
<script>
import axios from 'axios'
	export default {
		data() {
			return {
				overlaylong: 'overlaylong',
				overlaytitle: 'overlaytitle',
				disfiex: 0,
				loginData: {
					username: '',
					password: ''
				},
				liveData: {
					username: '',
					password: '',
					phone: ''
				}
				
			}
		},
		methods: {

			login(){
				axios({
				url: '/api/user/user/login',
				method: 'get',
				params: { // data表示通过请求体传参
					username: this.loginData.username,
					password: this.loginData.password,
				}
				}).then(res => { // 成功回调函数
					if(res.data.code == 1){ // 登录成功
						console.log(res.data.data.token)
						this.$message("登录成功")
						this.$store.dispatch('userLogin', res.data.data);
						this.$router.push({path: '/user'});
					}else{
						console.log(res.data.msg)
						this.$message(res.data.msg)
					}
				})
			},
			live(){
				axios({
				url: '/api/user/user/qualification',
				method: 'get',
				params: { // data表示通过请求体传参
					username: this.liveData.username,
					password: this.liveData.password,
					phone: this.liveData.phone
				}
				}).then(res => { // 成功回调函数再发请求
					if(res.data.code == 1){ // 资格校验成功,再发注册请求
						axios({
							url: '/api/user/user/signUp',
							method: 'post',
							data: this.liveData,
							}).then(res => { // 成功回调函数
								if(res.data.code == 1){ // 注册成功
									this.$message("注册成功");
									this.Signup();
								}else{
									this.$message(res.data.msg);
								}
							})
					}else{
						this.$message(res.data.msg)
					}
				})
			},


			Signin() {
				this.overlaylong = "overlaylongleft"
				this.overlaytitle = "overlaytitleright"
				setTimeout(() => {
					this.disfiex = 1
				}, 200)
			},
			Signup() {
				this.overlaylong = "overlaylongright"
				this.overlaytitle = "overlaytitleleft"
 
				setTimeout(() => {
					this.disfiex = 0
				}, 200)
			}
		}
	}
</script>