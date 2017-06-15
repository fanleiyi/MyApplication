package com.tarena.karen.weather;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.natasa.progressviews.CircleSegmentBar;
import com.tarena.karen.weather.adapter.WeatherAdapter;
import com.tarena.karen.weather.entity.MyWeather;
import com.tarena.karen.weather.manager.HttpWeatherManager;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawer_Layout)
    DrawerLayout drawerLayout_Weather;
   // @BindView(R.id.navigation_Weather)
    NavigationView navigationView_Weather;
    @BindView(R.id.imageView_Menu)
    ImageView imageView_Menu;
    @BindView(R.id.textView_CityName)
    TextView textView_CityName;
    @BindView(R.id.imageView_Statics)
    ImageView imageView_Static;
    @BindView(R.id.recyclerView_Weather)
    RecyclerView recyclerView_Weather;
    @BindView(R.id.refresh_Layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.circle_RealTime_Temp)
    CircleSegmentBar progressBar;
    @BindView(R.id.textView_RealTime_Update)
    TextView textView_RealTime_Update;
    @BindView(R.id.textView_RealTime_WeekDay)
    TextView textView_RealTime_WeekDay;
    @BindView(R.id.textView_RealTime_Condition)
    TextView textView_RealTime_Condition;
    @BindView(R.id.frameLayout_ChartLine)
    FrameLayout frameLayout_ChartLine;

    Handler handler=null;
    Runnable runnable=null;
    WeatherAdapter adapter=null;

    List<MyWeather.Result.Data.WeatherX> weathers=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialProperties();
        initialRealTime();
        setListener();
    }

    /**
     * 初始当天的天气信息
     */
    private void initialRealTime() {
        progressBar.setCircleViewPadding(5);
        progressBar.setWidth(280);
        progressBar.setWidthProgressBackground(30);
        progressBar.setWidthProgressBarLine(25);
        progressBar.setStartPositionInDegrees(90);
        progressBar.setLinearGradientProgress(true);

    }

    private void setListener() {
        navigationView_Weather.
                setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean
            onNavigationItemSelected(
                    @NonNull MenuItem item) {
                Toast.makeText(MainActivity.this,"hahah",Toast.LENGTH_LONG).show();
                String cityName=item.getTitle().toString();
                textView_CityName.setText(cityName);
                refreshWeathers(cityName,true);
                drawerLayout_Weather.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    private void initialProperties() {
        handler=new Handler();
        //实例化数据适配器
        adapter=new WeatherAdapter(this);

        imageView_Menu.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        imageView_Static.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        navigationView_Weather= (NavigationView) findViewById(R.id.navigation_Weather);
        //设置导航栏的图标颜色
        navigationView_Weather.setItemIconTintList(null);
        //定义一个布局管理器
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //把布局管理器设置给recycleView
        recyclerView_Weather.setLayoutManager(layoutManager);
        //设置适配器
        recyclerView_Weather.setAdapter(adapter);
    }

    private void setTemperature(final int temp){
        handler.removeCallbacksAndMessages(null);
        runnable=new Runnable() {
            int progress=0;
            @Override
            public void run() {
                progress++;
                if (progress<100*temp/50){
                    progressBar.setProgress((float) progress);
                    progressBar.setText(temp+"℃");
                }
                handler.postDelayed(runnable,100);
            }
        };
        handler.postDelayed(runnable,1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获得当前城市的名字
    String cityName= textView_CityName.getText().toString();
    refreshWeathers(cityName,true);
}

    /**
     * 刷新天气的信息
     * @param cityName 城市的名字
     * @param isClear 是否清空原有数据
     */
    public void refreshWeathers(String cityName, final boolean isClear){
        //加载天气信息
        HttpWeatherManager.loadWeather(this, cityName,
                new HttpWeatherManager.WeatherLoadListener() {
            @Override
            public void onWeatherLoadEnd(MyWeather myWeather) {
                weathers=myWeather.getResult().getData().getWeather();
                //最近七天天气的数据
                List<MyWeather.Result.Data.WeatherX> weathers= myWeather.getResult().getData().getWeather();
                //把数据添加到适配器集合中
                adapter.addWeathers(weathers,isClear);
                //数据加载完毕把下拉刷新设置为false
                refreshLayout.setRefreshing(false);

                // 天气的最新更新时间
                String date = myWeather.getResult().getData().getRealtime().getDate();
                String time = myWeather.getResult().getData().getRealtime().getTime();
                textView_RealTime_Update.setText(date+" "+time);
                String condition = myWeather.getResult().getData().getRealtime().getWeather().getInfo();
                String quality  = myWeather.getResult().getData().getPm25().getPm25().getQuality();
                textView_RealTime_Condition.setText(condition + "|" + "空气质量:" + quality);
                int weekNo=myWeather.getResult().getData().getRealtime().getWeek();
                String weekDay=switchWeekDay(weekNo);
                textView_RealTime_WeekDay.setText(weekDay);
                // 获得当天最高温度
                int temp=Integer.parseInt(myWeather.getResult().getData().getRealtime().getWeather().getTemperature());
                setTemperature(temp);

            }
        });
    }

    private String switchWeekDay(int weekNo) {
        String weekDay = "";
        switch (weekNo) {
            case 1:
                weekDay = "星期一";
                break;
            case 2:
                weekDay = "星期二";
                break;
            case 3:
                weekDay = "星期三";
                break;
            case 4:
                weekDay = "星期四";
                break;
            case 5:
                weekDay = "星期五";
                break;
            case 6:
                weekDay = "星期六";
                break;
            case 7:
                weekDay = "星期日";
                break;
        }
        return weekDay;
    }


    @OnClick(R.id.imageView_Menu)
    public void clickImageMenu(){
        //如果侧滑菜单是关闭则打开
        //如果侧滑菜单是打开的则关闭
        if(!drawerLayout_Weather.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout_Weather.openDrawer(Gravity.LEFT);
        }else{
            drawerLayout_Weather.closeDrawer(Gravity.LEFT);
        }
    }

    /**
     * 图标按钮的点击事件
     */
    @OnClick(R.id.imageView_Statics)
    public void clickImageStatic(){
        int visibility=frameLayout_ChartLine.getVisibility();
        if (visibility==View.VISIBLE){
            frameLayout_ChartLine.setVisibility(View.INVISIBLE);
        }else {
            frameLayout_ChartLine.setVisibility(View.VISIBLE);
            // 获得图表对象
            GraphicalView view=ChartFactory.getLineChartView(this,getDataSet(),getRenderer());
            // 把图表对象加到布局中
            frameLayout_ChartLine.addView(view);
        }
    }

    //绘制天气变化的拆线图
    /**
     * 1.创建总的数据系列
     * 2.分别为白天温度趋势和夜晚温度趋势创建
     * 子数据系列
     * 3.创建总的渲染器
     * 4分别为两条拆线创建子渲染器
     */
    public XYMultipleSeriesDataset getDataSet(){
        XYMultipleSeriesDataset dataSet=
                new XYMultipleSeriesDataset();
        String[] titles={"白天温度","夜晚温度"};
        //构建x轴的数据源
        List<double[]> x=
                new ArrayList<double[]>();
        for(int i=0;i<titles.length;i++){
            double d[] =new double[]{1,2,3,4,5,6,7};
            x.add(d);
        }
        //构建y轴的数据源
        List<double[]> y=new ArrayList<double[]>();
        double days[]=new double[7];
        double nights[]=new double[7];
        for(int i=0;i<weathers.size();i++){
            days[i]=Double.parseDouble(weathers.get(i).getInfo().getDay().get(2));
            nights[i]=Double.parseDouble(weathers.get(i).getInfo().getNight().get(2));
        }
        //把数组加到集合中
        y.add(days);
        y.add(nights);
        //创建两个数据系列再把数据加到数据系列中
        for(int i=0;i<titles.length;i++){
            XYSeries series=
                    new XYSeries(titles[i]);
            //创建坐标点
            double[] xv=x.get(i);
            double[] yv=y.get(i);
            for(int k=0;k<xv.length;k++){
                series.add(xv[k],yv[k]);
            }
            //把数据系列加到总的数据集中
            dataSet.addSeries(series);
        }
        return dataSet;
    }

    public XYMultipleSeriesRenderer getRenderer(){
        XYMultipleSeriesRenderer renderer=
                new XYMultipleSeriesRenderer();
        int colors[]={Color.GREEN,Color.YELLOW};
        //定义拐点的样式
        PointStyle[] styles=new PointStyle[]{
                PointStyle.CIRCLE,PointStyle.SQUARE
        };
        //X轴标签的个数为7
        renderer.setXLabels(7);
        renderer.setShowGrid(true);

        //设置轴标签的对齐方式
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        //设置整个图表的标题
        renderer.setChartTitle("温度趋势");
        renderer.setXTitle("未来7天");
        renderer.setYTitle("温度");
        renderer.setChartTitleTextSize(48);
        renderer.setAxisTitleTextSize(30);
        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(26);

        renderer.setYLabels(8);
        renderer.setXAxisMax(7.5);
        renderer.setXAxisMin(0.5);
        renderer.setYAxisMin(-15);
        renderer.setYAxisMax(50);

        //设置座标轴的颜色
        renderer.setAxesColor(Color.LTGRAY);
        //设置标签文本的颜色
        renderer.setLabelsColor(Color.LTGRAY);
        //设置拐点的大小
        renderer.setPointSize(8f);
        renderer.setMargins(new int[]{50,50,15,30});
        int length=colors.length;
        for(int i=0;i<length;i++){
            XYSeriesRenderer r=new XYSeriesRenderer();
            r.setLineWidth(5);

            r.setColor(colors[i]);
            r.setFillPoints(true);
            r.setPointStyle(styles[i]);
            //把子渲染器加到总渲染器中
            renderer.addSeriesRenderer(r);
        }
        return  renderer;
    }
}
