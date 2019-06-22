package pass.kk.passwordstore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Main7Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        ImageButton b=(ImageButton)findViewById(R.id.button7);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et=(EditText)findViewById(R.id.editText7);
                String text=et.getText().toString();
                SharedPreferences sf=getApplicationContext().getSharedPreferences("Sec",0);
                SharedPreferences.Editor se=sf.edit();


                se.putString("text",text);
//
                se.commit();
                Intent i=new Intent(Main7Activity.this,Signup2Activity.class);
                i.putExtra("sec",text);
                Main7Activity.this.startActivity(i);
                Main7Activity.this.finish();
            }
        });
    }
}
