import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "element-plus/dist/index.css";
import ElementUI from "element-plus";
import ElementPlusIconsVue from "@element-plus/icons-vue";
import JsonViewer from "vue-json-viewer";

createApp(App)
    .use(store)
    .use(router)
    .use(ElementUI)
    .use(ElementPlusIconsVue)
    .use(JsonViewer)
    .mount("#app");
