<template>
  <el-row>
    <el-col :span="3">
      <el-button type="warning" @click="this.deleteSens(this.deleteSenIds)"
      >批量删除
      </el-button>
    </el-col>
    <el-col :span="5">
      <el-input v-model="name" clearable placeholder="输入名称以查询">
      </el-input>
    </el-col>
    <el-col :span="3">
      <el-button type="primary" @click="this.lookOneSenByName(this.name)"
      >查询
      </el-button>
    </el-col>
    <el-col :span="3">
      <el-button type="primary" @click="this.insertDialogVisible = true"
      >添加传感器
      </el-button>
    </el-col>
  </el-row>
  <el-row>
    <el-col>
      <el-table
          height="500"
          :data="tableData"
          border
          @selection-change="getSenIds"
      >
        <el-table-column type="selection" width="55"/>
        <el-table-column
            v-for="column in this.tableColumn"
            :key="column.index"
            :label="column.label"
            :prop="column.prop"
            :width="defaultColumn"
            align="center"
        >
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button size="small" @click="this.updateSenClick(scope.row)"
            >修改
            </el-button>
            <el-button
                size="small"
                type="danger"
                @click="deleteOneSen(scope.row.id)"
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
  <!-- 添加一台传感器 -->
  <el-dialog
      v-model="insertDialogVisible"
      title="添加传感器"
      width="30%"
      center
      draggable
  >
    <el-form
        :model="insertSenForm"
        :rules="senRule"
        ref="insertSen"
        @keyup.enter="this.insertOneSen(this.insertSenForm)"
    >
      <el-form-item prop="name" label="传感器名">
        <el-input v-model="this.insertSenForm.name"></el-input>
      </el-form-item>
      <el-form-item prop="equipId" label="选择设备">
        <el-select
            v-model="this.insertSenForm.equipId"
            placeholder="选择设备"
            clearable
        >
          <el-option v-for="item in equMap" :key="item.key" :value="item.value"
          ><span style="float: left">{{ item.label }}</span>
            <span
                style="
                float: right;
                color: var(--el-text-color-secondary);
                font-size: 13px;
              "
            >{{ item.value }}</span
            ></el-option
          >
        </el-select>
      </el-form-item>
      <el-form-item prop="maxValue" label="最大值">
        <el-input-number v-model="this.insertSenForm.maxValue"/>
      </el-form-item>
      <el-form-item prop="minValue" label="最小值">
        <el-input-number v-model="this.updateSenForm.minValue"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="insertDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="this.insertOneSen(this.insertSenForm)"
      >添加
      </el-button>
    </template>
  </el-dialog>
  <!-- 修改传感器 -->
  <el-dialog
      v-model="updateDialogVisible"
      title="修改传感器"
      width="30%"
      center
      draggable
  >
    <el-form
        :model="updateSenForm"
        :rules="senRule"
        ref="updateSen"
        @keyup.enter="this.updateOneSen(this.updateSenForm)"
    >
      <el-form-item prop="name" label="传感器名">
        <el-input v-model="this.updateSenForm.name"></el-input>
      </el-form-item>
      <el-form-item prop="equipId" label="选择设备">
        <el-select
            v-model="this.updateSenForm.equipId"
            placeholder="选择设备"
            clearable
        >
          <el-option v-for="item in equMap" :key="item.key" :value="item.value"
          ><span style="float: left">{{ item.label }}</span>
            <span
                style="
                float: right;
                color: var(--el-text-color-secondary);
                font-size: 13px;
              "
            >{{ item.value }}</span
            ></el-option
          >
        </el-select>
      </el-form-item>
      <el-form-item prop="maxValue" label="最大值">
        <el-input-number v-model="this.updateSenForm.maxValue"/>
      </el-form-item>
      <el-form-item prop="minValue" label="最小值">
        <el-input-number v-model="this.updateSenForm.minValue"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="updateDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="this.updateOneSen(this.updateSenForm)"
      >修改
      </el-button>
    </template>
  </el-dialog>
</template>

<script>
import {
  lookAllSen,
  lookOneSenByName,
  insertOneSen,
  updateOneSen,
  deleteOneSen,
} from "../api/sen";

