package com.phone.moran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.HHApplication;
import com.phone.moran.R;
import com.phone.moran.activity.PaintActivity;
import com.phone.moran.activity.RecyclerActivity;
import com.phone.moran.adapter.ImagePagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Paint;
import com.phone.moran.model.RecommendHomeArray;
import com.phone.moran.presenter.implPresenter.RecommendFragmentImpl;
import com.phone.moran.presenter.implView.IRecommendFragment;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DensityUtils;
import com.phone.moran.tools.ImageLoader;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.view.ScrollerViewPager;
import com.phone.moran.view.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFragment extends BaseFragment implements IRecommendFragment, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.viewpager_recommend)
    ScrollerViewPager viewpagerRecommend;
    @BindView(R.id.upload_new_btn)
    LinearLayout uploadNewBtn;
    @BindView(R.id.upload_image1)
    RoundedImageView uploadImage1;
    @BindView(R.id.upload_num1)
    TextView uploadNum1;
    @BindView(R.id.upload_read_num1)
    TextView uploadReadNum1;
    @BindView(R.id.upload_title1)
    TextView uploadTitle1;
    @BindView(R.id.upload_FL1)
    FrameLayout uploadFL1;
    @BindView(R.id.upload_image2)
    RoundedImageView uploadImage2;
    @BindView(R.id.upload_num2)
    TextView uploadNum2;
    @BindView(R.id.upload_read_num2)
    TextView uploadReadNum2;
    @BindView(R.id.upload_title2)
    TextView uploadTitle2;
    @BindView(R.id.upload_FL2)
    FrameLayout uploadFL2;
    @BindView(R.id.title_new_btn)
    LinearLayout titleNewBtn;
    @BindView(R.id.title_image1)
    RoundedImageView titleImage1;
    @BindView(R.id.title_num1)
    TextView titleNum1;
    @BindView(R.id.title_read_num1)
    TextView titleReadNum1;
    @BindView(R.id.title_title1)
    TextView titleTitle1;
    @BindView(R.id.title_FL1)
    FrameLayout titleFL1;
    @BindView(R.id.title_image2)
    RoundedImageView titleImage2;
    @BindView(R.id.title_num2)
    TextView titleNum2;
    @BindView(R.id.title_read_num2)
    TextView titleReadNum2;
    @BindView(R.id.title_title2)
    TextView titleTitle2;
    @BindView(R.id.title_FL2)
    FrameLayout titleFL2;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImagePagerAdapter bannerAdapter;
    private List<String> bannerStrs = new ArrayList<>();
    private List<Paint> newArray = new ArrayList<>();
    private List<Paint> hotArray = new ArrayList<>();
    private List<Paint> bannerList = new ArrayList<>();

    private RecommendFragmentImpl recommendFragmentImpl;


    public RecommendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendFragment newInstance(String param1, String param2) {
        RecommendFragment fragment = new RecommendFragment();
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

        bannerAdapter = new ImagePagerAdapter(bannerStrs, getActivity());
        recommendFragmentImpl = new RecommendFragmentImpl(getActivity(), token, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, v);

        initView(v);
        setListener();

        if (bannerStrs.size() == 0)
            onRefresh();
        else
            initDataSource();

        return v;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        srl.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.purple),
                ContextCompat.getColor(getActivity(), R.color.yellow),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.bg_grey2));
        srl.setProgressViewOffset(false, 0, DensityUtils.dip2px(30));
        viewpagerRecommend.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        srl.setOnRefreshListener(this);
        uploadNewBtn.setOnClickListener(this);
        titleNewBtn.setOnClickListener(this);
        uploadFL1.setOnClickListener(this);
        uploadFL2.setOnClickListener(this);
        titleFL1.setOnClickListener(this);
        titleFL2.setOnClickListener(this);

        viewpagerRecommend.setOnSingleTouchListener(new ScrollerViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch(View view, int positon) {
                Intent intent = new Intent(getActivity(), PaintActivity.class);
                intent.putExtra(Constant.PAINT, PaintActivity.RECOMMEND);
                startActivity(intent.putExtra(Constant.PAINT_ID, bannerList.get(viewpagerRecommend.getCurrentItem()).getPaint_id()));
            }
        });

    }

    @Override
    protected void initDataSource() {
        super.initDataSource();

        try {
            bannerAdapter.notifyDataSetChanged();
            //最新上传
            ImageLoader.displayImg(this, newArray.get(0).getTitle_url(), uploadImage1);
            ImageLoader.displayImg(this, newArray.get(1).getTitle_url(), uploadImage2);
            uploadNum1.setText(String.valueOf(newArray.get(0).getPicture_num()));
            uploadNum2.setText(String.valueOf(newArray.get(1).getPicture_num()));
            uploadReadNum1.setText(String.valueOf(newArray.get(0).getRead_num()));
            uploadReadNum2.setText(String.valueOf(newArray.get(1).getRead_num()));
            uploadTitle1.setText(newArray.get(0).getPaint_title());
            uploadTitle2.setText(newArray.get(1).getPaint_title());

            //最热
            ImageLoader.displayImg(this, hotArray.get(0).getTitle_url(), titleImage1);
            ImageLoader.displayImg(this, hotArray.get(1).getTitle_url(), titleImage2);
            titleNum1.setText(String.valueOf(hotArray.get(0).getPicture_num()));
            titleNum2.setText(String.valueOf(hotArray.get(1).getPicture_num()));
            titleReadNum1.setText(String.valueOf(hotArray.get(0).getRead_num()));
            titleReadNum2.setText(String.valueOf(hotArray.get(1).getRead_num()));
            titleTitle1.setText(hotArray.get(0).getPaint_title());
            titleTitle2.setText(hotArray.get(1).getPaint_title());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showProgressDialog() {
        srl.setRefreshing(true);
        dialog.show();
    }

    @Override
    public void hidProgressDialog() {
        dialog.hide();
        srl.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        if (error.equals("Expired Client-Token")) {
            PreferencesUtils.putString(getActivity(), Constant.ACCESS_TOKEN, "");
            PreferencesUtils.putString(getActivity(), Constant.AVATAR, "");
            PreferencesUtils.putString(getActivity(), Constant.USER_NAME, "");
            PreferencesUtils.putString(getActivity(), Constant.MINE_BG, "");
            PreferencesUtils.putString(getActivity(), Constant.USER_ID, "");

            /*LocalPaints l = new LocalPaints();
            //获取本地的 local collect  为空则放入默认的画单  我的收藏
            ArrayList<Paint> paints = new ArrayList<>();
            Paint p = new Paint();
            p.setPaint_id(-1);
            p.setPaint_title("我的收藏");
            paints.add(p);
            l.setPaints(paints);
            diskLruCacheHelper.put(Constant.LOCAL_MINE + userId, l);*/

            HHApplication.checkLogin();
        }
        srl.setRefreshing(false);
        AppUtils.showToast(getActivity().getApplicationContext(), error);
    }

    @Override
    public void updateMain(RecommendHomeArray recommendHomeArray) {

        bannerStrs.clear();
        newArray.clear();
        hotArray.clear();
        bannerList.clear();

        List<Paint> banners = recommendHomeArray.getBanner();
        for (int i = 0; i < banners.size(); i++) {
            bannerStrs.add(banners.get(i).getTitle_url());
        }

        bannerList.addAll(recommendHomeArray.getBanner());
        newArray.addAll(recommendHomeArray.getNew_arry());
        hotArray.addAll(recommendHomeArray.getHot_arry());

        diskLruCacheHelper.put(Constant.LAST_PAINT, newArray.get(0));

        initDataSource();
    }

    @Override
    public void onClick(View v) {
        if (hotArray.size() == 0) {
            return;
        }
        switch (v.getId()) {
            case R.id.upload_new_btn:
                Intent intentNew = new Intent(getActivity(), RecyclerActivity.class);
                intentNew.putExtra(RecyclerActivity.UPLOAD_TYPE, RecyclerActivity.UPLOAD_NEW);
                startActivity(intentNew);
                break;
            case R.id.title_new_btn:
                Intent intentHot = new Intent(getActivity(), RecyclerActivity.class);
                intentHot.putExtra(RecyclerActivity.UPLOAD_TYPE, RecyclerActivity.UPLOAD_HOT);
                startActivity(intentHot);
                break;

            case R.id.upload_FL1:
                Intent upload1Intent = new Intent(getActivity(), PaintActivity.class);
                startActivity(upload1Intent.putExtra(Constant.PAINT_ID, newArray.get(0).getPaint_id()));
                break;
            case R.id.upload_FL2:
                Intent upload2Intent = new Intent(getActivity(), PaintActivity.class);
                startActivity(upload2Intent.putExtra(Constant.PAINT_ID, newArray.get(1).getPaint_id()));
                break;
            case R.id.title_FL1:
                Intent title1Intent = new Intent(getActivity(), PaintActivity.class);
                startActivity(title1Intent.putExtra(Constant.PAINT_ID, hotArray.get(0).getPaint_id()));
                break;
            case R.id.title_FL2:
                Intent title2Intent = new Intent(getActivity(), PaintActivity.class);
                startActivity(title2Intent.putExtra(Constant.PAINT_ID, hotArray.get(1).getPaint_id()));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        recommendFragmentImpl.getMainData();
    }
}
