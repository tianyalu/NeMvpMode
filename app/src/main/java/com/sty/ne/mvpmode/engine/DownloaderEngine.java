package com.sty.ne.mvpmode.engine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sty.ne.mvpmode.DownloaderContract;
import com.sty.ne.mvpmode.model.ImageBean;
import com.sty.ne.mvpmode.presenter.DownLoaderPresenter;
import com.sty.ne.mvpmode.utils.Constant;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloaderEngine implements DownloaderContract.M {
    private DownLoaderPresenter presenter;
    public DownloaderEngine(DownLoaderPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestDownloader(ImageBean imageBean) {
        //Presenter层让我做这个需求
        new Thread(new Downloader(imageBean)).start();
    }

    private class Downloader implements Runnable {
        private final ImageBean imageBean;

        public Downloader(ImageBean imageBean) {
            this.imageBean = imageBean;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imageBean.getRequestPath());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");

                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    showUi(Constant.SUCCESS, bitmap);
                }else {
                    showUi(Constant.ERROR, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showUi(Constant.ERROR, null);
            }
        }

        private void showUi(int resultCode, Bitmap bitmap) {
            imageBean.setBitmap(bitmap);
            presenter.responseDownloaderResult(resultCode == Constant.SUCCESS, imageBean);
        }
    }
}
