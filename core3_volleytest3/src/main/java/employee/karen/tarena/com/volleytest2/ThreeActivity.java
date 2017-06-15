package employee.karen.tarena.com.volleytest2;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class ThreeActivity extends AppCompatActivity {

    NetworkImageView imageView_NetWork;
    RequestQueue queue=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        imageView_NetWork= (NetworkImageView)
                findViewById(R.id.imageView_NetWork);
        queue= Volley.newRequestQueue(this);
        imageView_NetWork.setDefaultImageResId(R.mipmap.ic_launcher);
        imageView_NetWork.setErrorImageResId(R.mipmap.ic_launcher);
    }

    public void loadNetWork(final View view){
        ImageLoader loader=new ImageLoader(queue, new ImageLoader.ImageCache() {
            int maxSize=1024*1024*4;
            LruCache<String,Bitmap> cache=new LruCache<String,Bitmap>(maxSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()*value.getHeight();
                }
            };

            @Override
            public Bitmap getBitmap(String s) {
                return cache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                cache.put(s,bitmap);
            }
        });
        String url="http://192.168.199.186:8080/MusicServer/images/meiyikedoushizhanxinde.jpg";
        imageView_NetWork.setImageUrl(url,loader);
    }
}
