package com.example.administrator.myapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.example.administrator.myapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataCollectActivity extends AppCompatActivity {
    private ListView lv_data;
    private String[] strs = {"Iamge", "Text"};
    private String[] str = {"测点及绑定关系", "数据采集时间明细表",
            "温控设备测点手工录入表"};
    private int[] icon = {R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};
    private int[] icons = {R.id.iv_item, R.id.tv_item};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("数据采集要求相关信息");
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init();
        lv_data.setAdapter(new SimpleAdapter(getApplicationContext(), getData(), R.layout.listview_table_item, strs, icons));

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        startActivity(new Intent(getApplicationContext(), VerTabOneActivity.class));
                        Toast.makeText(getApplicationContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        startActivity(new Intent(getApplicationContext(), VerTabTwoActivity.class));
                        Toast.makeText(getApplicationContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
//                        startActivity(new Intent(getApplicationContext(), VerTabThreeActivity.class));
                        Toast.makeText(getApplicationContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    private void init(){
        lv_data = (ListView) findViewById(R.id.lv_data);
    }
    private List<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> sourceList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap = new HashMap<>();
        for (int i = 0; i < str.length; i++) {
            hashMap.put("Iamge", icon[i]);
            hashMap.put("Text", str[i]);
            sourceList.add(hashMap);
            hashMap = new HashMap<>();
        }
        return sourceList;
    }
}
