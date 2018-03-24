package com.example.kei.inifilecontrollertest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    // パーミッションのリクエストコード
    private static final int PERMISSION_REQUEST_CODE = 1000;

    // ボタン
    private Button mBtnLoadSample;
    private Button mBtnWriteSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLoadSample = (Button) findViewById(R.id.btn_loadSample);
        mBtnLoadSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LoadSampleActivity.class);
                startActivity(intent);
            }
        });

        mBtnWriteSample = (Button) findViewById(R.id.btn_writeSample);
        mBtnWriteSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), WriteSampleActivity.class);
                startActivity(intent);
            }
        });

        // パーミッション要求
        requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 許可を得たのでディレクトリ作成
                createDir();
                return;
            }
        }
        finish();
    }

    private void requestPermission() {
        // 許可されていない場合は許可を求めるダイアログを表示
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        // 許可を得ている場合はディレクトリ作成
        else {
            createDir();
        }
    }

    private void createDir() {
        // ベースのディレクトリがない場合は作成
        File dir = new File(Const.BASE_DIR);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                finish();
            }
        }
        // assetsのサンプルファイルをローカルにコピー
        copySampleIniFile();
    }

    private void copySampleIniFile() {
        File file = new File(Const.LOAD_FILE_PATH);
        if (file.exists()) {
            enableButtons();
            return;
        }

        try {
            InputStream inputStream = getAssets().open(Const.LOAD_FILE);
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) >= 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e(e);
        }
        enableButtons();
    }

    private void enableButtons() {
        mBtnLoadSample.setEnabled(true);
        mBtnWriteSample.setEnabled(true);
    }
}
