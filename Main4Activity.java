package pass.kk.passwordstore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main4Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ImageButton b=(ImageButton)findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText e = (EditText) findViewById(R.id.editText6);
                String val = e.getText().toString();
                if (!val.equals("")) {

                    try {
                            Intent i=new Intent(Main4Activity.this,ContentActivity.class);
                            i.putExtra("Paas",val);
                            i.putExtra("Show",true);
                            Main4Activity.this.startActivity(i);
                            Main4Activity.this.finish();
                  
                    }
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"Enter valid input",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
