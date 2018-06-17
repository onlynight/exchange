exchange
========

This is a plugin based translator for typed text translate, 
such as android ```strings.xml``` or ios ```Localizable.strings```.
Now it official support android and ios strings resources document 
translate plugins, and official support google, baidu and youdao 
translate platform plugins. You can simply use it.

For more details you can see the [wiki][1].

### API USE DOC:

- baidu translate doc:
**http://api.fanyi.baidu.com/api/trans/product/apidoc**
- google translate doc:
**https://cloud.google.com/translate/v2/quickstart**
- yoduao translate doc:
**http://ai.youdao.com/docs/doc-trans-api.s#p01**

### SUPPORT LANGUAGE DOC

- baidu
**http://api.fanyi.baidu.com/api/trans/product/apidoc**
- google
**https://cloud.google.com/translate/docs/languages**
- youdao
**http://ai.youdao.com/docs/doc-trans-api.s#p05**

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
    + ```sourceLanguage``` source language, ```destinationLanguage``` destination language, the value you can find in ```support_language.txt```.
    + ```sourceFilePath``` translate file path, plugin will scan the path to translate. 
    it should in your tools root path, it is a **RELATIVE** path.
4. Copy the file you want to translate in the tools path.
5. run the script ```run.bat``` or ```run.sh```, it depends on your system.


[1]: https://github.com/onlynight/exchange/wiki
[2]: https://github.com/onlynight/exchange/releases
[3]: http://www.oracle.com/technetwork/java/javase/downloads/index.html