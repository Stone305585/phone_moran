package com.phone.moran.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.phone.moran.R;
import com.phone.moran.activity.RegisterActivity;
import com.phone.moran.presenter.implPresenter.LoginActivityImpl;
import com.phone.moran.tools.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MobileRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MobileRegisterFragment extends BaseFragment {

    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.choose_front_btn)
    LinearLayout chooseFrontBtn;
    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.password_repeat_et)
    EditText passwordRepeatEt;
    @BindView(R.id.next_btn)
    Button nextBtn;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LoginActivityImpl loginActivityImpl;

    public MobileRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MobileRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MobileRegisterFragment newInstance(String param1, String param2) {
        MobileRegisterFragment fragment = new MobileRegisterFragment();
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

        loginActivityImpl = new LoginActivityImpl(getActivity(), token, (RegisterActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        setListener();
        return view;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void setListener() {
        super.setListener();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone = mobileEt.getText().toString();
                String password = passwordEt.getText().toString();
                String password1 = passwordRepeatEt.getText().toString();
                if (!password.equals(password1)) {
                    AppUtils.showToast(getActivity().getApplicationContext(), getResources().getString(R.string.password_not_identify));
                } else {
                    if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone)) {
                        loginActivityImpl.getCode(phone);
                    } else {
                        AppUtils.showToast(getActivity().getApplicationContext(), getResources().getString(R.string.user_name_email));

                    }
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private String phone;
    private String password;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
