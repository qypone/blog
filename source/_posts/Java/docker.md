---
title: docker实际使用记录
tags:
  - java
categories:
  - docker
abbrlink: jvm-memory
date: 2022-08-05 23:16:00
---

# 开发使用的docker相关指令
## 启动
> docker run --name mysql_jeecg -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7 --lower_case_table_names=1

> docker run -d --name redis_jeecg -p 6379:6379 redis --requirepass “redis123456”

## docker中导出mysqldump文件
> mysqldump -uroot -p123456 jeecg-boot > wash_01.sql

## 导出mysqldump文件
> docker exec -it mysql_jeecg mysqldump -uroot -p123456 jeecg-boot > E:\gitee\gen-code/wash_02.sql

## 复制本地到docker
> docker cp ./wash_01.sql mysql_jeecg:/bin/wash_01.sql

## 复制docker到本地
> docker cp mysql_jeecg:/bin/wash_01.sql E:\gitee\gen-code/wash_01.sql