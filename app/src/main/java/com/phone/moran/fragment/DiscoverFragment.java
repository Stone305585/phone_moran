package com.phone.moran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.activity.PlayPictureActivity;
import com.phone.moran.activity.SearchActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.diskCache.DiskLruCacheHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.search_bar)
    TextView searchBar;
    @BindView(R.id.show_btn)
    ImageView showBtn;
//    @BindView(R.id.viewpager_discver)
//    ViewPager viewpagerDiscver;
    @BindView(R.id.root_coordinator_layout)
    CoordinatorLayout rootCoordinatorLayout;
    Unbinder unbinder;
    @BindView(R.id.recommend_btn)
    TextView recommendBtn;
    @BindView(R.id.today_new_btn)
    TextView todayNewBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private MainPagerAdapter mainPagerAdapter;
    private RecommendFragment recommendFragment;
    private TodayNewFragment todayNewFragment;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();


    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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

        recommendFragment = RecommendFragment.newInstance("", "");
        todayNewFragment = TodayNewFragment.newInstance("", "");
        fm = getActivity().getSupportFragmentManager();
//        mainPagerAdapter = new MainPagerAdapter(fm, fragmentList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        setListener();
        return view;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        fragmentList.add(recommendFragment);
        fragmentList.add(todayNewFragment);
//        viewpagerDiscver.setAdapter(mainPagerAdapter);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_fragment, recommendFragment);
        ft.commit();

        todayNewBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        recommendBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
    }

    @Override
    protected void setListener() {
        super.setListener();

        recommendBtn.setOnClickListener(this);
        todayNewBtn.setOnClickListener(this);
        searchBar.setOnClickListener(this);

        showBtn.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.recommend_btn:
//                viewpagerDiscver.setCurrentItem(0);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_fragment, recommendFragment);
                ft.commit();
                todayNewBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                recommendBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
                break;
            case R.id.today_new_btn:
//                viewpagerDiscver.setCurrentItem(1);
                FragmentTransaction ft1 = fm.beginTransaction();
                ft1.replace(R.id.content_fragment, todayNewFragment);
                ft1.commit();
                recommendBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                todayNewBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
                break;
            case R.id.search_bar:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;

            case R.id.show_btn:

                Paint paint = diskLruCacheHelper.getAsSerializable(Constant.LAST_PAINT);
                if(paint == null) {
                    try {
                        diskLruCacheHelper = new DiskLruCacheHelper(getActivity().getApplicationContext());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    paint = diskLruCacheHelper.getAsSerializable(Constant.LAST_PAINT);

                    if(paint == null) {
                        AppUtils.showToast(getActivity().getApplicationContext(), getResources().getString(R.string.net_dissconncted));
                        return;
                    }
                }
                Intent intent = new Intent(getActivity(), PlayPictureActivity.class);
                intent.putExtra(Constant.PAINT, paint);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
