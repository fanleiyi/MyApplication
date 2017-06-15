package com.tarena.karen.youlu03.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.adapter.CalllogAdapter;
import com.tarena.karen.youlu03.adapter.ContactsAdatper;
import com.tarena.karen.youlu03.adapter.ConversationAdapter;
import com.tarena.karen.youlu03.entity.Calllog;
import com.tarena.karen.youlu03.entity.Contact;
import com.tarena.karen.youlu03.entity.Conversation;

/**
 * Created by pjy on 2017/5/18.
 */

public class DialogManager {
    public static void showAddContactDialog(
            final Context context){
        AlertDialog.Builder builder=
                new AlertDialog.
                Builder(context);
//        builder.setTitle();
//        builder.setIcon();
//        builder.setNegativeButton();
//        builder.setPositiveButton();
//        builder.setMessage();
          final AlertDialog dialog=
                  builder.create();
          dialog.show();
          //把定义好的对话框的布局解析成一个View对象
          //添加到对话框上面去
          View dialogView= View.inflate(
                  context,
                  R.layout.inflate_contactadd_dialog_layout,
                  null);
          dialog.setContentView(dialogView);
        //完成对话框上面的控件的初始设置
        ImageView imageView_Close= (ImageView) dialogView.
                findViewById(R.id.imageView_ContactAdd_Close);

        ImageView imageView_Confirm=(ImageView) dialogView.
                findViewById(R.id.imageView_ContactAdd_Confirm);

        final EditText editText_Name= (EditText) dialogView.
                findViewById(
                R.id.editText_ContactAdd_Name);
        final EditText editText_Email= (EditText) dialogView.
                findViewById(R.id.editText_ContactAdd_Email);
        final EditText editText_Phone= (EditText) dialogView.
                findViewById(R.id.editText_ContactAdd_Phone);
        final EditText editText_Company= (EditText) dialogView.
                findViewById(R.id.editText_ContactAdd_Company);
        final EditText editText_Address= (EditText) dialogView.
                findViewById(R.id.editText_ContactAdd_Postal);
        imageView_Close.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imageView_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加联系人的功能通过
                // 调用系统联系人的添加的模块儿
                String name=editText_Name.
                        getText().toString();
                String phone=editText_Phone.
                        getText().toString();
                if(TextUtils.isEmpty(name)
                        ||TextUtils.isEmpty(phone)){
                    Toast.makeText(context,
                            "联系人姓名或电话号码不能为空!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String address=editText_Address.
                        getText().toString();
                String email=editText_Email.
                        getText().toString();
                String company=editText_Company.
                        getText().toString();

                //创建用来激活系统联系人添加功能的Activity的Intent
                Intent intent=new Intent();
                intent.setAction(ContactsContract.
                    Intents.SHOW_OR_CREATE_CONTACT);
                Uri uri= Uri.parse("tel:"+phone);
                intent.setData(uri);

                //在Intent中添加姓名的附加信息
                intent.putExtra(ContactsContract.
                        Intents.Insert.NAME,name);
                intent.putExtra(ContactsContract.
                        Intents.Insert.EMAIL,email);
                intent.putExtra(ContactsContract.
                        Intents.Insert.POSTAL,address);
                intent.putExtra(ContactsContract.
                        Intents.Insert.COMPANY,company);
                //激活系统的activity
                context.startActivity(intent);
                //关闭当前的对话框
                dialog.dismiss();
            }
        });

    }

    /**
     * 显示编辑联系人的对话框
     * @param context
     * @param contact
     */
    public static void showEditContactDialog(
            final Context context,
            final Contact contact){
        AlertDialog.Builder builder=
                new AlertDialog.
                Builder(context);
        //创建对话框
        final AlertDialog editDialog=
                builder.create();
        editDialog.show();
        //将自定义的布局文件解析之后
        //转换成一个view对象
        View editView=View.inflate(
                context,
                R.layout.inflate_contactdetail_dialog_layout,
                null);
        //将解析后的View设置给对话框
        editDialog.setContentView(editView);
        //获得对话框布局上的各个控件
        ImageView imageView_Edit= (ImageView) editView.
                findViewById(
                R.id.imageView_ContactDetail_Edit);
        ImageView imageView_Close=
                (ImageView) editView.
                findViewById(R.id.imageView_ContactDetail_Close);
        ImageView imageView_Header=
                (ImageView) editView.
                findViewById(R.id.imageView_ContactDetail_Header);

        TextView textView_Name= (TextView) editView.
                findViewById(R.id.textView_ContactDetail_Name);
        TextView textView_Phone= (TextView) editView.
                findViewById(R.id.textView_ContactDetail_Phone);
        TextView textView_Email= (TextView) editView.
                findViewById(R.id.textView_ContactDetail_Email);
        TextView textView_Address=(TextView) editView.
                findViewById(R.id.textView_ContactDetail_Address);


        //显示联系人的详情
        textView_Name.setText(contact.getName());
        textView_Phone.setText(contact.getPhone());
        if(!TextUtils.isEmpty(contact.getEmail())) {
            textView_Email.setText(contact.getEmail());
        }else{
            textView_Email.setText("没有邮箱信息");
        }
        if(!TextUtils.isEmpty(contact.getAddress())) {
            textView_Address.setText(contact.getAddress());
        }else{
            textView_Address.setText("没有通讯地址");
        }

        //获得联系人的头像
        Bitmap photo=ContactsManager.
                getPhotoByPhotoId(
                context,contact.getPhotoId());
        //将头像进行格式化处理
        photo=ImageManager.
                formatBitmap(context,photo);
        //将头像设置到控件上
        imageView_Header.setImageBitmap(photo);

        imageView_Close.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.dismiss();
            }
        });
        //实现联系人的编辑的处理
        imageView_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=
                  new Intent(Intent.ACTION_EDIT);
                //设置意图的data
                Uri uri=Uri.parse(
                        "content://contacts/people/"+
                        contact.get_id());
                intent.setData(uri);
                intent.putExtra("finishActivityOnSaveCompleted",true);
                context.startActivity(intent);
                editDialog.dismiss();
            }
        });
    }

    public static void showDeleteContactDialog(
            final Context context,
            final Contact contact,
            final ContactsAdatper adatper){
        AlertDialog.Builder builder=
                new AlertDialog.
                Builder(context);
        builder.setIcon(
                R.drawable.ic_warning);
        builder.setTitle("系统提示");
        builder.setMessage("确定要删除当前联系人吗?");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("删除",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(
                    DialogInterface dialogInterface, int i) {
                //删除联系人
                ContactsManager.
                        deleteContact(
                        context, contact);
                adatper.removeDatas(contact);
                //关闭对话框
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }

    /**
     *
     * @param context
     * @param calllog 被删除的对话框对象
     * @param adapter 适配器对象
     */
    public static  void showDeleteCallLogDialog(
            final Context context,
            final Calllog calllog,
            final CalllogAdapter adapter){
        AlertDialog.Builder builder=
                new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("系统提示");
        builder.setMessage("确定要删除当前的通话记录吗?");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContactsManager.deleteCalllog(context,calllog);
                //将适配器中的通话记录对象移除
                adapter.removeDatas(calllog);
                //关闭对话框
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 显示删除会话的对话框
     * @param context
     * @param conversation
     * @param adapter
     */
    public static  void showDeleteConversationDialog(
            final Context context,
            final Conversation conversation,
            final ConversationAdapter adapter){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("系统提示");
        builder.setMessage("确定要删除当前会话吗?");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SMSManager.deleteConversation(context,
                        conversation.getThread_id());
                adapter.removeDatas(conversation);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
