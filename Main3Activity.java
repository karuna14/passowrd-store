package pass.kk.passwordstore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button b=(Button)findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1=(EditText)findViewById(R.id.editText4);
                EditText e2=(EditText)findViewById(R.id.editText5);

                String name1=e1.getText().toString();
                String pass1=e2.getText().toString();

                SharedPreferences sf=getSharedPreferences("detail",0);
                String name=sf.getString("name","");
                String pass=sf.getString("pass","");
                if((!name1.equals("")&&(!pass1.equals("")))) {
                    if (name.equals(name1) && pass.equals(pass1)) {
                        Intent i = new Intent(Main3Activity.this, Main4Activity.class);
                        Main3Activity.this.startActivity(i);
                        Main3Activity.this.finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"You have entered wrong login credential",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter a valid input",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button b2=(Button)findViewById(R.id.button6);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main3Activity.this, Main8Activity.class);
                Main3Activity.this.startActivity(i);
                Main3Activity.this.finish();
            }
        });
    }
}
