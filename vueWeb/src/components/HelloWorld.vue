<template>
  <div class="container">
      <form class="form-signin" method="post" action="http://localhost:8080/login">
      <h2 class="form-signin-heading">Please sign in</h2>
      <p>
        <label for="username" class="sr-only">Username</label>
        <input type="text"  v-model="loginName" id="username" name="loginName" class="form-control" placeholder="Username" required autofocus>
      </p>
      <p>
        <label for="password" class="sr-only">Password</label>
        <input type="password" v-model="password" id="password" name="password" class="form-control" placeholder="Password" required>
      </p>
      <button  v-on:click="login" class="btn btn-lg btn-primary btn-block" type="button">Sign in</button>
          <p>{{info}}</p>
      <p>{{password}}</p>
      <p>{{loginName}}</p>
      </form>
  </div>
</template>

<script>
import qs from 'qs';
import axios from 'axios'

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
    data(){
        return{
            info:null,
            loginName:'',
            password:''
        }
    },
    methods:{
      login:function () {
          console.log("12 :"+this.loginName+" "+this.password);
         return  axios({
             method: 'post',
             url: '/api/login',
             data: {
                 loginName : this.loginName,
                 password : this.password
             },
             transformRequest: [function (data) {
                 return qs.stringify(data)
             }],
             headers : {
                 'Content-Type': 'application/x-www-form-urlencoded'
             }
          })
          .then(response => (this.info = response))

          .catch(function (error) {
              console.log(error.config);
          })


    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
