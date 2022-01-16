---
title: JVM图形化工具
description: 'jconsole、jvisualvm、VisualGC、jmc'
tags:
  - java
categories:
  - jvm
abbrlink: bffdd008
date: 2021-12-29 23:57:00
---

## jconsole

查看内存、线程、类、jvm相关信息

可以直接执行GC

![image-20220104222219287](jvm-graphicalTool/image-20220104222219287.png)

## jvisualvm

可以dump堆

![image-20220104223212490](jvm-graphicalTool/image-20220104223212490.png)

可以查看线程的状态变化，运行时长，当运行时间比较久，那么可以来排查是否出现问题

![image-20220104223450608](jvm-graphicalTool/image-20220104223450608.png)

抽样器：可以观察一定时间内系统运行的情况，包括CPU和内存使用情况，jconsole只有历史趋势和当前时间点的状况

## VisualGC

插件，idea或eclipse可以安装，图形化展示

![image-20220104224710378](jvm-graphicalTool/image-20220104224710378.png)

## jmc

检测死锁、对象创建速度

飞行记录器：一段时间内jvm情况，存成文件
