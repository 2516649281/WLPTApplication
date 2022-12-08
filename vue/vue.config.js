const {defineConfig} = require("@vue/cli-service");
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: "8000", // 设置端口号
        proxy: {
            "/api": {
                target: process.env.VUE_APP_BASE_URL, //API服务器的地址
                ws: true, //代理websockets
                changeOrigin: true, // 是否跨域，虚拟的站点需要更管origin
                pathRewrite: {
                    "^/api": "",
                },
            },
        },
    },
    assetsDir: "static",
    parallel: false,
    publicPath: "./",
});
