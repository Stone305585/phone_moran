package com.phone.moran.tools.net;


import com.phone.moran.model.Back;
import com.phone.moran.model.CategoryArtBack;
import com.phone.moran.model.CategorySceneBack;
import com.phone.moran.model.ClassicQBack;
import com.phone.moran.model.DeviceInfoBack;
import com.phone.moran.model.PaintBack;
import com.phone.moran.model.PaintListBack;
import com.phone.moran.model.PictureBack;
import com.phone.moran.model.PioneerHomeBack;
import com.phone.moran.model.RecommendHomeBack;
import com.phone.moran.model.RegisterBack;
import com.phone.moran.model.SearchBack;
import com.phone.moran.model.SearchHotBack;
import com.phone.moran.model.UserBack;
import com.phone.moran.model.UserInfosBack;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 所有的接口类
 * Created by tristab on 15/10/23.
 */

public interface ApiHelper {

    public String GETMAINDATA = "discovery/get_recommend_home";
    public String GETPIONEERMAIN = "discovery/get_pioneer_home";
    public String GETPAINTDETAIL = "painting/2/info";
    public String GETCQLIST = "discovery/get_cq_list";
    public String SEARCHHOT = "search/get_hot_words";

    //首页发现
    @GET("discovery/get_recommend_home")
    Observable<RecommendHomeBack> getRecommendMain();

    //首页先锋
    @GET("discovery/get_pioneer_home")
    Observable<PioneerHomeBack> getPioneerMain();

    //画单详情
    @POST("painting/{paint_id}/info")
    Observable<PaintBack> getPaintDetail(@Path("paint_id") int paint_id, @Body RequestBody body);

    //画作详情
    @GET("painting/picture/{picture_id}/info")
    Observable<PictureBack> getPictureDetail(@Path("picture_id") int picture_id);

    //获取画单列表
    @GET("painting/{type_id}/paint_list")
    Observable<PaintListBack> getPaintList(@Path("type_id") int type_id);

    //画单收藏
    @POST("painting/collect")
    Observable<Back> collectPaint(@Body RequestBody body);

    //大咖说点赞
    @POST("discovery/mq_love")
    Observable<Back> collectMq(@Body RequestBody body);

    //读精彩列表
    @GET("discovery/get_cq_list")
    Observable<ClassicQBack> getCqList();

    //---------------======搜索==========-----------------------
    //读精彩列表
    @GET("search/info")
    Observable<SearchBack> search(@Query("kw") String kw);
    //读精彩列表
    @GET("search/get_hot_words")
    Observable<SearchHotBack> getSearchHot();


    //-----------------------分类----------------
    @GET("classify/get_art_home")
    Observable<CategoryArtBack> getArtHome();

    @GET("classify/get_scene_home")
    Observable<CategorySceneBack> getSceneHome();

    @GET("classify/{type_id}/list_info")
    Observable<PaintListBack> getClassifyList(@Path("type_id") int type_id);

    //--------------------播放画单-----------------------------
    //播放画单
    @POST("painting/play")
    Observable<Back> play(@Body RequestBody body);

    //播放画作
    @POST("painting/picture_play")
    Observable<Back> playPic(@Body RequestBody body);

    //添加tips
    @POST("painting/add_tips")
    Observable<Back> addTips(@Body RequestBody body);

    //添加内衬
    @POST("painting/add_frame")
    Observable<Back> addFrame(@Body RequestBody body);

    //修改播放模式
    @POST("painting/modify_play_type")
    Observable<Back> modifyPlayType(@Body RequestBody body);

    //------------------------用户---------------------------
    //获取验证码
    @POST("user/send_code")
    Observable<Back> sendCode(@Body RequestBody body);

    //校验验证码
    @POST("user/verify_code")
    Observable<Back> verifyCode(@Body RequestBody body);

    //用户注册
    @POST("user/register")
    Observable<RegisterBack> register(@Body RequestBody body);

    //用户注册
    @POST("user/register")
    Observable<RegisterBack> registerMobile(@Body RequestBody body);


    //用户登录
    @POST("user/login")
    Observable<RegisterBack> login(@Body RequestBody body);

    //重置密码
    @POST("user/reset_password")
    Observable<RegisterBack> resetPassword(@Body RequestBody body);

    //用户退出
    @POST("user/logout")
    Observable<Back> logout();

    //获取用户信息
    @GET("user/get_info")
    Observable<UserBack> getUserInfo();

    //设置用户信息
    @POST("user/set_info ")
    Observable<UserBack> setUser(@Body RequestBody body);

    //用户三方绑定

    /**
     * // 用户三方绑定
     // route: /api/user/bind_thirdparty                 method: POST
     // 必需字段: 全部
     rpc BindThirdparty (BindThirdpartyReq) returns (BindThirdpartyRsp) {}
     }
     enum RegisterType {
     phone = 0;
     email = 1;
     wechat = 2;
     qq = 3;
     zhifubao = 4;
     weibo = 5;
     }

     message BindThirdpartyReq {
     Header header = 500;         // 统一包头

     string source = 1;           // 来源

     RegisterType register_type = 2; // 注册类型

     string state        = 3; // 三方登录标记token
     string code         = 4; // 三方登录的auth_token
     uint32 bind_uin     = 5; //绑定三方登录账户的uin
     string access_token = 6; //三方登录access_token
     string auth_token   = 7; //三方登录autho_token
     }

     message BindThirdpartyRsp {
     Header header = 500; //统一包头

     int32 ret = 1;            // 返回码
     string err = 2;           // 错误信息
     uint32 uin = 3;           // 用户id
     string token = 4;         // 用户token
     }
     * @param body
     * @return
     */
    @POST("user/bind_thirdparty ")
    Observable<RegisterBack> bindThird(@Body RequestBody body);


    //--------------------设备绑定--------------------

    //请求绑定
    @POST("user/device_bind")
    Observable<Back> bind(@Body RequestBody body);
    //拒绝和同意
    @POST("master/process_device_bind")
    Observable<Back> processDeviceBind(@Body RequestBody body);

    //解除绑定关系
    @POST("user/delete_bind")
    Observable<Back> removeUser(@Body RequestBody body);

    //获取用户下面所有设备
    @GET("user/get_bind_device")
    Observable<DeviceInfoBack> getUserDeviceInfo();

    //获取设备下所有的用户classify/{type_id}/list_info
    @GET("master/{device_id}/get_device_info ")
    Observable<UserInfosBack> getUserInfoByDevice(@Path("device_id") String device_id);


    //上传用户头像   /api/imgs/upload/user_head


    //意见反馈
    @POST("user/proposal")
    Observable<Back> suggest(@Body RequestBody body);

}