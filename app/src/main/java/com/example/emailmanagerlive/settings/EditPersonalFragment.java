package com.example.emailmanagerlive.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.databinding.FragmentEditPersonalBinding;
import com.example.emailmanagerlive.databinding.FragmentEditSignatureBinding;
import com.google.android.material.snackbar.Snackbar;

public class EditPersonalFragment extends Fragment {

    private SettingsViewModel mViewModel;

    public static EditPersonalFragment newInstance() {
        return new EditPersonalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentEditPersonalBinding binding = FragmentEditPersonalBinding.inflate(inflater, container, false);
        mViewModel = SettingsActivity.obtainViewModel(getActivity());
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSnackBar();
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

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.bindPersonal();
    }

}
