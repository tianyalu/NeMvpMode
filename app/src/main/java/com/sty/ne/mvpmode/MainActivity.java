package com.sty.ne.mvpmode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sty.ne.mvpmode.model.ImageBean;
import com.sty.ne.mvpmode.presenter.DownLoaderPresenter;
import com.sty.ne.mvpmode.utils.Constant;

// MVC中Activity是Controller层；MVP中Activity是View层
public class MainActivity extends AppCompatActivity implements DownloaderContract.PV{
    private ImageView ivImage;
    private Button btnDownload;
    private DownLoaderPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        ivImage = findViewById(R.id.iv_image);
        btnDownload = findViewById(R.id.btn_download);
        presenter = new DownLoaderPresenter(this);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnDownloadClicked();
            }
        });
    }

    private void onBtnDownloadClicked() {
        ImageBean imageBean = new ImageBean();
        imageBean.setRequestPath(Constant.IMAGE_PATH);
        requestDownloader(imageBean);
    }

    @Override
    public void requestDownloader(ImageBean imageBean) {
        if(presenter != null) {
            presenter.requestDownloader(imageBean);
        }
    }

    @Override
    public void responseDownloaderResult(boolean isSuccess, ImageBean imageBean) {
        Toast.makeText(this, isSuccess ? "下载成功" : "下载失败", Toast.LENGTH_SHORT).show();
        if(isSuccess && imageBean.getBitmap() != null) {
            ivImage.setImageBitmap(imageBean.getBitmap());
        }
    }
}
