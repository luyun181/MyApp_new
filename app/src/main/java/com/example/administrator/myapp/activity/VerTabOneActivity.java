package com.example.administrator.myapp.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myapp.R;
import com.example.administrator.myapp.utils.CustomXWPFDocument;
import com.example.administrator.myapp.utils.DialogListener;
import com.example.administrator.myapp.utils.DividerItemDecoration;
import com.example.administrator.myapp.utils.WordUtil;
import com.example.administrator.myapp.utils.WritePadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class VerTabOneActivity extends AppCompatActivity {
    private RecyclerView rl_table;
    private Map<String, Object> param = new Hashtable<>();
    private Bitmap mSignBitmap;
    private String signPath;
    private Button btn_new;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    break;

            }
            return false;
        }
    });
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tab_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("验证领导小组人员及职责列表");
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        rl_table.setLayoutManager(new GridLayoutManager(this, 4));
        rl_table.setAdapter(new MultipleItemAdapter(this));
        rl_table.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CustomXWPFDocument doc = WordUtil.generateWord(param, path + "daa.docx");
                        FileOutputStream fopts = null;
                        try {
                            fopts = new FileOutputStream(path + "3.docx");
                            doc.write(fopts);
                            fopts.flush();
                            fopts.close();
//                            fileScan(path + "2.docx");
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
                startActivity(new Intent(getApplication(),InfoActivity.class));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void init() {
        rl_table = (RecyclerView) findViewById(R.id.rl_table);
        btn_new = (Button) findViewById(R.id.button);
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
            mTitles = context.getResources().getStringArray(R.array.text_arr);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()) {
                return new TextViewHolder(mLayoutInflater.inflate(R.layout.table_one_item_text, parent, false));
            } else {
                return new ImageViewHolder(mLayoutInflater.inflate(R.layout.table_one_iamge, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TextViewHolder) {
                ((TextViewHolder) holder).mTextView.setText(mTitles[position]);
            } else if (holder instanceof ImageViewHolder) {
//                ((ImageViewHolder) holder).mTextView.setText(mTitles[position]);
            }
        }

        @Override
        public int getItemViewType(int position) {
            int temp = 0;
            switch (position) {
                case 4:
                case 8:
                case 12:
                case 16:
                    temp = ITEM_TYPE.ITEM_TYPE_TITLE.ordinal();
                    break;
                default:
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

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView mImageView;
            TextView mTextView;
            LinearLayout mLinear;

            ImageViewHolder(View view) {
                super(view);
                mImageView = (ImageView) view.findViewById(R.id.table_image);
                mTextView = (TextView) view.findViewById(R.id.table_text1);
                Log.d("TextViewHolder", "onClick--> position = " + "{txt}" + getPosition() + "param.size==" + param.size());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TextViewHolder", "onClick--> position = " + "{txt}" + getPosition());
                        WritePadDialog writeTabletDialog = new WritePadDialog(
                                VerTabOneActivity.this, new DialogListener() {
                            @Override
                            public void refreshActivity(Object object) {
                                mTextView.setVisibility(View.GONE);
                                mSignBitmap = (Bitmap) object;
                                signPath = createFile("tableone" + getPosition());
                                BitmapFactory.Options options = new
                                        BitmapFactory.Options();
                                options.inSampleSize = 4;
                                options.inTempStorage = new byte[5 *
                                        1024];
                                Bitmap zoombm = BitmapFactory.decodeFile(signPath,
                                        options);
                                mImageView.setImageBitmap(zoombm);
                                mImageView.setVisibility(View.VISIBLE);
                                Map<String, Object> fuhe = new HashMap<String, Object>();
                                fuhe.put("width", 180);
                                fuhe.put("height", 150);
                                fuhe.put("type", "jpg");
                                try {
                                    fuhe.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                                            + File.separator + "lengkuImage1/ceshiren.jpg"), true));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                param.put("${qianming+" + getPosition() + "}", fuhe);
                                Log.d("xx",param.get("{qianming+" + getPosition() + "}")+"==qianming"+getPosition());

                            }
                        });
                        writeTabletDialog.setCanceledOnTouchOutside(false);
                        writeTabletDialog.show();
                        Toast.makeText(v.getContext(), param.size() + "..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        public class TextViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            TextViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.table_text);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ImageViewHolder", "onClick--> position = " + getPosition());
                    }
                });
            }
        }

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
}
