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
    .b1 {
        background-color: #4CAF50; /* Green */
        margin: 40px;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        width: 300px;
        height: 200px;
        font-size: 50px;
        border-radius: 12px;
    }
    .b2 {
        background-color: #008CBA; /* Green */
        margin: 40px;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        width: 300px;
        height: 200px;
        font-size: 50px;
        border-radius: 12px;
    }
    
</style>
 
<template>
	<div class="center">
        <button class="b1" @click="userLogin">业主登录</button>
        <button class="b2" @click="adminLogin">管理员登录</button>
	</div>
</template>
 
<script>
	export default {
		methods: {
            userLogin(){
                this.$router.push({path: '/userLogin'});
            },
            adminLogin(){
                this.$router.push({path: '/adminLogin'});
            }
		}
	}
</script>