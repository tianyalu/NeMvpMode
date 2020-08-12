package com.sty.ne.mvpmode;

import com.sty.ne.mvpmode.model.ImageBean;

//View层交互，Model层交互共同的需求(契约、合同)
public interface DownloaderContract {
    interface M {
        //Presenter层告诉Model层需要做什么事情
        void requestDownloader(ImageBean imageBean);
    }

    interface PV {
        //View层告诉Presenter层需要做什么事情
        void requestDownloader(ImageBean imageBean);

        //Presenter层得到Model层的结果返回，再通知View层
        void responseDownloaderResult(boolean isSuccess, ImageBean imageBean);
    }
}

