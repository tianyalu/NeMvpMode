# `MVP`思想精髓与解耦

[TOC]

## 一、理论

### 1.1 `MVP`思想精髓

![image](https://github.com/tianyalu/NeMvpMode/raw/master/show/mvp_process_relation.png)

![image](https://github.com/tianyalu/NeMvpMode/raw/master/show/mvp_process_relation2.png)

### 1.2 巧妙解耦`View`与`Model`

![image](https://github.com/tianyalu/NeMvpMode/raw/master/show/view_model_decoupling.png)

## 二、实践

### 2.1 实现效果

本文使用`MVP`实现了点击按钮通过网络请求下载图片并渲染到视图上的操作，效果如下图所示：

![image](https://github.com/tianyalu/NeMvpMode/raw/master/show/show.gif)

### 2.2 实现思路

本文`MVP`的实现结构如下图所示：

![image](https://github.com/tianyalu/NeMvpMode/raw/master/show/mvp_implementation_structure.png)

由上图可知，我们的`P`层几乎不做事情。而谷歌的`sample`中，`P`层是几乎包揽了所有的活。

### 2.3 内存泄漏

如果我们在`P`层执行了耗时操作，当用户退出`Activity`之后`Activity`依然不能销毁，也会导致内存泄漏，由此因出弱引用，参考：

```java
    @Override
    public void requestDownloader(ImageBean imageBean) {
        //接收到View层的指令，去完成某个需求（可以自己完成，也可以让别人去完成）
        try {
            model.requestDownloader(imageBean);
        } catch (Exception e) {
            e.printStackTrace();
            //省略了异常的处理
        }

        //内存泄漏？--> 弱引用
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SystemClock.sleep(50000);
//            }
//        });
    }
```



