package com.example.emailmanagerlive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.emailmanagerlive.data.EmailParams;
import com.example.emailmanagerlive.emails.drafts.DraftsFragment;
import com.example.emailmanagerlive.emails.inbox.InboxFragment;
import com.example.emailmanagerlive.emails.sent.SentFragment;
import com.example.emailmanagerlive.send.SendEmailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailParams params = new EmailParams();
                params.setType(EmailParams.Type.INBOX);
                params.setFunction(EmailParams.Function.NORMAL_SEND);
                SendEmailActivity.start2SendEmailActivity(MainActivity.this, params,null);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.textView)).setText(EmailApplication.getAccount().getAccount());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragmentInActivity(InboxFragment.newInstance(), getSupportFragmentManager());
        //启动新消息提醒任务
        startNewEmailWorker();
    }

    private void startNewEmailWorker() {
        //设置约束条件
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)//网络可用
                .setRequiresBatteryNotLow(false)//设备电池是否不应低于临界阈值,默认false
                .build();

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(NewEmailWorker.class,
                1, TimeUnit.MINUTES)//一分钟执行一次
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance().enqueue(workRequest);
    }

    private void replaceFragmentInActivity(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            replaceFragmentInActivity(InboxFragment.newInstance(), getSupportFragmentManager());
        } else if (id == R.id.nav_send) {
            replaceFragmentInActivity(SentFragment.newInstance(), getSupportFragmentManager());
        } else if (id == R.id.nav_drafts) {
            replaceFragmentInActivity(DraftsFragment.newInstance(), getSupportFragmentManager());
        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public static void start2MainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
