# 开发使用的docker相关指令

## 启动
> docker run --name mysql_jeecg -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7 --lower_case_table_names=1

> docker run -d --name redis_jeecg -p 6379:6379 redis --requirepass “redis123456”

## 进入mysql容器
> docker exec it 011e02dcb43d /bin/bash

## 导出mysqldump文件（用这个......）
> docker exec -it mysql_wash mysqldump -uroot -pmysql jeecg-wash2 > E:\gitee\gen-code/wash_04.sql

## docker中导出mysqldump文件
> mysqldump -uroot -p123456 jeecg-boot > wash_03.sql

## 复制本地到docker
> docker cp ./wash_01.sql mysql_jeecg:/bin/wash_01.sql

## 复制docker到本地
> docker cp mysql_jeecg:/bin/wash_01.sql E:\gitee\gen-code/wash_01.sql

## 推送镜像
> docker tag mysql:5.7 qypone/jeecg-mysql-wash:v0.1
> 
> docker push qypone/jeecg-mysql-wash:v0.1

## 为什么docker推送的mysql没有数据
> https://www.jianshu.com/p/530d00f97cbf