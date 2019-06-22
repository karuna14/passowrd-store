package pass.kk.passwordstore;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Main8Activity extends Activity {


    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private TextView textView;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        final  FingerprintHandler helper = new FingerprintHandler(this);
        Button b=(Button)findViewById(R.id.button8);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    keyguardManager =
                            (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                    fingerprintManager =
                            (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

                    textView = (TextView) findViewById(R.id.textView2);


                    if (!fingerprintManager.isHardwareDetected()) {

                        textView.setText("Your device doesn't support fingerprint authentication");
                    }

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                        textView.setText("Please enable the fingerprint permission");
                    }


                    if (!fingerprintManager.hasEnrolledFingerprints()) {

                        textView.setText("No fingerprint configured. Please register at least one fingerprint in your device's Settings");
                    }

                    if (!keyguardManager.isKeyguardSecure()) {

                        textView.setText("Please enable lockscreen security in your device's Settings");
                    } else {
                        try {

                            generateKey();
                        } catch (FingerprintException e) {
                            e.printStackTrace();
                        }

                        if (initCipher()) {


                            cryptoObject = new FingerprintManager.CryptoObject(cipher);


                            helper.startAuth(fingerprintManager, cryptoObject);

                        }
                    }

                }
            }
        });


    }



    private void generateKey() throws FingerprintException {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");

            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);


            keyGenerator.init(new


                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)


                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());


            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    public boolean initCipher() {
        try {

            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;
        } catch (KeyPermanentlyInvalidatedException e) {

            //Return false if cipher initialization failed//
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
  }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }


    /*
     * Condition IV: Check if the permission has been added to
     * the app. This permission will be granted as soon as the user
     * installs the app on their device.
     *
     * */
    public static boolean isPermissionGranted(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
