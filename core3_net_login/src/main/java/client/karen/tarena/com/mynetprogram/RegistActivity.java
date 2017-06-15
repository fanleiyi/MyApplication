package client.karen.tarena.com.mynetprogram;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import client.karen.tarena.com.mynetprogram.entity.User;
import client.karen.tarena.com.mynetprogram.manager.HttpManager;

public class RegistActivity extends AppCompatActivity {
    EditText edit_UName;
    EditText edit_Password;
    EditText edit_Email;
    EditText edit_Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initialUI();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            User resultUser= (User) msg.obj;
            if(resultUser!=null){
                Toast.makeText(RegistActivity.this,
                        "注册成功,请记好你的帐户信息:"
                                +resultUser.toString(),
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(RegistActivity.this,
                        "注册失败!",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    private void initialUI() {
        edit_UName= (EditText) findViewById(R.id.editText_UserName);
        edit_Password= (EditText) findViewById(R.id.editText_Password);
        edit_Email= (EditText) findViewById(R.id.editText_Email);
        edit_Phone= (EditText) findViewById(R.id.editText_Phone);
    }

    public void register(View view){
        String username=edit_UName.getText().toString();
        String password=edit_Password.getText().toString();
        String email=edit_Email.getText().toString();
        String phone=edit_Phone.getText().toString();

        final User user=new User(username,password,email,phone);

        //网络访问需要做异步处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                User resultUser=HttpManager.registHttpPost(user);
                Message message=handler.obtainMessage();
                //在该消息中可以封装由子线程发回给主线程的数据
                message.obj=resultUser;
                handler.sendMessage(message);
            }
        }).start();

    }
    public void onClick02(View v) {
        finish();
    }
}
