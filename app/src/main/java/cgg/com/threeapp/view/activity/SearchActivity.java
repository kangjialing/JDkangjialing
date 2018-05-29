package cgg.com.threeapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import cgg.com.threeapp.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText search_edit;
    private FlexboxLayout mflexbox;
    private List<String> mHotTitles;
    private TextView edit_button;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_edit = findViewById(R.id.search_edit);
        edit_button = findViewById(R.id.edit_button);
        mflexbox = findViewById(R.id.flexbox_layout);
        back = findViewById(R.id.back);

        edit_button.setOnClickListener(this);
        back.setOnClickListener(this);
        initTag();

        edit_button.setOnClickListener(this);
    }

    private void initTag() {
        mHotTitles = new ArrayList<>();
        mHotTitles.add("手机");
        mHotTitles.add("台式电脑");
        mHotTitles.add("笔记本");
        mHotTitles.add("联想");
        mHotTitles.add("月饼");
        mHotTitles.add("婴儿车");
        mHotTitles.add("六个核桃");
        mHotTitles.add("拉菲82年");
        mHotTitles.add("热血健身器材");
        mHotTitles.add("贝贝熊");
        mHotTitles.add("大胖子");
        mHotTitles.add("热干面");
        mHotTitles.add("香辣烤肉");
        // 通过代码向FlexboxLayout添加View
        for (int i = 0; i < mHotTitles.size(); i++) {
            final String itemStr = mHotTitles.get(i);
            final TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.flexbox_text_bg);
            textView.setText(itemStr);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(15, 15, 15, 15);
            textView.setClickable(true);
            textView.setFocusable(true);
            //textView.setTextColor(getResources().getColor(R.color.flexbox_text_color));
            mflexbox.addView(textView);
            //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
            ViewGroup.LayoutParams params = textView.getLayoutParams();
            if (params instanceof FlexboxLayout.LayoutParams) {
                FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
                //layoutParams.setFlexBasisPercent(0.5f);
                layoutParams.setMargins(10, 8, 10, 8);
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoSearchListActivity(itemStr);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_button:
                String editStr = search_edit.getText().toString();
                if (editStr != null && !"".equals(editStr.trim())) {
                    // 将搜索的内容传递给webViewActivity
                    gotoSearchListActivity(editStr);
                } else {
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                finish();
                break;

        }
    }

    public void gotoSearchListActivity(String productName){
        Intent intent = new Intent(SearchActivity.this, SeekActivity.class);
        intent.putExtra("productName",productName);
        startActivity(intent);
    }
}
