package com.example.flem;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DesktopActivity extends AppCompatActivity {

    TableLayout tableLayout;
    TextView idTable, dispoTable;
    ArrayList<String> day;
    Typeface titleTable, textTable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desktop_main);
        tableLayout = findViewById(R.id.tableLayout);
        idTable = findViewById(R.id.textViewID);
        dispoTable = findViewById(R.id.textViewDispo);
        titleTable = ResourcesCompat.getFont(this,R.font.nunito_blackitalic);
        textTable = ResourcesCompat.getFont(this,R.font.nunito_regular);
        idTable.setTypeface(titleTable);
        dispoTable.setTypeface(titleTable);
        day=new ArrayList<String>();
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(50,0,50,0);
        try {
            File updateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondUpdateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            Workbook wb=null;
            Sheet sh=null;
            int row=0;
            if (updateFile.exists()) {
                wb = Workbook.getWorkbook(updateFile);
                sh = wb.getSheet(1);
                row = sh.getRows();
                for (int i=2;i<row;i++){
                    TableRow tableRow = new TableRow(this);
                    tableRow.setBackgroundResource(R.drawable.customer_tablerow_desktop);
                    TextView textView = new TextView(this);
                    TextView textView1 = new TextView(this);
                    Cell office = sh.getCell(0,i);
                    textView.setText(office.getContents());
                    textView.setTypeface(textTable);
                    Cell monday = sh.getCell(1,i);
                    Cell tuesday = sh.getCell(2,i);
                    Cell wednesday = sh.getCell(3,i);
                    Cell thursday = sh.getCell(4,i);
                    Cell friday  = sh.getCell(5,i);
                    if (monday.getContents().matches("0")){
                        day.add("Lundi");
                    }
                    if (tuesday.getContents().matches("0")){
                        day.add("Mardi");
                    }
                    if (wednesday.getContents().matches("0")){
                        day.add("Mercredi");
                    }
                    if (thursday.getContents().matches("0")){
                        day.add("Jeudi");
                    }
                    if (friday.getContents().matches("0")) {
                        day.add("Vendredi");
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(day);
                    textView1.setText(sb.toString().replace("[", "").replace("]", ""));
                    textView1.setTypeface(textTable);
                    textView.setTextSize(15);
                    textView1.setTextSize(15);
                    textView.setLayoutParams(params);
                    textView1.setLayoutParams(params);
                    tableRow.addView(textView);
                    tableRow.addView(textView1);
                    tableLayout.addView(tableRow);
                    day.clear();
                }

            }else if (secondUpdateFile.exists()) {
                wb = Workbook.getWorkbook(secondUpdateFile);
                sh = wb.getSheet(1);
                row = sh.getRows();
                for (int i=2;i<row;i++) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setBackgroundResource(R.drawable.customer_tablerow_desktop);
                    TextView textView = new TextView(this);
                    TextView textView1 = new TextView(this);
                    Cell office = sh.getCell(0, i);
                    textView.setText(office.getContents());
                    textView.setTypeface(textTable);
                    Cell monday = sh.getCell(1, i);
                    Cell tuesday = sh.getCell(2, i);
                    Cell wednesday = sh.getCell(3, i);
                    Cell thursday = sh.getCell(4, i);
                    Cell friday = sh.getCell(5, i);
                    if (monday.getContents().matches("0")) {
                        day.add("Lundi");
                    }
                    if (tuesday.getContents().matches("0")) {
                        day.add("Mardi");
                    }
                    if (wednesday.getContents().matches("0")) {
                        day.add("Mercredi");
                    }
                    if (thursday.getContents().matches("0")) {
                        day.add("Jeudi");
                    }
                    if (friday.getContents().matches("0")) {
                        day.add("Vendredi");
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(day);
                    textView1.setText(sb.toString().replace("[", "").replace("]", ""));
                    textView1.setTypeface(textTable);
                    textView.setTextSize(15);
                    textView1.setTextSize(15);
                    textView.setLayoutParams(params);
                    textView1.setLayoutParams(params);
                    tableRow.addView(textView);
                    tableRow.addView(textView1);
                    tableLayout.addView(tableRow);
                    day.clear();
                }
            }

        }catch (Exception e){}
    }
}
