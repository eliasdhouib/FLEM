package com.example.flem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import java.io.File;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DaySelectionActivity extends AppCompatActivity {

    CheckBox lundi,mardi,mercredi,jeudi,vendredi;
    TextView textViewName;
    Button valider;
    String id_ligne,id_team;
    int ligne;
    Typeface typeface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.days_selection);
        lundi = findViewById(R.id.checkBoxLundi);
        mardi = findViewById(R.id.checkBoxMardi);
        mercredi = findViewById(R.id.checkBoxMercredi);
        jeudi = findViewById(R.id.checkBoxJeudi);
        vendredi = findViewById(R.id.checkBoxVendredi);
        valider = findViewById(R.id.valider_jours);
        textViewName = findViewById(R.id.textViewUpdateDays);
        String text = textViewName.getText().toString();
        Intent intent = getIntent();
        id_ligne = intent.getStringExtra("id");
        typeface = ResourcesCompat.getFont(this,R.font.nunito_regular);
        textViewName.setTypeface(typeface);
        try {
            File updateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondUpdateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            Workbook wb = null;
            Sheet sh = null;
            if (updateFile.exists()) {
                wb = Workbook.getWorkbook(updateFile);
                sh = wb.getSheet(0);
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    Cell id = sh.getCell(0,i);
                    if (id.getContents().matches(id_ligne)){
                        ligne = id.getRow();
                    }
                }
                textViewName.setText(text+" "+sh.getCell(1,ligne).getContents());
                if (sh.getCell(11,ligne).getContents().contains("1")){
                    lundi.setChecked(true);
                }
                if (sh.getCell(12,ligne).getContents().matches("1")){
                    mardi.setChecked(true);
                }
                if (sh.getCell(13,ligne).getContents().matches("1")){
                    mercredi.setChecked(true);
                }
                if (sh.getCell(14,ligne).getContents().matches("1")){
                    jeudi.setChecked(true);
                }
                if (sh.getCell(15,ligne).getContents().matches("1")){
                    vendredi.setChecked(true);
                }
            } else if (secondUpdateFile.exists()) {
                wb = Workbook.getWorkbook(secondUpdateFile);
                sh = wb.getSheet(0);
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    Cell id = sh.getCell(0,i);
                    if (id.getContents().matches(id_ligne)){
                        ligne = id.getRow();
                    }
                }
                textViewName.setText(text+" "+sh.getCell(1,ligne).getContents());
                if (sh.getCell(11,ligne).getContents().contains("1")){
                    lundi.setChecked(true);
                }
                if (sh.getCell(12,ligne).getContents().matches("1")){
                    mardi.setChecked(true);
                }
                if (sh.getCell(13,ligne).getContents().matches("1")){
                    mercredi.setChecked(true);
                }
                if (sh.getCell(14,ligne).getContents().matches("1")){
                    jeudi.setChecked(true);
                }
                if (sh.getCell(15,ligne).getContents().matches("1")){
                    vendredi.setChecked(true);
                }
            }
        }catch (Exception e){}
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File copyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
                    File secondCopyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
                    if (secondCopyInitialFile.exists()){
                        Log.d("exists","Compagny-copy2");
                        Workbook wb = Workbook.getWorkbook(secondCopyInitialFile);
                        WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls"), wb);
                        WritableSheet workSheet = wwb.getSheet(0);
                        if (mardi.isChecked()) {
                            Label label = new Label(12,ligne,"1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(12, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (lundi.isChecked()) {
                            Label label = new Label(11, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(11, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (mercredi.isChecked()) {
                            Label label = new Label(13, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(13, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (jeudi.isChecked()) {
                            Label label = new Label(14, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(14, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (vendredi.isChecked()) {
                            Label label = new Label(15, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(15, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (lundi.isChecked()||mardi.isChecked()||mercredi.isChecked()||jeudi.isChecked()||vendredi.isChecked()){
                            Label label = new Label(9, ligne, "1");
                            Label label1 = new Label(10,ligne,"0");
                            workSheet.addCell(label);
                            workSheet.addCell(label1);
                        } else {
                            Label label = new Label(9, ligne, "0");
                            workSheet.addCell(label);
                        }
                        id_team = workSheet.getCell(8,ligne).getContents();
                        wwb.write();
                        wwb.close();
                        secondCopyInitialFile.delete();
                        Intent i = new Intent(getApplicationContext(),TimetableTeam.class);
                        i.putExtra("id_team",id_team);
                        startActivity(i);
                        finish();
                    }
                    else if (copyInitialFile.exists()){
                        Workbook wb = Workbook.getWorkbook(copyInitialFile);
                        WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls"), wb);
                        Log.d("exists","Compagny-copy");
                        WritableSheet workSheet = wwb.getSheet(0);
                        if (mardi.isChecked()) {
                            Label label = new Label(12, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(12, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (lundi.isChecked()) {
                            Label label = new Label(11, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(11, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (mercredi.isChecked()) {
                            Label label = new Label(13, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(13, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (jeudi.isChecked()) {
                            Label label = new Label(14, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(14, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (vendredi.isChecked()) {
                            Label label = new Label(15, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(15, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (lundi.isChecked()||mardi.isChecked()||mercredi.isChecked()||jeudi.isChecked()||vendredi.isChecked()){
                            Label label = new Label(9, ligne, "1");
                            Label label1 = new Label(10,ligne,"0");
                            workSheet.addCell(label);
                            workSheet.addCell(label1);
                        } else {
                            Label label = new Label(9, ligne, "0");
                            workSheet.addCell(label);
                        }
                        id_team = workSheet.getCell(8,ligne).getContents();
                        wwb.write();
                        wwb.close();
                        copyInitialFile.delete();
                        Intent i = new Intent(getApplicationContext(),TimetableTeam.class);
                        i.putExtra("id_team",id_team);
                        startActivity(i);
                        finish();
                    }
                    else{ //(!copyInitialFile.exists()&&!secondCopyInitialFile.exists()) {
                        Log.d("exists","Compagny");
                        AssetManager assetManager = getAssets();
                        InputStream inputStream = assetManager.open("Compagny.xls");
                        Workbook wb = Workbook.getWorkbook(inputStream);
                        WritableWorkbook wwb = Workbook.createWorkbook(new File(getExternalFilesDir(null) + "Compagny-copy.xls"), wb);
                        WritableSheet workSheet = wwb.getSheet(0);
                        if (mardi.isChecked()) {
                            Label label = new Label(12, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(12, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (lundi.isChecked()) {
                            Label label = new Label(11, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(11, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (mercredi.isChecked()) {
                            Label label = new Label(13, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(13, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (jeudi.isChecked()) {
                            Label label = new Label(14, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(14, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (vendredi.isChecked()) {
                            Label label = new Label(15, ligne, "1");
                            workSheet.addCell(label);
                        } else {
                            Label label = new Label(15, ligne, "0");
                            workSheet.addCell(label);
                        }
                        if (lundi.isChecked()||mardi.isChecked()||mercredi.isChecked()||jeudi.isChecked()||vendredi.isChecked()){
                            Label label = new Label(9, ligne, "1");
                            Label label1 = new Label(10,ligne,"0");
                            workSheet.addCell(label);
                            workSheet.addCell(label1);
                        } else {
                            Label label = new Label(9, ligne, "0");
                            workSheet.addCell(label);
                        }
                        wwb.write();
                        wwb.close();
                        Intent i = new Intent(getApplicationContext(),TimetableTeam.class);
                        startActivity(i);
                        finish();
                    }

                }catch (Exception e) {e.printStackTrace(); Log.d("Exception","Message "+e.toString());}

            }
        });
    }

}
