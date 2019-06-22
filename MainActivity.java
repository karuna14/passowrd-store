package pass.kk.passwordstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sf=getSharedPreferences("detail",0);
                String name=sf.getString("name","");
                if(name.equals("")) {
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"You already signed up",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(i);

            }
        });
    }


}
