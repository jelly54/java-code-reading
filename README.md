<div align="center">  
    <p>
        <a href="https://tf2jaguar.github.io"><img src="https://badgen.net/badge/tf2jaguar/read?icon=sourcegraph&color=4ab8a1" alt="read" /></a>
        <img src="https://badgen.net/github/stars/tf2jaguar/java-code-reading?icon=github&color=4ab8a1" alt="stars" />
        <img src="https://badgen.net/github/forks/tf2jaguar/java-code-reading?icon=github&color=4ab8a1" alt="forks" />
        <img src="https://badgen.net/github/open-issues/tf2jaguar/java-code-reading?icon=github" alt="issues" />
    </p>
</div>

# java源码调试

> java 1.8
> IDEA 2019.3

## 源码下载

- 新建一个纯 java 项目，(idea编辑器操作: File -> new -> Project -> Java)
- 从openjdk下载源码 [https://hg.openjdk.java.net/jdk8/jdk8/langtools/archive/tip.zip](https://hg.openjdk.java.net/jdk8/jdk8/langtools/archive/tip.zip)
- 解压下载的文件，将 `langtools-1ff9d5118aae/src/share/classes` 下的 `com` 整个文件夹拷贝到新项目中的 `src` 目录下即可

```
java-code-reading
    |- src
        |- com.sun.source
        |- com.sun.tools
    |- README.md
```

## 配置环境

- 配置 idea 的 java 环境
- 配置该项目的编译输出路径 (File -> Project Structure -> Project -> Project compiler output -> 设定到项目下的一个新目录即可 `output` )
- 设置项目的 src 目录设置为 Sources (File -> Project Structure -> Modules -> Sources -> 右键src选择 Sources)
- 配置 source 文件加载顺序 `<Module source>` 调整到默认的 jdk1.8 之前 (File -> Project Structure -> Modules -> Dependencies -> 拖动到第一个位置)

## debug javac

- 在 `src/com` 包下新建一个包 `jelly`。新建一个类 `Hello.java`
- IDEA 右上角，设定启动参数 `Edit Configurations`

```
Main class: com.sun.tools.javac.Main
Program arguments: /Users/xxx/Documents/java-code-reading/src/com/jelly/bytecode/Hello.java
```

配置完成点击运行，会在 `Hello.java` 同级目录生成 `Hello.class` 就成功了
