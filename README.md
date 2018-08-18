# RongCloudDemo
融云IM学习Demo

> 编辑日期: 2018年8月18日 星期六

#### Android环境

---

1. AndroidStudio 3.0.1
2. com.android.tools.build:gradle:3.0.1
3. JDK1.8
4. compileSdkVersion 27
5. targetSdkVersion 23

<br/>

#### SDK学习内容

---

##### 第一步 配置
1. 导入IMKit,IMLib
2. IMLib的AndroidManifest.xml要配置 **app_key**
3. app模块也要配置AndroidManifest
4. 将 PushLib 中的 jar 包 和 pushDaemon -> libs 目录下
应用所支持平台的 so 拷贝到您应用的 libs 目录下，
另外还需要将 pushDaemon -> executable 目录下各平台
的可执行文件 push_daemon 拷贝到您应用 Module 的 assets 目录下

##### 第二步 初始化
1. RM.init()

##### 第三步 连接融云服务器
1. RM.connect(token,listener)

##### 第四部 聊天
1. 单聊
2. 群聊


#### 常见问题
---

connect 无回调，报找不到 libsqlite.so 异常 解决方法
TargetSdkVersion 为24时，找不到libsqlite.so，导致连上无法连接服务器。 - 融云即时通讯云
http://support.rongcloud.cn/kb/NTQw