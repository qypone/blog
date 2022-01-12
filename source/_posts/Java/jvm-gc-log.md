---
title: GC日志分析和调优
description: '待定..'
tags:
  - java
categories:
  - java
abbrlink: bffdd006
date: 2022-01-12 22:54:00
---

[案例类位置]: https://github.com/qypone/learn/blob/main/src/main/java/com/qypone/learn/gc/GCLogAnalysis.java

对比各种GC在1s内创建对象的效率和GC情况，可以调整堆大小（Xmx Xms）看看变化

```sh
## 串行GC
java -XX:+UseSerialGC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

## 并行GC
-XX:+UseParallelGC

## CMS
-XX:+UseConcMarkSweepGC

## G1
-XX:+UseG1GC
```

## 具体案例

...
