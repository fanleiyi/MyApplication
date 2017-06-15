package employee.karen.tarena.com.volleytest2;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_Music;
    RequestQueue queue=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue= Volley.newRequestQueue(this);
        imageView_Music= (ImageView) findViewById(R.id.imageView_Music);

    }

    public void loadImage(View view){
        //创建图片请求对象
        String url="http://192.168.199.186:8080/MusicServer/images/meiyikedoushizhanxinde.jpg";
        ImageRequest request=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView_Music.setImageBitmap(bitmap);
            }
        }, 80, 80, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG:","error");
            }
        });
        queue.add(request);
    }
}
