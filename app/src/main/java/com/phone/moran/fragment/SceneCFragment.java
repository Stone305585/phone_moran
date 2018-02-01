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
import com.phone.moran.model.CategorySceneBack;
import com.phone.moran.model.Paint;
import com.phone.moran.presenter.implPresenter.SceneCFragmentImpl;
import com.phone.moran.presenter.implView.ISceneCFragment;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.view.recycler.AdjustGLManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SceneCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SceneCFragment extends BaseFragment implements ISceneCFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.weizhi_recycler)
    RecyclerView weizhiRecycler;
    @BindView(R.id.jiaju_recycler)
    RecyclerView jiajuRecycler;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArtRoundGridAdapter artRoundGridAdapterA;
    private ArtRoundGridAdapter artRoundGridAdapterB;

    private List<Paint> categoriesA = new ArrayList<>();
    private List<Paint> categoriesB = new ArrayList<>();

    private SceneCFragmentImpl sceneCFragmentImpl;

    private CategorySceneBack categoryArtBack;

    public SceneCFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SceneCFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SceneCFragment newInstance(String param1, String param2) {
        SceneCFragment fragment = new SceneCFragment();
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

        sceneCFragmentImpl = new SceneCFragmentImpl(getActivity(), token, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scene, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (categoriesA.size() == 0) {
            initDataSource();
        }
        initView(v);
        setListener();
        return v;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        weizhiRecycler.setItemAnimator(new DefaultItemAnimator());
        jiajuRecycler.setItemAnimator(new DefaultItemAnimator());
        weizhiRecycler.setLayoutManager(new AdjustGLManager(getActivity(), 3));
        jiajuRecycler.setLayoutManager(new AdjustGLManager(getActivity(), 3));
        weizhiRecycler.setAdapter(artRoundGridAdapterA);
        jiajuRecycler.setAdapter(artRoundGridAdapterB);
    }

    @Override
    protected void setListener() {
        super.setListener();

        artRoundGridAdapterA.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Paint paint = (Paint) model;
                Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
                intent.putExtra(CategoryDetailActivity.TYPEID, categoryArtBack.getScene_home_page().get(0).getType_id());
                intent.putExtra(Constant.PAINT_ID, paint.getPaint_id());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        artRoundGridAdapterB.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Paint paint = (Paint) model;
                Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
                intent.putExtra(CategoryDetailActivity.TYPEID, categoryArtBack.getScene_home_page().get(1).getType_id());
                intent.putExtra(Constant.PAINT_ID, paint.getPaint_id());
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

        if (connected) {
            sceneCFragmentImpl.getMainData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (unbinder != null) {
                unbinder.unbind();
                unbinder = null;
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
        AppUtils.showToast(getActivity().getApplicationContext(), error);
    }

    @Override
    public void updateMain(CategorySceneBack caBack) {

        categoryArtBack = caBack;

        categoriesA.clear();
        categoriesB.clear();

        categoriesA.addAll(caBack.getScene_home_page().get(0).getPaints());
        categoriesB.addAll(caBack.getScene_home_page().get(1).getPaints());

        artRoundGridAdapterA.notifyDataSetChanged();
        artRoundGridAdapterB.notifyDataSetChanged();
    }
}
