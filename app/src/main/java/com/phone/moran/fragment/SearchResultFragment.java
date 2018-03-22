package com.phone.moran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.phone.moran.R;
import com.phone.moran.activity.PaintActivity;
import com.phone.moran.activity.PictureActivity;
import com.phone.moran.activity.SearchActivity;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.SearchAuthorRecyclerAdapter;
import com.phone.moran.adapter.SearchPaintRecyclerAdapter;
import com.phone.moran.adapter.SearchPicRecyclerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Author;
import com.phone.moran.model.Paint;
import com.phone.moran.model.Picture;
import com.phone.moran.tools.SLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends BaseFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.result_recycler)
    RecyclerView resultRecycler;
    Unbinder unbinder;

    //当前页面的类型
    private String mParam1;
    //当前页面的数据集
    private String mParam2;


    private SearchAuthorRecyclerAdapter authorAdapter;
    private SearchPaintRecyclerAdapter paintAdapter;
    private SearchPicRecyclerAdapter picAdapter;

    private List<Author> authorList = new ArrayList<>();
    private List<Picture> pictureList = new ArrayList<>();
    private List<Paint> paintList = new ArrayList<>();


    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(String param1, String param2) {
        SearchResultFragment fragment = new SearchResultFragment();

        SLogger.d("<<", "11111mparam1--->" + param1+ "---11111mparam2-->" + param2);

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

        SLogger.d("<<", "mparam1--->" + mParam1+ "---mparam2-->" + mParam2);
        authorAdapter = new SearchAuthorRecyclerAdapter(getActivity(), authorList);
        picAdapter = new SearchPicRecyclerAdapter(getActivity(), pictureList);
        paintAdapter = new SearchPaintRecyclerAdapter(getActivity(), paintList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);
        unbinder = ButterKnife.bind(this, v);

        initView(v);
        setListener();
//        initDataSource();
        return v;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        try {
            resultRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            resultRecycler.setItemAnimator(new DefaultItemAnimator());

            if (mParam2 != null) {
                switch (mParam1) {
                    case SearchActivity.AUTHOR:
                        authorList.clear();
                        List<Author> authors = JSONArray.parseArray(mParam2, Author.class);
                        authorList.addAll(authors);
                        resultRecycler.setAdapter(authorAdapter);
                        break;
                    case SearchActivity.PAINT:
                        paintList.clear();
                        List<Paint> paints = JSONArray.parseArray(mParam2, Paint.class);
                        paintList.addAll(paints);
                        resultRecycler.setAdapter(paintAdapter);
                        break;
                    case SearchActivity.PICTURE:
                        pictureList.clear();
                        List<Picture> pictures = JSONArray.parseArray(mParam2, Picture.class);
                        pictureList.addAll(pictures);
                        resultRecycler.setAdapter(picAdapter);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void setListener() {
        super.setListener();

        paintAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Intent intent = new Intent(getActivity(), PaintActivity.class);
                intent.putExtra(Constant.PAINT_ID, ((Paint)model).getPaint_id());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        picAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Intent intent = new Intent(getActivity(), PictureActivity.class);
                intent.putExtra(Constant.PICTURE_ID, ((Picture) model).getPicture_id());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        authorAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Intent intent = new Intent(getActivity(), PaintActivity.class);
                intent.putExtra(Constant.PAINT_ID, ((Author)model).getPaint_id());
                startActivity(intent);
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
    }

    public String getmParam1() {
        return mParam1;
    }

    public void setmParam1(String mParam1) {
        this.mParam1 = mParam1;
    }

    public String getmParam2() {
        return mParam2;

    }

    public void setmParam2(String mParam2) {
        this.mParam2 = mParam2;

        switch (mParam1) {
            case SearchActivity.AUTHOR:
                authorList.clear();
                List<Author> authors = JSONArray.parseArray(mParam2, Author.class);
                authorList.addAll(authors);
                resultRecycler.setAdapter(authorAdapter);
                break;
            case SearchActivity.PAINT:
                paintList.clear();
                List<Paint> paints = JSONArray.parseArray(mParam2, Paint.class);
                paintList.addAll(paints);
                resultRecycler.setAdapter(paintAdapter);
                break;
            case SearchActivity.PICTURE:
                pictureList.clear();
                List<Picture> pictures = JSONArray.parseArray(mParam2, Picture.class);
                pictureList.addAll(pictures);
                resultRecycler.setAdapter(picAdapter);
                break;
        }
    }
}
