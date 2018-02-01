package com.phone.moran.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.phone.moran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentHuawenIListener} interface
 * to handle interaction events.
 * Use the {@link ShareHuawenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareHuawenFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.none_huawen)
    FrameLayout noneHuawen;
    @BindView(R.id.huawen1)
    FrameLayout huawen1;
    @BindView(R.id.huawen2)
    FrameLayout huawen2;
    @BindView(R.id.huawen3)
    FrameLayout huawen3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentHuawenIListener mListener;

    public ShareHuawenFragment() {
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
    public static ShareHuawenFragment newInstance(String param1, String param2) {
        ShareHuawenFragment fragment = new ShareHuawenFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_huawen, container, false);
        unbinder = ButterKnife.bind(this, view);

        setListener();
        return view;
    }

    @Override
    protected void setListener() {
        super.setListener();

        noneHuawen.setOnClickListener(this);
        huawen1.setOnClickListener(this);
        huawen2.setOnClickListener(this);
        huawen3.setOnClickListener(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int uri) {
        if (mListener != null) {
            mListener.onFragmentHuawenI(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentHuawenIListener) {
            mListener = (OnFragmentHuawenIListener) context;
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
        try{
            unbinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if (mListener == null)
            return;

        switch (v.getId()) {
            case R.id.huawen1:
                mListener.onFragmentHuawenI(1);
                break;
            case R.id.huawen3:
                mListener.onFragmentHuawenI(3);
                break;
            case R.id.huawen2:
                mListener.onFragmentHuawenI(2);
                break;
            case R.id.none_huawen:
                mListener.onFragmentHuawenI(0);
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
    public interface OnFragmentHuawenIListener {
        // TODO: Update argument type and name
        void onFragmentHuawenI(int uri);
    }
}
