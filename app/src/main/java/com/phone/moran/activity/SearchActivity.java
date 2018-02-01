package com.phone.moran.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.fragment.SearchResultFragment;
import com.phone.moran.model.SearchBack;
import com.phone.moran.presenter.implPresenter.SearchActivityImpl;
import com.phone.moran.presenter.implView.ISearchActivity;
import com.phone.moran.tools.AppUtils;
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

        tabLayout.setVisibility(View.GONE);
        viewpagerSearch.setVisibility(View.GONE);

        searchBtn.setText("取消");

        recentTagList.add("人物");
        recentTagList.add("风景");

        hotTagList.add("现代");
        hotTagList.add("写实");
        hotTagList.add("抽象");
        hotTagList.add("国画");
        hotTagList.add("人物");
        hotTagList.add("风景");
        hotTagList.add("水墨画");

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
    }

    @Override
    protected void setListener() {
        super.setListener();

        hotTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                SLogger.d("<<", "--_>>>>>" + tag);
                kw = tag;
                searchActivityImpl.getSearchResult(kw);
            }
        });

        searchBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

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
                if(!TextUtils.isEmpty(key)) {
                    searchBtn.setText("搜索");
                } else {
                    searchBtn.setText("取消");
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
                if(searchBtn.getText().equals("取消")){
                    finish();
                } else {
                    String kw = searchBar.getText().toString();
                    searchActivityImpl.getSearchResult(kw);
                }
                break;
            case R.id.close_group:
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
