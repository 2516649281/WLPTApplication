<template>
  <el-row>
    <el-col :span="3">
      <el-button type="warning" @click="this.deleteEqus(this.deleteEquIds)"
      >批量删除
      </el-button>
    </el-col>
    <el-col :span="5">
      <el-input v-model="name" clearable placeholder="输入名称以查询">
      </el-input>
    </el-col>
    <el-col :span="3">
      <el-button type="primary" @click="this.lookOneEquByName(this.name)"
      >查询
      </el-button>
    </el-col>
    <el-col :span="3">
      <el-button type="primary" @click="this.insertDialogVisible = true"
      >添加设备
      </el-button>
    </el-col>
  </el-row>
  <el-row>
    <el-col>
      <el-table
          height="500"
          :data="tableData"
          border
          @selection-change="getEquIds"
      >
        <el-table-column type="selection" width="55"/>
        <el-table-column
            v-for="column in this.tableColumn"
            :key="column.index"
            :label="column.label"
            :prop="column.prop"
            :width="defaultColumn"
            align="center"
        ><!-- 状态列特殊处理 -->
          <template #default="scope" v-if="column.prop === 'status'">
            {{ scope.row.status === 0 ? "在线" : "离线" }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button size="small" @click="this.updateEquClick(scope.row)"
            >修改
            </el-button>
            <el-button
                size="small"
                type="danger"
                @click="deleteOneEqu(scope.row.id)"
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
  <!-- 添加一台设备 -->
  <el-dialog
      v-model="insertDialogVisible"
      title="添加设备"
      width="30%"
      center
      draggable
  >
    <el-form
        :model="insertEquForm"
        :rules="equRule"
        ref="insertEqu"
        @keyup.enter="this.insertOneEqu(this.insertEquForm)"
    >
      <el-form-item prop="name" label="设备名">
        <el-input
            v-model="this.insertEquForm.name"
            placeholder="定义设备名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="uid" label="设备ID">
        <el-input
            v-model="this.insertEquForm.uid"
            placeholder="定义设备ID"
        ></el-input>
      </el-form-item>
      <el-form-item prop="num" label="设备通道号">
        <el-input-number v-model="this.insertEquForm.num" :min="1"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="insertDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="this.insertOneEqu(this.insertEquForm)"
      >添加
      </el-button>
    </template>
  </el-dialog>
  <!-- 修改设备 -->
  <el-dialog
      v-model="updateDialogVisible"
      title="修改设备"
      width="30%"
      center
      draggable
  >
    <el-form
        :model="updateEquForm"
        :rules="equRule"
        ref="updateEqu"
        @keyup.enter="this.updateOneEqu(this.updateEquForm)"
    >
      <el-form-item prop="name" label="设备名">
        <el-input
            v-model="this.updateEquForm.name"
            placeholder="输入设备名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="uid" label="设备ID">
        <el-input
            v-model="this.updateEquForm.uid"
            placeholder="输入设备ID"
        ></el-input>
      </el-form-item>
      <el-form-item prop="num" label="设备通道号">
        <el-input-number v-model="this.updateEquForm.num" :min="1"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="updateDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="this.updateOneEqu(this.updateEquForm)"
      >修改
      </el-button>
    </template>
  </el-dialog>
</template>

<script>
import {
  lookAllEqu,
  lookOneEquByName,
  insertOneEqu,
  updateOneEqu,
  deleteOneEqu,
} from "../api/equ";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询需要的设备名
      name: "",
      //添加时需要的表单元素
      insertEquForm: {
        name: "",
        num: 1,
        uid: "",
      },
      //修改时需要的表单元素
      updateEquForm: {
        name: "",
        num: "",
        uid: "",
      },
      //添加对话框是否显示
      insertDialogVisible: false,
      //修改对话框是否显示
      updateDialogVisible: false,
      //批量删除时的id集合
      deleteEquIds: [],
      //表单校验
      equRule: {
        //名称
        name: [
          {
            required: true,
            message: "设备名不得为空!",
            trigger: "blur",
          },
        ],
        //通道号
        num: [
          {
            required: true,
            message: "通道号不得为空",
            trigger: "change",
          },
        ],
        //设备ID
        uid: [
          {
            required: true,
            message: "设备ID不得为空!",
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
          label: "设备名",
          prop: "uid",
        },
        {
          index: 3,
          label: "设备名",
          prop: "name",
        },
        {
          index: 4,
          label: "设备通道号",
          prop: "num",
        },
      ],
    };
  },
  created() {
    this.lookAllEqu();
  },
  methods: {
    //查看所有设备逻辑
    lookAllEqu() {
      lookAllEqu().then((req) => {
        this.tableData = req.data.data;
        console.log(this.tableData);
      });
    },
    //根据设备名查询设备
    lookOneEquByName(name) {
      console.log(name);
      if (name === "") {
        this.lookAllEqu();
        return;
      }
      lookOneEquByName(name).then((req) => {
        this.tableData = req.data.data;
      });
    },
    //添加一台设备
    insertOneEqu(obj) {
      console.log(obj);
      this.$refs["insertEqu"].validate((valid) => {
        if (valid) {
          insertOneEqu(obj).then((req) => {
            if (req.data.success) {
              this.$message({
                type: "success",
                message: "添加设备成功!可继续进行添加!",
                showClose: true,
              });
              this.lookAllEqu();
              // this.insertDialogVisible = false;
              // this.insertEquForm = {};
            } else {
              this.$message({
                type: "error",
                message: "添加设备失败!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    //修改一台设备
    updateOneEqu(obj) {
      console.log(obj);
      this.$refs["updateEqu"].validate((valid) => {
        if (valid) {
          updateOneEqu(obj).then((req) => {
            if (req.data.success) {
              this.$message({
                type: "success",
                message: "修改设备成功!",
                showClose: true,
              });
              this.updateDialogVisible = false;
              this.lookAllEqu();
            } else {
              this.$message({
                type: "error",
                message: "修改设备失败!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    //删除一条设备信息
    deleteOneEqu(ids) {
      var id = [ids];
      this.$confirm("你真的要删除这条设备的信息吗?(真的永久)", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteOneEqu(id).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除设备成功!",
              showClose: true,
            });
            this.lookAllEqu();
          } else {
            this.$message({
              type: "error",
              message: "删除设备失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //批量删除
    deleteEqus(ids) {
      console.log(ids);
      this.$confirm(`你真的要删除${ids.length}条设备的信息吗?(真的永久)`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteOneEqu(ids).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除设备成功!",
              showClose: true,
            });
            this.lookAllEqu();
          } else {
            this.$message({
              type: "error",
              message: "删除设备失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //修改设备前的附加操作
    updateEquClick(obj) {
      console.log(obj);
      this.updateEquForm = obj;
      this.updateDialogVisible = true;
    },
    //获取选中的Id值
    getEquIds(val) {
      var ids = [];
      for (let i = 0; i < val.length; i++) {
        ids[i] = val[i].id;
      }
      this.deleteEquIds = ids;
    },
  },
};
</script>

<style>
</style>