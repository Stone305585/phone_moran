package com.phone.moran.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.wifi.WifiConnect;
import com.zxing.encoding.EncodingHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WifiConFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiConFragment extends BaseFragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.name_wifi)
    TextView nameWifi;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.join_btn)
    TextView joinBtn;
    @BindView(R.id.cancel_btn)
    TextView cancelBtn;
    Unbinder unbinder;
    @BindView(R.id.input_pass_LL)
    LinearLayout inputPassLL;
    @BindView(R.id.res_img)
    ImageView resImg;

    private String ssid;
    private String mParam2;
    private WifiManager wifiManager;
    private WifiConnect wifiConnect;
    private WifiConnect.WifiCipherType cipherType = WifiConnect.WifiCipherType.WIFICIPHER_NOPASS;

    public WifiConnect.WifiCipherType getCipherType() {
        return cipherType;
    }

    public void setCipherType(WifiConnect.WifiCipherType cipherType) {
        this.cipherType = cipherType;
    }

    public WifiConFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ssid   Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiConFragment.
     */
    public static WifiConFragment newInstance(String ssid, String param2) {
        WifiConFragment fragment = new WifiConFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, ssid);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ssid = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiConnect = new WifiConnect(wifiManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wifi_con, container, false);
        unbinder = ButterKnife.bind(this, v);

        initView(v);
        setListener();
        return v;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            inputPassLL.setVisibility(View.VISIBLE);
            resImg.setVisibility(View.GONE);
            passwordEt.setText("");
        }
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        if (cipherType == WifiConnect.WifiCipherType.WIFICIPHER_NOPASS) {
            resImg.setVisibility(View.VISIBLE);
            inputPassLL.setVisibility(View.GONE);
            try {
                resImg.setImageBitmap(EncodingHandler.createQRCode(ssid + "&" + "" + "0", 350));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resImg.setVisibility(View.GONE);
            inputPassLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();

        joinBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.join_btn:
                createResImg();
                break;

            case R.id.cancel_btn:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }

    }

    private void createResImg() {
        String password = passwordEt.getText().toString();
        if (TextUtils.isEmpty(password)) {
            AppUtils.showToast(getActivity().getApplicationContext(), getResources().getString(R.string.type_password));
        } else {
            dialog.show();
            boolean res = true;
            //TODO 这里不再需要校验
//          boolean res = wifiConnect.connect(ssid, password, cipherType);
            if (res) {
                //TODO 链接成功 调用回掉接口
//                        AppUtils.showToast(getActivity().getApplicationContext(), "连接成功");
                inputPassLL.setVisibility(View.GONE);
                resImg.setVisibility(View.VISIBLE);

                try {
                    int type = 0;
                    if (cipherType == WifiConnect.WifiCipherType.WIFICIPHER_WPA) {
                        type = 2;
                    } else if (cipherType == WifiConnect.WifiCipherType.WIFICIPHER_WEP) {
                        type = 1;
                    } else {
                        type = 0;
                    }
                    resImg.setImageBitmap(convert(EncodingHandler.createQRCode(ssid + "&" + password + "&" + type, 350)));
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    AppUtils.showToast(getActivity().getApplicationContext(), "生成二维码失败，稍后重试");

                }
            } else {
                dialog.dismiss();
                AppUtils.showToast(getActivity().getApplicationContext(), "密码错误");
            }
        }
    }

    /**
     * 转换二维码为镜像
     *
     * @param a
     * @return
     */
    Bitmap convert(Bitmap a) {
        int w = a.getWidth();
        int h = a.getHeight();
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        m.postRotate(-90);  //旋转-90度
        Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);
        cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()), new Rect(0, 0, w, h), null);
        return newb;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

}
