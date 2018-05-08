package com.phone.moran.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.SearchResultFragment;
import com.phone.moran.model.SearchBack;
import com.phone.moran.presenter.implPresenter.SearchActivityImpl;
import com.phone.moran.presenter.implView.ISearchActivity;
import com.phone.moran.tools.AppTypeface;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.tools.SLogger;
import com.phone.moran.view.ScrollerViewPager;
import com.phone.moran.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements View.OnClickListener, ISearchActivity {

    public static final String PAINT = "paint";
    public static final String PICTURE = "picture";
    public static final String AUTHOR = "author";

    @BindView(R.id.search_bar)
    EditText searchBar;
    @BindView(R.id.search_btn)
    TextView searchBtn;
    @BindView(R.id.recent_tag_group)
    TagGroup recentTagGroup;
    @BindView(R.id.hot_tag_group)
    TagGroup hotTagGroup;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_search)
    ScrollerViewPager viewpagerSearch;
    @BindView(R.id.clear_btn)
    ImageView clearBtn;
    @BindView(R.id.close_group)
    ImageView closeGroup;
    @BindView(R.id.word_LL)
    LinearLayout wordLL;

    private ArrayList<String> recentTagList = new ArrayList<>();
    private ArrayList<String> hotTagList = new ArrayList<>();
    private SearchActivityImpl searchActivityImpl;
    private String[] mTitles;
    private String pageFlag;

    private String kw = "";

    private SearchResultFragment pictureFragment;
    private SearchResultFragment paintFragment;
    private SearchResultFragment authorFragment;

    private String authorJsonStr = "";
    private String pictureJsonStr = "";
    private String paintJsonStr = "";

    private FragmentManager fm;
    private MainPagerAdapter fragPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchActivityImpl = new SearchActivityImpl(getApplicationContext(), token, this);

        fm = getSupportFragmentManager();

        initView();
        setListener();
        initDataSource();
    }


    @Override
    protected void initView() {
        super.initView();

        String recent = PreferencesUtils.getString(getApplicationContext(), Constant.RECENT_SEARCH);

        if (!TextUtils.isEmpty(recent)) {

            String[] res = recent.split(",");

            for (int i = 0; i < res.length; i++) {
                if (!TextUtils.isEmpty(res[i]))
                    recentTagList.add(res[i]);
            }
        }

        tabLayout.setVisibility(View.GONE);
        viewpagerSearch.setVisibility(View.GONE);

        searchBtn.setText(getResources().getString(R.string.cancel));

        recentTagGroup.setTags(recentTagList);
        hotTagGroup.setTags(hotTagList);

        mTitles = getResources().getStringArray(R.array.search_result_arrays);
        tabLayout.addTab(tabLayout.newTab().setText(mTitles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(mTitles[1]));
        tabLayout.addTab(tabLayout.newTab().setText(mTitles[2]));

        //给ViewPager添加监听(这里我们直接使用TabLayout里面提供的TabLayoutOnPageChangeListener无需自己再编写)
        viewpagerSearch.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        fragPagerAdapter = new MainPagerAdapter(fm, fragmentList);
        viewpagerSearch.setAdapter(fragPagerAdapter);

        changeViewGroupFonts(this, (ViewGroup)tabLayout, AppTypeface.REPLACE_FONT, 15, Color.BLACK);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void setListener() {
        super.setListener();

        hotTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                SLogger.d("<<", "--_>>>>>" + tag);
                searchBar.setText(tag);
                kw = tag;
                searchActivityImpl.getSearchResult(kw);
            }
        });

        recentTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                SLogger.d("<<", "--_>>>>>" + tag);
                searchBar.setText(tag);
                kw = tag;
                searchActivityImpl.getSearchResult(kw);
            }
        });

        searchBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        closeGroup.setOnClickListener(this);

        //设置setOnTabSelectedListener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换到制定的item
                viewpagerSearch.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String key = s.toString();
                if (!TextUtils.isEmpty(key)) {
                    searchBtn.setText(getResources().getString(R.string.search));
                } else {
                    searchBtn.setText(getResources().getString(R.string.cancel));
                }
            }
        });
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();

        searchActivityImpl.getHotSearch();

    }

    @Override
    public void getSearchHot(List<String> hotwords) {
        hotTagList.clear();
        hotTagList.addAll(hotwords);
        hotTagGroup.setTags(hotTagList);
    }

    @Override
    public void searchSuccess(SearchBack searchRes) {

        if ((searchRes.getAuthro_info() == null || searchRes.getAuthro_info().size() == 0) &&
                (searchRes.getPaint_info() == null || searchRes.getPaint_info().size() == 0) &&
                (searchRes.getPicture_info() == null || searchRes.getPicture_info().size() == 0)) {
            AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.no_result));
        } else
            try {

                tabLayout.setVisibility(View.VISIBLE);
                viewpagerSearch.setVisibility(View.VISIBLE);
                wordLL.setVisibility(View.GONE);

                pictureJsonStr = JSONArray.toJSONString(searchRes.getPicture_info());
                paintJsonStr = JSONArray.toJSONString(searchRes.getPaint_info());
                authorJsonStr = JSONArray.toJSONString(searchRes.getAuthro_info());
                if (pictureFragment == null) {

                    pictureFragment = SearchResultFragment.newInstance(PICTURE, pictureJsonStr);
                    authorFragment = SearchResultFragment.newInstance(AUTHOR, authorJsonStr);
                    paintFragment = SearchResultFragment.newInstance(PAINT, paintJsonStr);

                    fragmentList.add(pictureFragment);
                    fragmentList.add(authorFragment);
                    fragmentList.add(paintFragment);

                    fragPagerAdapter.notifyDataSetChanged();
                } else {
                    pictureFragment.setmParam2(pictureJsonStr);
                    paintFragment.setmParam2(paintJsonStr);
                    authorFragment.setmParam2(authorJsonStr);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.search_btn:
                if (searchBtn.getText().equals(getResources().getString(R.string.cancel))) {
                    finish();
                } else {
                    String kw = searchBar.getText().toString();
                    searchActivityImpl.getSearchResult(kw);

                    String recent = PreferencesUtils.getString(getApplicationContext(), Constant.RECENT_SEARCH);

                    //校验本地是否已经存储改kw
                    if (!TextUtils.isEmpty(recent)) {

                        String[] res = recent.split(",");

                        boolean isHave = false;

                        for (int i = 0; i < res.length; i++) {

                            if (res[i].equals(kw)) {
                                isHave = true;
                                break;
                            }
                        }

                        if (!isHave) {
                            recentTagList.add(kw);
                            recent = recent + ("," + kw);
                        }
                    } else {
                        recent = kw;
                        recentTagList.add(kw);
                    }


                    PreferencesUtils.putString(getApplicationContext(), Constant.RECENT_SEARCH, recent);

                    recentTagGroup.setTags(recentTagList);

                }
                break;
            case R.id.close_group:
                recentTagList.clear();
                recentTagGroup.setTags(recentTagList);
                PreferencesUtils.putString(getApplicationContext(), Constant.RECENT_SEARCH, "");
                break;
            case R.id.clear_btn:
                searchBar.setText("");
                tabLayout.setVisibility(View.GONE);
                viewpagerSearch.setVisibility(View.GONE);
                wordLL.setVisibility(View.VISIBLE);
                break;
        }
    }
}
