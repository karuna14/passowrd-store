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

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button b=(Button)findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1=(EditText)findViewById(R.id.editText);
                EditText e2=(EditText)findViewById(R.id.editText2);
                EditText e3=(EditText)findViewById(R.id.editText3);
                String name=e1.getText().toString();
                String pass=e2.getText().toString();
                String cpass=e3.getText().toString();
                if((!name.equals(""))&& (!pass.equals(""))){
                    if(pass.equals(cpass)){
                        SharedPreferences s=getApplicationContext().getSharedPreferences("detail",0);
                        SharedPreferences.Editor se=s.edit();
                        se.putString("name",name);
                        se.putString("pass",pass);
                        se.commit();
                        Intent i=new Intent(Main2Activity.this,Main7Activity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Passowrds mismatch",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter valid input",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
