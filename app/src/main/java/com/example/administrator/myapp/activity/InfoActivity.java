package com.example.administrator.myapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.myapp.R;


public class InfoActivity extends AppCompatActivity {
    private TextView tv_com_name;
    private TextView tv_com_id;
    private TextView tv_pro_name;
    private Spinner sp_type;
    private Spinner sp_basic;
    private String CONFIG = "config";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("基本信息");
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();


        final SharedPreferences sp = getSharedPreferences(CONFIG, 0);
        String com_name = sp.getString("com_name", "");
        String com_id = sp.getString("com_id", "");
        String pro_name = sp.getString("pro_name", "");
        int or_type = sp.getInt("org_type", 0);
        int or_provic = sp.getInt("org_provic", 0);
        tv_pro_name.setText(pro_name);
        tv_com_name.setText(com_name);
        tv_com_id.setText(com_id);
        sp_type.setSelection(or_type,true);
        sp_basic.setSelection(or_provic, true);

        final String[] types = getResources().getStringArray(R.array.ory_tpye);
        final String[] provics = getResources().getStringArray(R.array.ory_provic);
        sp_type.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types));
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = types[position];
                sp.edit().putInt("org_type", position).commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*sp_basic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String provic = provics[position];
                sp.edit().putInt("org_provic", position).commit();
            }
        });*/
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void init() {
        tv_com_id = (TextView) findViewById(R.id.tv_con_id);
        tv_com_name = (TextView) findViewById(R.id.tv_con_name);
        tv_pro_name = (TextView) findViewById(R.id.tv_pro_name);
        sp_type = (Spinner) findViewById(R.id.ed_org_type);
        sp_basic = (Spinner) findViewById(R.id.ed_org_basis);

    }
}
