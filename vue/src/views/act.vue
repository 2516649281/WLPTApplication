<template>
  <el-row>
    <el-col :span="3">
      <el-button type="warning" @click="this.deleteActs(this.deleteActIds)"
      >批量删除
      </el-button>
    </el-col>
    <el-col :span="5">
      <el-input v-model="name" clearable placeholder="输入名称以查询">
      </el-input>
    </el-col>
    <el-col :span="3">
      <el-button type="primary" @click="this.lookOneActByName(this.name)"
      >查询
      </el-button>
    </el-col>
    <el-col :span="3">
      <el-button type="primary" @click="this.insertDialogVisible = true"
      >添加执行器
      </el-button>
    </el-col>
  </el-row>
  <el-row>
    <el-col>
      <el-table
          height="500"
          :data="tableData"
          border
          @selection-change="getActIds"
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
            <el-button size="small" @click="this.updateActClick(scope.row)"
            >修改
            </el-button>
            <el-button
                size="small"
                type="danger"
                @click="deleteOneAct(scope.row.id)"
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
  <!-- 添加一台执行器 -->
  <el-dialog
      v-model="insertDialogVisible"
      title="添加执行器"
      width="30%"
      center
      draggable
  >
    <el-form
        :model="insertActForm"
        :rules="actRule"
        ref="insertAct"
        @keyup.enter="this.insertOneAct(this.insertActForm)"
    >
      <el-form-item prop="name" label="执行器名">
        <el-input
            v-model="this.insertActForm.name"
            placeholder="输入执行器名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="equipId" label="选择设备">
        <el-select
            v-model="this.insertActForm.equipId"
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
      <el-form-item prop="valueList" label="取值范围">
        <el-input
            v-model="this.insertActForm.valueList"
            placeholder="例如:[1,2,3]"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="insertDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="this.insertOneAct(this.insertActForm)"
      >添加
      </el-button>
    </template>
  </el-dialog>
  <!-- 修改执行器 -->
  <el-dialog
      v-model="updateDialogVisible"
      title="修改执行器"
      width="30%"
      center
      draggable
  >
    <el-form
        :model="updateActForm"
        :rules="actRule"
        ref="updateAct"
        @keyup.enter="this.updateOneAct(this.updateActForm)"
    >
      <el-form-item prop="name" label="执行器名">
        <el-input
            v-model="this.updateActForm.name"
            placeholder="输入执行器名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="equipId" label="选择设备">
        <el-select
            v-model="this.updateActForm.equipId"
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
      <el-form-item prop="valueList" label="取值范围">
        <el-input
            v-model="this.updateActForm.valueList"
            placeholder="例如:[1,2,3]"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="updateDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="this.updateOneAct(this.updateActForm)"
      >修改
      </el-button>
    </template>
  </el-dialog>
</template>

<script>
import {
  lookAllAct,
  lookOneActByName,
  insertOneAct,
  updateOneAct,
  deleteOneAct,
} from "../api/act";

import {lookAllEqu} from "../api/equ";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询需要的执行器名
      name: "",
      //添加时需要的表单元素
      insertActForm: {
        name: "",
        valueList: "",
        equipId: "",
      },
      //修改时需要的表单元素
      updateActForm: {
        name: "",
        valueList: "",
        equipId: "",
      },
      //添加对话框是否显示
      insertDialogVisible: false,
      //修改对话框是否显示
      updateDialogVisible: false,
      //批量删除时的id集合
      deleteActIds: [],
      //表单校验
      actRule: {
        //名称
        name: [
          {
            required: true,
            message: "执行器名不得为空!",
            trigger: "blur",
          },
        ],
        //设备ID
        equipId: [
          {
            required: true,
            message: "请选择设备!",
            trigger: "blur",
          },
        ],
        //取值范围
        valueList: [
          {
            required: true,
            message: "取值范围不得为空!",
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
          label: "设备ID",
          prop: "equipId",
        },
        {
          index: 3,
          label: "执行器名",
          prop: "name",
        },
        {
          index: 4,
          label: "取值范围",
          prop: "valueList",
        },
      ],
    };
  },
  created() {
    this.lookAllAct();
    this.lookAllEqu();
  },
  methods: {
    //查看所有执行器逻辑
    lookAllAct() {
      lookAllAct().then((req) => {
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
    //根据执行器名查询执行器
    lookOneActByName(name) {
      console.log(name);
      if (name === "") {
        this.lookAllAct();
        return;
      }
      lookOneActByName(name).then((req) => {
        this.tableData = req.data.data;
      });
    },
    //添加一台执行器
    insertOneAct(obj) {
      console.log(obj);
      this.$refs["insertAct"].validate((valid) => {
        if (valid) {
          insertOneAct(obj).then((req) => {
            if (req.data.success) {
              this.$message({
                type: "success",
                message: "添加执行器成功!可继续进行添加!",
                showClose: true,
              });
              this.lookAllAct();
              // this.insertDialogVisible = false;
              // this.insertActForm = {};
            } else {
              this.$message({
                type: "error",
                message: "添加执行器失败!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    //修改一台执行器
    updateOneAct(obj) {
      console.log(obj);
      this.$refs["updateAct"].validate((valid) => {
        if (valid) {
          updateOneAct(obj).then((req) => {
            if (req.data.success) {
              this.$message({
                type: "success",
                message: "修改执行器成功!",
                showClose: true,
              });
              this.updateDialogVisible = false;
              this.lookAllAct();
            } else {
              this.$message({
                type: "error",
                message: "修改执行器失败!",
                showClose: true,
              });
            }
          });
        }
      });
    },
    //删除一条执行器信息
    deleteOneAct(ids) {
      var id = [ids];
      this.$confirm("你真的要删除这条执行器的信息吗?(真的永久)", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteOneAct(id).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除执行器成功!",
              showClose: true,
            });
            this.lookAllAct();
          } else {
            this.$message({
              type: "error",
              message: "删除执行器失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //批量删除
    deleteActs(ids) {
      console.log(ids);
      this.$confirm(`你真的要删除${ids.length}条执行器的信息吗?(真的永久)`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteOneAct(ids).then((req) => {
          if (req.data.success) {
            this.$message({
              type: "success",
              message: "删除执行器成功!",
              showClose: true,
            });
            this.lookAllAct();
          } else {
            this.$message({
              type: "error",
              message: "删除执行器失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //修改执行器前的附加操作
    updateActClick(obj) {
      console.log(obj);
      this.updateActForm = obj;
      this.updateDialogVisible = true;
    },
    //获取选中的Id值
    getActIds(val) {
      var ids = [];
      for (let i = 0; i < val.length; i++) {
        ids[i] = val[i].id;
      }
      this.deleteActIds = ids;
    },
  },
};
</script>

<style>
</style>