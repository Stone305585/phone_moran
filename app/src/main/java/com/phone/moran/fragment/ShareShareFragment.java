package com.phone.moran.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phone.moran.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentShareIListener} interface
 * to handle interaction events.
 * Use the {@link ShareShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareShareFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.share_wechat)
    ImageView shareWechat;
    @BindView(R.id.share_sina)
    ImageView shareSina;
    @BindView(R.id.share_inx)
    ImageView shareInx;
    @BindView(R.id.share_twitter)
    ImageView shareTwitter;
    @BindView(R.id.share_facebook)
    ImageView shareFacebook;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentShareIListener mListener;

    public ShareShareFragment() {
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
    public static ShareShareFragment newInstance(String param1, String param2) {
        ShareShareFragment fragment = new ShareShareFragment();
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
        View view = inflater.inflate(R.layout.fragment_share_share, container, false);
        unbinder = ButterKnife.bind(this, view);

        setListener();
        return view;
    }

    @Override
    protected void setListener() {
        super.setListener();

        shareFacebook.setOnClickListener(this);
        shareSina.setOnClickListener(this);
        shareWechat.setOnClickListener(this);
        shareInx.setOnClickListener(this);
        shareTwitter.setOnClickListener(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int uri) {
        if (mListener != null) {
            mListener.onFragmentShareI(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentShareIListener) {
            mListener = (OnFragmentShareIListener) context;
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
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

        if(mListener == null)
            return;

        switch (v.getId()) {
            case R.id.share_facebook:
                mListener.onFragmentShareI(4);
                break;
            case R.id.share_wechat:
                mListener.onFragmentShareI(0);
                break;
            case R.id.share_twitter:
                mListener.onFragmentShareI(3);
                break;
            case R.id.share_inx:
                mListener.onFragmentShareI(2);
                break;
            case R.id.share_sina:
                mListener.onFragmentShareI(1);
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
    public interface OnFragmentShareIListener {
        // TODO: Update argument type and name
        void onFragmentShareI(int uri);
    }
}
