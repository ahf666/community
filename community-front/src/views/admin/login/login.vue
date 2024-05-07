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
		<h1>管理员登录</h1>
		<div class="logon">
			<div :class="overlaylong">
				<div class="overlaylong-Signin" v-if="disfiex == 0">
					<h2 class="overlaylongH2">登录</h2>
					<input type="text" placeholder="账号" v-model="username">
					<input type="password" placeholder="密码" v-model="password">
					<button class="inupbutton" @click="login">登录</button>
				</div>
			</div>

			<div :class="overlaytitle">
                <h2 class="overlaytitleH2">欢迎您！</h2>
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
			username: '',
			password: '',
		}
	},
	methods: {
		login(){
			axios({
				url: '/api/admin/admin/login',
				method: 'get',
				params: { // data表示通过请求体传参
					username: this.username,
					password: this.password
				}
			}).then(res => { // 成功回调函数再发请求
				console.log(res.data.data)
				if(res.data.code == 1){ // 登录成功
					console.log(res.data.data.token)
					this.$message("登录成功")
					this.$store.dispatch('adminLogin', res.data.data);
          			this.$router.push({path: '/admin'});
				}else{
					console.log(res.data.msg)
					this.$message(res.data.msg)
				}

			})
		}
	}
}
</script>