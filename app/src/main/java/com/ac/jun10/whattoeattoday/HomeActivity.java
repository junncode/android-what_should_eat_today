package com.ac.jun10.whattoeattoday;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nameTextView;
    private TextView emailTextView;
    private FirebaseAuth auth;

    private LinearLayout bobtingButton;
    private LinearLayout schoolfoodButton;
    private LinearLayout cafeButton;
    private LinearLayout pubButton;
    private LinearLayout navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//firebase 회원 불러오기
        auth = FirebaseAuth.getInstance();
 //권한 주기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
//navigation 헤더 색상 변경
        navHeader = (LinearLayout)view.findViewById(R.id.nav_header);
        navHeader.setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
//navigation 헤더에 나타나는 유져의 이름과 아이디를 변경
        nameTextView = (TextView)view.findViewById(R.id.header_name_textView);
        emailTextView = (TextView)view.findViewById(R.id.header_email_textView);

        nameTextView.setText((auth.getCurrentUser().getDisplayName()));
        emailTextView.setText(auth.getCurrentUser().getEmail());
//HomeActivity의 actionbar 설정
        getSupportActionBar().setTitle("오늘 뭐 먹지?");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
//첫 번째 밥팅 버튼
        bobtingButton = (LinearLayout) findViewById(R.id.bobting_button);
        bobtingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BobtingActivity.class);
                startActivity(intent);
            }
       });
//두 번째 학식 버튼
        schoolfoodButton = (LinearLayout) findViewById(R.id.schoolfood_button);
        schoolfoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.soongguri.com/"));
                startActivity(intent);
            }
        });
//세 번째 카페 버튼
        cafeButton = (LinearLayout)findViewById(R.id.cafe_button);
        cafeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CafeActivity.class);
                intent.putExtra("activityTitle", "학교 앞 카페");
                intent.putExtra("databaseName", "cafes");
                startActivity(intent);
            }
        });
//네 번째 술집 버튼
        pubButton = (LinearLayout)findViewById(R.id.pub_button);
        pubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CafeActivity.class);
                intent.putExtra("activityTitle", "학교 앞 술집");
                intent.putExtra("databaseName", "pubs");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reselect) {

            Intent myintent = new Intent(this, FavoriteActivity.class);
            myintent.putExtra("approach", 1);
            startActivity(myintent);

        } else if (id == R.id.nav_store_rank) {

            Intent myintent = new Intent(this, StoreRankActivity.class);
            startActivity(myintent);

        } else if (id == R.id.nav_logout){
            auth.signOut();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
