https://android.googlesource.com/platform/development/+/refs/heads/main/samples/ApiDemos/

# 启用MDM功能
```shell
adb shell dpm set-device-owner com.android.settingc/com.android.settingc.AdminReceiver  
adb shell dpm set-profile-owner com.android.settingc/com.android.settingc.AdminReceiver  
```
# 取消MDM功能
```shell
adb shell dpm remove-active-admin com.android.settingc/com.android.settingc.AdminReceiver  
```