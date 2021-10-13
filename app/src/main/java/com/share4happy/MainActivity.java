package com.share4happy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listViewdsFont;
    ArrayList<String> ds_NameFont = new ArrayList<>();
    ArrayAdapter<String> adapterFont;
    String tenluutru ="trang thai font";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addcontrols();
        addEvent();
    }

    private void addEvent() {
        //Tao su kien khi click chon doi font
        listViewdsFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Để thay đổi font chữ ta dùng TypeFace, createFromAsset
                // đối số 1: là lệnh lấy từ asset, đối số 2: đường dẫn tập tin
                Typeface typeface = Typeface.createFromAsset(getAssets(),"font/"+ds_NameFont.get(i));
                //Sử dụng font chữ cho textview
                textView.setTypeface(typeface);
                // Lưu trạng thái khi font chữ được thay đổi
                //Mode_Private: lưu cho phần mềm sử dụng
                //hàm getSharedPreferences luôn khác null, tự động tạo ra tên file
                SharedPreferences preferences = getSharedPreferences(tenluutru,MODE_PRIVATE);
                //cho phép dữ liệu xuống file
                SharedPreferences.Editor editor = preferences.edit();
                // Đánh dấu lưu trữ trạng thái
                editor.putString("Fontchu","font/"+ds_NameFont.get(i));
                editor.commit();
            }
        });
    }

    private void addcontrols() {
        listViewdsFont = findViewById(R.id.listview_dsFont);
        textView = findViewById(R.id.txtTextView);
        // tao list chua ten font chu
//        ds_NameFont.add("0291-LNTH-Cabron.ttf");
//        ds_NameFont.add("beer money.ttf");
//        ds_NameFont.add("Beon.otf");
//        ds_NameFont.add("BERNIERDistressed-Regular.otf");
//        ds_NameFont.add("BERNIERRegular-Regular.otf");
//        ds_NameFont.add("BERNIERShade-Regular.otf");
//        ds_NameFont.add("Courgette-Regular.ttf");
//        ds_NameFont.add("LPR Music Warrior-v2.ttf");
//        ds_NameFont.add("norwester.otf");
        //Cách 2 đọc file trong asset

        try {
            // để sử dụng được asset ta dùng AssetManager
            AssetManager assetManager = getAssets();
            //Để đọc được tập tin trong Asset ta dùng assetManager.list
            String arrFontName[] = assetManager.list("font"); // font là tên folder chứa tập tin
            //Thêm tất cả các tên tạp tin vào list để hiện thị trên listview
            ds_NameFont.addAll(Arrays.asList(arrFontName));
            // Hien thi ds ten font chu len ListView
            adapterFont = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    ds_NameFont
            );
            listViewdsFont.setAdapter(adapterFont);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Để load font đã đưuọc lưu trong ứng dụng
        SharedPreferences preferences = getSharedPreferences(tenluutru,MODE_PRIVATE);
        //Lấy trực tiếp font ra
        String _font = preferences.getString("Fontchu","");
        if (_font.length() >0){
            //lấy font đã được lưu trước đó
            Typeface typeface = Typeface.createFromAsset(getAssets(),_font);
            textView.setTypeface(typeface);
        }


    }
}