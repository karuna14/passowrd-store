package pass.kk.passwordstore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;

public class Main10Activity extends Activity {
DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        Intent i = getIntent();
        final String paas = i.getStringExtra("Paas");
        db = new DB(this);
        ArrayList<String> al = db.getAllName();
        ArrayList<String> pall = db.getAllPass();
        for (int ii = 0; ii < al.size(); ii++) {
            String name = al.get(ii);
            String pass = pall.get(ii);
            final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.con3);


            final LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);


            final TextView tv = new TextView(this);

            tv.setTextSize(20);
            tv.setPadding(10, 5, 10, 10);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLACK);

            tv.setBackgroundResource(R.drawable.textview);

            final BigInteger bigInteger = new BigInteger(pass);



            try {
                tv.setText("FOR :" + name + "\nPASSWORD: " +decrypt(bigInteger, paas));
               // et.setText(decrypt(bigInteger, paas));
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "This not corrcet Password", Toast.LENGTH_SHORT).show();
            }



            // tv.setId(View.generateViewId());
            tv.isClickable();
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText et1 = new EditText(getApplicationContext());
//                tv.setText("For:"+name+"/n");
                    et1.setTextSize(20);
                    et1.setSingleLine(true);
                    et1.setPadding(10, 5, 10, 10);
                    et1.setTextColor(Color.BLUE);
                    et1.setBackgroundColor(Color.WHITE);
//                 tv.setBackground(Color.BLACK);
                    et1.setBackgroundResource(R.drawable.textview2);

                    String va = tv.getText().toString();

               final     String name = va.substring(5, va.indexOf("\n"));
                final    String pass = va.substring(va.indexOf("\n") + 11);

                    et1.setText(name);
                    final EditText et2 = new EditText(getApplicationContext());
                    et2.setSingleLine(true);
                    et2.setTextSize(20);
                    et2.setPadding(10, 5, 10, 10);
                    et2.setTextColor(Color.BLUE);
                    et2.setBackgroundColor(Color.WHITE);

                    et2.setBackgroundResource(R.drawable.textview2);
                    et2.setText(pass);


                    Button b=new Button(getApplicationContext());
                    b.setText("Update");
                    b.setBackgroundColor(Color.BLUE);
                    linearLayout.addView(et1);
                    linearLayout.addView(et2);
                    linearLayout.addView(b);
                    b.setBackgroundResource(R.drawable.butt);

                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db = new DB(getApplicationContext());
                         try{
                            db.delet(name,encrypt(pass).toString());
                            boolean b = db.insert(et1.getText().toString(), encrypt(et2.getText().toString()).toString());
                            Intent i=new Intent(Main10Activity.this,ContentActivity.class);

                           i.putExtra("Show",true);
                            i.putExtra("Paas",paas);
                           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             Main10Activity.this.startActivity(i);
                            Main10Activity.this.finish();
                         }
                         catch (Exception e){
                             Toast.makeText(getApplicationContext(),"Y need to enter with corrcet key..pls relogin",Toast.LENGTH_SHORT).show();
                         }
                        }
                    });




                }
            });

            // linearLayout1.addView(cb);
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


        return om;
    }
}
