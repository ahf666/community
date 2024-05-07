<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import login from "./views/login/login.vue"
  export default {
    name: 'App',
    components:{
      login
    }
  }
</script>
