package pass.kk.passwordstore;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends AuthenticationCallback {
    private CancellationSignal cancellationSignal;
    private Context context;

    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

        public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override


    public void onAuthenticationError(int errMsgId, CharSequence errString) {



        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override

    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
    }

    @Override

    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override


    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context,
                Main5Activity.class));
    }
}
