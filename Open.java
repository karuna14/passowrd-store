package pass.kk.passwordstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class Open extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Open.this,MainActivity.class);
                Open.this.startActivity(i);
                Open.this.finish();
            }
        },2000);
    }
}
