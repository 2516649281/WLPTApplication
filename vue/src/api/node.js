import axios from "../utils/axios";

//获取传感器数据
export function getNode(uid) {
    return axios.get(`/node/${uid}`);
}

//设置执行器数据
export function setNode(uid, index) {
    return axios.get(`/node?uid=${uid}&index=${index}`);
}
