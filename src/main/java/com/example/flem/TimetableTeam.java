package com.example.flem;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import java.io.File;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class TimetableTeam extends AppCompatActivity {

    TableLayout timetable;
    TableRow tmonday,ttusday,twednesday,tthursday,tfriday;
    TextView textmonday, texttuesday, textwednesday, textthursday, textfriday;
    String id_team;
    ArrayList<Integer> id_row;
    ArrayList<String> id_employe,id_employe_day;
    ArrayList<StateVo> listVOs;
    Spinner spinerTimeTable;
    MyAdapterButton myAdapterButton;
    ArrayList<String> spinnerArray;
    ArrayList<Spinner> spinnerList;
    ArrayList<String> duplicateMonday;
    ArrayList<String> duplicateTuesday;
    ArrayList<String> duplicateWednesday;
    ArrayList<String> duplicateThursday;
    ArrayList<String> duplicateFriday;
    ImageView valider;
    Typeface titleDays, buttonValidate;
    boolean data;
    boolean data2;
    private TimetableTeam timetableTeam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_team);
        this.timetableTeam=this;
        data=false;
        data2=false;
        titleDays = ResourcesCompat.getFont(this,R.font.nunito_blackitalic);
        buttonValidate = ResourcesCompat.getFont(this,R.font.nunito_regular);
        timetable = findViewById(R.id.tableLayoutTimetable);
        tmonday = findViewById(R.id.tableRowMonday);
        ttusday = findViewById(R.id.tableRowTuesday);
        twednesday = findViewById(R.id.tableRowWednesday);
        tthursday = findViewById(R.id.tableRowThursday);
        tfriday = findViewById(R.id.tableRowFriday);
        textmonday = findViewById(R.id.textViewTimetableMonday);
        texttuesday = findViewById(R.id.textViewTimetableTuesday);
        textwednesday = findViewById(R.id.textViewTimetableWednesday);
        textthursday = findViewById(R.id.textViewTimetableThursday);
        textfriday = findViewById(R.id.textViewTimetableFriday);
        textmonday.setTypeface(titleDays);
        texttuesday.setTypeface(titleDays);
        textwednesday.setTypeface(titleDays);
        textthursday.setTypeface(titleDays);
        textfriday.setTypeface(titleDays);
        spinerTimeTable = findViewById(R.id.spinnerTimetable);
        valider = findViewById(R.id.imageViewValidateTimetable);
        Intent intent = getIntent();
        id_team = intent.getStringExtra("id_team");
        id_row = new ArrayList<Integer>();
        listVOs = new ArrayList<StateVo>();
        id_employe = new ArrayList<String>();
        id_employe_day = new ArrayList<String>();
        spinnerArray = new ArrayList<String>();
        spinnerList = new ArrayList<Spinner>();
        duplicateMonday = new ArrayList<String>();
        duplicateTuesday = new ArrayList<String>();
        duplicateWednesday = new ArrayList<String>();
        duplicateThursday = new ArrayList<String>();
        duplicateFriday = new ArrayList<String>();
        spinnerArray.add("Choisir bureau");
        try {
            File updateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondUpdateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            Workbook wb=null;
            Sheet sh=null;
            if (updateFile.exists()) {
                wb = Workbook.getWorkbook(updateFile);
                sh = wb.getSheet(1);
                int row = sh.getRows();
                for (int i = 2; i < row; i++) {
                    spinnerArray.add(sh.getCell(0,i).getContents());
                }
            } else if (secondUpdateFile.exists()) {
                wb = Workbook.getWorkbook(secondUpdateFile);
                sh = wb.getSheet(1);
                int row = sh.getRows();
                for (int i = 2; i < row; i++) {
                    spinnerArray.add(sh.getCell(0,i).getContents());
                }
            }
        }catch (Exception e){}

        try {
            Cell id=null,name=null, firs_name=null, office=null, monday=null, tuesday=null, wednesday=null, thursday=null, friday=null, role=null;
            File updateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondUpdateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            Workbook wb=null;
            Sheet sh=null;
            Sheet sh1=null;
            if (updateFile.exists()) {
                wb = Workbook.getWorkbook(updateFile);
                sh = wb.getSheet(0);
                sh1 = wb.getSheet(1);
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    Cell row_position = sh.getCell(8,i);
                    Cell id_position = sh.getCell(0,i);
                    if (row_position.getContents().matches(id_team)){
                        id_row.add(row_position.getRow());
                        id_employe.add(id_position.getContents());
                    }
                }
            } else if (secondUpdateFile.exists()) {
                wb = Workbook.getWorkbook(secondUpdateFile);
                sh = wb.getSheet(0);
                sh1 = wb.getSheet(1);
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    Cell row_position = sh.getCell(8,i);
                    Cell id_position = sh.getCell(0,i);
                    if (row_position.getContents().matches(id_team)){
                        id_row.add(row_position.getRow());
                        id_employe.add(id_position.getContents());
                    }
                }
            }
            int row = sh1.getRows();
            ArrayList<String> select_employe = new ArrayList<String>();
            select_employe.add("");
            for (int i=0;i<id_row.size();i++){
                id = sh.getCell(0,id_row.get(i));
                name = sh.getCell(1,id_row.get(i));
                firs_name = sh.getCell(2,id_row.get(i));
                office = sh.getCell(16,id_row.get(i));
                monday = sh.getCell(11,id_row.get(i));
                tuesday = sh.getCell(12,id_row.get(i));
                wednesday = sh.getCell(13,id_row.get(i));
                thursday = sh.getCell(14,id_row.get(i));
                friday = sh.getCell(15,id_row.get(i));
                role = sh.getCell(7,id_row.get(i));
                if (!role.getContents().matches("0")){
                    select_employe.add(name.getContents());
                }
                if (!monday.getContents().matches("0")||!tuesday.getContents().matches("0")||!wednesday.getContents().matches("0")||!thursday.getContents().matches("0")||!friday.getContents().matches("0")){
                    if (!role.getContents().matches("0")){
                        id_employe_day.add(id.getContents());
                    }
                }
                if (monday.getContents().matches("1")){
                    TableRow tableRow = new TableRow(this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    TextView n = new TextView(this);
                    if (role.getContents().matches("0")){
                        TextView off = new TextView(this);
                        off.setText("Bureau fixe");
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        tableRow.addView(off);
                    }else {
                        Spinner off = new Spinner(this);
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                        off.setAdapter(spinnerArrayAdapter);
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        off.setId(Integer.parseInt(id.getContents()));
                        for (int j = 0; j < row; j++) {
                            if (id.getContents().matches(sh1.getCell(1, j).getContents())) {
                                int position = spinnerArray.indexOf(sh1.getCell(0, j).getContents());
                                off.setSelection(position);
                                break;
                            } else {
                                off.setSelection(0);
                            }
                        }
                        tableRow.addView(off);
                        spinnerList.add(off);
                    }
                    timetable.addView(tableRow,timetable.indexOfChild(tmonday)+1);
                }
                if (tuesday.getContents().matches("1")){
                    TableRow tableRow = new TableRow(this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    TextView n = new TextView(this);
                    if (role.getContents().matches("0")){
                        TextView off = new TextView(this);
                        off.setText("Bureau fixe");
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        tableRow.addView(off);
                    }else {
                        Spinner off = new Spinner(this);
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                        off.setAdapter(spinnerArrayAdapter);
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        off.setId(Integer.parseInt(id.getContents()));
                        for (int j = 0; j < row; j++) {
                            if (id.getContents().matches(sh1.getCell(2, j).getContents())) {
                                int position = spinnerArray.indexOf(sh1.getCell(0, j).getContents());
                                off.setSelection(position);
                                break;
                            } else {
                                off.setSelection(0);
                            }
                        }
                        tableRow.addView(off);
                        spinnerList.add(off);
                    }
                    timetable.addView(tableRow,timetable.indexOfChild(ttusday)+1);
                }
                if (wednesday.getContents().matches("1")){
                    TableRow tableRow = new TableRow(this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    TextView n = new TextView(this);
                    if (role.getContents().matches("0")){
                        TextView off = new TextView(this);
                        off.setText("Bureau fixe");
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        tableRow.addView(off);
                    }else {
                        Spinner off = new Spinner(this);
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                        off.setAdapter(spinnerArrayAdapter);
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        off.setId(Integer.parseInt(id.getContents()));
                        for (int j = 0; j < row; j++) {
                            if (id.getContents().matches(sh1.getCell(3, j).getContents())) {
                                int position = spinnerArray.indexOf(sh1.getCell(0, j).getContents());
                                off.setSelection(position);
                                break;
                            } else {
                                off.setSelection(0);
                            }
                        }
                        tableRow.addView(off);
                        spinnerList.add(off);
                    }
                    timetable.addView(tableRow,timetable.indexOfChild(twednesday)+1);
                }
                if (thursday.getContents().matches("1")){
                    TableRow tableRow = new TableRow(this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    TextView n = new TextView(this);
                    if (role.getContents().matches("0")){
                        TextView off = new TextView(this);
                        off.setText("Bureau fixe");
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        tableRow.addView(off);
                    }else {
                        Spinner off = new Spinner(this);
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                        off.setAdapter(spinnerArrayAdapter);
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        off.setId(Integer.parseInt(id.getContents()));
                        for (int j = 0; j < row; j++) {
                            if (id.getContents().matches(sh1.getCell(4, j).getContents())) {
                                int position = spinnerArray.indexOf(sh1.getCell(0, j).getContents());
                                off.setSelection(position);
                                break;
                            } else {
                                off.setSelection(0);
                            }
                        }
                        tableRow.addView(off);
                        spinnerList.add(off);
                    }
                    timetable.addView(tableRow,timetable.indexOfChild(tthursday)+1);
                }
                if (friday.getContents().matches("1")){
                    TableRow tableRow = new TableRow(this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    TextView n = new TextView(this);
                    if (role.getContents().matches("0")){
                        TextView off = new TextView(this);
                        off.setText("Bureau fixe");
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        tableRow.addView(off);
                    }else {
                        Spinner off = new Spinner(this);
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                        off.setAdapter(spinnerArrayAdapter);
                        n.setText(name.getContents() + " " + firs_name.getContents());
                        tableRow.addView(n);
                        off.setId(Integer.parseInt(id.getContents()));
                        for (int j = 0; j < row; j++) {
                            if (id.getContents().matches(sh1.getCell(5, j).getContents())) {
                                int position = spinnerArray.indexOf(sh1.getCell(0, j).getContents());
                                off.setSelection(position);
                                break;
                            } else {
                                off.setSelection(0);
                            }
                        }
                        tableRow.addView(off);
                        spinnerList.add(off);
                    }
                    timetable.addView(tableRow,timetable.indexOfChild(tfriday)+1);
                }
            }
            for (int j = 0; j < select_employe.size(); j++) {
                StateVo stateVo = new StateVo();
                stateVo.setTitle(select_employe.get(j));
                listVOs.add(stateVo);
            }
            myAdapterButton = new MyAdapterButton(TimetableTeam.this, 0, listVOs,id_employe);
            spinerTimeTable.setAdapter(myAdapterButton);
        }catch (Exception e){}
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data=false;
                data2=false;
                for (int i = 0; i < spinnerList.size(); i++) {
                    if (spinnerList.get(i).getSelectedItem().toString().matches(spinnerArray.get(0))) {
                        Toast.makeText(getApplicationContext(), "Une ou des personne(s) n'ont pas été affectées à un bureau", Toast.LENGTH_LONG).show();
                        data2 = true;
                    }
                }
                try {
                    File copyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
                    File secondCopyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
                    if (secondCopyInitialFile.exists()) {
                        Workbook wb = Workbook.getWorkbook(secondCopyInitialFile);
                        WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls"), wb);
                        WritableSheet workSheet = wwb.getSheet(1);
                        int rowSheet = workSheet.getRows();
                        int columnSheet = workSheet.getColumns();
                        for (int k = 2; k < rowSheet; k++) {
                            for (int s = 2; s < columnSheet; s++) {
                                Label label = new Label(s, k, "0");
                                workSheet.addCell(label);
                            }
                        }
                        wwb.write();
                        wwb.close();
                        secondCopyInitialFile.delete();
                    } else if (copyInitialFile.exists()) {
                        Workbook wb = Workbook.getWorkbook(copyInitialFile);
                        WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls"), wb);
                        WritableSheet workSheet = wwb.getSheet(1);
                        int rowSheet = workSheet.getRows();
                        int columnSheet = workSheet.getColumns();
                        for (int k = 2; k < rowSheet; k++) {
                            for (int s = 2; s < columnSheet; s++) {
                                Label label = new Label(s, k, "0");
                                workSheet.addCell(label);
                            }
                        }
                        wwb.write();
                        wwb.close();
                        copyInitialFile.delete();
                    }
                } catch (Exception e) {
                }
                if (!(timetable.indexOfChild(tmonday) + 1 == timetable.indexOfChild(ttusday))) {
                    for (int d = timetable.indexOfChild(tmonday) + 1; d < timetable.indexOfChild(ttusday); d++) {
                        View child = timetable.getChildAt(d);
                        if (child instanceof TableRow) {
                            TableRow row = (TableRow) child;
                            View object = row.getVirtualChildAt(1);
                            if (object instanceof Spinner) {
                                Spinner spinner = (Spinner) row.getChildAt(1);
                                if (duplicateMonday != null && duplicateMonday.size() > 0) {
                                    if (duplicateMonday.contains(spinner.getSelectedItem().toString())) {
                                        Toast.makeText(getApplicationContext(), "Une ou plusieurs personnes sont affectées à un même bureau le lundi", Toast.LENGTH_LONG).show();
                                        break;
                                    } else {
                                        data = false;
                                        duplicateMonday.add(spinner.getSelectedItem().toString());
                                        WriteOffice("Monday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                    }
                                } else {
                                    duplicateMonday.add(spinner.getSelectedItem().toString());
                                    WriteOffice("Monday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                }
                            }

                        }

                    }
                }
                if (!(timetable.indexOfChild(ttusday) + 1 == timetable.indexOfChild(twednesday))) {
                    for (int d = timetable.indexOfChild(ttusday) + 1; d < timetable.indexOfChild(twednesday); d++) {
                        View child = timetable.getChildAt(d);
                        if (child instanceof TableRow) {
                            TableRow row = (TableRow) child;
                            View object = row.getVirtualChildAt(1);
                            if (object instanceof Spinner) {
                                Spinner spinner = (Spinner) row.getChildAt(1);
                                if (duplicateTuesday != null && duplicateTuesday.size() > 0) {
                                    Log.d("duplicateTuesday", "tuesday " + spinner.getSelectedItem().toString());
                                    if (duplicateTuesday.contains(spinner.getSelectedItem().toString())) {
                                        Toast.makeText(getApplicationContext(), "Une ou plusieurs personnes sont affectées à un même bureau le mardi", Toast.LENGTH_LONG).show();
                                        break;
                                    } else {
                                        data = false;
                                        duplicateTuesday.add(spinner.getSelectedItem().toString());
                                        WriteOffice("Tuesday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                    }
                                } else {
                                    duplicateTuesday.add(spinner.getSelectedItem().toString());
                                    WriteOffice("Tuesday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                }
                            }
                        }

                    }
                }
                if (!(timetable.indexOfChild(twednesday) + 1 == timetable.indexOfChild(tthursday))) {
                    for (int d = timetable.indexOfChild(twednesday) + 1; d < timetable.indexOfChild(tthursday); d++) {
                        View child = timetable.getChildAt(d);
                        if (child instanceof TableRow) {
                            TableRow row = (TableRow) child;
                            View object = row.getVirtualChildAt(1);
                            if (object instanceof Spinner) {
                                Spinner spinner = (Spinner) row.getChildAt(1);
                                if (duplicateWednesday != null && duplicateWednesday.size() > 0) {
                                    if (duplicateWednesday.contains(spinner.getSelectedItem().toString())) {
                                        Toast.makeText(getApplicationContext(), "Une ou plusieurs personnes sont affectées à un même bureau le mercredi", Toast.LENGTH_LONG).show();
                                        break;
                                    } else {
                                        data = false;
                                        duplicateWednesday.add(spinner.getSelectedItem().toString());
                                        WriteOffice("Wednesday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                    }
                                } else {
                                    duplicateWednesday.add(spinner.getSelectedItem().toString());
                                    WriteOffice("Wednesday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                }
                            }
                        }

                    }
                }
                if (!(timetable.indexOfChild(tthursday) + 1 == timetable.indexOfChild(tfriday))) {
                    for (int d = timetable.indexOfChild(tthursday) + 1; d < timetable.indexOfChild(tfriday); d++) {
                        View child = timetable.getChildAt(d);
                        if (child instanceof TableRow) {
                            TableRow row = (TableRow) child;
                            View object = row.getVirtualChildAt(1);
                            if (object instanceof Spinner) {
                                Spinner spinner = (Spinner) row.getChildAt(1);
                                if (duplicateThursday != null && duplicateThursday.size() > 0) {
                                    if (duplicateMonday.contains(spinner.getSelectedItem().toString())) {
                                        Toast.makeText(getApplicationContext(), "Une ou plusieurs personnes sont affectées à un même bureau le jeudi", Toast.LENGTH_LONG).show();
                                        break;
                                    } else {
                                        data = false;
                                        duplicateThursday.add(spinner.getSelectedItem().toString());
                                        WriteOffice("Thursday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                    }
                                } else {
                                    duplicateThursday.add(spinner.getSelectedItem().toString());
                                    WriteOffice("Thursday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                }
                            }
                        }

                    }
                }
                if (!(timetable.indexOfChild(tfriday) == timetable.getChildCount())) {
                    for (int d = timetable.indexOfChild(tfriday) + 1; d < timetable.getChildCount(); d++) {
                        View child = timetable.getChildAt(d);
                        if (child instanceof TableRow) {
                            TableRow row = (TableRow) child;
                            View object = row.getVirtualChildAt(1);
                            if (object instanceof Spinner) {
                                Spinner spinner = (Spinner) row.getChildAt(1);
                                if (duplicateFriday != null && duplicateFriday.size() > 0) {
                                    if (duplicateFriday.contains(spinner.getSelectedItem().toString())) {
                                        Toast.makeText(getApplicationContext(), "Une ou plusieurs personnes sont affectées à un même bureau le vendredi", Toast.LENGTH_LONG).show();
                                        break;
                                    } else {
                                        data = false;
                                        duplicateFriday.add(spinner.getSelectedItem().toString());
                                        WriteOffice("Friday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                    }
                                } else {
                                    duplicateFriday.add(spinner.getSelectedItem().toString());
                                    WriteOffice("Friday", spinner.getSelectedItem().toString(), String.valueOf(spinner.getId()));
                                }
                            }
                        }

                    }
                }
                if (data2==false) {
                    validation();
                    Intent intent = new Intent(getApplicationContext(),ManagerActivity.class);
                    intent.putExtra("id_employe","89");
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void WriteOffice(String day, String office, String id){
        try {
            File copyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondCopyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            if (secondCopyInitialFile.exists()) {
                Workbook wb = Workbook.getWorkbook(secondCopyInitialFile);
                WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls"), wb);
                WritableSheet workSheet = wwb.getSheet(1);
                //Sheet sheet = wb.getSheet(1);
                int row = workSheet.getRows();
                int rowId=0;
                for (int i=0;i<row;i++) {
                    Cell rowOffice = workSheet.getCell(0, i);
                    if (rowOffice.getContents().matches(office)) {
                        rowId = rowOffice.getRow();
                    }
                }
                if (day.matches("Monday")){
                    Label label = new Label(1,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Tuesday")){
                    Label label = new Label(2,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Wednesday")){
                    Label label = new Label(3,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Thursday")){
                    Label label = new Label(4,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Friday")){
                    Label label = new Label(5,rowId,id);
                    workSheet.addCell(label);
                }
                wwb.write();
                wwb.close();
                secondCopyInitialFile.delete();
            }else if (copyInitialFile.exists()) {
                Workbook wb = Workbook.getWorkbook(copyInitialFile);
                WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls"), wb);
                WritableSheet workSheet = wwb.getSheet(1);
                int row = workSheet.getRows();
                int rowId=0;
                for (int i=0;i<row;i++) {
                    Cell rowOffice = workSheet.getCell(0, i);
                    if (rowOffice.getContents().matches(office)) {
                        rowId = rowOffice.getRow();

                    }
                }
                if (day.matches("Monday")){
                    Label label = new Label(1,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Tuesday")){
                    Label label = new Label(2,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Wednesday")){
                    Label label = new Label(3,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Thursday")){
                    Label label = new Label(4,rowId,id);
                    workSheet.addCell(label);
                }
                if (day.matches("Friday")){
                    Label label = new Label(5,rowId,id);
                    workSheet.addCell(label);
                }
                wwb.write();
                wwb.close();
                copyInitialFile.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("exception","e "+e);
        }
    }

    public void validation(){
        try {
            File updateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondUpdateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            if (updateFile.exists()) {
                Workbook wb = Workbook.getWorkbook(updateFile);
                WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls"), wb);
                WritableSheet workSheet = wwb.getSheet(0);
                int row = workSheet.getRows();
                for (int i=2;i<row;i++){
                    Cell id = workSheet.getCell(0,i);
                    if (id.getContents().isEmpty()){
                        break;
                    }
                    if (id_employe_day.contains(id.getContents())){
                        Label label = new Label(10,i,"1");
                        workSheet.addCell(label);
                    }else {
                        Label label = new Label(10,i,"0");
                        workSheet.addCell(label);
                    }
                }
                wwb.write();
                wwb.close();
            } else if (secondUpdateFile.exists()) {
                Workbook wb = Workbook.getWorkbook(secondUpdateFile);
                WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls"), wb);
                WritableSheet workSheet = wwb.getSheet(0);
                int row = workSheet.getRows();
                for (int i=2;i<row;i++){
                    Cell id = workSheet.getCell(0,i);
                    if (id.getContents().isEmpty()){
                        break;
                    }
                    if (id_employe_day.contains(id.getContents())){
                        Label label = new Label(10,i,"1");
                        workSheet.addCell(label);
                    }else {
                        Label label = new Label(10,i,"0");
                        workSheet.addCell(label);
                    }
                }
                wwb.write();
                wwb.close();
            }
        }catch (Exception e){}
    }
}
