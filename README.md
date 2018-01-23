JodelPatched
============
> The JodelPatched mod provides additional features to Jodel.

**Disclaimer:** *This project is not affiliated with Jodel or Jodel Venture GmbH.*

![](https://user-images.githubusercontent.com/5908498/35289623-23110e2a-0068-11e8-9ff5-fdcab6d168ba.png)

# Usage


## Installation
Visit our [Telegram channel](https://t.me/joinchat/Ahz5YERU5uIIPM5O_v_XSg) for the latest releases.

To best enjoy the spoofing feature and the account backup we recommend installing our companion app, *JodelTools*.

1. Install JodelTools and backup your account if you'd like to keep it
2. Uninstall any previous Jodel/Patched
3. Install the newest release


## Features
1. Toggle beta features
2. Spoof location (applied only to Jodel)
3. *JodelTools* required
4. Upload from gallery
5. No blur on images
6. Save image by double tap
7. Images are saved to `/sdcard/JodelPatched/images`.
8. Copy text by double tap
9. Account backup
   1. *JodelTools* required
   2. Account backup is saved to `/sdcard/JodelPatched/udi`

### Account backup

`UDI` stands for *Unique Device Identifier*. This id is generated by the Jodel app and is used to identify an account. It is used by the built-in backup to restore an account on a new device, however, this backup isn't supported by JodelPatched.

*Instead* you can use our companion app *JodelTools* to backup your account. JodelTools can backup your account to file. The file is then automatically utilized when JodelPatched is first launched.

*Do not share your udi file with anyone.*


# Development
If you're more interested in development and the technical stuff then read ahead!

##### Guidelines

Bans, analytics and regular user data collection is not intended to be circumvented. Abuse of the Jodel platform is not endorsed.

## Quick start

1. Place source `.apk` into `source/apk/`
2. Clone [dexpatcher-gradle-tools](https://github.com/DexPatcher/dexpatcher-gradle-tools) into a nearby folder
3. Reference the `dexpatcher-gradle-tools` in `./local.properties` like [the example](local.example.properties)
4. Run the `patched/installDebug` gradle task to install on device
5. Run the `cleanAll` gradle task upon apk update

## Debug features

By default the app supports debugging via Setho. Connect your device via `adb` and navigate to `chrome://inspect`. Among things you can inspect *requests* and *resources*.

## Features and files

*TBA*
