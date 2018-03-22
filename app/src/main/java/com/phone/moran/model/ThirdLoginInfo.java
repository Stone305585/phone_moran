package com.phone.moran.model;

/**
 *
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
 * Created by stone on 2016/7/25.
 */
public class ThirdLoginInfo {

    public static int PHONE = 0;
    public static int EMAIL = 1;
    public static int WECHAT = 2;
    public static int QQ = 3;
    public static int SINA = 5;



    private String source;
    private int register_type;
    private String state;
    private String code;
    private int bind_uin;
    private String access_token;
    private String auth_token;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRegister_type() {
        return register_type;
    }

    public void setRegister_type(int register_type) {
        this.register_type = register_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBind_uin() {
        return bind_uin;
    }

    public void setBind_uin(int bind_uin) {
        this.bind_uin = bind_uin;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
