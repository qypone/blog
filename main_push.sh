#!/bin/bash
git add --all
echo "请输入提交描述文字如果没有默认: update"
read describe
if [ ! -n "$describe" ]
then
describe="update"
fi
echo $describe
git commit -m $describe

echo "正在pull远程代码..."
git pull origin main
echo "正在push远程代码..."
git push origin main

echo "请输入要拉取的分支名称"
echo "0 -> main"
echo "如果是其他分支请直接输入分支名称"

echo "---END---"
