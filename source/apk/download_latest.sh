#!/bin/bash
# Adapted from bitbucket.org/cfib90/ojoc-keyhack

source=$(curl -s "https://apk-dl.com/com.tellm.android.app")
date=$(echo $source | grep -Po "(?<=\"datePublished\" : \").+?(?=\")")
apkurl=$(echo $source | grep -Po "http:\/\/apkfind.com\/root\/apk\/[0-9]+\/[0-9]+\/[0-9]+\/com.tellm.android.app_[0-9]+\.apk")
repoversion=$(echo $source | grep -Po "(?<=\"softwareVersion\" : \").+?(?=\")")
localversion=$(ls -1 | grep -Po '(\d)\.(\d+)\.(\d+)' | sort -nur)

if [ "$repoversion" = "$localversion" ]; then
    echo "No newer version than the local $localversion"
else
    filename="com.tellm.android.app-$repoversion.apk"
    echo "New version($repoversion) from $date" >&2
    wget -O $filename $apkurl"?dl=2"
fi
