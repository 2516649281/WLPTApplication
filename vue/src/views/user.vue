<template>
  <el-row>
    <el-col :span="3">
      <el-button type="warning" @click="this.deleteUsers(this.deleteUserIds)"
      >批量删除
      </el-button>
    </el-col>
  </el-row>
  <el-row>
    <el-col>
      <el-table
          height="500"
          :data="tableData"
          border
          @selection-change="getUserIds"
      >
        <el-table-column type="selection" width="55"/>
        <el-table-column
            v-for="column in this.tableColumn"
            :key="column.index"
            :label="column.label"
            :prop="column.prop"
            :width="defaultColumn"
            align="center"
        />
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button size="small" @click="this.updateUserClick(scope.row)"
            >修改
            </el-button>
            <el-button
                size="small"
                type="danger"
                @click="deleteOneUser(scope.row.id)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
  <el-row>
    <el-col></el-col>
  </el-row>
  <!-- 修改用户 -->
  <el-dialog
      v-model="updateDialogVisible"
      title="修改用户"
      width="30%"
      center
      draggable
  >
    <el-form
        :model="updateUserForm"
        :rules="userRule"
        ref="updateUser"
        @keyup.enter="this.updateOneUser(this.updateUserForm)"
    >
      <el-form-item prop="name" label="用户名">
        <el-input v-model="this.updateUserForm.name"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="updateDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="this.updateOneUser(this.updateUserForm)"
      >修改
      </el-button>
    </template>
  </el-dialog>
</template>

<script>
import {lookAllUser, updateOneUser, deleteOneUser} from "../api/user";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //添加时需要的表单元素
      insertUserForm: {
        name: "",
        age: 18,
        sex: 1,
        address: "",
        classId: "",
      },
      //修改时需要的表单元素
      updateUserForm: {
        name: "",
      },
      //修改对话框是否显示
      updateDialogVisible: false,
      //批量删除时的id集合
      deleteUserIds: [],
      //表单校验
      userRule: {
        //姓名
        name: [
          {
            required: true,
            message: "用户名不得为空!",
            trigger: "blur",
          },
        ],
      },
      //列宽
      defaultColumn: 160,
      //表格列名集合
      tableColumn: [
        {
          index: 1,
          label: "编号",
          prop: "id",
        },
        {
          index: 2,
          label: "用户名",
          prop: "name",
        },
      ],
    };
  },
  created() {
    this.lookAllUser();
  },
  methods: {
    //查看所有用户逻辑
    lookAllUser() {
      lookAllUser().then((req) => {
        this.tableData = req.data.data;
        console.log(this.tableData);
      });
    },
    //修改一名用户
    updateOneUser(obj) {
      console.log(obj);
      this.$refs["updateUser"].validate((valid) => {
        if (valid) {
          updateOneUser(obj).then((req) => {
            if (req.data.success) {
              this.$message({
                type: "success",
                message: "修改用户成功!",
                showClose: true,
              });
              this.updateDialogVisible = false;
              this.lookAllUser();
            } else {
              this.$message({
                type: "error",
                message: "修改用户失败!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    //删除一条用户信息
    deleteOneUser(ids) {
      var id = [ids];
      var user = sessionStorage.getItem("user");
      this.$confirm("你真的要删除这条用户的信息吗?(真的永久)", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //非法删除判断
        if (ids.toString() === user) {
          this.$message({
            type: "error",
            message: "不能删除自己哦!",
            showClose: true,
          });
          return;
        }
        deleteOneUser(id).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除用户成功!",
              showClose: true,
            });
            this.lookAllUser();
          } else {
            this.$message({
              type: "error",
              message: "删除用户失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //批量删除
    deleteUsers(ids) {
      this.$confirm(`你真的要删除${ids.length}条用户的信息吗?(真的永久)`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteOneUser(ids).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除用户成功!",
              showClose: true,
            });
            this.lookAllUser();
          } else {
            this.$message({
              type: "error",
              message: "删除用户失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //修改用户前的附加操作
    updateUserClick(obj) {
      console.log(obj);
      this.updateUserForm = obj;
      this.updateDialogVisible = true;
    },
    //获取选中的Id值
    getUserIds(val) {
      var ids = [];
      for (let i = 0; i < val.length; i++) {
        ids[i] = val[i].id;
      }
      this.deleteUserIds = ids;
    },
  },
};
</script>

<style>
</style>