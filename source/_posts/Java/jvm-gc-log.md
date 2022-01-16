---
title: GC日志分析和调优
description: '待定..'
tags:
  - java
categories:
  - jvm
abbrlink: bffdd006
date: 2022-01-12 22:54:00
---

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

> 注意：jdk 11 很多参数已经改变，比如-XX:+PrintGCDateStamps 可以用-Xlog:gc::utctime 替换等，如果在jdk11情况下使用-XX:+PrintGCDateStamps会报错

[案例类位置]: https://github.com/qypone/learn/blob/main/src/main/java/com/qypone/learn/gc/GCLogAnalysis.java	"案例类位置GCLogAnalysis.java"

## 具体案例

观察使用不同gc、不同堆大小时gc次数、gc使用时间、创建对象数量（类比吞吐量）

```sh
## -XX:+UseParallelGC -Xms512m -Xmx512m -Xlog:gc::utctime
[2022-01-13T08:29:34.525+0000] Using Parallel
正在执行...
[2022-01-13T08:29:34.744+0000] GC(0) Pause Young (Allocation Failure) 128M->46M(491M) 11.421ms
[2022-01-13T08:29:34.792+0000] GC(1) Pause Young (Allocation Failure) 175M->91M(491M) 16.296ms
[2022-01-13T08:29:34.838+0000] GC(2) Pause Young (Allocation Failure) 219M->139M(491M) 16.988ms
[2022-01-13T08:29:34.870+0000] GC(3) Pause Young (Allocation Failure) 268M->189M(491M) 14.530ms
[2022-01-13T08:29:34.917+0000] GC(4) Pause Young (Allocation Failure) 317M->236M(491M) 14.767ms
[2022-01-13T08:29:34.948+0000] GC(5) Pause Young (Allocation Failure) 365M->281M(420M) 13.197ms
[2022-01-13T08:29:34.980+0000] GC(6) Pause Young (Allocation Failure) 338M->301M(455M) 7.924ms
[2022-01-13T08:29:34.996+0000] GC(7) Pause Young (Allocation Failure) 358M->319M(455M) 9.343ms
[2022-01-13T08:29:35.011+0000] GC(8) Pause Young (Allocation Failure) 376M->340M(455M) 11.656ms
[2022-01-13T08:29:35.066+0000] GC(9) Pause Full (Ergonomics) 340M->225M(455M) 44.972ms
[2022-01-13T08:29:35.074+0000] GC(10) Pause Young (Allocation Failure) 283M->252M(455M) 4.573ms
[2022-01-13T08:29:35.090+0000] GC(11) Pause Young (Allocation Failure) 309M->270M(455M) 7.438ms
[2022-01-13T08:29:35.121+0000] GC(12) Pause Young (Allocation Failure) 327M->293M(455M) 7.376ms
[2022-01-13T08:29:35.137+0000] GC(13) Pause Young (Allocation Failure) 350M->316M(455M) 8.412ms
[2022-01-13T08:29:35.169+0000] GC(14) Pause Full (Ergonomics) 316M->254M(455M) 40.576ms
[2022-01-13T08:29:35.184+0000] GC(15) Pause Young (Allocation Failure) 311M->273M(455M) 3.663ms
[2022-01-13T08:29:35.200+0000] GC(16) Pause Young (Allocation Failure) 330M->290M(455M) 5.928ms
[2022-01-13T08:29:35.215+0000] GC(17) Pause Young (Allocation Failure) 347M->311M(455M) 6.460ms
[2022-01-13T08:29:35.267+0000] GC(18) Pause Full (Ergonomics) 311M->264M(455M) 42.320ms
[2022-01-13T08:29:35.278+0000] GC(19) Pause Young (Allocation Failure) 321M->288M(455M) 4.945ms
[2022-01-13T08:29:35.294+0000] GC(20) Pause Young (Allocation Failure) 345M->311M(455M) 7.811ms
[2022-01-13T08:29:35.325+0000] GC(21) Pause Young (Allocation Failure) 368M->329M(455M) 7.420ms
[2022-01-13T08:29:35.367+0000] GC(22) Pause Full (Ergonomics) 329M->276M(455M) 43.999ms
[2022-01-13T08:29:35.373+0000] GC(23) Pause Young (Allocation Failure) 334M->302M(455M) 4.605ms
[2022-01-13T08:29:35.404+0000] GC(24) Pause Young (Allocation Failure) 360M->326M(455M) 8.467ms
[2022-01-13T08:29:35.454+0000] GC(25) Pause Full (Ergonomics) 326M->291M(455M) 48.146ms
[2022-01-13T08:29:35.469+0000] GC(26) Pause Young (Allocation Failure) 348M->313M(455M) 4.236ms
[2022-01-13T08:29:35.483+0000] GC(27) Pause Young (Allocation Failure) 370M->332M(455M) 7.558ms
[2022-01-13T08:29:35.530+0000] GC(28) Pause Full (Ergonomics) 332M->302M(455M) 54.121ms
[2022-01-13T08:29:35.597+0000] GC(29) Pause Full (Ergonomics) 359M->308M(455M) 52.551ms
[2022-01-13T08:29:35.671+0000] GC(30) Pause Full (Ergonomics) 366M->313M(455M) 53.099ms
执行结束！ 共生成对象次数:8837

Process finished with exit code 0


## 使用并行GC调大堆内存以后
## -XX:+UseParallelGC -Xms2g -Xmx2g -Xlog:gc::utctime
[2022-01-13T08:36:04.045+0000] Using Parallel
正在执行...
[2022-01-13T08:36:04.428+0000] GC(0) Pause Young (Allocation Failure) 512M->135M(1963M) 29.344ms
[2022-01-13T08:36:04.554+0000] GC(1) Pause Young (Allocation Failure) 647M->258M(1963M) 37.316ms
[2022-01-13T08:36:04.681+0000] GC(2) Pause Young (Allocation Failure) 770M->385M(1963M) 38.086ms
[2022-01-13T08:36:04.797+0000] GC(3) Pause Young (Allocation Failure) 897M->493M(1963M) 34.733ms
[2022-01-13T08:36:04.923+0000] GC(4) Pause Young (Allocation Failure) 1005M->615M(1963M) 42.802ms
[2022-01-13T08:36:05.047+0000] GC(5) Pause Young (Allocation Failure) 1128M->737M(1678M) 38.070ms
[2022-01-13T08:36:05.098+0000] GC(6) Pause Young (Allocation Failure) 964M->792M(1820M) 23.323ms
[2022-01-13T08:36:05.177+0000] GC(7) Pause Young (Allocation Failure) 1019M->843M(1820M) 32.086ms
执行结束！ 共生成对象次数:16649

Process finished with exit code 0

## 使用CMS保持内存不变
## -XX:+UseConcMarkSweepGC -Xms2g -Xmx2g -Xlog:gc::utctime
[2022-01-13T08:42:30.758+0000] Using Concurrent Mark Sweep
Java HotSpot(TM) 64-Bit Server VM warning: Option UseConcMarkSweepGC was deprecated in version 9.0 and will likely be removed in a future release.
正在执行...
[2022-01-13T08:42:31.175+0000] GC(0) Pause Young (Allocation Failure) 546M->158M(1979M) 33.897ms
[2022-01-13T08:42:31.309+0000] GC(1) Pause Young (Allocation Failure) 704M->286M(1979M) 38.614ms
[2022-01-13T08:42:31.462+0000] GC(2) Pause Young (Allocation Failure) 832M->409M(1979M) 69.045ms
[2022-01-13T08:42:31.625+0000] GC(3) Pause Young (Allocation Failure) 955M->540M(1979M) 73.136ms
[2022-01-13T08:42:31.783+0000] GC(4) Pause Young (Allocation Failure) 1087M->669M(1979M) 70.563ms
[2022-01-13T08:42:31.954+0000] GC(5) Pause Young (Allocation Failure) 1216M->804M(1979M) 75.068ms
[2022-01-13T08:42:31.954+0000] GC(6) Pause Initial Mark 815M->815M(1979M) 0.173ms
[2022-01-13T08:42:31.954+0000] GC(6) Concurrent Mark
执行结束！ 共生成对象次数:15471
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Mark 4.449ms
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Preclean
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Preclean 1.941ms
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Abortable Preclean
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Abortable Preclean 0.009ms
[2022-01-13T08:42:31.969+0000] GC(6) Pause Remark 820M->820M(1979M) 1.483ms
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Sweep
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Sweep 2.025ms
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Reset
[2022-01-13T08:42:31.969+0000] GC(6) Concurrent Reset 5.784ms

Process finished with exit code 0
```



