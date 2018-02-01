//package com.phone.moran.tools.net;//package com.stone.fenghuo.tools.net;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//
//import com.alibaba.fastjson.JSON;
//import com.phone.moran.model.Back;
//import com.phone.moran.tools.AppUtils;
//import com.phone.moran.tools.SLogger;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.MultipartBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * Created by stone on 15/11/13.
// */
//
//public class UploadImgUtils {
//    public static OkHttpClient client = new OkHttpClient();
//    public static UploadImagCallBack mCallBack;
//
//    public static void upload(Context context, final File file, String source, UploadImagCallBack callBack) {
//
//
//
//        if (!NetWorkUtil.isNetworkAvailable(context)) {
//            AppUtils.showToast(context, "网络失去连接");
//            return;
//        }
//        mCallBack = callBack;
//
//        SLogger.d("file", "--->>2");
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
////                .addFormDataPart("token", "123456")
////                .addFormDataPart("fileData", file.getName(), fileBody)
//                .addFormDataPart("fileData", "fileData", fileBody)
//                .build();
//
//        Request request =
//                new Request.Builder().url(RetrofitUtils.BASE_URL + source).post(requestBody).build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                SLogger.d("file", "--->>failed");
//                //TODO 网络出错
//                Message message = Message.obtain();
//                message.what = 2;
//                message.obj = request.toString();
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//
//
//                if (response.code() == 200) {
//                    SLogger.d("file", "--->>200");
//                    Message message = new Message();//TODO 添加上传图片的回调处理
//                    try {
////                        SLogger.d("file", "--->>" + response.body().string());
//                        message.what = 1;
//                        String url = response.body().string();
//                        SLogger.d("file", "--->>" + url);
//                        Back responseData = JSON.parseObject(url, Back.class);
//                        message.obj = responseData.getData().getPhotoUrl();
//                        SLogger.d("file", "--->>url" + responseData.getData().getPhotoUrl());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        message.what = 2;
//                        message.obj = e.toString();
//                    }
//                    handler.sendMessage(message);
//
//                } else {
//                    Message message = Message.obtain();
//                    message.what = 2;
//                    message.obj = response.toString();
//                }
//            }
//        });
//
//    }
//
//
//    public static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//                String url = (String) msg.obj;
//                if (mCallBack != null) {
//                    mCallBack.onUploadFinish(url);
//                }
//            }else if(msg.what == 2){
//                String error = (String) msg.obj;
//                if (mCallBack != null) {
//                    mCallBack.onFailFinish(error);
//                }
//            }
//        }
//    };
//
//    public interface UploadImagCallBack {
//        void onUploadFinish(String url);
//        void onFailFinish(String response);
//    }
//}
