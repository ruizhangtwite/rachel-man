### 欢迎阅读

@(个人学习)[Spring FrameWork|Spring Boot| Redis]

本仓库主要是为了**个人学习所用**，是个人开发过程中的杂记，一些技术的积累，结合个人笔记，以备个人后期查阅！
 ----------


## 仓库项目内容简介

分别用于对仓库中的不同项目涉及点进行说明

--------
- **[Spring+SpingMVC+Mybatis全注解案例](https://github.com/ruizhangtwite/RachelMan/tree/master/AnnotationConfigLession)**
采用Java Config的方式改写Spring MVC xml形式实现的后台业务
- **[基于Zookeeper的分布式锁](https://github.com/ruizhangtwite/RachelMan/tree/master/DistributeLock)**
借用zookeeper实现的分布式锁，实现了两种方式的分布式锁，一种是借助于zookeeper中在同一节点层次下不能有相同的两个节点（不足是会出现惊群效应），另一种是创建临时顺序节点，只针对未获取锁的节点的上一个节点添加监听器的原理
- **[模拟Redis哨兵（Sentinel）实现机制](https://github.com/ruizhangtwite/RachelMan/tree/master/MyRedisClient)**
分析Redis所遵循的RESP协议，模拟Redis客户端的实现；Redis哨兵机制的模拟实现
- **[Spring+Redis xml配置化](https://github.com/ruizhangtwite/RachelMan/tree/master/SpringRedisDemo)**
Spring+Redis xml配置化
- **[SpringBoot WebService](https://github.com/ruizhangtwite/RachelMan/tree/master/webservices-study)**
WebService规范；注解实现WebService的配置
- **[数据库连接池](https://github.com/ruizhangtwite/RachelMan/tree/master/ThreadPoolUtil)**
WebService规范；模仿MyBatis，借助BlockingQueue，自实现数据库连接池
- **[Tomcat原理自实现](https://github.com/ruizhangtwite/rachel_man/tree/master/TomcatSimpleAnalysis)**
模拟Tomcat加载Servlet；Connector处理IO请求
