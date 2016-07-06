package com.example.administrator.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.R;
import com.example.administrator.myapp.utils.CheckBoxUtils;
import com.example.administrator.myapp.utils.CustomXWPFDocument;
import com.example.administrator.myapp.utils.DialogListener;
import com.example.administrator.myapp.utils.WordUtil;
import com.example.administrator.myapp.utils.WritePadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class OrzDetailActivity extends AppCompatActivity {
    private RecyclerView rcl_view;
    private TextView tv_cheshiren, tv_date, tv_cheshiren1, tv_date1;
    private ImageView iv_ceshiren, iv_ceshiren1;
    private Button btn_ok;
    private Bitmap mSignBitmap;
    private String signPath;
    private LinearLayout ll_ceshiren;
    private LinearLayout ll_ceshiren1;
    private Map<String, Object> param = new Hashtable<>();
    private SimpleDateFormat formatter;
    private Date curDate;
    private String str;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(getApplicationContext(),"文件生成"+param.size(),Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orz_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("验证测试设备运行确认表");
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        rcl_view.setLayoutManager(new LinearLayoutManager(this));
        rcl_view.setAdapter(new MultipleItemAdapter(this));
        ll_ceshiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WritePadDialog writeTabletDialog = new WritePadDialog(
                        OrzDetailActivity.this, new DialogListener() {
                    @Override
                    public void refreshActivity(Object object) {
                        tv_cheshiren.setVisibility(View.GONE);
                        mSignBitmap = (Bitmap) object;
                        signPath = createFile("ceshiren");

                        BitmapFactory.Options options = new
                                BitmapFactory.Options();
                        options.inSampleSize = 4;
                        options.inTempStorage = new byte[5 *
                                1024];
                        Bitmap zoombm = BitmapFactory.decodeFile(signPath,
                                options);
                        iv_ceshiren.setImageBitmap(mSignBitmap);
                        iv_ceshiren.setVisibility(View.VISIBLE);
                        formatter = new SimpleDateFormat("yyyy年MM月dd日 ");
                        curDate = new Date(System.currentTimeMillis());//获取当前时间
                        str = "日期：" + formatter.format(curDate);
                        tv_date.setText(str);
                        Map<String, Object> ceshi = new HashMap<String, Object>();
                        ceshi.put("width", 100);
                        ceshi.put("height", 150);
                        ceshi.put("type", "jpg");
                        try {
                            ceshi.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + "lengkuImage1/ceshiren.jpg"), true));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        param.put("${ceshiren}", ceshi);
                    }
                });
                writeTabletDialog.show();
            }
        });
        ll_ceshiren1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WritePadDialog writeTabletDialog = new WritePadDialog(
                        OrzDetailActivity.this, new DialogListener() {
                    @Override
                    public void refreshActivity(Object object) {
                        tv_cheshiren1.setVisibility(View.GONE);
                        mSignBitmap = (Bitmap) object;
                        signPath = createFile("fuheren");

                        BitmapFactory.Options options = new
                                BitmapFactory.Options();
                        options.inSampleSize = 4;
                        options.inTempStorage = new byte[5 *
                                1024];
                        Bitmap zoombm = BitmapFactory.decodeFile(signPath,
                                options);
                        iv_ceshiren1.setImageBitmap(mSignBitmap);
                        iv_ceshiren1.setVisibility(View.VISIBLE);
                        formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
                        curDate = new Date(System.currentTimeMillis());//获取当前时间
                        str = "日期：" + formatter.format(curDate);
                        tv_date1.setText(str);
                        Map<String, Object> fuhe = new HashMap<String, Object>();
                        fuhe.put("width", 180);
                        fuhe.put("height", 150);
                        fuhe.put("type", "jpg");
                        try {
                            fuhe.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + "lengkuImage1/fuheren.jpg"), true));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        param.put("${fuheren}", fuhe);
                    }
                });
                writeTabletDialog.show();
            }
        });
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CustomXWPFDocument doc = WordUtil.generateWord(param, path + "da.docx");
                        FileOutputStream fopts = null;
                        try {
                            fopts = new FileOutputStream(path + "2.docx");
                            doc.write(fopts);
                            fopts.flush();
                            fopts.close();
                            fileScan(path + "2.docx");
                        } catch (FileNotFoundException e) {
                            Toast.makeText(getApplication(), "fileNOTFound", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message mess = handler.obtainMessage();
                        mess.what = 0;
                        handler.sendMessage(mess);

                    }
                }).start();
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

    private void init() {
        rcl_view = (RecyclerView) findViewById(R.id.rcl_view);
        tv_cheshiren = (TextView) findViewById(R.id.tv_ceshiren);
        tv_cheshiren1 = (TextView) findViewById(R.id.tv_ceshiren1);
        iv_ceshiren = (ImageView) findViewById(R.id.iv_ceshiren);
        iv_ceshiren1 = (ImageView) findViewById(R.id.iv_ceshiren1);
        ll_ceshiren = (LinearLayout) findViewById(R.id.ll_ceshiren);
        ll_ceshiren1 = (LinearLayout) findViewById(R.id.ll_ceshiren1);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date1 = (TextView) findViewById(R.id.tv_date1);
        btn_ok = (Button) findViewById(R.id.btn_ok);
    }

    private String createFile(String dirPath) {
        ByteArrayOutputStream baos = null;
        String _path = null;
        File destDir = new File(Environment.getExternalStorageDirectory()
                + File.separator + "lengkuImage1");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try {
            /*String sign_dir = Environment.getExternalStorageDirectory()
                    + File.separator+"lengkuImage"+File.separator;*/
            _path = destDir.getAbsolutePath() + File.separator + dirPath + ".jpg";
            baos = new ByteArrayOutputStream();
            mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(_path)).write(photoBytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }

    /**
     * 出发扫描 mtp下的文件，在保存文件到 sd卡下后，不能显示，故这里触发一下扫描机制，让手机连上电脑后，就可以读出文件了
     *
     * @param fName，文件的完整路径名
     */
    public void fileScan(String fName) {
        Uri data = Uri.parse("file:///" + fName);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    public enum ITEM_TYPE {
        ITEM_TYPE_TITLE,
        ITEM_TYPE_TEXT
    }

    public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private String[] mTitles;

        public MultipleItemAdapter(Context context) {
            mTitles = context.getResources().getStringArray(R.array.title_array);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.ITEM_TYPE_TITLE.ordinal()) {
                return new ImageViewHolder(mLayoutInflater.inflate(R.layout.recycerl_view_title, parent, false));
            } else {
                return new TextViewHolder(mLayoutInflater.inflate(R.layout.recycerl_view_text, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TextViewHolder) {
                ((TextViewHolder) holder).mTextView.setText(mTitles[position]);
            } else if (holder instanceof ImageViewHolder) {
                ((ImageViewHolder) holder).mTextView.setText(mTitles[position]);
            }
        }

        @Override
        public int getItemViewType(int position) {
            int temp = 0;
            switch (position) {
                case 0:
                case 4:
                case 7:
                case 9:
                case 11:
                    temp = ITEM_TYPE.ITEM_TYPE_TITLE.ordinal();
                    break;
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 8:
                case 10:
                case 12:
                case 13:
                case 14:
                    temp = ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
                    break;
            }
//            return position % 2 == 0 ? ITEM_TYPE.ITEM_TYPE_TITLE.ordinal() : ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
            return temp;
        }


        @Override
        public int getItemCount() {
            return mTitles == null ? 0 : mTitles.length;
        }

        public class TextViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            CheckBox ck1, ck11;

            TextViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.tv_text);
                ck1 = (CheckBox) view.findViewById(R.id.ck_1);
                ck11 = (CheckBox) view.findViewById(R.id.ck_11);
                new CheckBoxUtils().onlyOneChecked(ck1, ck11);
                ck1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (ck1.isChecked()) {
                            param.put("${check" + getPosition() + "}", ck1.getText().toString());
//                            Toast.makeText(getApplicationContext(), "${check" + getPosition() + "}", Toast.LENGTH_SHORT).show();
                            Log.d("TextViewHolder", "getPosition===" + "${check" + getPosition() + "}");
                        }
                    }
                });
                ck11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (ck11.isChecked()) {
                            param.put("${check" + getPosition() + "}", ck11.getText().toString());
                            Log.d("TextViewHolder", "getPosition2===" + getPosition());
                        }
                    }
                });
                Log.d("TextViewHolder", "onClick--> position = " + "{txt}" + getPosition() + "param.size==" + param.size());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TextViewHolder", "onClick--> position = " + "{txt}" + getPosition());
                        Toast.makeText(v.getContext(), param.size() + "..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            ImageViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.tv_title);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ImageViewHolder", "onClick--> position = " + getPosition());
                    }
                });
            }
        }

    }
}
