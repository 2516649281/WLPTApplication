import axios from "../utils/axios";

//查看所有传感器配置
export function lookAllSen() {
    return axios.get("/sen");
}

//根据名称值查看传感器配置
export function lookOneSenByName(name) {
    return axios.get(`/sen/${name}`);
}

//添加一条传感器配置
export function insertOneSen(obj) {
    return axios({
        url: "/sen",
        method: "POST",
        data: obj,
    });
}

//修改一条传感器配置信息
export function updateOneSen(obj) {
    return axios({
        url: "/sen",
        method: "PUT",
        data: obj,
    });
}

//删除一条传感器配置信息
export function deleteOneSen(senId) {
    return axios({
        url: "/sen",
        method: "DELETE",
        data: senId,
    });
}
