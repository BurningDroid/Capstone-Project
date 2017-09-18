package com.youknow.timeisgold.view;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.utils.SharedPrefUtil;
import com.youknow.timeisgold.view.activity.CategoryGridFragment;
import com.youknow.timeisgold.view.activity.details.CategoryDetailsActivity;
import com.youknow.timeisgold.view.auth.AuthActivity;
import com.youknow.timeisgold.view.history.HistoryFragment;
import com.youknow.timeisgold.view.statistics.StatisticsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, MainContractor.View {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static boolean NOT_INITIALIZED = false;

    FirebaseAuth mAuth;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    GoogleApiClient mGoogleApiClient;
    MainContractor.Presenter mPresenter;
    static Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mPresenter = MainPresenter.getInstance(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .enableAutoManage(MainActivity.this /* FragmentActivity */, MainActivity.this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (NOT_INITIALIZED == SharedPrefUtil.getInstance(this).isInitialized()) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return;
        }

        mPresenter.getRunningActivity();

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        View headerLayout = mNavigationView.getHeaderView(0);
        Button btnSignInOut = (Button) headerLayout.findViewById(R.id.btn_sign_in_out);
        if (user == null) {
            btnSignInOut.setText(getString(R.string.common_signin_button_text));
            btnSignInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, AuthActivity.class));
                    finish();
                }
            });
        } else {
            btnSignInOut.setText(getString(R.string.signout));
            btnSignInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.signOut();
                    mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    recreate();
                }
            });
            ImageView ivProfile = (ImageView) headerLayout.findViewById(R.id.iv_profile);
            Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(ivProfile);
            TextView tvName = (TextView) headerLayout.findViewById(R.id.tv_name);
            tvName.setText(user.getDisplayName());
        }

        if (fragment == null) {
            onNavigationItemSelected(mNavigationView.getMenu().findItem(R.id.nav_start_activity));
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            fragment = new StatisticsFragment();
            Bundle args = new Bundle();
            args.putInt(getString(R.string.key_statistics_day), 0);
            fragment.setArguments(args);
        } else if (id == R.id.nav_yesterday) {
            fragment = new StatisticsFragment();
            Bundle args = new Bundle();
            args.putInt(getString(R.string.key_statistics_day), 1);
            fragment.setArguments(args);
        } else if (id == R.id.nav_week) {
            fragment = new StatisticsFragment();
            Bundle args = new Bundle();
            args.putInt(getString(R.string.key_statistics_day), 7);
            fragment.setArguments(args);
        } else if (id == R.id.nav_month) {
            fragment = new StatisticsFragment();
            Bundle args = new Bundle();
            args.putInt(getString(R.string.key_statistics_day), 30);
            fragment.setArguments(args);
        } else if (id == R.id.nav_start_activity) {
            fragment = CategoryGridFragment.newInstance();
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void loadCategoryDetails(Activity activity) {
        Intent intent = new Intent(this, CategoryDetailsActivity.class);
        intent.putExtra(getString(R.string.key_activity), activity);
        startActivity(intent);
        finish();
        return;
    }
}
