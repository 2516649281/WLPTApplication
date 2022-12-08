import axios from "../utils/axios";

//查看所有执行器配置
export function lookAllAct() {
    return axios.get("/act");
}

//根据名称值查看执行器配置
export function lookOneActByName(name) {
    return axios.get(`/act/${name}`);
}

//添加一条执行器配置
export function insertOneAct(obj) {
    return axios({
        url: "/act",
        method: "POST",
        data: obj,
    });
}

//修改一条执行器配置信息
export function updateOneAct(obj) {
    return axios({
        url: "/act",
        method: "PUT",
        data: obj,
    });
}

//删除一条执行器配置信息
export function deleteOneAct(actId) {
    return axios({
        url: "/act",
        method: "DELETE",
        data: actId,
    });
}
