package com.example.emailmanagerlive.send;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.EmailRepository;
import com.example.emailmanagerlive.databinding.ActivitySendEmailBinding;
import com.example.emailmanagerlive.send.adapter.AttachmentListAdapter;
import com.example.multifile.XRMultiFile;
import com.example.multifile.ui.EMDecoration;
import com.google.android.material.snackbar.Snackbar;

public class SendEmailActivity extends AppCompatActivity implements SendEmailNavigator {

    public static final int SEND = 1;
    public static final int REPLY = 2;
    public static final int FORWARD = 3;

    private ActivitySendEmailBinding binding;
    private SendEmailViewModel viewModel;
    private ProgressDialog dialog;
    private AttachmentListAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_email);
        binding.rvAttachment.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAttachment.addItemDecoration(new EMDecoration(this, EMDecoration.VERTICAL_LIST,
                R.drawable.list_divider, 0));
        setupToolbar();
        setupAdapter();
        setupViewModel();
        setupSnackBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.start(getIntent().getIntExtra("type", -1), (Email) getIntent()
                .getParcelableExtra("data"));
    }

    @Override
    public void onSending(String msg) {
        dialog = ProgressDialog.show(this, "", msg, false, false);
    }

    @Override
    public void onSent() {
        if (dialog != null)
            dialog.cancel();
        SystemClock.sleep(500);
        finish();
    }

    @Override
    public void onSentError() {
        if (dialog != null)
            dialog.cancel();
    }

    @Override
    public void onSaved() {
        if (dialog != null)
            dialog.cancel();
        SystemClock.sleep(500);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            int type = getIntent().getIntExtra("type", -1);
            if (type == SEND) {
                viewModel.send();
            } else if (type == REPLY) {
                viewModel.reply();
            } else {
                viewModel.forward();
            }
        } else if (id == R.id.action_attach) {
            openFile();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        viewModel.handleOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        isSave();
    }

    private void setupSnackBar() {
        viewModel.getSnackBarText().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                String msg = stringEvent.getContentIfNotHandled();
                if (!TextUtils.isEmpty(msg))
                    Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewModel() {
        viewModel = new SendEmailViewModel(EmailRepository.provideRepository(), EmailApplication.getAccount());
        viewModel.setNavigator(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        listAdapter.setViewModel(viewModel);
    }

    private void setupAdapter() {
        listAdapter = new AttachmentListAdapter(this, this);
        binding.rvAttachment.setAdapter(listAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSave();
            }
        });

    }

    private void openFile() {
        XRMultiFile.get()
                .with(this)
                .lookHiddenFile(false)
                .limit(3)
                .select(this, 715);
    }

    private void isSave() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("是否存入草稿箱")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.save2Drafts();
                    }
                }).show();
    }

    public static void start2SendEmailActivity(Context context, int type, Email data) {
        context.startActivity(new Intent(context, SendEmailActivity.class)
                .putExtra("type", type)
                .putExtra("data", data));
    }
}
