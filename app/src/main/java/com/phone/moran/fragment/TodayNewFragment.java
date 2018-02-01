package com.phone.moran.fragment;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.activity.PaintActivity;
import com.phone.moran.activity.ReadRecyclerActivity;
import com.phone.moran.activity.ShareActivity;
import com.phone.moran.adapter.ImagePagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.ClassicQuote;
import com.phone.moran.model.MasterQuote;
import com.phone.moran.model.Paint;
import com.phone.moran.model.PioneerHomeArray;
import com.phone.moran.presenter.implPresenter.PioneerFragmentImpl;
import com.phone.moran.presenter.implView.IPioneerFragment;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DateUtils;
import com.phone.moran.tools.ImageLoader;
import com.phone.moran.tools.SLogger;
import com.phone.moran.view.FoldingLayout1;
import com.phone.moran.view.ScrollerViewPager;
import com.phone.moran.view.TouchFoldingLayout;
import com.phone.moran.view.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayNewFragment extends BaseFragment implements View.OnClickListener, IPioneerFragment, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.viewpager_today)
    ScrollerViewPager viewpagerToday;
    @BindView(R.id.upload_new_btn)
    LinearLayout uploadNewBtn;
    @BindView(R.id.week_daka)
    TextView weekDaka;
    @BindView(R.id.date_daka)
    TextView dateDaka;
    @BindView(R.id.content_daka)
    TextView contentDaka;
    @BindView(R.id.thumb_btn)
    LinearLayout thumbBtn;
    @BindView(R.id.share_btn)
    LinearLayout shareBtn;
    @BindView(R.id.title_new_btn)
    LinearLayout titleNewBtn;
    Unbinder unbinder;
    @BindView(R.id.foldingView)
    FoldingLayout1 foldingView;
    @BindView(R.id.touch_FL)
    TouchFoldingLayout touchFoldingLayout;
    @BindView(R.id.main_LL)
    LinearLayout mainLL;
