package com.phone.moran.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phone.moran.MainActivity;
import com.phone.moran.R;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.WifiRecyclerAdapter;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.wifi.WifiConnect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.WIFI_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WifiListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiListFragment extends BaseFragment {
    private static final int GPS_REQUEST_CODE = 223;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.wifi_recycler)
    RecyclerView wifiRecycler;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WifiRecyclerAdapter stringRecyclerAdapter;
    private List<ScanResult> wifiArray = new ArrayList<>();
    private WifiConnect wifiConnect;

    private FragmentManager fm;
    private WifiConFragment wifiConFragment;


    public WifiListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiListFragment newInstance(String param1, String param2) {
        WifiListFragment fragment = new WifiListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        stringRecyclerAdapter = new WifiRecyclerAdapter(getActivity(), wifiArray);
        wifiConnect = new WifiConnect((WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE));
        fm = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wifi_list, container, false);
        unbinder = ButterKnife.bind(this, v);

        initView(v);
        setListener();
        return v;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        wifiRecycler.setItemAnimator(new DefaultItemAnimator());
        wifiRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        wifiRecycler.setAdapter(stringRecyclerAdapter);

        getAllNetWorkList();

    }


    @Override
    protected void setListener() {
        super.setListener();

//        backTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(wifiConFragment != null && wifiConFragment.isVisible()) {
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.hide(wifiConFragment);
//                    ft.commit();
//                } else {
//                    //TODO 调用上一层的接口
//                }
//
//            }
//        });

        stringRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {

                ScanResult sr = (ScanResult) model;
                WifiConfiguration wifiConfiguration = wifiConnect.isExsits(sr.SSID);
                AppUtils.showToast(getActivity().getApplicationContext(), sr.BSSID);
//                if (wifiConfiguration == null) {
                FragmentTransaction ft = fm.beginTransaction();
                if (wifiConFragment == null) {
                    wifiConFragment = WifiConFragment.newInstance(sr.SSID, "");
                    ft.add(R.id.mine_content, wifiConFragment);
                } else {
                    wifiConFragment.setSsid(sr.SSID);
                }

                String capabilities = sr.capabilities;
                if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                    wifiConFragment.setCipherType(WifiConnect.WifiCipherType.WIFICIPHER_WPA);
                } else if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                    wifiConFragment.setCipherType(WifiConnect.WifiCipherType.WIFICIPHER_WEP);
                } else {
                    wifiConFragment.setCipherType(WifiConnect.WifiCipherType.WIFICIPHER_NOPASS);
                }
                ((MainActivity)getActivity()).setMineF(wifiConFragment);

                ft.show(wifiConFragment);
//                    ft.addToBackStack(null);
                ft.commit();


//                } else {
//                    wifiConnect.getWifiManager().enableNetwork(wifiConfiguration.networkId, true);
//                }
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getAllNetWorkList() {
        if (AppUtils.checkGPSIsOpen(getActivity())) {
            wifiConnect.startScan();
            //TODO 这里获取的list size == 0  看看是不是需要权限才行，试试S3上， 打开WiFi试试
            List<ScanResult> list = wifiConnect.getmWifiList();
            if (list != null) {
                wifiArray.clear();
                wifiArray.addAll(wifiConnect.getmWifiList());
                stringRecyclerAdapter.notifyDataSetChanged();
            }
        } else {
            AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
            final AlertDialog ad = b.create();
            b.setMessage("需要打开定位");
            b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ad.dismiss();
                }
            });
            b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ad.dismiss();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, GPS_REQUEST_CODE);
                }
            });

            b.show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if(wifiArray.size() == 0) {
            getAllNetWorkList();
        }

    }

    /* private void sss() {
        wifiInfoAdapter = new WiFiInfoAdapter(getApplicationContext(),
                wifiArray);
        listWifi.setAdapter(wifiInfoAdapter);
        //
        wiFiAdmin.getConfiguration();
        listWifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String wifiItemSSID = null;
            public void onItemClick(android.widget.AdapterView<?> parent,
                                    android.view.View view, int position, long id) {
                SLogger.d("<<", "BSSID:" + list.get(position).BSSID);
                // 连接WiFi
                wifiItemSSID = list.get(position).SSID;
                int wifiItemId = wiFiAdmin.IsConfiguration("\""
                        + list.get(position).SSID + "\"");
                if (wifiItemId != -1) {
                    if (wiFiAdmin.ConnectWifi(wifiItemId)) {
                        // 连接已保存密码的WiFi
                        Toast.makeText(getApplicationContext(), "正在连接",
                                Toast.LENGTH_SHORT).show();
                        updateButton.setVisibility(View.INVISIBLE);
                        updateProgress.setVisibility(View.VISIBLE);
                        new Thread(new refreshWifiThread()).start();
                    }
                } else {
                    // 没有配置好信息，配置
                    WifiPswDialog pswDialog = new WifiPswDialog(
                            WifiListActivity.this,
                            new OnCustomDialogListener() {
                                @Override
                                public void back(String str) {
                                    wifiPassword = str;
                                    if (wifiPassword != null) {
                                        int netId = wiFiAdmin
                                                .AddWifiConfig(list,
                                                        wifiItemSSID,
                                                        wifiPassword);
                                        if (netId != -1) {
                                            wiFiAdmin.getConfiguration();// 添加了配置信息，要重新得到配置信息
                                            if (wiFiAdmin
                                                    .ConnectWifi(netId)) {
                                                // 连接成功，刷新UI
                                                updateProgress
                                                        .setVisibility(View.VISIBLE);
                                                updateButton
                                                        .setVisibility(View.INVISIBLE);
                                                new Thread(
                                                        new refreshWifiThread())
                                                        .start();
                                            }
                                        } else {
                                            // 网络连接错误
                                        }
                                    } else {
                                    }
                                }
                            });
                    pswDialog.show();
                }
            }
        });
    }*/
}
