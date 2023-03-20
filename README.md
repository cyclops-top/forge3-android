# Forge3 android （开发中）
> `forge` 是本人单独开发的一个内部使用apk管理工具，目前重构第三版，该项目为 `forge` 配套的`Android`客户端（PS. 未完成），
> 后台服务使用`Spring boot（kotlin）`开发，
> 后台管理界面目前计划使用`Angular`进行开发

## 特点
- 使用Google推荐框架，将项目分为`数据层`，`网域层`（非必须），`界面层`
- 将功能详细分为不同模块，自定义`build gradle`插件，将不同功能模块，相同引用集合到一起。如不同模块的数据层，基本只需要引用`forge.android.data`插件即可，如有其他单独使用的库，再自行添加
- 统一管理所有的第三方应用库版本
- 所有模块对外暴露基本以接口形式，进行解耦，并提供`hilt`注入，开发过程中不必关心引用到的模块内部实现。如调用网络请求都是对外暴露`XXXApi`的接口文件，并不关心是使用的哪种技术，比如retrofit或者应用的第三方库（转内部实现接口）
- 完全遵循单向数据流（UDF）原则进行开发

## 使用技术
- UI 使用 `Jetpack Compose` 
- 数据
  - 本地数据 `Room`、`datastore`
  - 远程 `kotlin 协程` + `Retofit` + `Okhttp(引擎使用Cronet，目前仅为测试随时可修改)`
- 框架 `MVVM`
- DI `hilt`
- 导航 `navigation`
- 主题 `material` + `material3`(优先使用)
- 序列化 `kotlinx-serialization`
- 启动 `startup`
- 列表 `paging3`
