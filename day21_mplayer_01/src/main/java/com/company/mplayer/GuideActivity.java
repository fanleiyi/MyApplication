package com.company.mplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.company.mplayer.listener.BasePageChangeListener;

public class GuideActivity extends Activity implements OnClickListener{
    private int images[]={R.mipmap.aha,R.mipmap.ahb,R.mipmap.ahc};
    private ViewPager viewPager;
    private RadioGroup rGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setLogonButtons();
        setViewPager();
        setRadioGroup();
    }

    private void setLogonButtons() {
        Button logonBtn= (Button) findViewById(R.id.logonId);
        logonBtn.setOnClickListener(this);
        Button enterBtn= (Button) findViewById(R.id.enterId);
        enterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.logonId){
            startActivity(new Intent(this,LoginActivity.class));
        }else if(v.getId()==R.id.enterId){
            startActivity(new Intent(this,MainActivity.class));
        }
    }
    /**初始化radiogroup**/
    private void setRadioGroup(){
        rGroup = (RadioGroup) findViewById(R.id.rGroupId);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,
                                         int checkedId) {
                //获得当前选中的radiobutton
                RadioButton btn= (RadioButton)
                        group.findViewById(checkedId);
                //获得此radiobutton在radiogroup中的位置
                int position=group.indexOfChild(btn);
                //设置当前选中的viewpager
                viewPager.setCurrentItem(position);

            }
        });
        RadioButton rBtn= (RadioButton) rGroup.findViewById(R.id.radio0);
        rBtn.setChecked(true);
    }

    /**初始化viewpager*/
    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPagerId);
        PagerAdapter adapter=new ImageAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new BasePageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(rGroup!=null) {
                    RadioButton rBtn = (RadioButton) rGroup.getChildAt(position);
                    rBtn.setChecked(true);
                }
            }
        });
    }
    class ImageAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return images.length;
        }

        /***当此方法的返回值为true时才会以分页形式呈现数据
         * @param view 为添加到容器的view
         * @param object 为instantiateItem方法的返回值(key)
         *  */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;

        }
        /**类似ArrayAdapter中的getView方法，此方法用于
         * 初始化一个item并将item添加到viewpager容器*/
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i("TAG","instantiateItem.position="+position);
            //1.itemview
            ImageView imageView=new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //2.itemdata
            int imageId=images[position];
            //3.binddata
            imageView.setImageResource(imageId);
            //4.add data to container
            container.addView(imageView);//container默认指向viewpager对象

            //ViewPager对象内部默认采用key/value的形式存储数据
            return imageView;//返回的key，值为通过addView方法添加的view

            //此方法的返回值会影响isViewFromObject的编写
        }
        /**当页面在滑动时有些view要从容器中移除，移除时会执行此方法
         * @param container  代表当前容器对象(ViewPager)
         * @param position  当前viewpager中item的位置
         * @param object  代表要移除的view的key
         * */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("TAG","destroyItem.position="+position);
            container.removeView((ImageView)object);
        }
    }
}
