package com.pfl.lib_common.utils;

import com.esread.sunflower.common.base.mvp.BaseView;
import com.esread.sunflower.common.exception.ApiException;
import com.esread.sunflower.common.exception.NoNetworkException;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.FlowableSubscriber;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private BaseView baseView;

    public BaseSubscriber(BaseView view) {
        baseView = view;
    }


    @Override
    public void onComplete() {
        disposes();
        baseView.hideLoading();
    }

    private void disposes() {
        if (!isDisposed()) {
            dispose();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        String message = e.getMessage();
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK, message);
        } else if (e instanceof NoNetworkException) {
            onException(ExceptionReason.NOT_NETWORK, message);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR, message);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT, message);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR, message);
        } else if (e instanceof ApiException) { // 自定义异常
            ApiException exception = (ApiException) e;
            onFailed(exception.getCode(), exception.getMsg());
        } else if (e instanceof NoNetworkException) { // 没有网络
            onException(ExceptionReason.NOT_NETWORK, message);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR, message);
        }
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    public abstract void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param code     服务器返回code
     * @param errorMsg 服务器返回错误消息
     */
    public void onFailed(int code, String errorMsg){
        disposes();
        baseView.hideLoading();
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason, String message) {
        switch (reason) {
            case PARSE_ERROR:
            case BAD_NETWORK:
            case CONNECT_ERROR:
            case CONNECT_TIMEOUT:
            case NOT_NETWORK:
                onFailed(400,message);
                break;
            default:
                onFailed(400,message);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 没有网络
         */
        NOT_NETWORK,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
