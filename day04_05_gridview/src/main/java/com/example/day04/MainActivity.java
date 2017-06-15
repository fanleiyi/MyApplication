package com.example.day04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] text = {"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};
    private Integer[] image = { R.mipmap.png_13,R.mipmap.png_14,R.mipmap.png_15,
            R.mipmap.png_16,R.mipmap.png_17,R.mipmap.png_18,
            R.mipmap.png_19,R.mipmap.png_20,R.mipmap.png_21,
            R.mipmap.png_22,R.mipmap.png_23,R.mipmap.png_24,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GridView gridView=(GridView) findViewById(R.id.gridView);

     /*   ArrayAdapter<Integer> adapter=new ArrayAdapter<Integer>(
                this,R.layout.grid_item_01,images);*/

        ImageAdapter imageAdapter=new ImageAdapter();
        gridView.setAdapter(imageAdapter);


    }
    /**自己定义适配器*/
    class ImageAdapter extends BaseAdapter{

        /**返回要构建的item的个数*/
        @Override
        public int getCount() {
            return image.length;
        }

        /**返回当前position位置的item上的数据*/
        @Override
        public Object getItem(int position) {
            return image[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**Adapter的作用是什么？构建item的一个工厂
         * Adapter通过哪个方法构建item呢？getView方法
         * @param position 代表要构建的item的位置
         * @param convertView 代表一个可重用的item 列表项对象
         * @param parent 代表一个容器，要呈现item的那个容器
         *
         * getView方法实现的功能类似包饺子
         * */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

                View v=null;
                ViewHolder viewHolder;
                //1.item view (饺子皮)
                //根据图纸(R.layout.grid_item_01)构建饺子皮对象
                if(convertView==null) {//convertView是一个可重用的列表对象，它的值最初为null
                    //创建itemview
                    v = View.inflate(MainActivity.this, R.layout.item, null);
                    //每个itemview都要对应的viewholder对象
                    viewHolder=new ViewHolder();
                    viewHolder.imageView=(ImageView) v.findViewById(R.id.iamgeView);
                    viewHolder.textView=(TextView) v.findViewById(R.id.textView);
                    //建立itemview与viewholder的关系
                    v.setTag(viewHolder);

                }else{
                v=convertView;//重用convertview
                viewHolder= (ViewHolder) v.getTag();
            }
            //ImageView imageView=new ImageView(MainActivity.this);
            //imageView.setPadding(16,16,16,16);
            //2.item data (部分饺子馅)
            int imgResId=image[position];
            String name=text[position];

            // 3.bind data(将饺子馅放到皮包起来)
            //ImageView imageView=(ImageView) v.findViewById(R.id.imageId);
            //TextView textView= (TextView) v.findViewById(R.id.textId);
            //imageView.setImageResource(imgResId);
            //textView.setText(name);

            viewHolder.imageView.setImageResource(imgResId);
            viewHolder.textView.setText(name);

            return v;
        }
        class ViewHolder{//View对象的持有者
            //一个对象靠属性存储数据
            ImageView imageView;
            TextView textView;
        }
    }
}
