几个模块的作用：
1.initializer：全量数据初始化程序（hfile生成加载程序）
2.increment：增量数据加载程序（tar包扫描解压程序，解析文件入库程序，调度任务维护程序）
3.coprocessor：hbase协处理器（hbase到处hdfs程序）
4.controller：调度控制器（消息触发程序，任务配置解析程序，任务依赖检查程序，hbase协处理器调用程序，任务执行程序，任务维护程序）
5.sqlexecutor：sql执行程序（任务配置解析程序，SQL执行程序，结果输出程序）