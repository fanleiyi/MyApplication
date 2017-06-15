package com.example.day06_03_zhihu.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day06_03_zhihu.activity.LoginActivity;
import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.manager.ImageManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfFragment extends BaseFragment implements View.OnClickListener {


    private TextView textView_username;

    public SelfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView =  inflater.inflate(R.layout.fragment_self, container, false);
        initialUI();

        return contentView;
    }


    @Override
    public void initialUI() {
        ImageView imageView_photo = (ImageView) contentView.findViewById(R.id.image_photo);
        Bitmap photo= ImageManager.formatBitmapByResources(getActivity(),R.mipmap.png_04);
        imageView_photo.setImageBitmap(photo);
        textView_username = (TextView) contentView.findViewById(R.id.text_username);
        textView_username.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_username:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }

    }
}
