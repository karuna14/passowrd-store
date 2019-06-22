package pass.kk.passwordstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class Main9Activity extends Activity {
    pass.kk.passwordstore.DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        Intent i=getIntent();
        String paas=i.getStringExtra("Paas");
        db = new pass.kk.passwordstore.DB(this);
        ArrayList<String> al = db.getAllName();
        ArrayList<String> pall = db.getAllPass();
        for (int ii = 0; ii < al.size(); ii++) {
            String name = al.get(ii);
            String pass = pall.get(ii);
            final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.con2);

            LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);



      final      TextView tv = new TextView(this);

            tv.setTextSize(20);
            tv.setPadding(10, 5, 10, 10);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLACK);

            tv.setBackgroundResource(R.drawable.textview);
//                linearLayout1.addView(tv);

//                TextView tv2=new TextView(this);
            BigInteger bigInteger = new BigInteger(pass);
            try {
                tv.setText("FOR :" + name + "\nPASSWORD: " + decrypt(bigInteger, paas));
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "This not corrcet Password", Toast.LENGTH_SHORT).show();
            }


           // tv.setId(View.generateViewId());
            tv.isClickable();
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String va=tv.getText().toString();
                   final     String name=va.substring(5,va.indexOf("\n"));

                  final      String pass=va.substring(va.indexOf("\n")+11);
                       try {
                    db = new DB(getApplicationContext());

                                   boolean b=db.delet(name, encrypt(pass).toString());



                        }
                        catch (Exception e){
                           Toast.makeText(getApplicationContext(),"Y need to enter with corrcet key..pls relogin",Toast.LENGTH_SHORT).show();
                        }
                    Intent in=getIntent();
                    String paas=in.getStringExtra("Paas");
                    Intent i=new Intent(Main9Activity.this,ContentActivity.class);
                    i.putExtra("Show",true);
                    i.putExtra("Paas",paas);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Main9Activity.this.startActivity(i);
                    Main9Activity.this.finish();
                    return true;
                }
            });

            linearLayout1.addView(tv);


            linearLayout.addView(linearLayout1);

        }
    }
    public BigInteger encrypt(String m){
        SharedPreferences sf=getSharedPreferences("encrypt",0);


        String e=sf.getString("e","1");
        String n=sf.getString("n","1");

        BigInteger e1=new BigInteger(e);
        BigInteger n1=new BigInteger(n);

        String chi="";
        // byte[] bytes=m.getBytes();
        int i=0;
        while(i<m.length()){
            int ac=(int)m.charAt(i);
            chi=chi+ac;
            i++;

        }

        return (new BigInteger(chi)).modPow(e1,n1);
        //return en;
    }
    public String decrypt(BigInteger m,String paas){
        SharedPreferences sf=getSharedPreferences("encrypt",0);

        String d2=sf.getString("d","1");
        String n=sf.getString("n","1");
        String d=paas.concat(d2);
        BigInteger d1=new BigInteger(d);
        BigInteger n1=new BigInteger(n);

        String om="";
        BigInteger cm=m.modPow(d1,n1);
        String c=cm.toString();
        int i=0;
        while(
                i<c.length()-1) {

            if (c.substring(i, i + 2).startsWith("1")) {
                int t = Integer.parseInt(c.substring(i, i + 3));
                char ch = (char) t;
                om = om + ch;
                i = i + 3;

            } else {
                int t = Integer.parseInt(c.substring(i, i + 2));
                char ch = (char)t ;
                om = om + ch;
                i = i + 2;
            }



        }


        String ans=om;
        return om;
    }
}