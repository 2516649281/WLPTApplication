<template>
  <div id="background"></div>
  <el-form
      class="main"
      :model="user"
      ref="user"
      :rules="registerRoles"
      @keyup.enter="register(user)"
  >
    <el-form-item><h1>注册</h1></el-form-item>
    <el-form-item prop="name">
      <el-input required="required" placeholder="用户名" v-model="user.name"/>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
          show-password
          required="required"
          placeholder="密码"
          v-model="user.password"
      />
    </el-form-item>
    <el-form-item prop="Apassword">
      <el-input
          show-password
          required="required"
          placeholder="确认密码"
          v-model="user.Apassword"
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
      <el-button type="primary" @click="register(user)">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {register} from "../api/user";
import SIdentify from "../components/identify.vue";

export default {
  components: {
    SIdentify: SIdentify,
  },
  data() {
    return {
      user: {
        name: "",
        password: "",
        Apassword: "",
        code: "",
      },
      //随机数仓库
      identifyCodes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
      //验证码
      identifyCode: "",
      registerRoles: {
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
          {
            min: 8,
            max: 16,
            message: "密码长度在8~16之间!",
            trigger: "blur",
          },
        ],
        Apassword: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (value === "") {
                callback(new Error("请再次输入密码!"));
              } else if (value !== this.user.password) {
                callback(new Error("两次输入密码不一致!"));
              } else {
                callback();
              }
            },
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
      },
    };
  },
  created() {
    this.refreshCode();
  },
  methods: {
    register(user) {
      this.$refs["user"].validate((valid) => {
        if (valid) {
          register(user.name, user.password).then((req) => {
            if (req.data.status === 200) {
              this.$message({
                type: "success",
                message: "注册成功!",
                showClose: true,
              });
              this.$router.push({
                name: "login",
              });
            } else {
              this.$message({
                type: "error",
                message: "注册失败!",
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
