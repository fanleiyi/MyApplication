package employee.karen.tarena.com.volleytest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;

import employee.karen.tarena.com.volleytest2.request.PullParserRequest;

public class PullParserActivity extends AppCompatActivity {

    RequestQueue queue =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_parser);
        queue= Volley.newRequestQueue(this);
    }

    public void testParser(View view){
        String url= "http://flash.weather.com.cn/wmaps/xml/china.xml";
        PullParserRequest request=
                new PullParserRequest(url, new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser parser) {
                        try {
                            int eventType=parser.getEventType();
                            while(eventType!=XmlPullParser.END_DOCUMENT){
                                switch (eventType){
                                    case  XmlPullParser.START_TAG:
                                        String nodeName=parser.getName();
                                        if(nodeName.equals("city")){
                                            String pyName=parser.getAttributeValue(1);
                                            Log.i("TAG:pyName",pyName);
                                        }
                                        break;
                                }
                                //移动文件指针，指向下一个标签
                                eventType=parser.next();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        queue.add(request);

    }
}



