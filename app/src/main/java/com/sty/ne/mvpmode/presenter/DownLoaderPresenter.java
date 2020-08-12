package com.sty.ne.mvpmode.presenter;

import android.os.SystemClock;

import com.sty.ne.mvpmode.DownloaderContract;
import com.sty.ne.mvpmode.MainActivity;
import com.sty.ne.mvpmode.engine.DownloaderEngine;
import com.sty.ne.mvpmode.model.ImageBean;

//P层几乎不做事情？谷歌的sample中，P层是几乎包揽了所有的活
public class DownLoaderPresenter implements DownloaderContract.PV {
    private MainActivity view;
    private DownloaderEngine model; //下载的模型

    public DownLoaderPresenter(MainActivity view) {
        this.view = view;
        model = new DownloaderEngine(this);
    }

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

    @Override
    public void responseDownloaderResult(final boolean isSuccess, final ImageBean imageBean) {
        //将完成的结果告诉View层（刷新UI）
        view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.responseDownloaderResult(isSuccess, imageBean);
            }
        });
    }
}
