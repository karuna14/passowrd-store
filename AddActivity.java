package pass.kk.passwordstore;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends Activity {
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db=new DB(this);
        Button b = (Button) findViewById(R.id.Add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nam=(EditText)findViewById(R.id.name);
                EditText pas=(EditText)findViewById(R.id.pass);
                String name=nam.getText().toString();
                String pass=pas.getText().toString();
                Intent i=new Intent();
                boolean bool=true;
                if((!name.equals(""))&&(!pass.equals(""))) {
                    ArrayList<String> al=db.getAllName();
                    for(int j=0;j<al.size();j++){
                        if(name.equals(al.get(j))){
                            bool=false;
                        }
                    }
                    if(bool) {
                        i.putExtra("Name", name);
                        i.putExtra("Pass", pass);
                        setResult(1, i);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Already exists "+name+".Try to update",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
