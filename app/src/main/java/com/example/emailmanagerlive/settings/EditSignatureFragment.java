package com.example.emailmanagerlive.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emailmanagerlive.databinding.FragmentEditSignatureBinding;

public class EditSignatureFragment extends Fragment {
    public static EditSignatureFragment newInstance() {
        return new EditSignatureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentEditSignatureBinding binding = FragmentEditSignatureBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //    public static void main(String[] args) {
//        int[] split = {1, 3, 4, 5, 8, 16, 17, 18, 20, 30, 40, 41, 42, 50, 52};
//        StringBuilder sb = new StringBuilder();
//        if (split[1] - split[0] == 1) {
//            sb.append(split[0]);
//        } else {
//            sb.append(split[0] + ",");
//        }
//        for (int i = 1; i < split.length - 1; i++) {
//            if (split[i + 1] - split[i] == 1 && split[i] - split[i - 1] == 1) {
//
//            } else if (split[i] - split[i - 1] == 1 && split[i + 1] - split[i] > 1) {
//                sb.append("-" + split[i] + ",");
//            } else if (split[i] - split[i - 1] > 1 && split[i + 1] - split[i] == 1) {
//                sb.append(split[i]);
//            } else if (split[i] - split[i - 1] > 1 && split[i + 1] - split[i] > 1) {
//                sb.append(split[i] + ",");
//            }
//
//        }
//        if (split[split.length - 1] - split[split.length - 2] == 1) {
//            sb.append("-" + split[split.length - 1]);
//        } else {
//            sb.append(split[split.length - 1]);
//        }
//
//        System.out.println(sb.toString());
//    }
}
