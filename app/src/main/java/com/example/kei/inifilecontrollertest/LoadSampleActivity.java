package com.example.kei.inifilecontrollertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import jp.team.e_works.inifilelib.IniFileLoader;
import jp.team.e_works.inifilelib.IniItem;

public class LoadSampleActivity extends AppCompatActivity {
    // ローダー
    private IniFileLoader mLoader;
    // 最初のロードを行っているか
    private boolean mIsFirstLoaded = false;

    // ロード結果を表示するテキストビュー
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_sample);

        // テキストビューを取得
        mResultTextView = (TextView) findViewById(R.id.txt_result);

        // ローダーを生成
        mLoader = new IniFileLoader();

        Button btnLoad = (Button) findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsFirstLoaded) {
                    // ファイルパスを指定してロード
                    mLoader.load(Const.LOAD_FILE_PATH);
                    Log.d("load file : " + Const.LOAD_FILE_PATH);
                } else {
                    // リロード
                    mLoader.reload();
                    Log.d("reload");
                }
                outputLoadResult(mLoader.getAllDataList());
            }
        });
    }

    private void outputLoadResult(List<IniItem> list) {
        StringBuilder sb = new StringBuilder();
        String section = null;
        for (IniItem item : list) {
            if (item.getSection() != null && !item.getSection().equals(section)) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append("[");
                sb.append(item.getSection());
                sb.append("]\n");

                section = item.getSection();
            }
            sb.append(item.getKey());
            sb.append("=");
            sb.append(item.getValue());
            sb.append("\n");
        }
        mResultTextView.setText(sb);
        Log.d(sb.toString());
    }
}
