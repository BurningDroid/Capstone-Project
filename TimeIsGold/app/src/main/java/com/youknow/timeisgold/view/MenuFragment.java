package com.youknow.timeisgold.view;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.view.auth.AuthActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    interface MenuListener {
        void onMenuSelected(int id);
    }

    private static final String TAG = MenuFragment.class.getSimpleName();

    @BindView(R.id.btn_sign_in_out)
    Button mBtnSignInOut;
    @BindView(R.id.iv_profile)
    ImageView mIvProfile;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.menu_today)
    LinearLayout mMenuToday;
    @BindView(R.id.menu_yesterday)
    LinearLayout mMenuYesterday;
    @BindView(R.id.menu_week)
    LinearLayout mMenuWeek;
    @BindView(R.id.menu_month)
    LinearLayout mMenuMonth;
    @BindView(R.id.menu_start_activity)
    LinearLayout mMenuStartActivity;
    @BindView(R.id.menu_history)
    LinearLayout mMenuHistory;

    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    MainContractor.Presenter mPresenter;
    MenuListener mMenuListener;

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[TIG] onCreateView");
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, root);

        mMenuToday.setOnClickListener(this);
        mMenuYesterday.setOnClickListener(this);
        mMenuWeek.setOnClickListener(this);
        mMenuMonth.setOnClickListener(this);
        mMenuStartActivity.setOnClickListener(this);
        mMenuHistory.setOnClickListener(this);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "[TIG] onCreate");
        super.onCreate(savedInstanceState);
        mPresenter = MainPresenter.getInstance(getContext());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            mBtnSignInOut.setText(getString(R.string.common_signin_button_text));
            mBtnSignInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), AuthActivity.class));
                    getActivity().finish();
                }
            });
        } else {
            mBtnSignInOut.setText(getString(R.string.signout));
            mBtnSignInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.signOut();
                    mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    getActivity().recreate();
                }
            });
            Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(mIvProfile);
            mTvName.setText(user.getDisplayName());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuListener) {
            mMenuListener = (MenuListener) context;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        int id;
        switch (v.getId()) {
            case R.id.menu_today:
                id = R.id.nav_today;
                break;
            case R.id.menu_yesterday:
                id = R.id.nav_yesterday;
                break;
            case R.id.menu_week:
                id = R.id.nav_week;
                break;
            case R.id.menu_month:
                id = R.id.nav_month;
                break;
            case R.id.menu_history:
                id = R.id.nav_history;
                break;
            default:
                id = R.id.nav_start_activity;
                break;
        }

        mMenuListener.onMenuSelected(id);
    }
}
