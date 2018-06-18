exchange
========

中文说明文档 [README_CN.md][5] 

This is a plugin based translator for typed text translate, 
such as android ```strings.xml``` or ios ```Localizable.strings```.
Now it official support android and ios strings resources document 
translate plugins, and official support google, baidu and youdao 
translate platform plugins. You can simply use it.

For more details you can see the [wiki][1].

You can simply use like this:

```bash
~/sh run.sh
```

then the file will translate to your specified out path.

## USE

1. First download release zip file here [LATEST RELEASE][2].
2. Install JRE or JDK to run this tools. You can download here [JDK DOWNLOAD][3].
3. Config ```translate_config.ini``` file in the RELEASE zip file root path.
    + ```translatePlatform``` choose the translate platform, official support ```google```, ```baidu``` and ```youdao```.
    + Translate platform may need you register developer account to access translate api, 
    this ```appId```, ```appKey``` and ```apiUrl``` three param you can get it form translate 
    platform api doc. ```others``` is a list of param, now it doesn't use, it will pass to your 
    plugin, if you need some params in config you can set it here.
    + **baidu** need ```appId```, ```appKey``` and ```apiUrl```, you should replace it with your own key.
    + **google** just need ```appKey``` and ```apiUrl```.
    + **youdao** just like **baidu**.
    + ```apiUrl``` this param may not change, it just support you a way to change if need.
    + ```textType``` set the source typed text type, official support android and ios strings doc.
    + ```sourceLanguage``` source language
    + ```destinationLanguage``` destination language, the value you can find in ```support_language.txt```.
    It can be a single value, or a list value, or special "all" value.
    + ```sourceFilePath``` translate file path, plugin will scan the path to translate. 
    it should in your tools root path, it is a **RELATIVE** path.
4. Copy the file you want to translate in the tools path.
5. run the script ```run.bat``` or ```run.sh```, it depends on your system.

```ini
[platform key]
# baidu translate api info, you should replace it with your own.
# the doc is here: http://api.fanyi.baidu.com/api/trans/product/apidoc
;appId = 123412341
;appKey = asdfasqre14as11423412
;apiUrl = https://api.fanyi.baidu.com/api/trans/vip/translate
;others =

# google translate api info, you should replace it with your own.
# doc is here: https://cloud.google.com/translate/v2/quickstart
appId =
appKey = asdkfhkjhewqrWEQRBAWER124512
apiUrl = https://translation.googleapis.com/language/translate/v2
others =

# youdao translate api info, you should replace it with your own.
# the doc is here: http://ai.youdao.com/docs/doc-trans-api.s#p02
; appId = 1234542212341
; appKey = asdftrrsaqwerfgasTQE1234RTEWQtw
; apiUrl = http://openapi.youdao.com/api
; others =

[platform]
# now support google, baidu, youdao translate platform api.
# the value you can choose are "google", "baidu", "youdao"
translatePlatform = google

[text type]
# now support android .xml string res and ios .strings string res translate.
# the value you can choose are "android" or "ios"
textType = android

[translator]
# the source language you provided.
sourceLanguage = zh-CN

# if you want translate all language you should use "all" to replace this value.
# use "all" means you want translate the platform support all languages;
# or you should use language code to tell the translate you want to translate.
#
# the platform support language contains in the root of the project support_laguage.txt
# you can check this file to get the platform support language
# eg1: destinationLanguage = en
# eg2: destinationLanguage = en,zh-TW
# eg3: destinationLanguage = all
destinationLanguage = en

# the translate file must in the same path or sub dir of the translator.jar.
# this value assign the folder name, if you don't want use sub dir,
# just use "/" to replace the value
sourceFilePath = values
```

## API USE DOC:

- baidu translate doc:
**http://api.fanyi.baidu.com/api/trans/product/apidoc**
- google translate doc:
**https://cloud.google.com/translate/v2/quickstart**
- yoduao translate doc:
**http://ai.youdao.com/docs/doc-trans-api.s#p01**

## SUPPORT LANGUAGE DOC

- baidu
**http://api.fanyi.baidu.com/api/trans/product/apidoc**
- google
**https://cloud.google.com/translate/docs/languages**
- youdao
**http://ai.youdao.com/docs/doc-trans-api.s#p05**

## RELEASE

If you want to generate release package, just run the script
in the project root path:

on windows:

```bash
./gradlw.bat release
```

on linux:

```bash
~/sh gradlw release
```

Then, the release package will generate into 
**/release/release-<version>** folder.

## How to create plugins

Here is a plugin demo project, it will help you to create plugins [plugin course][4].

[1]: https://github.com/onlynight/exchange/wiki
[2]: https://github.com/onlynight/exchange/releases
[3]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[4]: https://github.com/onlynight/exhange-plugin-demo
[5]: ./README_CN.md