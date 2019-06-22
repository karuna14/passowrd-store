package pass.kk.passwordstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class Main5Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Button b=(Button)findViewById(R.id.enw);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText et = (EditText) findViewById(R.id.encw);
                    String text = et.getText().toString();
                    SharedPreferences sf = getSharedPreferences("In", 0);


                    String e = sf.getString("in", "0");
                    BigInteger e1 = new BigInteger(e);
                    BigInteger n1 = new BigInteger(text);
                    BigInteger s = e1.subtract(n1);

                    AlertDialog.Builder ad = new AlertDialog.Builder(Main5Activity.this);
                    ad.setTitle("Important Info");
                    ad.setMessage("Note: This is your key " + String.valueOf(s));
                    ad.setCancelable(false);
                    ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(Main5Activity.this, Main4Activity.class);
                            Main5Activity.this.startActivity(i);
                            Main5Activity.this.finish();
                        }

                    });
                    ad.create().show();

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Try your best",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
