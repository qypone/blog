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

# echo "正在pull远程代码..."
# git pull origin main
echo "正在push远程代码..."
git push origin main

echo "---END---"
