package com.phone.moran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.activity.PlayPictureActivity;
import com.phone.moran.activity.SearchActivity;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Paint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends BaseFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    @BindView(R.id.yishu_btn)
    TextView yishuBtn;
    @BindView(R.id.xinqing_btn)
    TextView xinqingBtn;
    @BindView(R.id.changjing_btn)
    TextView changjingBtn;
    @BindView(R.id.viewpager_category)
    ViewPager viewpagerCategory;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArtCFragment artCFragment;
    private MoodCFragment moodCFragment;
    private SceneCFragment sceneCFragment;
    private MainPagerAdapter mainPagerAdapter;

    private List<Fragment> fragmentList = new ArrayList<>();

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        mainPagerAdapter = new MainPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        artCFragment = new ArtCFragment();
        moodCFragment = new MoodCFragment();
        sceneCFragment = new SceneCFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);

        setListener();
        return view;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        fragmentList.add(artCFragment);
        fragmentList.add(moodCFragment);
        fragmentList.add(sceneCFragment);
        viewpagerCategory.setAdapter(mainPagerAdapter);

        backTitle.setVisibility(View.INVISIBLE);

        rightImageBtn3.setVisibility(View.VISIBLE);
        rightImageBtn3.setImageDrawable(getResources().getDrawable(R.mipmap.show_right));
        title.setText(getResources().getString(R.string.category));

        yishuBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
        xinqingBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        changjingBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    protected void setListener() {
        super.setListener();

        yishuBtn.setOnClickListener(this);
        xinqingBtn.setOnClickListener(this);
        changjingBtn.setOnClickListener(this);

        rightImageBtn3.setOnClickListener(this);

        backTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yishu_btn:
                viewpagerCategory.setCurrentItem(0);
                yishuBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
                xinqingBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                changjingBtn.setBackgroundColor(getResources().getColor(R.color.transparent));

                break;
            case R.id.xinqing_btn:
                viewpagerCategory.setCurrentItem(1);
                xinqingBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
                yishuBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                changjingBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case R.id.changjing_btn:
                viewpagerCategory.setCurrentItem(2);
                changjingBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
                xinqingBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                yishuBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;

            case R.id.right_image_btn3:
                Paint paint = diskLruCacheHelper.getAsSerializable(Constant.LAST_PAINT);
                Intent intent = new Intent(getActivity(), PlayPictureActivity.class);
                intent.putExtra(Constant.PAINT, paint);
                startActivity(intent);
                break;
        }
    }
}
