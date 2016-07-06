package com.example.administrator.myapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myapp.R;
import com.example.administrator.myapp.utils.DialogListener;
import com.example.administrator.myapp.utils.DividerItemDecoration;
import com.example.administrator.myapp.utils.WritePadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2016-06-22.
 */
public class TextFragment extends Fragment {
    private EditText ed_pro_item;
    private EditText ed_teacher_item;
    private EditText ed_data_item;
    private EditText ed_site_item;
    private RecyclerView rl_frag;
    private Map<String, Object> param = new Hashtable<>();
    private Bitmap mSignBitmap;
    private String signPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_fragment, container, false);
        ed_pro_item = (EditText) view.findViewById(R.id.pro_item);
        ed_teacher_item = (EditText) view.findViewById(R.id.teacher_item);
        ed_data_item = (EditText) view.findViewById(R.id.data_item);
        ed_site_item = (EditText) view.findViewById(R.id.site_item);
        rl_frag = (RecyclerView) view.findViewById(R.id.rl_frag);


        ed_pro_item.getText().toString();
        ed_teacher_item.getText().toString();
        ed_data_item.getText().toString();
        ed_site_item.getText().toString();
        rl_frag.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rl_frag.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rl_frag.setAdapter(new MultipleItemAdapter(getContext()));

        return view;
    }

    public enum ITEM_TYPE {
        ITEM_TYPE_TITLE,
        ITEM_TYPE_TEXT,
        ITEM_TYPE_SIGN
    }

    public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private String[] mTitles;

        public MultipleItemAdapter(Context context) {
            mTitles = context.getResources().getStringArray(R.array.text_title);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()) {
                return new TextViewHolder(mLayoutInflater.inflate(R.layout.table_three_item_text, parent, false));
            } else if (viewType == ITEM_TYPE.ITEM_TYPE_TITLE.ordinal()) {
                return new ImageViewHolder(mLayoutInflater.inflate(R.layout.table_one_iamge, parent, false));
            } else if (viewType == ITEM_TYPE.ITEM_TYPE_SIGN.ordinal()) {
                return new SignViewHolder(mLayoutInflater.inflate(R.layout.table_three_item_edit, parent, false));
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TextViewHolder) {
//                ((TextViewHolder) holder).mTextView.setText(mTitles[position]);
            } else if (holder instanceof ImageViewHolder) {

//                ((ImageViewHolder) holder).mTextView.setText(mTitles[position]);
            } else if (holder instanceof SignViewHolder) {
                ((SignViewHolder) holder).mEditView.setText(mTitles[position]);
            }
        }

        @Override
        public int getItemViewType(int position) {
            int temp = 0;
            switch (position) {
                case 0:
                case 1:
                case 2:
                case 3:
                    temp = ITEM_TYPE.ITEM_TYPE_SIGN.ordinal();
                    break;
                case 4:
                case 6:
                case 8:
                case 10:
                case 12:
                case 14:
                case 16:
                case 18:
                case 20:
                case 22:
                    temp = ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
                    break;
                default:
                    temp = ITEM_TYPE.ITEM_TYPE_TITLE.ordinal();
                    break;
            }
//            return position % 2 == 0 ? ITEM_TYPE.ITEM_TYPE_TITLE.ordinal() : ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
            return temp;
        }


        @Override
        public int getItemCount() {
            return 24;
//            return mTitles == null ? 0 : mTitles.length;
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
                                getActivity(), new DialogListener() {
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
                            }
                        });
                        writeTabletDialog.setCanceledOnTouchOutside(false);
                        writeTabletDialog.show();
                        Toast.makeText(v.getContext(), getPosition() + "..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        public class TextViewHolder extends RecyclerView.ViewHolder {
            EditText mEditView;

            TextViewHolder(View view) {
                super(view);
                mEditView = (EditText) view.findViewById(R.id.table_edit);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ImageViewHolder", "onClick--> position = " + getPosition());
                    }
                });
            }
        }

        public class SignViewHolder extends RecyclerView.ViewHolder {
            TextView mEditView;

            SignViewHolder(View view) {
                super(view);
                mEditView = (TextView) view.findViewById(R.id.table_text_view);

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
