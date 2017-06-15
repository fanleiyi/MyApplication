package com.tarena.fanly.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarena.fanly.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Guide_01Fragment extends BaseFragment {
    public Guide_01Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_01, container, false);

        return view;
    }


}
