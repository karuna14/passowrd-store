package pass.kk.passwordstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Signup2Activity extends
        Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);


        SecureRandom r=new SecureRandom();

        BigInteger bigInteger=BigInteger.probablePrime(64,r);

        BigInteger bigInteger2=BigInteger.probablePrime(64,r);

        BigInteger bigInteger3=BigInteger.probablePrime(64,r);

        BigInteger bigInteger4=BigInteger.probablePrime(64,r);

        BigInteger bigInteger5=BigInteger.probablePrime(64,r);

        BigInteger bigInteger6=BigInteger.probablePrime(64,r);




       final CheckBox cb11=(CheckBox)findViewById(R.id.ch11);
        cb11.setText(bigInteger.toString());

       final CheckBox cb12=(CheckBox)findViewById(R.id.ch12);
        cb12.setText(bigInteger2.toString());

       final CheckBox cb13=(CheckBox)findViewById(R.id.ch13);
        cb13.setText(bigInteger3.toString());
      //  int rn4=r.nextInt();
        //LinearLayout l=(LinearLayout)findViewById(R.id.a);

       final CheckBox cb21=(CheckBox)findViewById(R.id.ch21);
        cb21.setText(bigInteger4.toString());

      final   CheckBox cb22=(CheckBox)findViewById(R.id.ch22);
        cb22.setText(bigInteger5.toString());

       final CheckBox cb23=(CheckBox)findViewById(R.id.ch23);
        cb23.setText(bigInteger6.toString());


        ImageButton b=(ImageButton)findViewById(R.id.select);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val="";
                if(cb11.isChecked()){

                    val+=cb11.getText().toString();
                    val+=",";
                }
                 if(cb12.isChecked()){

                    val=val+cb12.getText().toString();
                    val+=",";
                }
                 if (cb13.isChecked()){

                    val+=cb13.getText().toString();
                    val+=",";
                }
               if(cb21.isChecked()){

                    val+=cb21.getText().toString();
                    val+=",";
                }
               if(cb22.isChecked()){

                    val+=cb22.getText().toString();
                    val+=",";
                }
               if(cb23.isChecked()){

                    val+=cb23.getText().toString();
                    val+=",";
                }

              //  Toast.makeText(getApplicationContext(),val,Toast.LENGTH_SHORT).show();
                //val="13,5";
                String[] ar=val.split(",");

                if(ar.length<2&&ar.length>2){
                    Toast.makeText(getApplicationContext(),"Please check 2 numbers",Toast.LENGTH_SHORT).show();
                }
                else{
                    BigInteger p=new BigInteger(ar[0]);
                    BigInteger q=new BigInteger(ar[1]);

                    BigInteger n=p.multiply(q);


                    BigInteger phi=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
                    SecureRandom r=new SecureRandom();
                    BigInteger e=BigInteger.probablePrime(64,r);
                    while(phi.gcd(e)==BigInteger.ONE){
                        e.add(BigInteger.ONE);
                    }
           BigInteger d=e.modInverse(phi);

                    SharedPreferences sf=getApplicationContext().getSharedPreferences("encrypt",0);
                    SharedPreferences.Editor se=sf.edit();
                    String dd=String.valueOf(d);
               final     String d1=dd.substring(0,5);
                    String d2=dd.substring(5);
                    se.putString("d",d2);
                    se.putString("e",String.valueOf(e));
                    se.putString("n",String.valueOf(n));

                    se.commit();
                    SharedPreferences sfe=getApplicationContext().getSharedPreferences("In",0);
                    SharedPreferences.Editor s=sfe.edit();
                    String ddf=getIntent().getStringExtra("sec");
                  //  Toast.makeText(getApplicationContext(),ddf,Toast.LENGTH_SHORT).show();
                    BigInteger bi=new BigInteger(ddf);
                    BigInteger c=new BigInteger(d1);
                    BigInteger be=c.add(bi);
                    s.putString("in",String.valueOf(be));
                    s.commit();
                   // Toast.makeText(getApplicationContext(),""+be,Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder ad=new AlertDialog.Builder(Signup2Activity.this);
                    ad.setTitle("Important Info");
                    ad.setMessage("Note: This is your key "+String.valueOf(d1));
                    ad.setCancelable(false);
                    ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                                Intent i=new Intent(Signup2Activity.this,ContentActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.putExtra("Paas",d1);
                                i.putExtra("Show",false);
                                startActivity(i);
                            }

                    });
                    ad.create().show();

                }

            }
        });

// insert into main view
    }

}
