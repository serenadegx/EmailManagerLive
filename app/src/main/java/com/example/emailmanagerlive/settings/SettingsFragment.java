package com.example.emailmanagerlive.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.databinding.FragmentSettingsBinding;
import com.example.emailmanagerlive.settings.adapter.AccountListAdapter;
import com.example.multifile.ui.EMDecoration;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment {
    private SettingsViewModel mViewModel;
    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rv.addItemDecoration(new EMDecoration(getContext(), EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));
        mViewModel = SettingsActivity.obtainViewModel(getActivity());
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
        setupSnackBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.start(getActivity().getSharedPreferences("email", Context.MODE_PRIVATE));
    }

    private void setupListAdapter() {
        AccountListAdapter listAdapter = new AccountListAdapter(getContext(), this);
        binding.rv.setAdapter(listAdapter);
    }

    private void setupSnackBar() {
        mViewModel.snackBarText.observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                String msg = stringEvent.getContentIfNotHandled();
                if (msg != null)
                    Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
}
