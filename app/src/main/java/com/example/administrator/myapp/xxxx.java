package com.example.administrator.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapp.utils.CustomXWPFDocument;
import com.example.administrator.myapp.utils.WordUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class xxxx extends Activity {
    private Button btn_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btn_new = (Button) findViewById(R.id.btn_new);
        btn_new.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("${name}", "panxianfeng");
                param.put("${zhuanye}", "computer");
                param.put("${sex}", "male");
                param.put("${school_name}", "他打鱼的jia");
                param.put("${date}", new Date().toString());

                Map<String,Object> header = new HashMap<String, Object>();
                header.put("width", 100);
                header.put("height", 150);
                header.put("type", "jpg");
                try {
                    header.put("content", WordUtil.inputStream2ByteArray(new FileInputStream("/sdcard/1.jpg"), true));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                param.put("${header}",header);

        /*Map<String,Object> twocode = new HashMap<String, Object>();
        twocode.put("width", 100);
        twocode.put("height", 100);
        twocode.put("type", "png");
        twocode.put("content", ZxingEncoderHandler.getTwoCodeByteArray("娴嬭瘯浜岀淮鐮�,huangqiqing", 100,100));
        param.put("${twocode}",twocode);  */

                CustomXWPFDocument doc =  WordUtil.generateWord(param, "/sdcard/1.docx");
                FileOutputStream fopts = null;
                try {
                    fopts = new FileOutputStream("/sdcard/2.docx");
                    doc.write(fopts);
                    fopts.flush();
                    fopts.close();
                } catch (FileNotFoundException e) {
                Toast.makeText(getApplication(),"fileNOTFound",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplication(),"xxx",Toast.LENGTH_SHORT).show();
            }
        });




    }

}
