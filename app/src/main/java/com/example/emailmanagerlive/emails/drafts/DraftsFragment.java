package com.example.emailmanagerlive.emails.drafts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.data.source.EmailRepository;
import com.example.emailmanagerlive.databinding.FragmentDraftsBinding;
import com.example.emailmanagerlive.emails.adapter.DraftsListAdapter;
import com.example.multifile.ui.EMDecoration;

public class DraftsFragment extends Fragment {

    private FragmentDraftsBinding binding;
    private DraftsViewModel viewModel;

    public static Fragment newInstance() {
        return new DraftsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDraftsBinding.inflate(inflater, container, false);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.addItemDecoration(new EMDecoration(getActivity(), EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));
        viewModel = new DraftsViewModel(EmailRepository.provideRepository());
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.loadDrafts(EmailApplication.getAccount());
    }

    private void setupListAdapter() {
        DraftsListAdapter listAdapter = new DraftsListAdapter(getContext(), getActivity());
        binding.rv.setAdapter(listAdapter);
    }
}
