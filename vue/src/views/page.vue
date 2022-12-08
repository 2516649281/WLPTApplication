<template>
  <el-row>
    <el-col :span="3">
      <el-menu
          default-active="1"
          text-color="#fff"
          background-color="#545c64"
          :collapse="collapse"
      >
        <el-menu-item
            v-for="item in menu"
            :key="item.index"
            :index="item.index"
            @click="this.getPath(item.path)"
        >
          <span>{{ item.name }}</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="21">
      <el-container>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      menu: [
        {
          index: 1,
          path: "user",
          name: "用户管理",
        },
        {
          index: 2,
          path: "equ",
          name: "设备管理",
        },
        {
          index: 3,
          path: "sen",
          name: "传感器管理",
        },
        {
          index: 4,
          path: "act",
          name: "执行器管理",
        },
        {
          index: 5,
          path: "login",
          name: "退出登录",
        },
      ],
    };
  },
  created() {
    this.getPath("user");
  },
  methods: {
    getPath(name) {
      if (name === "login") {
        this.$confirm(`你真的要退出登录吗?退出后需要重新登录才能使用此系统`, {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          sessionStorage.clear();
          this.$router.push({name: name});
        });
      } else {
        this.$router.push({name: name});
      }
    },
  },
};
</script>

<style scoped>
.el-row {
  height: 680px;
}

.el-menu {
  height: 680px;
}

.el-main {
  position: absolute;
  left: 200px;
  right: 0;
  top: 10px;
  bottom: 0;
  overflow-y: scroll;
}
</style>