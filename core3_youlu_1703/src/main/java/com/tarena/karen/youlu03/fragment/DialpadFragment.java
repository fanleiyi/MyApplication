package com.tarena.karen.youlu03.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.adapter.CalllogAdapter;
import com.tarena.karen.youlu03.entity.Calllog;
import com.tarena.karen.youlu03.manager.ContactsManager;
import com.tarena.karen.youlu03.manager.MediaManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public  class DialpadFragment extends BaseFragment {
    ListView listView_DialPad;
    CalllogAdapter adapter=null;
    RelativeLayout relativeLayout_DialPad=null;

    TextView textView_Title=null;
    ImageView imageView_BackSpace=null;
    ImageButton imageButton_Call;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView=inflater.inflate(R.layout.fragment_dailpad_layout,container,false);
        initialUI();
       // initialDailPad();
        checkPermission();
        return contentView;
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},102);
        }else{
            initialDailPad();
        }
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 102:
                if(grantResults.length>0){
                    if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivity(),"必须授予打电话的权限",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    initialDailPad();
                }else{
                    Toast.makeText(getActivity(),"没有相关权限的处理",
                            Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
    }

    private static String[] keys=new String[]{"1","2","3","4","5","6","7","8","9","0","*","#"};
    private void initialDailPad() {
        //获键盘布局
        relativeLayout_DialPad= (RelativeLayout)
                contentView.findViewById(R.id.relative_DialPad);

        DisplayMetrics metrics
                =new DisplayMetrics();
        getActivity().getWindowManager().
                getDefaultDisplay().getMetrics(metrics);
        //计算按键的宽度
        int width=metrics.widthPixels/3;
        //计算按键的高度
        int height=(int)TypedValue.
                applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,55,
                getResources().getDisplayMetrics());
        for(int i=0;i<keys.length;i++){
            final TextView key=new TextView(getActivity());
            key.setText(keys[i]);
            //设置按键控件的id(是一个非零正数)
            key.setId((i+1));
            key.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            key.setGravity(Gravity.CENTER);

            //创建一个布局参数
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.
                    LayoutParams(width,height);
            if(i%3!=0){
                //需要设置右对齐规则
                params.addRule(
                        RelativeLayout.RIGHT_OF,i);
            }
            if(i>=3){
                params.addRule(RelativeLayout.BELOW,(i-2));
            }
            //把控件添加到布局上面
            relativeLayout_DialPad.addView(key,params);

            key.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaManager.playMusic(getActivity(),R.raw.b);
                   //获得标题文本的内容
                    String title=textView_Title.getText().toString();
                    if("拨打电话".equals(title)){
                        //获得按键文本设置在标题上
                        textView_Title.setText(key.getText().toString());
                    }else if(title.length()==3||title.length()==8){
                        //在标题文本上先追加分割线再追加数字键
                        textView_Title.append("-");
                        textView_Title.append(key.getText().toString());
                    }else{
                        //直接在标题上追加数字键
                        textView_Title.append(key.getText().toString());
                    }
                }
            });
        }

    }

    @Override
    public void initialUI() {
        actionbar= (LinearLayout) contentView.findViewById(R.id.actionbar_Dialpad);
        initialActionbar(R.drawable.ic_add_icon,
                "拨打电话",R.drawable.ic_backspace);
        textView_Title= (TextView) actionbar.
                findViewById(R.id.textView_ActionBar_Title);
        imageView_BackSpace= (ImageView) actionbar.
                findViewById(R.id.imageView_ActionBar_Right);
        listView_DialPad= (ListView) contentView.findViewById(R.id.listView_Dialpad);
        imageButton_Call= (ImageButton) contentView.
                findViewById(R.id.imageButton_Call);


        adapter=new CalllogAdapter(getActivity());
        listView_DialPad.setAdapter(adapter);



        List<Calllog> calllogs=ContactsManager.getAllCalllogs(getActivity());
        adapter.addDatas(calllogs,true);

        textView_Title.addTextChangedListener(
                new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>13){
                    editable.delete(13,editable.length());
                }
            }
        });

        imageView_BackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //每按一次可以删除一个字符
                //可以使用截取子字符串的方式
                String title=textView_Title.getText().toString();
                if("拨打电话".equals(title)){
                    return;
                }
                if(title.length()==1){
                    textView_Title.setText("拨打电话");
                }else if(title.length()==5||title.length()==10){
                    textView_Title.setText(
                            title.substring(
                            0,title.length()-2));
                }else {
                    textView_Title.setText(
                    title.substring(
                            0,title.length()-1));
                }
            }
        });

        //实现拨打电话的监听的注册
        imageButton_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaManager.playMusic(getActivity(),R.raw.a);
                String number=textView_Title.getText().toString();

                Intent intent=new Intent(Intent.ACTION_CALL);
                Uri uri= Uri.parse("tel:"+number);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //回收音乐资源
        MediaManager.release();
    }
}
