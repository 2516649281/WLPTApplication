<template>
  <el-row>
    <el-col :span="3">
      <el-menu :default-active="1" text-color="#fff" background-color="#545c64">
        <el-menu-item
            v-for="item in senMenu"
            :key="item.id"
            :index="item.id"
            @click="this.getPath(item)"
        >
          <span>{{ item.name }}</span>
        </el-menu-item>
        <el-menu-item
            v-for="item in actMenu"
            :key="item.id + senMenu.length"
            :index="item.id + senMenu.length"
            @click="this.setPath(item)"
        >
          <span>{{ item.name }}</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="21">
      <el-container>
        <el-main>
          <el-descriptions
              title="设备概览"
              direction="vertical"
              :column="4"
              :size="size"
              border
          >
            <el-descriptions-item label="设备名">{{
                currentSen.name
              }}
            </el-descriptions-item>
            <el-descriptions-item label="设备ID">{{
                currentSen.equipId
              }}
            </el-descriptions-item>
          </el-descriptions>
          <el-divider/>
          <el-descriptions
              title="请求"
              direction="vertical"
              :column="2"
              :size="size"
              border
          >
            <el-descriptions-item label="参数"
            >设备ID(uid)
            </el-descriptions-item
            >
            <el-descriptions-item label="取值">{{
                this.currentType == 0 ? currentSen.equipId : currentAct.equipId
              }}
            </el-descriptions-item>
            <div v-if="currentType == 1">
              <el-descriptions-item label="参数"
              >键(index)
              </el-descriptions-item
              >
              <el-descriptions-item label="取值范围">{{
                  currentAct.valueList
                }}
              </el-descriptions-item>
            </div>

            <el-descriptions-item label="基本格式">{{
                this.currentType == 0
                    ? "http://{ip地址}:{端口号}/{设备ID}"
                    : "http://{ip地址}:{端口号}?uid={设备ID}&index={键值}"
              }}
            </el-descriptions-item>
          </el-descriptions>
          <el-divider/>
          <el-descriptions
              title="响应"
              direction="vertical"
              :column="3"
              :size="size"
              border
          >
            <el-descriptions-item label="状态码">status</el-descriptions-item>
            <el-descriptions-item label="消息">message</el-descriptions-item>
            <el-descriptions-item label="数据">data</el-descriptions-item>
            <el-descriptions-item label="测试"
            >
              <el-button type="primary" @click="resposeDialogVisible = true"
              >点击测试
              </el-button
              >
            </el-descriptions-item
            >
            <el-descriptions-item label="基本格式"
            ><code
            >{ "status":200, "message":"请求已完成!", "data":{ } }</code
            ></el-descriptions-item
            >
          </el-descriptions>
          <el-divider/>
          <el-dialog
              v-model="resposeDialogVisible"
              title="响应"
              width="30%"
              center
          >
            路径:
            <el-link type="primary" :href="currentSenURL">{{
                currentSenURL
              }}
            </el-link
            >
            <el-divider/>
            <el-tooltip :disabled="currentType == 0"
            >
              <template #content>
                index的取值:{{ currentAct.valueList }}
              </template>
              <div v-if="currentType == 1">
                键值:
                <el-input v-model="indexKey"></el-input>
                <el-divider/>
              </div>
            </el-tooltip>
            响应:
            <json-viewer :value="jsonData"></json-viewer>
            <template #footer>
              <el-button @click="resposeDialogVisible = false">关闭</el-button>
              <el-button
                  type="primary"
                  @click="
                  currentType == 0
                    ? getNode(currentSen.equipId)
                    : setNode(currentAct.equipId, indexKey)
                "
              >
                发送请求
              </el-button>
            </template>
          </el-dialog>
        </el-main>
      </el-container>
    </el-col>
  </el-row>
</template>

<script>
import {lookAllAct} from "../api/act";
import {lookAllSen} from "../api/sen";
import {getNode, setNode} from "../api/node";

export default {
  data() {
    return {
      indexKey: 0,
      currentType: 0,
      jsonData: "",
      currentSenURL: "",
      currentActURL: "",
      url: process.env.VUE_APP_BASE_URL,
      senMenu: [],
      actMenu: [],
      currentSen: {
        index: "",
        name: "",
        equipId: "",
        max: "",
        min: "",
      },
      currentAct: {
        index: "",
        name: "",
        equipId: "",
        valueList: "",
      },
      resposeDialogVisible: false,
    };
  },
  created() {
    this.getEqu();
    this.getPath(this.currentSen);
  },
  methods: {
    //改变主界面
    getPath(obj) {
      this.currentType = 0;
      this.currentSen = obj;
      this.currentSenURL = `${this.url}/node/${this.currentSen.equipId}`;
    },
    //改变主界面
    setPath(obj) {
      this.currentType = 1;
      this.currentAct = obj;
      this.currentSenURL = `${this.url}/node?uid=${this.currentAct.equipId}&index=${this.indexKey}`;
    },
    //获取设备
    getEqu() {
      lookAllSen().then((req) => {
        this.senMenu = req.data.data;
        this.currentSen = req.data.data[0];
      });
      lookAllAct().then((req) => {
        this.actMenu = req.data.data;
        this.currentAct = req.data.data[0];
      });
    },
    getNode(equipId) {
      getNode(equipId).then((req) => {
        this.jsonData = req.data;
      });
    },
    setNode(equipId, index) {
      setNode(equipId, index).then((req) => {
        this.jsonData = req.data;
      });
    },
  },
  watch: {
    indexKey: function (newVal, oldVal) {
      this.currentSenURL = `${this.url}node?uid=${this.currentAct.equipId}&index=${newVal}`;
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