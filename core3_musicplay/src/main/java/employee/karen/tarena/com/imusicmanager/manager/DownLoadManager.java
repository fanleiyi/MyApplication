package employee.karen.tarena.com.imusicmanager.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import employee.karen.tarena.com.imusicmanager.R;

/**
 * Created by pjy on 2017/6/7.
 */

public class DownLoadManager {

    /**
     * @param context
     * @param ticker  通知弹出时的文本
     * @param title   通知的标题
     * @param content 通知的内容
     */
    public static void
    sendNotifaction(
            Context context,
            String ticker,
            String title,
            String content) {
        //获得通各管理的服务类
        NotificationManager manager =
                (NotificationManager)
                        context.getSystemService(
                                Context.NOTIFICATION_SERVICE);
        Notification.Builder builder =
                new Notification.Builder(context);
        //设置要发出的通知的各个属性
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(content);
        //设置通话的小图标
        builder.setSmallIcon(R.drawable.favo);

        builder.setContentInfo(
                new SimpleDateFormat("HH:mm:ss").
                        format(new Date()));

        //创建通知的对象

        Notification notification = builder.build();
        //发送通知
        manager.notify(100, notification);
    }

    /**
     * 下载歌曲
     * @param context
     * @param musicPath 所下载的歌曲的路径
     * @param name 所下载的歌曲的名字
     */
    public static void downLoadFile(
            final Context context,
            String musicPath,
            String name){
        String downLoadPath= Environment.
                getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).
                getAbsolutePath();
        final File file=new File(downLoadPath,name);
        //实现文件的下载操作
        new AsyncTask<String,Void,File>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //当异步任务启动之前预先执行的一些操作
                //可以在该方法中处理
                sendNotifaction(
                        context,
                        "准备下载",
                        "准备下载",
                        "准备中......");
            }

            @Override
            protected File doInBackground(String... params) {
                String path=params[0];
                Log.i("TAG:path",path);
                try{
                    URL url=new URL(path);
                    HttpURLConnection connection=
                            (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setDoInput(true);

                    connection.connect();

                    //获得响应的状态码
                    int statusCode=connection.getResponseCode();
                    if(statusCode==200){
                        //获得从服务器端返回的输入流
                        InputStream is=connection.getInputStream();

                        //构建带有缓冲区的字节输入流
                        BufferedInputStream is2=
                                new BufferedInputStream(is);
                        byte[] buffer=new byte[1024*8];

                        //构建一个输出流
                        OutputStream os=new FileOutputStream(file);
                        BufferedOutputStream os2=
                                new BufferedOutputStream(os);

                        int downLoadCount=0;//每次下载的数据的长度
                        //所要下载的数据的总的长度
                        int contentLength=connection.getContentLength();

                        int len=0;
                        while((len=is2.read(buffer))!=-1){
                            //把读到的数据的长度进行累加
                            downLoadCount+=len;
                            //每读到一定长度的数据之后就发一个通知
                            sendNotifaction(
                                    context,
                                    "下载中",
                                    "下载百分比",
                                    downLoadCount*100/contentLength+"%");
                            //把读取到的数据写到文件中
                            os2.write(buffer,0,len);
                        }
                        os2.flush();
                        os2.close();
                        is2.close();
                        return file;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(File file) {
                sendNotifaction(
                        context,
                        "下载完毕",
                        file.getName(),
                        "音乐文件下载完成!");
            }

        }.execute(musicPath);

    }

}