//    @BindView(R.id.srl)
//    SwipeRefreshLayout srl;
    @BindView(R.id.thumb_img)
    ImageView thumbImg;
    @BindView(R.id.thumb_num)
    TextView thumbNum;
    @BindView(R.id.cq_image1)
    RoundedImageView cqImage1;
    @BindView(R.id.cq_title1)
    TextView cqTitle1;
    @BindView(R.id.cq_text1)
    TextView cqText1;
    @BindView(R.id.cq_LL1)
    LinearLayout cqLL1;
    @BindView(R.id.cq_image2)
    RoundedImageView cqImage2;
    @BindView(R.id.cq_title2)
    TextView cqTitle2;
    @BindView(R.id.cq_text2)
    TextView cqText2;
    @BindView(R.id.cq_LL2)
    LinearLayout cqLL2;
    @BindView(R.id.cq_image3)
    RoundedImageView cqImage3;
    @BindView(R.id.cq_title3)
    TextView cqTitle3;
    @BindView(R.id.cq_text3)
    TextView cqText3;
    @BindView(R.id.cq_LL3)
    LinearLayout cqLL3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImagePagerAdapter bannerAdapter;
    private List<String> bannerStrs = new ArrayList<>();
    private List<ClassicQuote> cqList = new ArrayList<>();
    private MasterQuote masterQuote = new MasterQuote();
    private List<Paint> bannerList = new ArrayList<>();

    private PioneerFragmentImpl pioneerFragmentImpl;


    private ValueAnimator valueAnimator;

    public TodayNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayNewFragment newInstance(String param1, String param2) {
        TodayNewFragment fragment = new TodayNewFragment();
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
        pioneerFragmentImpl = new PioneerFragmentImpl(getActivity(), token, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_today_new, container, false);
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

        touchFoldingLayout.setFoldingLayout1(foldingView);

//        srl.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.purple),
//                ContextCompat.getColor(getActivity(), R.color.yellow),
//                ContextCompat.getColor(getActivity(), R.color.blue),
//                ContextCompat.getColor(getActivity(), R.color.bg_grey2));
//        srl.setProgressViewOffset(false, 0, DensityUtils.dip2px(30));
        viewpagerToday.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();

    }

    @Override
    protected void setListener() {
        super.setListener();
        uploadNewBtn.setOnClickListener(this);
//        srl.setOnRefreshListener(this);
        uploadNewBtn.setOnClickListener(this);
        titleNewBtn.setOnClickListener(this);
        cqLL1.setOnClickListener(this);
        cqLL2.setOnClickListener(this);
        cqLL3.setOnClickListener(this);
        thumbBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);

        viewpagerToday.setOnSingleTouchListener(new ScrollerViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch(View view, int positon) {
                Intent intent = new Intent(getActivity(), PaintActivity.class);
                intent.putExtra(Constant.PAINT, PaintActivity.TODAY);
                startActivity(intent.putExtra(Constant.PAINT_ID, bannerList.get(viewpagerToday.getCurrentItem()).getPaint_id()));
            }
        });

        foldingView.setFoldListener(new FoldingLayout1.OnFoldListener() {
            @Override
            public void onStartFold() {
            }

            @Override
            public void onEndFold() {
            }

            @Override
            public void onGoneFold() {

            }

            @Override
            public void currentPixel(float pix) {
                SLogger.d("fffo", "pix-->>" + pix);

                if(pix > 565)
                    return;

//                if (foldingView.getState() == FoldingLayout1.STATE.FOLDING && foldingView.getTop() < (mainLL.getTop() + mainLL.getTranslationY())) {
//                if (foldingView.getTop() < (mainLL.getTop() + mainLL.getTranslationY())) {
//                    foldingView.setState(FoldingLayout1.STATE.FOLDING);
                    mainLL.setTranslationY(-pix);
//                }

            }
        });
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();

        try {
            bannerAdapter.notifyDataSetChanged();

            foldingView.setState(FoldingLayout1.STATE.NORMAL);
            foldingView.setFoldFactor(0);
            mainLL.setTranslationY(0);

            contentDaka.setText(masterQuote.getMq_content());
            dateDaka.setText(DateUtils.getChineseDate(System.currentTimeMillis()));

            SLogger.d("<<", "current time-->>" + System.currentTimeMillis());

            weekDaka.setText(DateUtils.getWeekOfDate(System.currentTimeMillis()));
            thumbNum.setText(String.valueOf(masterQuote.getMq_love_num()));
            if (masterQuote.getFlag() == 1) {
                thumbImg.setImageResource(R.mipmap.thumb_uped);
            } else {
                thumbImg.setImageResource(R.mipmap.thumb_up);
            }

            ClassicQuote item1 = cqList.get(0);
            ClassicQuote item2 = cqList.get(1);
//            ClassicQuote item3 = cqList.get(2);

            ImageLoader.displayImg(this, item1.getCq_img_url(), cqImage1);
            ImageLoader.displayImg(this, item2.getCq_img_url(), cqImage2);
//            ImageLoader.displayImg(this, item3.getCq_img_url(), cqImage3);

            cqText1.setText(item1.getCq_content());
            cqText2.setText(item2.getCq_content());
//            cqText3.setText(item3.getCq_content());

            cqTitle1.setText(item1.getCq_title());
            cqTitle2.setText(item2.getCq_title());
//            cqTitle3.setText(item3.getCq_title());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_new_btn:
                break;

            case R.id.title_new_btn:
                startActivity(new Intent(getActivity(), ReadRecyclerActivity.class));
                break;

            case R.id.cq_LL1:
                break;
            case R.id.cq_LL2:
                break;
//            case R.id.cq_LL3:
//                break;

            case R.id.thumb_btn:
                pioneerFragmentImpl.thumbMq(masterQuote.getMq_id());
                break;

            case R.id.share_btn:
                Intent intent = new Intent(getActivity(), ShareActivity.class);
                intent.putExtra(Constant.MASTER, masterQuote);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void updateMain(PioneerHomeArray pioneerHomeArray) {
        bannerStrs.clear();
        cqList.clear();
        bannerList.clear();

        List<Paint> banners = pioneerHomeArray.getBanner();
        for (int i = 0; i < banners.size(); i++) {
            bannerStrs.add(banners.get(i).getTitle_url());
        }
        cqList.addAll(pioneerHomeArray.getClassic_quote());
        bannerList.addAll(pioneerHomeArray.getBanner());
        masterQuote = pioneerHomeArray.getMaster_quote();
        initDataSource();
    }

    @Override
    public void thumbSuccess() {
        int temp = masterQuote.getMq_love_num();
        if(masterQuote.getFlag() == 1) {
            thumbNum.setText(String.valueOf((temp - 1)));
            masterQuote.setMq_love_num(--temp);
            masterQuote.setFlag(2);
            thumbImg.setImageResource(R.mipmap.thumb_up);
        } else {
            thumbNum.setText(String.valueOf((temp + 1)));
            masterQuote.setMq_love_num(++temp);
            masterQuote.setFlag(1);
            thumbImg.setImageResource(R.mipmap.thumb_uped);
        }
    }

    @Override
    public void showProgressDialog() {
//        srl.setRefreshing(true);
        dialog.show();
    }

    @Override
    public void hidProgressDialog() {
        dialog.hide();
//        srl.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
//        srl.setRefreshing(false);
        AppUtils.showToast(getActivity().getApplicationContext(), error);
    }

    @Override
    public void onRefresh() {
        pioneerFragmentImpl.getMainData();

//        if (foldingView.getState() != FoldingLayout1.STATE.GONE) {
//            curFactor = foldingView.getmFoldFactor();
//        }

        //TODO setFactor = 1/0;
    }

    private float curFactor;
}
