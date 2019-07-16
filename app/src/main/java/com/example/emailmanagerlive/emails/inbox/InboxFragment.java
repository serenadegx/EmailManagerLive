package com.example.emailmanagerlive.emails.inbox;

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
import com.example.emailmanagerlive.databinding.FragmentInboxBinding;
import com.example.emailmanagerlive.emails.adapter.InboxListAdapter;
import com.example.multifile.ui.EMDecoration;

public class InboxFragment extends Fragment {

    private FragmentInboxBinding binding;
    private InboxViewModel viewModel;

    public static Fragment newInstance() {
        return new InboxFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.addItemDecoration(new EMDecoration(getActivity(), EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));
        viewModel = new InboxViewModel(EmailRepository.provideRepository());
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    private void setupListAdapter() {
        InboxListAdapter listAdapter = new InboxListAdapter(getContext(), getActivity());
        binding.rv.setAdapter(listAdapter);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        viewModel.loadEmails();
//    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.loadEmails(EmailApplication.getAccount());
    }
}
