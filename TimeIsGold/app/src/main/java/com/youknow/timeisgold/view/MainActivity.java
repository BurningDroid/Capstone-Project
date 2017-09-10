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
import com.youknow.timeisgold.view.about.AboutFragment;
import com.youknow.timeisgold.view.activity.CategoryGridFragment;
import com.youknow.timeisgold.view.activity.CategoryPresenter;
import com.youknow.timeisgold.view.activity.details.CategoryDetailsActivity;
import com.youknow.timeisgold.view.history.HistoryFragment;
import com.youknow.timeisgold.view.settings.SettingsFragment;
import com.youknow.timeisgold.view.start.StartActivity;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, MainContractor.View {

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
            startActivity(new Intent(this, StartActivity.class));
            finish();
            return;
        }

        Activity activity = mPresenter.getRunningActivity();
        if (activity != null) {
            Intent intent = new Intent(this, CategoryDetailsActivity.class);
            intent.putExtra(getString(R.string.key_activity), activity);
            startActivity(intent);
            finish();
            return;
        }

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
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                    finish();
                }
            });
        } else {
            btnSignInOut.setText(getString(R.string.signout));
            btnSignInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

        mNavigationView.getMenu().getItem(1).setChecked(true);
        onNavigationItemSelected(mNavigationView.getMenu().getItem(1));
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
        Fragment fragment = null;

        if (id == R.id.nav_today) {
            fragment = new StatisticsFragment();
        } else if (id == R.id.nav_yesterday) {
            fragment = new StatisticsFragment();
        } else if (id == R.id.nav_week) {
            fragment = new StatisticsFragment();
        } else if (id == R.id.nav_month) {
            fragment = new StatisticsFragment();
        } else if (id == R.id.nav_start_activity) {
            fragment = new CategoryGridFragment();
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
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
}
