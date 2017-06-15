package employee.karen.tarena.com.imusicmanager.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import employee.karen.tarena.com.imusicmanager.util.StreamUtil;

/**
 * Created by pjy on 2017/6/5.
 * 自定义的一个音乐图片加载器
 * 实现音乐图片的本地缓存的处理
 */

public class MyImageLoader {
    /**
     * 如果我们需要加载一个音乐的专辑图片，
     * 可以先从内存缓存中去进行该专辑
     * 图片的查找，如果内存缓存中没有的话，
     * 再从文件缓存中查找，如果文件缓存中也
     * 没有的话，说明我们还从来没有网络上加载
     * 过要使用的图片，这时我们再从网络上加载
     * 加载完成之再分别把这个图片缓存到内存和
     * 文件中，以便于下次再使用的时候，可以直接
     * 从缓存中拿到。
     */
    //强引用里的数据是永远不可能被垃圾回收器回收的
    //软引用里存的数据在android3.0之后就变的不可靠。
    /*LruCache 集合最近最少使用
      存在于该集合中的数据都是强引用
      我们可以在定义这样的集合时设置一个最大的
      存储空间,当空间满了要加新的数据时会根据最近
      最少使用的算法
    */
    public static LruCache<String, Bitmap> lruCacheMemory = null;

    static {
        //设置内存的最大的缓存区域
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 8;
        lruCacheMemory=new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getHeight() * value.getRowBytes();
            }
        };
    }

    /**
     * @param context
     * @param imageView 专辑图片控件
     * @param imageUrl  网络图片的路径
     */
    public static void
    setBitmapFromCache(
            Context context,
            ImageView imageView,
            String imageUrl) {
        //从缓存中获得的图片
        Bitmap bitmap = null;
        //判断如果没有提供图片的路径就直接返回
        if (TextUtils.isEmpty(imageUrl)) {
            return;
        }
        //先从内存中查找有没有要使用的图片
        //如果有直接使用
        bitmap = getBitmapFromMemory(imageUrl);
        if (bitmap != null) {
            //把图片应用到控件上
            imageView.setImageBitmap(bitmap);
            return;
        }
        //再从文件缓存中查找有没有要使用的图片
        //如果有就直接使用
        bitmap = getBitmapFromFile(context, imageUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //说明使用的图片还没有从网加载过，
        //从网络上加载该图片
        loadBitmapFromHttp(context, imageView, imageUrl);
    }

    /**
     * @param context
     * @param imageView 设置图片的控件
     * @param imageUrl  所要加载的图片的路径
     */
    private static void loadBitmapFromHttp(
            Context context,
            ImageView imageView,
            String imageUrl) {
        //要访问网络,实现异步加载
        ImageAsyncTask task =
                new ImageAsyncTask(context,imageView);
        //启动异步任务
        task.execute(imageUrl);

    }

    //实现异步图片加载的任务类
    private static class
    ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        Context context;
        ImageView imageView;

        ImageAsyncTask(Context context,
                       ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(
                String... strings) {
            String path=strings[0];
            Bitmap bitmap=null;
            //构建URL对象
            try {
                URL url=new URL(path);
                HttpURLConnection connection=
                        (HttpURLConnection)
                        url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setDoInput(true);

                connection.connect();

                int statusCode=connection.getResponseCode();
                if(statusCode==200){
                    //获得响应的结果
                    InputStream is=connection.getInputStream();
                    bitmap = compressBitmap(is);

                    // bitmap=BitmapFactory.decodeStream(is);
                    //判断该图片是否加载成功
                    if(bitmap!=null){
                        //将图片缓存到内存中
                    lruCacheMemory.put(path,bitmap);
                    //将图片缓存到文件中
                    saveBitmapToFile(context,path,bitmap);
                    return bitmap;
                }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //把图片应用到控件上
            imageView.setImageBitmap(bitmap);
        }
    }

    private static Bitmap compressBitmap(InputStream is) {
        Bitmap bitmap;//把输入流转成一个字节数据
        byte[] datas= StreamUtil.createBytes(is);

        BitmapFactory.Options options=
                new BitmapFactory.Options();

        options.inJustDecodeBounds=true;
        BitmapFactory.decodeByteArray(datas,
                0,datas.length,options);
        //获得图片的边界的宽度和高度
        int outWidth=options.outWidth;
        int outHeight=options.outHeight;

        //设置压缩后的宽度和高度
        int targetWidth=65;
        int targetHeight=65;

        //计算宽度方向上的压缩比例
        int blw=outWidth/targetWidth;
        //计算高度方向上的压缩比例
        int blh=outHeight/targetHeight;

        int bl=blw>blh?blw:blh;
        if(bl<=0){
            bl=1;
        }
        options.inSampleSize=bl;
        options.inJustDecodeBounds=false;
        bitmap=BitmapFactory.decodeByteArray(datas,0,datas.length,options);
        return bitmap;
    }

    private static void saveBitmapToFile(
            Context context,
            String path,
            Bitmap bitmap) {
        try{
            File cacheDir=context.getCacheDir();
            if(!cacheDir.exists()){
                cacheDir.mkdir();
            }

            //获得要缓存的文件的名字
            String fileName=path.substring(path.lastIndexOf("/")+1);
            //创建一个文件对象
            File file=
                    new File(cacheDir,fileName);
            OutputStream os=
                    new FileOutputStream(file);
            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100, os);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static Bitmap
    getBitmapFromFile(
            Context context,
            String imageUrl) {
        Bitmap bitmap = null;
        //获得要查找的文件的名字
        String fileName = imageUrl.substring(
                imageUrl.lastIndexOf("/") + 1);
        //获得应用的缓存目录
        File cacheDir =
                context.getCacheDir();
        //遍历该缓存目录下的所有的文件判断是否有要使用
        //要使用的图片
        if (cacheDir != null) {
            //获得缓存目录下所有的文件对象构成的集合
            File[] files = cacheDir.listFiles();
            for (File file : files) {
                String name = file.getName();
                if (name.equals(fileName)) {
                    //查找到了要使用的图了
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    return bitmap;
                }
            }
        }

        return bitmap;
    }

    /**
     * 从内存缓存中查找要使用的图片
     *
     * @param imageUrl
     * @return
     */
    private static Bitmap
    getBitmapFromMemory(String imageUrl) {
        Bitmap bitmap = null;
        bitmap = lruCacheMemory.get(imageUrl);
        return bitmap;
    }
}