import {lookAllEqu} from "../api/equ";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询需要的传感器名
      name: "",
      //添加时需要的表单元素
      insertSenForm: {
        name: "",
        maxValue: 1,
        minValue: 1,
        equipId: "",
      },
      //修改时需要的表单元素
      updateSenForm: {
        name: "",
        maxValue: 1,
        minValue: 1,
        equipId: "",
      },
      //添加对话框是否显示
      insertDialogVisible: false,
      //修改对话框是否显示
      updateDialogVisible: false,
      //批量删除时的id集合
      deleteSenIds: [],
      //表单校验
      senRule: {
        //名称
        name: [
          {
            required: true,
            message: "传感器名不得为空!",
            trigger: "blur",
          },
        ],
        //设备ID
        equipId: [
          {
            required: true,
            message: "设备ID不得为空!",
            trigger: "blur",
          },
        ],
        //最大值
        maxValue: [
          {
            required: true,
            message: "最大值不得为空",
            trigger: "change",
          },
        ],
        //最小值
        minValue: [
          {
            required: true,
            message: "最小值不得为空!",
            trigger: "change",
          },
        ],
      },
      //列宽
      defaultColumn: 160,
      //设备列表
      equMap: [],
      //表格列名集合
      tableColumn: [
        {
          index: 1,
          label: "编号",
          prop: "id",
        },
        {
          index: 2,
          label: "传感器ID",
          prop: "equipId",
        },
        {
          index: 3,
          label: "传感器名",
          prop: "name",
        },
        {
          index: 4,
          label: "最大值",
          prop: "maxValue",
        },
        {
          index: 5,
          label: "最小值",
          prop: "minValue",
        },
      ],
    };
  },
  created() {
    this.lookAllSen();
    this.lookAllEqu();
  },
  methods: {
    //查看所有传感器逻辑
    lookAllSen() {
      lookAllSen().then((req) => {
        this.tableData = req.data.data;
        console.log(this.tableData);
      });
    },
    //获取所有设备
    lookAllEqu() {
      lookAllEqu().then((req) => {
        var equList = req.data.data;
        for (let i = 0; i < equList.length; i++) {
          var a = {
            key: i,
            value: equList[i].uid,
            label: equList[i].name,
          };
          this.equMap.push(a);
        }
      });
    },
    //根据传感器名查询传感器
    lookOneSenByName(name) {
      console.log(name);
      if (name === "") {
        this.lookAllSen();
        return;
      }
      lookOneSenByName(name).then((req) => {
        this.tableData = req.data.data;
      });
    },
    //添加一台传感器
    insertOneSen(obj) {
      console.log(obj);
      this.$refs["insertSen"].validate((valid) => {
        if (valid) {
          insertOneSen(obj).then((req) => {
            if (req.data.success) {
              this.$message({
                type: "success",
                message: "添加传感器成功!可继续进行添加!",
                showClose: true,
              });
              this.lookAllSen();
              // this.insertDialogVisible = false;
              // this.insertSenForm = {};
            } else {
              this.$message({
                type: "error",
                message: "添加传感器失败!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    //修改一台传感器
    updateOneSen(obj) {
      console.log(obj);
      this.$refs["updateSen"].validate((valid) => {
        if (valid) {
          updateOneSen(obj).then((req) => {
            if (req.data.success) {
              this.$message({
                type: "success",
                message: "修改传感器成功!",
                showClose: true,
              });
              this.updateDialogVisible = false;
              this.lookAllSen();
            } else {
              this.$message({
                type: "error",
                message: "修改传感器失败!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    //删除一条传感器信息
    deleteOneSen(ids) {
      var id = [ids];
      this.$confirm("你真的要删除这条传感器的信息吗?(真的永久)", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteOneSen(id).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除传感器成功!",
              showClose: true,
            });
            this.lookAllSen();
          } else {
            this.$message({
              type: "error",
              message: "删除传感器失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //批量删除
    deleteSens(ids) {
      console.log(ids);
      this.$confirm(`你真的要删除${ids.length}条传感器的信息吗?(真的永久)`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteOneSen(ids).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除传感器成功!",
              showClose: true,
            });
            this.lookAllSen();
          } else {
            this.$message({
              type: "error",
              message: "删除传感器失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //修改传感器前的附加操作
    updateSenClick(obj) {
      console.log(obj);
      this.updateSenForm = obj;
      this.updateDialogVisible = true;
    },
    //获取选中的Id值
    getSenIds(val) {
      var ids = [];
      for (let i = 0; i < val.length; i++) {
        ids[i] = val[i].id;
      }
      this.deleteSenIds = ids;
    },
  },
};
</script>

<style>
</style>