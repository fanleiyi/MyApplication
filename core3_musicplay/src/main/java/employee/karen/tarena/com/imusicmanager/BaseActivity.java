package employee.karen.tarena.com.imusicmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity {

    protected LinearLayout actionbar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void  initialUI();
    public void initialActionbar(int leftId,String title,int rightId){
        if(actionbar==null){
            return;
        }
        ImageView imageView_Left=
                (ImageView) actionbar.
                findViewById(R.id.imageView_Actionbar_Left);
        TextView textView_Title= (TextView) actionbar.
                findViewById(R.id.textView_Actionbar_Title);
        ImageView imageView_Right=
                (ImageView) actionbar.
                        findViewById(R.id.imageView_Actionbar_Right);
        if(leftId==-1){
            imageView_Left.setVisibility(View.INVISIBLE);
        }else {
            imageView_Left.setVisibility(View.VISIBLE);
            imageView_Left.setImageResource(leftId);
        }
        if(TextUtils.isEmpty(title)){
            textView_Title.setVisibility(View.INVISIBLE);
        }else{
            textView_Title.setVisibility(View.VISIBLE);
            textView_Title.setText(title);
        }
        if(rightId==-1){
            imageView_Right.setVisibility(View.INVISIBLE);
        }else{
            imageView_Right.setVisibility(View.VISIBLE);
            imageView_Right.setImageResource(rightId);
        }

    }
}
