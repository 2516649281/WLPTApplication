import axios from "../utils/axios";

//查看所有设备
export function lookAllEqu() {
    return axios.get("/equ");
}

//根据名称值查看设备
export function lookOneEquByName(name) {
    return axios.get(`/equ/${name}`);
}

//添加一条设备
export function insertOneEqu(obj) {
    return axios({
        url: "/equ",
        method: "POST",
        data: obj,
    });
}

//修改一条设备信息
export function updateOneEqu(obj) {
    return axios({
        url: "/equ",
        method: "PUT",
        data: obj,
    });
}

//删除一条设备信息
export function deleteOneEqu(equId) {
    return axios({
        url: "/equ",
        method: "DELETE",
        data: equId,
    });
}
