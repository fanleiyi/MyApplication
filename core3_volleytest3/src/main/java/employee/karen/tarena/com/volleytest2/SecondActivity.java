package employee.karen.tarena.com.volleytest2;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class SecondActivity extends AppCompatActivity {

    RequestQueue queue;
    ImageView imageView_Album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageView_Album = (ImageView) findViewById(R.id.imageView_Album);
        queue = Volley.newRequestQueue(this);
    }

    public void loadAlbum(View view) {
        ImageLoader imageLoader = new ImageLoader(queue,
                new ImageLoader.ImageCache() {
                    int maxSize=1024*1024*4;
                    LruCache<String,Bitmap> cache=
                            new LruCache<String,Bitmap>(maxSize){
                                @Override
                                protected int sizeOf(String key,
                                                     Bitmap value) {
                                    return value.getRowBytes()*
                                            value.getHeight();
                                }
                            };
                    @Override
                    public Bitmap getBitmap(String s) {
                        return cache.get(s);
                    }

                    @Override
                    public void putBitmap(String s, Bitmap bitmap) {
                        //把要缓存的图片加到集合中
                        cache.put(s,bitmap);
                    }
                });
        ImageLoader.ImageListener listener =
                ImageLoader.getImageListener(
                        imageView_Album,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher);
        String url = "http://192.168.199.186:8080/MusicServer/images/meiyikedoushizhanxinde.jpg";
        imageLoader.get(url, listener, 100, 100);
    }
}
