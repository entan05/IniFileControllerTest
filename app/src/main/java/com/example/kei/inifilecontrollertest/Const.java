package com.example.kei.inifilecontrollertest;

import android.os.Environment;

import java.io.File;

public class Const {
    /** ファイルセパレータ */
    public static final String SEPARATOR = File.separator;

    /** ファイルの保持先ディレクトリ */
    public static final String BASE_DIR = Environment.getExternalStorageDirectory().getPath() + SEPARATOR + "IniFileControllerTest";

    /** ロードするファイル名 */
    public static final String LOAD_FILE = "load_sample.ini";

    /** ロードするiniファイルのパス */
    public static final String LOAD_FILE_PATH = BASE_DIR + SEPARATOR + LOAD_FILE;

    /** 書き込み先iniファイルパス */
    public static final String WRITE_FILE_PATH = BASE_DIR + SEPARATOR + "write_sample.ini";
}
