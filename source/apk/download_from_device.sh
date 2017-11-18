#!/bin/bash
apkinfo () {
	aapt dump badging $1 | grep --color -Po "(versionCode|versionName|platformBuildVersionName|targetSdkVersion|sdkVersion)[:=]'.*?'"
}

package="com.tellm.android.app"
file=$(adb shell pm path $package | grep -Po '/.*')
adb pull $file
versionName=$(apkinfo base.apk | grep -Po "(?<=versionName=')(\d*.\d*.\d*)")
filename="$package-$versionName.apk"
mv base.apk $filename
echo "Saved apk as $filename"

