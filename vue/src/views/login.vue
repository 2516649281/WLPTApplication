<template>
  <div id="background"></div>
  <el-form
      class="main"
      :model="user"
      ref="user"
      :rules="loginRoles"
      @keyup.enter="login(user.name, user.password)"
  >
    <el-form-item><h1>登录</h1></el-form-item>
    <el-form-item prop="name">
      <el-input required="required" v-model="user.name" placeholder="用户名"/>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
          show-password
          required="required"
          v-model="user.password"
          placeholder="密码"
      />
    </el-form-item>
    <el-form-item prop="code">
      <el-input
          class="code"
          required="required"
          placeholder="验证码"
          v-model="user.code"
      >
        <template v-slot:suffix>
          <s-identify
              :identifyCode="identifyCode"
              @click="refreshCode"
          ></s-identify>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item>
      <router-link to="/register">没有账号?注册一个!</router-link>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="login(user.name, user.password)"
      >登录
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {login} from "../api/user";
import SIdentify from "../components/identify.vue";
import jwtDecode from "jwt-decode";

export default {
  components: {
    SIdentify: SIdentify,
  },
  data() {
    return {
      user: {
        name: "",
        password: "",
        code: "",
      },
      //随机数仓库
      identifyCodes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
      //验证码
      identifyCode: "",
      loginRoles: {
        name: [
          {
            required: true,
            message: "账号不得为空!",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "密码不得为空!",
            trigger: "blur",
          },
        ],
        code: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (value === "") {
                callback(new Error("验证码不得为空!"));
              } else if (value !== this.identifyCode) {
                callback(new Error("验证码错误!"));
                this.refreshCode();
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        imgSrc: require("../assets/bg.jpg"),
      },
    };
  },
  created() {
    this.refreshCode();
  },
  methods: {
    login(name, password) {
      this.$refs["user"].validate((valid) => {
        if (valid) {
          login(name, password).then((req) => {
            console.log(req);
            if (req.data.status === 200) {
              this.$message({
                type: "success",
                message: "登陆成功!",
                showClose: true,
              });
              //获取token
              var token = req.data.data;
              console.log(jwtDecode(token));
              sessionStorage.setItem("token", token);
              sessionStorage.setItem("user", jwtDecode(token).user);
              this.$router.push({
                name: "page",
              });
            } else {
              this.$message({
                type: "error",
                message: "登陆失败!请检查密码是否输入正确!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode +=
            this.identifyCodes[this.randomNum(0, this.identifyCodes.length)];
      }
    },
  },
};
</script>

<style src="../css/login-register.css" scoped>
</style>
