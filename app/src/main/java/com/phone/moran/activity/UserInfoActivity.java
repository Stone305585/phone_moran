package com.phone.moran.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.model.User;
import com.phone.moran.model.UserBack;
import com.phone.moran.presenter.implPresenter.UserActivityImpl;
import com.phone.moran.presenter.implView.IUserInfoActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DateUtils;
import com.phone.moran.tools.ImageLoader;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.net.NetWorkUtil;
import com.phone.moran.tools.net.RetrofitUtils;
import com.phone.moran.tools.photos.PhotoCompresser;
import com.phone.moran.tools.photos.PhotoUtils;
import com.phone.moran.view.MyDateDialog;
import com.phone.moran.view.WheelView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener, IUserInfoActivity, MyDateDialog.OnDateSetListener {

    public static final int DES = 222;

    @BindView(R.id.back_title)
    ImageView backTitle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_text_btn)
    TextView rightTextBtn;
    @BindView(R.id.right_image_btn1)
    ImageView rightImageBtn1;
    @BindView(R.id.right_image_btn2)
    ImageView rightImageBtn2;
    @BindView(R.id.right_image_btn3)
    ImageView rightImageBtn3;
    @BindView(R.id.rl_title)
    LinearLayout rlTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.image_mine_bg)
    ImageView imageMineBg;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.gender_et)
    TextView genderEt;
    @BindView(R.id.birthday_et)
    TextView birthdayEt;
    @BindView(R.id.city_et)
    EditText cityEt;
    @BindView(R.id.des_paint_et)
    TextView desPaintEt;

    private User user;
    private User originalUser;

    private View popView;
    private TextView cancelBtn;
    private TextView okBtn;
    private WheelView wheelView;

    private String headUrl = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1512208952&di=2f3eee9e927f7aa769ad99efce9e2e9b&src=http://www.pp3.cn/uploads/201606/20160617016.jpg";
    private String bgUrl = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1512208952&di=2f3eee9e927f7aa769ad99efce9e2e9b&src=http://www.pp3.cn/uploads/201606/20160617016.jpg";
    //用于存放生日
    private int[] dateDefault = new int[3];
    //是否点击过生日
    private boolean isCalendaClick = false;
    private int sexFlag = 1;
    private String city = "";
    private String birthDay = "";
    private AlertDialog.Builder sexDialogBuilder;
    private View sexChooseView;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    private AlertDialog mPhotoDialog;

    private UserActivityImpl userActivityImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info2);
        ButterKnife.bind(this);

        userActivityImpl = new UserActivityImpl(this, token, this);

        initView();
//        initPopWin();

        setListener();
        initDataSource();
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();

        if (connected) {
            userActivityImpl.getUserInfo();
        }
    }

    @Override
    protected void initView() {
        super.initView();

        user = new User();
        originalUser = new User();
        title.setText("我的资料");
    }

    @Override
    protected void setListener() {
        super.setListener();

        backTitle.setOnClickListener(this);
//        okBtn.setOnClickListener(this);
//        cancelBtn.setOnClickListener(this);
        birthdayEt.setOnClickListener(this);
        genderEt.setOnClickListener(this);
        imageHead.setOnClickListener(this);
        imageMineBg.setOnClickListener(this);
        desPaintEt.setOnClickListener(this);

        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String name = s.toString();
                if(!TextUtils.isEmpty(name)) {
                    user.setNick_name(name);
                }
            }
        });

        cityEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String city = s.toString();
                if(!TextUtils.isEmpty(city)) {
                    user.setRegion(city);
                }
            }
        });
    }

    public void initPopWin() {
        popView = LayoutInflater.from(this).inflate(R.layout.gender_pop, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(popView);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.text_red));
        popupWindow.setBackgroundDrawable(dw);
        wheelView = (WheelView) popView.findViewById(R.id.wheel_view);
        okBtn = (TextView) popView.findViewById(R.id.ok_btn);
        cancelBtn = (TextView) popView.findViewById(R.id.cancel_btn);

        List<String> genderList = new ArrayList<>();
        genderList.add("男");
        genderList.add("女");
        genderList.add("5");
        genderList.add("4");
        genderList.add("3");
        genderList.add("2");
        genderList.add("1");

        wheelView.setItems(genderList);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                //selectedIndex当前高亮的位置
                //item当前高亮的位置的内容
                SLogger.d("<<", "性别->>" + selectedIndex);
                sexFlag = (++selectedIndex);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image_head:
                curUploadFlag = 0;
                goPhotoGrid();
                break;
            case R.id.image_mine_bg:
                curUploadFlag = 1;
                goPhotoGrid();
                break;
            case R.id.gender_et:
                initAlertDialog();
