package com.tarena.karen.youlu03;

import android.content.Intent;
import android.provider.Telephony;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.tarena.karen.youlu03.adapter.MyFragmetPagerAdapter;
import com.tarena.karen.youlu03.fragment.CalllogFragment;
import com.tarena.karen.youlu03.fragment.ContactsFragment;
import com.tarena.karen.youlu03.fragment.DialpadFragment;
import com.tarena.karen.youlu03.fragment.SMSFragment;

public class MainActivity extends FragmentActivity{

    //初始化ViewPager
    //创建适配器对象
    //创建Fragment对象
    //将Fragment添加到适配器集合中
    //将ViewPager和适配器进行关联
    ViewPager viewPager;
    MyFragmetPagerAdapter adapter = null;
    CalllogFragment calllog_fragment;
    ContactsFragment contact_fragment;
    DialpadFragment dialpad_fragment;
    SMSFragment sms_fragment = null;

    RadioGroup radioGroup;
    //保存默认短信应用的包名的
    String defaultSmsApp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultSmsApp=Telephony.Sms.
                getDefaultSmsPackage(this);

        initialFragments();
        setListener();
        //设置默认的短信的应用为当前的应用
        setDefaultSMS(getPackageName());
    }

    private void setDefaultSMS(String packagename) {

        Intent intent=new Intent(Telephony.Sms.
                Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.
                EXTRA_PACKAGE_NAME,
                packagename);
        startActivity(intent);
    }

    private void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        //第一个页面被选中
                        //把第一个单选按钮的选中状态设置真
                        radioGroup.check(R.id.radio_CallLog);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio_Contacts);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio_Dialpad);
                        break;
                    case 3:
                        radioGroup.check(R.id.radio_SMS);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //为radio_group添加监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(
                    RadioGroup radioGroup,
                    int i) {
                switch (i) {
                    case R.id.radio_CallLog:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.radio_Contacts:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.radio_Dialpad:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.radio_SMS:
                        viewPager.setCurrentItem(3,false);
                        break;
                }
            }
        });
    }

    private void initialFragments() {
        radioGroup = (RadioGroup)
                findViewById(R.id.radioGroup_Bottom);
        viewPager = (ViewPager)
                findViewById(R.id.viewPager_Main);
        adapter = new MyFragmetPagerAdapter(
                getSupportFragmentManager());
        calllog_fragment = new CalllogFragment();
        contact_fragment = new ContactsFragment();
        dialpad_fragment = new DialpadFragment();
        sms_fragment = new SMSFragment();

        //将创建好fragment添加到适配器集合中

        viewPager.setAdapter(adapter);

        adapter.addFragment(calllog_fragment);
        adapter.addFragment(contact_fragment);
        adapter.addFragment(dialpad_fragment);
        adapter.addFragment(sms_fragment);
        //设置viewpager当前选中项
        viewPager.setCurrentItem(1, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //再把系统短信应用还原成默认的
        setDefaultSMS(defaultSmsApp);
    }
}
