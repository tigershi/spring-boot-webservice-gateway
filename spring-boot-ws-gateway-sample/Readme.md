# ysbf_gaosi_ws

---
此项目包括的功能


JDK版本
---------
* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

编译
-------
* 进入项目根文件夹finance-new-parent

```
 cd finance-new-parent
 ./gradlew clean build
 ```
Jar 文件生成位置:
 ```
finance-new-parent/publish (Eg.ysbf_gaosi_ws-1.0.0.jar ）
 ```
启动程序 dev 环境
---------
 ```
 java -jar ysbf_gaosi_ws-1.0.0.jar
```
启动程序 test 环境
---------
 ```
 nohup java -jar ysbf_gaosi_ws-1.0.0.jar --spring.profiles.active=test &
```
在生产环境（pro）启动程序
---------
```
 nohup java -jar ysbf_gaosi_ws-1.0.0.jar --spring.profiles.active=prd &

```
