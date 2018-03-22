package com.phone.moran.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phone.moran.R;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.MoodRoundGridAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalMoods;
import com.phone.moran.model.Mood;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentMoodIListener} interface
 * to handle interaction events.
 * Use the {@link ShareMoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareMoodFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler_mood)
    RecyclerView recyclerMood;


    MoodRoundGridAdapter moodRoundGridAdapter;
    List<Mood> moodList = new ArrayList<>();

    LocalMoods localMoods;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentMoodIListener mListener;

    private int moodIndex;

    public ShareMoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareShareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareMoodFragment newInstance(String param1, String param2) {
        ShareMoodFragment fragment = new ShareMoodFragment();
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

        localMoods = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MOOD + userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pop_add_mood, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        setListener();
        return view;
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.text_red));

        moodList.addAll(localMoods.getMoods());

        moodRoundGridAdapter = new MoodRoundGridAdapter(getActivity(), moodList, 1);
        recyclerMood.setItemAnimator(new DefaultItemAnimator());
        recyclerMood.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerMood.setAdapter(moodRoundGridAdapter);

        moodRoundGridAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {

                mListener.onFragmentMoodI(position);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int uri) {
        if (mListener != null) {
            mListener.onFragmentMoodI(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentMoodIListener) {
            mListener = (OnFragmentMoodIListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {

        if (mListener == null)
            return;

        switch (v.getId()) {
            case R.id.huawen1:
                mListener.onFragmentMoodI(1);
                break;
            case R.id.huawen3:
                mListener.onFragmentMoodI(3);
                break;
            case R.id.huawen2:
                mListener.onFragmentMoodI(2);
                break;
            case R.id.none_huawen:
                mListener.onFragmentMoodI(0);
                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentMoodIListener {
        // TODO: Update argument type and name
        void onFragmentMoodI(int uri);
    }
}
