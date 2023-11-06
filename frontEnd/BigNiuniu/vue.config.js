const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack: (config) => {
    config.plugin("define").tap((definitions) => {
      Object.assign(definitions[0]["process.env"], {
        NODE_HOST: '"http://localhost:8080"',
      });
      return definitions;
    });
  },
  devServer: {
    allowedHosts: "all",
    // client: {
    //   //启动检测
    //   overlay: true,
    // },
  },
});
