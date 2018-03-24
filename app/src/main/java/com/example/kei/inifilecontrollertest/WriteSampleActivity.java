package com.example.kei.inifilecontrollertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import jp.team.e_works.inifilelib.IniFileWriter;
import jp.team.e_works.inifilelib.IniItem;
import jp.team.e_works.inifilelib.IniItemComparator;

public class WriteSampleActivity extends AppCompatActivity {
    // セクション入力欄
    private EditText mSectionInput;
    private EditText mKeyInput;
    private EditText mValueInput;
    private EditText mCommentInput;

    private TextView mDataOutput;

    private IniFileWriter mWriter;

    private ArrayList<IniItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_sample);

        mWriter = new IniFileWriter();
        mItems = new ArrayList<>();

        mSectionInput = (EditText) findViewById(R.id.txt_section);
        mKeyInput = (EditText) findViewById(R.id.txt_key);
        mValueInput = (EditText) findViewById(R.id.txt_value);
        mCommentInput = (EditText) findViewById(R.id.txt_comment);

        mDataOutput = (TextView) findViewById(R.id.txt_datas);

        Button btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(mAddClickListener);

        Button btnWrite = (Button) findViewById(R.id.btn_write);
        btnWrite.setOnClickListener(mWriteClickListener);
    }

    private View.OnClickListener mAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String section = mSectionInput.getText().toString();
            if (TextUtils.isEmpty(section)) {
                section = null;
            }
            String key = mKeyInput.getText().toString();
            String value = mValueInput.getText().toString();
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                Log.w("key or value is empty");
                return;
            }
            String comment = mCommentInput.getText().toString();
            if (TextUtils.isEmpty(comment)) {
                comment = null;
            }

            IniItem item = new IniItem(section, key, value, comment);
            mItems.add(item);

            Collections.sort(mItems, new IniItemComparator());
            StringBuilder sb = new StringBuilder();
            for (IniItem i : mItems) {
                sb.append(i.toString());
                sb.append("\n");
            }
            mDataOutput.setText(sb);

            mSectionInput.setText("");
            mKeyInput.setText("");
            mValueInput.setText("");
            mCommentInput.setText("");
        }
    };

    private View.OnClickListener mWriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mWriter.add(mItems);
            boolean result = mWriter.write(Const.WRITE_FILE_PATH, IniFileWriter.MODE.NEW);

            if (result) {
                Log.d("write success");
                mDataOutput.setText("");
                mItems.clear();
            } else {
                Log.w("write faild");
            }
        }
    };
}
