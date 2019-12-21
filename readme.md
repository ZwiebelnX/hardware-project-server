## 关于这个项目

本项目是南京理工大学计算机科学与工程学院2016级硬件课程设计III中，机械臂选题的上层软件。包含后端代码与build之后的前端页面。

前端页面暂时不打算放出，并在前端中加有成员小组标识。**请想要借鉴的同学斟酌采用**

串口通讯方面参考了2015级学长的相关代码。

## 项目开发框架

+ 前端：
Vue.js + Vuex + Element
+ 后端：
基于SpringBoot的SpringWeb + RXTX串口通讯

## 项目结构

需要说明一点，本项目中的机械臂位置信息查询等信息获取功能原本打算使用WebSocket完成。但由于时间原因，采用了http轮询策略代替。

故项目结构中的WebSocket相关设置无效。

```
│  HardwareServerApplication.java   // mian类
│  
├─config
│      WebSocketConfig.java           
│      
├─controller
│      ArmController.java           // http请求转发控制器
│      
├─module
│  ├─bo
│  │      ArmPosition.java          // 机械臂动作类型枚举类
│  │      ArmPositionInfoBO.java    // 机械臂动作BO
│  │      
│  └─dto                            // 与前端相关的数据传输类
│          AxisControlDTO.java
│          DigitalControlDTO.java
│          RemoteControlDTO.java
│          SendCharDTO.java
│          
├─service
│      ArmService.java              // 逻辑处理服务
│      
└─utils
    ├─arm
    │      CommAccessor.java        // 串口通讯核心类 
    │      CommConfig.java          // 串口相关设置
    │      
    └─socket
            MyWebSocketHandler.java
            MyWebSocketHandlerInterceptor.java
```



