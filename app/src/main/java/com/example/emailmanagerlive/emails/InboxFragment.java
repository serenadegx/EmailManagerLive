package com.example.emailmanagerlive.emails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emailmanagerlive.databinding.FragmentInboxBinding;

public class InboxFragment extends Fragment {

    public static Fragment newInstance() {
        return new InboxFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentInboxBinding binding = FragmentInboxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
