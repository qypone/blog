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

echo "请输入要拉取的分支名称"
echo "0 -> main"
echo "如果是其他分支请直接输入分支名称"

istrue=1

while [ $istrue == 1 ]
do
read barch
tempbarch=""
if [ $barch == 0 ]
then
echo "输入的是main"
tempbarch="main"
else
echo "输入的是其他分支"
tempbarch=$barch
fi
echo $tempbarch
git pull origin $tempbarch
if [ $? == 0 ]
then
istrue=0
echo $istrue
else
echo "请输入正确的分支名称"
fi
done

echo "请输入要上传的分支名称"
echo "0 -> main"
echo "1 -> develop"
echo "如果是其他分支请直接输入分支名称"

istruetwo=1

while [ $istruetwo == 1 ]
do
read barcha
pushbarch=""
if [ $barcha == 0 ]
then
echo "输入的是main"
pushbarch="main"
else
echo "输入的是其他分支"
pushbarch=$barcha
fi
echo $pushbarch
git push origin $pushbarch
if [ $? == 0 ]
then
istruetwo=0
echo $istruetwo
else
echo "请输入正确的分支名称"
fi
done
echo "---END---"
