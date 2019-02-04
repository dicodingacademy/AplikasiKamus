package com.dicoding.picodiploma.kamus;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.kamus.services.DataManagerService;

import java.lang.ref.WeakReference;

import static com.dicoding.picodiploma.kamus.services.DataManagerService.CANCEL_MESSAGE;
import static com.dicoding.picodiploma.kamus.services.DataManagerService.FAILED_MESSAGE;
import static com.dicoding.picodiploma.kamus.services.DataManagerService.PREPARATION_MESSAGE;
import static com.dicoding.picodiploma.kamus.services.DataManagerService.SUCCESS_MESSAGE;
import static com.dicoding.picodiploma.kamus.services.DataManagerService.UPDATE_MESSAGE;

public class MainActivity extends AppCompatActivity implements HandlerCallback {
    private ProgressBar progressBar;

    private LinearLayout layout;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        layout = findViewById(R.id.loading);
        tvStatus = findViewById(R.id.tv_status);

        Intent mBoundServiceIntent = new Intent(MainActivity.this, DataManagerService.class);
        Messenger mActivityMessenger = new Messenger(new IncomingHandler(this));
        mBoundServiceIntent.putExtra(DataManagerService.ACTIVITY_HANDLER, mActivityMessenger);

        bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(mServiceConnection);
    }

    /***
     * Service Connection adalah interface yang digunakan untuk menghubungkan antara boundservice dengan activity
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

    };

    @Override
    public void onPreparation() {
        layout.setVisibility(View.VISIBLE);
        tvStatus.setText(R.string.loading_text);
    }

    @Override
    public void updateProgress(long progress) {
        progressBar.setProgress((int) progress);

    }

    @Override
    public void loadSuccess() {
        startActivity(new Intent(MainActivity.this, KamusActivity.class));
        finish();
    }

    @Override
    public void loadFailed() {
        Toast.makeText(this, R.string.failed_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadCancel() {
        finish();
    }

    private static class IncomingHandler extends Handler {

        final WeakReference<HandlerCallback> weakCallback;

        IncomingHandler(HandlerCallback callback) {
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PREPARATION_MESSAGE:
                    weakCallback.get().onPreparation();
                    break;
                case UPDATE_MESSAGE:
                    Bundle bundle = msg.getData();
                    long progress = bundle.getLong("KEY_PROGRESS");
                    weakCallback.get().updateProgress(progress);
                    break;
                case SUCCESS_MESSAGE:
                    weakCallback.get().loadSuccess();
                    break;
                case FAILED_MESSAGE:
                    weakCallback.get().loadFailed();
                    break;
                case CANCEL_MESSAGE:
                    weakCallback.get().loadCancel();
                    break;
            }
        }
    }

}

interface HandlerCallback {
    void onPreparation();

    void updateProgress(long progress);

    void loadSuccess();

    void loadFailed();

    void loadCancel();
}