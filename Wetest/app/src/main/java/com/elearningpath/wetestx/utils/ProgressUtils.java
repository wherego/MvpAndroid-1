package com.elearningpath.wetestx.utils;

import android.content.Context;
import android.widget.Toast;

import com.elearningpath.wetestx.R;
import com.elearningpath.wetestx.widgets.LoadingDialog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建者：韦小宝
 * 创建日期：16/12/8
 * 版本号：1.0.0
 * 功能说明：
 */

public class ProgressUtils {
    private LoadingDialog progress;
    private Context context;
    private Subscriber subscriber;

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public ProgressUtils( Context context) {
        this.context = context;
    }

    public  void progressShow(){
        if (progress==null){
            initProgress();
        }
        progress.show();
        Observable.interval(8, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (progress!=null){
                            if (progress.isShowing()){
                                progress.dismiss();
                                if (subscriber!=null){
                                    subscriber.unsubscribe();
                                }
                                Toast.makeText(context, "网络情况太差，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void initProgress() {
        progress=new LoadingDialog(context);
        progressShow();
    }
    public void progressDismiss(){
        if (progress!=null) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }
    }
}