//                popupWindow.showAtLocation(backTitle, Gravity.BOTTOM, 0, 0);
//                // 设置popWindow弹出窗体可点击
//                popupWindow.setFocusable(true);
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 0.7f;
//                getWindow().setAttributes(params);
                break;
            case R.id.birthday_et:
                showCalendeDialog();
                break;
            case R.id.des_paint_et:
                startActivityForResult(new Intent(UserInfoActivity.this, InputInfoActivity.class), DES);
                break;

            case R.id.ok_btn:
                user.setGender(sexFlag);
                break;

            case R.id.cancel_btn:
                popupWindow.dismiss();
                break;

            case R.id.back_title:
                userActivityImpl.uploadUser(user);
                dialog.show();
                break;

        }
    }

    private void goPhotoGrid() {
        checkPermission();
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        intent.setShowGif(false);
        startActivityForResult(intent, PhotoUtils.PICKPHTOO);
    }

    /**
     * ANDROID 6.0权限申请。。
     */
    private void checkPermission() {

        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionState != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        110);
            }
        }
    }

    //上传图片的名字
    private String imageName;
    private File headFile;
    private File zoomFile;

    private int curUploadFlag = 0;//0:head;1:bg

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case DES:
                    user.setPersonal_profile(data.getStringExtra(InputInfoActivity.INFOS));
                    desPaintEt.setText(user.getPersonal_profile());
                    break;

                case PhotoUtils.PICKPHTOO:
                    ArrayList<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    if (paths != null && paths.size() > 0) {
                        headFile = new File(paths.get(0));
                        zoomFile = PhotoUtils.photoZoom(UserInfoActivity.this,
                                FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", headFile),
                                PhotoUtils.CROPPHOTO, 1, 1);
                    }
                    break;
                case PhotoUtils.TAKEPHOTO:
                    headFile = PhotoUtils.onPhotoFromCamera(this, imageName);
                    zoomFile = PhotoUtils.photoZoom(this,
                            FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", headFile),
                            PhotoUtils.CROPPHOTO, 1, 1);
                    break;
                case PhotoUtils.CROPPHOTO:
                    if (dialog != null && !dialog.isShowing())
                        dialog.show();

