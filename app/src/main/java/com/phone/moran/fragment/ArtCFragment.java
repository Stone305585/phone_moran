package com.phone.moran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phone.moran.R;
import com.phone.moran.activity.CategoryDetailActivity;
import com.phone.moran.adapter.ArtRoundGridAdapter;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.CategoryArtBack;
import com.phone.moran.model.Paint;
import com.phone.moran.presenter.implPresenter.ArtCFragmentImpl;
import com.phone.moran.presenter.implView.IArtCFragment;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.view.recycler.AdjustGLManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtCFragment extends BaseFragment implements IArtCFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.fengge_recycler)
    RecyclerView fenggeRecycler;
    @BindView(R.id.zuopin_recycler)
    RecyclerView zuopinRecycler;
    @BindView(R.id.niandai_recycler)
    RecyclerView niandaiRecycler;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArtRoundGridAdapter artRoundGridAdapterA;
    private ArtRoundGridAdapter artRoundGridAdapterB;
    private ArtRoundGridAdapter artRoundGridAdapterC;

    private List<Paint> categoriesA = new ArrayList<>();
    private List<Paint> categoriesB = new ArrayList<>();
    private List<Paint> categoriesC = new ArrayList<>();

    private ArtCFragmentImpl artCFragmentImpl;

    private CategoryArtBack categoryArtBack;


    public ArtCFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtCFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtCFragment newInstance(String param1, String param2) {
        ArtCFragment fragment = new ArtCFragment();
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

        artRoundGridAdapterA = new ArtRoundGridAdapter(getActivity(), categoriesA);
        artRoundGridAdapterB = new ArtRoundGridAdapter(getActivity(), categoriesB);
        artRoundGridAdapterC = new ArtRoundGridAdapter(getActivity(), categoriesC);

        artCFragmentImpl = new ArtCFragmentImpl(getContext(), token, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_yishu, container, false);
        unbinder = ButterKnife.bind(this, v);

        if(categoriesA.size() == 0) {
            initDataSource();
        }
        initView(v);
        setListener();
        return v;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        fenggeRecycler.setItemAnimator(new DefaultItemAnimator());
        zuopinRecycler.setItemAnimator(new DefaultItemAnimator());
        niandaiRecycler.setItemAnimator(new DefaultItemAnimator());
        fenggeRecycler.setLayoutManager(new AdjustGLManager(getActivity(), 3));
        zuopinRecycler.setLayoutManager(new AdjustGLManager(getActivity(), 3));
        niandaiRecycler.setLayoutManager(new AdjustGLManager(getActivity(), 3));
        fenggeRecycler.setAdapter(artRoundGridAdapterA);
        zuopinRecycler.setAdapter(artRoundGridAdapterB);
        niandaiRecycler.setAdapter(artRoundGridAdapterC);
    }

    @Override
    protected void setListener() {
        super.setListener();

        artRoundGridAdapterA.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {

                if(categoryArtBack != null) {
                    Paint paint = (Paint)model;
                    Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
                    intent.putExtra(CategoryDetailActivity.TYPEID, categoryArtBack.getArt_home_page().get(0).getType_id());
                    intent.putExtra(Constant.PAINT_ID, paint.getPaint_id());
                    intent.putExtra(Constant.TITLE, paint.getPaint_name());
                    startActivity(intent);
                }


            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        artRoundGridAdapterB.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Paint paint = (Paint)model;
                Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
                intent.putExtra(CategoryDetailActivity.TYPEID, categoryArtBack.getArt_home_page().get(1).getType_id());
                intent.putExtra(Constant.PAINT_ID, paint.getPaint_id());
                intent.putExtra(Constant.TITLE, paint.getPaint_name());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        artRoundGridAdapterC.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Paint paint = (Paint)model;
                Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
                intent.putExtra(CategoryDetailActivity.TYPEID, categoryArtBack.getArt_home_page().get(2).getType_id());
                intent.putExtra(Constant.PAINT_ID, paint.getPaint_id());
                intent.putExtra(Constant.TITLE, paint.getPaint_name());
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
        if(connected) {
            artCFragmentImpl.getMainData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
        AppUtils.showToast(getActivity().getApplicationContext(), error);
    }

    @Override
    public void updateMain(CategoryArtBack caBack) {

        categoryArtBack = caBack;

        categoriesA.clear();
        categoriesB.clear();
        categoriesC.clear();

        categoriesA.addAll(caBack.getArt_home_page().size() > 0 ? caBack.getArt_home_page().get(0).getPaints() : new ArrayList<Paint>());
        categoriesB.addAll(caBack.getArt_home_page().size() > 1 ? caBack.getArt_home_page().get(1).getPaints() : new ArrayList<Paint>());
        categoriesC.addAll(caBack.getArt_home_page().size() > 2 ? caBack.getArt_home_page().get(2).getPaints() : new ArrayList<Paint>());

       /* for(int i = 0; i < categoriesA.size(); i++) {
            Paint p = categoriesA.get(i);

            switch (p.getPaint_title()) {
                case "写实":
                    p.setPaint_title(getResources().getString(R.string.realism_paint));
                    break;
                case "抽象":
                    p.setPaint_title(getResources().getString(R.string.abstract_paint));
                    break;
                case "现代":
                    p.setPaint_title(getResources().getString(R.string.modern_new));
                    break;
                case "人物":
                    p.setPaint_title(getResources().getString(R.string.figure_paint));
                    break;
                case "山水":
                    p.setPaint_title(getResources().getString(R.string.water_paint));
                    break;
                case "花鸟":
                    p.setPaint_title(getResources().getString(R.string.bird_paint));
                    break;


            }
        }

        for(int i = 0; i < categoriesB.size(); i++) {
            Paint p = categoriesB.get(i);

            switch (p.getPaint_title()) {
                case "国画":
                    p.setPaint_title(getResources().getString(R.string.tradition_paint));
                    break;
                case "书法":
                    p.setPaint_title(getResources().getString(R.string.calligraphy));
                    break;
                case "油画":
                    p.setPaint_title(getResources().getString(R.string.oil_paint));
                    break;
                case "版画":
                    p.setPaint_title(getResources().getString(R.string.wood_paint));
                    break;
                case "素描":
                    p.setPaint_title(getResources().getString(R.string.sketch));
                    break;
                case "雕塑":
                    p.setPaint_title(getResources().getString(R.string.sculpture));
                    break;

                case "新媒体艺术":
                    p.setPaint_title(getResources().getString(R.string.new_media));
                    break;
            }
        }

        for(int i = 0; i < categoriesC.size(); i++) {
            Paint p = categoriesC.get(i);

            switch (p.getPaint_title()) {
                case "近代":
                    p.setPaint_title(getResources().getString(R.string.modern));
                    break;
                case "当代":
                    p.setPaint_title(getResources().getString(R.string.contemporary));
                    break;
                case "古代":
                    p.setPaint_title(getResources().getString(R.string.ancient));
                    break;

            }
        }*/
        artRoundGridAdapterC.notifyDataSetChanged();
        artRoundGridAdapterB.notifyDataSetChanged();
        artRoundGridAdapterA.notifyDataSetChanged();
    }
}
