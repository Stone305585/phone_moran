package com.phone.moran.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.tools.AppTypeface;
import com.phone.moran.view.ScrollerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EaselFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EaselFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ScrollerViewPager viewpager;
    Unbinder unbinder;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecentPaintFragment recentPaintFragment;
    CollectPaintFragment collectPaintFragment;
    MinePaintFragment minePaintFragment;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private MainPagerAdapter mainPagerAdapter;


    public EaselFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EaselFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EaselFragment newInstance(String param1, String param2) {
        EaselFragment fragment = new EaselFragment();
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

        minePaintFragment = MinePaintFragment.newInstance("", "");
        recentPaintFragment = RecentPaintFragment.newInstance("", "");
        collectPaintFragment = CollectPaintFragment.newInstance("", "");
        fm = getActivity().getSupportFragmentManager();
        mainPagerAdapter = new MainPagerAdapter(fm, fragmentList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_easel, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        setListener();

        return view;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        title.setText(getResources().getString(R.string.tab_3));

        backTitle.setVisibility(View.INVISIBLE);

        fragmentList.add(minePaintFragment);
        fragmentList.add(recentPaintFragment);
        fragmentList.add(collectPaintFragment);

        viewpager.setAdapter(mainPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_art_list)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.recent_art)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.mine_collect)));


        //给ViewPager添加监听(这里我们直接使用TabLayout里面提供的TabLayoutOnPageChangeListener无需自己再编写
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        changeViewGroupFonts(getActivity(), (ViewGroup)tabLayout, AppTypeface.REPLACE_FONT, 15, Color.BLACK);

    }

    @Override
    protected void setListener() {
        super.setListener();

        //设置setOnTabSelectedListener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换到制定的item
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