//                    if (zoomFile != null) {
//                        int degree = PhotoUtils.readPictureDegree(zoomFile.getAbsolutePath());
//                        if (degree != 0) {
//                            new PhotoUtils.DegreeImgTask(this, zoomFile, degree, new PhotoUtils.onDegreeImgFinishListerner() {
//                                @Override
//                                public void onDegreeImgFinish(File file) {
//                                    zoomFile = file;
//                                }
//                            }).execute();
//                        }
//                    }
                    //先压缩头像图片
                    new PhotoCompresser(200, headFile, new PhotoCompresser.CompressCallBack() {
                        @Override
                        public void onCompressDone(final File outputFile) {

                            if (curUploadFlag == 0)
                                uploadHead(getApplicationContext(), outputFile, "imgs/upload/user_head");
                            else
                                uploadHead(getApplicationContext(), outputFile, "imgs/upload/backend_img");

                        }
                    }, this).execute();
                    break;
            }


        }
    }

    private void initAlertDialog() {
        sexChooseView = LayoutInflater.from(this).inflate(R.layout.sex_choose_dialog, null);
        sexDialogBuilder = new AlertDialog.Builder(this).setView(sexChooseView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        maleBtn = (RadioButton) sexChooseView.findViewById(R.id.male_btn);
        femaleBtn = (RadioButton) sexChooseView.findViewById(R.id.female_btn);

        if (sexFlag == 1)
            maleBtn.setChecked(true);
        else
            femaleBtn.setChecked(true);

        maleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sexFlag = 1;
                    femaleBtn.setChecked(false);
                    genderEt.setText("男");
                    user.setGender(1);
                } else {
                    sexFlag = 2;
                    femaleBtn.setChecked(true);
                    genderEt.setText("女");
                    user.setGender(2);
                }
            }
        });
        femaleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sexFlag = 2;
                    maleBtn.setChecked(false);
                    genderEt.setText("女");
                    user.setGender(2);
                } else {
                    sexFlag = 1;
                    maleBtn.setChecked(true);
                    genderEt.setText("男");
                    user.setGender(1);
                }
            }
        });
        sexDialogBuilder.show();

    }


    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hidProgressDialog() {
        dialog.hide();
    }

    @Override
    public void showError(String error) {
        dialog.hide();
        AppUtils.showToast(getApplicationContext(), error);
    }

    @Override
    public void updateUserInfo(User user) {
        this.originalUser = user;
        try {
            ImageLoader.displayImg(this, user.getHead_url(), imageHead);
            ImageLoader.displayImg(this, user.getBackground(), imageMineBg);
            if (user.getGender() == 1) {
                genderEt.setText("男");
            } else
                genderEt.setText("女");

            nameEt.setText(user.getNick_name());
            cityEt.setText(user.getRegion());
            desPaintEt.setText(user.getPersonal_profile());
            birthdayEt.setText(user.getBirth_year() + "-" + user.getBirth_month() + "-" + user.getBirth_day());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(User user) {
        dialog.dismiss();
        finish();
    }

    /**
     * 调用日历
     */
    private void showCalendeDialog() {

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(dateDefault[0], dateDefault[1] - 1, dateDefault[2]);
        MyDateDialog pickerDialog = new MyDateDialog(this, AlertDialog.THEME_HOLO_LIGHT, this, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        //设置起始日期和结束日期
        DatePicker datePicker = pickerDialog.getDatePicker();
        Date date_s = DateUtils.getDateFromFormat("1960-01-01");
        datePicker.setMinDate(date_s.getTime());
        datePicker.setMaxDate(System.currentTimeMillis());
        pickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        birthDay = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        birthdayEt.setText(birthDay);

        user.setBirth_year(year);
        user.setBirth_month(monthOfYear + 1);
        user.setBirth_day(dayOfMonth);

        dateDefault[0] = year;
        dateDefault[1] = monthOfYear + 1;
        dateDefault[2] = dayOfMonth;
    }

    /**
     * 用于点击edittext以外的位置，隐藏键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (AppUtils.hidenSoftInputASimple(ev, this)) {
            case 0:
                return super.dispatchTouchEvent(ev);
            case 1:
                return true;
            case 2:
                return onTouchEvent(ev);
            default:
                return true;
        }

    }

    public OkHttpClient client = new OkHttpClient();

    public void uploadHead(Context context, final File file, String source) {


        if (!NetWorkUtil.isNetworkAvailable(context)) {
            AppUtils.showToast(context, "网络失去连接");
            return;
        }

        SLogger.d("file", "--->>2");
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
//                .addFormDataPart("token", "123456")
//                .addFormDataPart("fileData", file.getName(), fileBody)
                .addFormDataPart("file", "file", fileBody)
                .build();

        Request request =
                new Request.Builder().url(RetrofitUtils.BASE_URL + source).post(requestBody).build();

        token = PreferencesUtils.getString(this, Constant.ACCESS_TOKEN);
        String uin = PreferencesUtils.getString(this, Constant.USER_ID);

        Request request1 = request.newBuilder().header("User-Uin", uin).header("Client-Token", token).removeHeader("Content-Type").header("Content-Type", "application/json").build();

        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                SLogger.d("file", "--->>failed");
                //TODO 网络出错
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {


                if (response.code() == 200) {
                    SLogger.d("file", "--->>200");
                    Message message = new Message();//TODO 添加上传图片的回调处理
                    try {
//                        SLogger.d("file", "--->>" + response.body().string());
                        final String url = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                if (curUploadFlag == 0){
                                    user.setHead_url(((UserBack) JSONObject.parseObject(url, UserBack.class)).getUser_info().getHead_url());
                                    PreferencesUtils.putString(getApplicationContext(), Constant.AVATAR, user.getHead_url());
                                    ImageLoader.displayImg(UserInfoActivity.this, file, imageHead);

                                } else {
                                    user.setBackground(((UserBack) JSONObject.parseObject(url, UserBack.class)).getUser_info().getBackground());
                                    PreferencesUtils.putString(getApplicationContext(), Constant.MINE_BG, user.getBackground());
                                    ImageLoader.displayImg(UserInfoActivity.this, file, imageMineBg);
                                }
                                AppUtils.showToast(getApplicationContext(), "上传成功");

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                AppUtils.showToast(getApplicationContext(), "上传失败");

                            }
                        });
                    }
//                    handler.sendMessage(message);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            AppUtils.showToast(getApplicationContext(), "上传失败");

                        }
                    });
                }
            }
        });

    }
}
