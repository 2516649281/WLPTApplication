import axios from "../utils/axios";

//登陆
export function login(username, password) {
    return axios({
        url: "/user/login",
        method: "POST",
        params: {
            username: username,
            password: password,
        },
    });
}

//注册
export function register(username, password) {
    return axios({
        url: "/user/register",
        method: "POST",
        params: {
            username: username,
            password: password,
        },
    });
}

//查看所有用户
export function lookAllUser() {
    return axios.get("/user");
}

//根据id值查看用户
export function lookUserById(userId) {
    return axios.get(`/user/${userId}`);
}

//修改一条用户信息
export function updateOneUser(obj) {
    return axios({
        url: "/user",
        method: "PUT",
        data: obj,
    });
}

//删除一条用户信息
export function deleteOneUser(userId) {
    return axios({
        url: "/user",
        method: "DELETE",
        data: userId,
    });
}
