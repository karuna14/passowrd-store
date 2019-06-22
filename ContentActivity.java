package pass.kk.passwordstore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.math.BigInteger;
import java.util.ArrayList;

public class ContentActivity extends Activity {
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent i=getIntent();
       final String paas=i.getStringExtra("Paas");
        if(i.getBooleanExtra("Show",false)){
            db=new DB(this);
            ArrayList<String> al=db.getAllName();
            ArrayList<String> pall=db.getAllPass();
            for(int ii=0;ii<al.size();ii++){
                String name=al.get(ii);
                String pass=pall.get(ii);
                final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.con);


                LinearLayout linearLayout1=new LinearLayout(this);
                linearLayout1.setOrientation(LinearLayout.VERTICAL);


                TextView tv=new TextView(this);
//                tv.setText("For:"+name+"/n");
                tv.setTextSize(20);
                 tv.setPadding(10,5,10,10);
                 tv.setTextColor(Color.WHITE);
                 tv.setBackgroundColor(Color.BLACK);
//                 tv.setBackground(Color.BLACK);
                 tv.setBackgroundResource(R.drawable.textview);
//                linearLayout1.addView(tv);

//                TextView tv2=new TextView(this);
                BigInteger bigInteger=new BigInteger(pass);
                try {
                    tv.setText("FOR :" + name + "\nPASSWORD: " + decrypt(bigInteger,paas));
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"This not corrcet Password",Toast.LENGTH_SHORT).show();
                }

                linearLayout1.addView(tv);

                linearLayout.addView(linearLayout1);


            }
        }

        final FloatingActionButton f1=(FloatingActionButton)findViewById(R.id.b);
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionMenu m=(FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu);
       m.close(true);


                Intent i = new Intent(ContentActivity.this, AddActivity.class);

               startActivityForResult(i, 0);

            }
        });
        FloatingActionButton f2=(FloatingActionButton)findViewById(R.id.bu2);
        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionMenu m=(FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu);
                m.close(true);
                Intent i = new Intent(ContentActivity.this, Main9Activity.class);
                i.putExtra("Paas",paas);
                startActivity(i);
            }
        });
        FloatingActionButton f3=(FloatingActionButton)findViewById(R.id.bu3);
        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionMenu m=(FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu);
                m.close(true);
                Intent i = new Intent(ContentActivity.this, Main10Activity.class);
                i.putExtra("Paas",paas);
                startActivity(i);
            }
        });
    }
    @Override
        protected void onActivityResult(int r,int re,Intent data){
         if(r==0 && re==1){
             String name=data.getStringExtra("Name");
             String pas=data.getStringExtra("Pass");
             final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.con);



             LinearLayout linearLayout1=new LinearLayout(this);
             linearLayout1.setOrientation(LinearLayout.VERTICAL);


             TextView tv2=new TextView(this);
             tv2.setTextSize(20);
             tv2.setPadding(10,5,10,10);
             tv2.setTextColor(Color.WHITE);
             tv2.setBackgroundColor(Color.BLACK);

             tv2.setBackgroundResource(R.drawable.textview);
            String pass=encrypt(pas).toString();

             tv2.setText("FOR :" + name + "\nPASSWORD: " + pas);
             linearLayout1.addView(tv2);

             linearLayout.addView(linearLayout1);

             db=new DB(this);
             boolean b=db.insert(name,pass);

         }
         else if(r==1&&re==1){
             final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.con);
             linearLayout.removeAllViewsInLayout();
             db=new DB(this);
             ArrayList<String> al=db.getAllName();
             ArrayList<String> pall=db.getAllPass();
             for(int ii=0;ii<al.size();ii++){
                 String name=al.get(ii);
                 String pass=pall.get(ii);
                 final LinearLayout linearLayou=(LinearLayout)findViewById(R.id.con);


                 LinearLayout linearLayout1=new LinearLayout(this);
                 linearLayout1.setOrientation(LinearLayout.VERTICAL);


                 TextView tv=new TextView(this);
                 tv.setTextSize(20);
                 tv.setPadding(10,5,10,10);
                 tv.setTextColor(Color.WHITE);
                 tv.setBackgroundColor(Color.BLACK);
                 tv.setBackgroundResource(R.drawable.textview);
                 BigInteger bigInteger=new BigInteger(pass);
                 try {
                     Intent i=getIntent();
                     final String paas=i.getStringExtra("Paas");

                     tv.setText("FOR :" + name + "\nPASSWORD: " + decrypt(bigInteger,paas));
                 }
                 catch(Exception e){
                     Toast.makeText(getApplicationContext(),"This not corrcet Password",Toast.LENGTH_SHORT).show();
                 }
                 linearLayou.addView(tv);
                 linearLayout1.addView(linearLayou);
         }}
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
