const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  chainWebpack: (config) => {
    config.module
      .rule("vue")
      .use("vue-loader")
      .tap((options) => {
        options.compilerOptions = {
          ...(options.compilerOptions || {}),
          isCustomElement: (tag) => tag.startsWith("chart"), // 修改为实际的图表组件前缀
        };
        return options;
      });
  },
};