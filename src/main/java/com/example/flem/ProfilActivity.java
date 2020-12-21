package com.example.flem;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ProfilActivity extends AppCompatActivity {

    TextView textViewNom, textViewEmail, textViewFonction, textViewValidation, textViewBureau;
    Spinner spinnerJours;
    ImageView profil;
    String id;
    int id_ligne;
    ArrayList<String> days;
    ArrayList<StateVo> listVOs;
    Typeface typeface;
    int date;
    private String Document_img1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        days = new ArrayList<String>();
        date = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);
        profil = findViewById(R.id.imageViewProfil);
        typeface = ResourcesCompat.getFont(this,R.font.nunito_regular);
        textViewNom = findViewById(R.id.texViewNameFirst);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewFonction = findViewById(R.id.textViewFonction);
        spinnerJours = findViewById(R.id.textViewJours);
        String[] select_qualification = {"Mes jours de télétravail", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
        listVOs = new ArrayList<>();
        textViewValidation = findViewById(R.id.textViewValidation);
        textViewBureau = findViewById(R.id.textViewBureau);
        Intent intent = getIntent();
        id = intent.getStringExtra("id_employe");
        FicheEmploye(select_qualification);
        String path = "/sdcard/Android/data/com.example.flem/Pictures";
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().matches(id+".png")){
                String path2 = "/sdcard/Android/data/com.example.flem/Pictures/"+id+".png";
                Bitmap photo = BitmapFactory.decodeFile(path2);
                photo = getResizedBitmap(photo,400);
                profil.setBackground(null);
                profil.setImageBitmap(photo);
            }
        }
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void selectImage() {
        final CharSequence[] options = { "Prendre une photo", "Importer depuis la galerie","Annuler" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modifier la photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Prendre une photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Importer depuis la galerie")) {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Annuler")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap=getResizedBitmap(bitmap, 400);
                    profil.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = "/sdcard/Android/data/com.example.flem/Pictures/"+id+".png";
                    OutputStream outFile = null;
                    File file = new File(path);
                    profil.setBackground(null);
                    profil.setImageBitmap(bitmap);
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                profil.setBackground(null);
                profil.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
                try {
                    copy(new File(picturePath),new File("/sdcard/Android/data/com.example.flem/Pictures/"+id+".png"));
                }catch (Exception e){}

            }
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public void FicheEmploye(String[] select_qualification){
        Cell email=null,id_employe=null,nom=null,prenom=null,fonction=null,validation_manager=null,lundi=null,mardi=null,mercredi=null,jeudi=null,vendredi=null,bureau=null;
        Boolean presentiel=false;
        try {
            File updateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondUpdateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            if (updateFile.exists()){
                Workbook wb = Workbook.getWorkbook(updateFile);
                Sheet sh = wb.getSheet(0);
                Sheet sheet = wb.getSheet(1);
                int row = sh.getRows();
                int row2 = sheet.getRows();
                for (int i = 0; i < row; i++) {
                    id_employe = sh.getCell(0, i);
                    if (id_employe.getContents().contains(id)) {
                        id_ligne = id_employe.getRow();
                    }
                }
                switch (date){
                    case Calendar.FRIDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(5,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.THURSDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(4,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.WEDNESDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(3,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.TUESDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(2,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.MONDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(1,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                }
                nom = sh.getCell(1, id_ligne);
                prenom = sh.getCell(2, id_ligne);
                fonction = sh.getCell(3, id_ligne);
                validation_manager = sh.getCell(10, id_ligne);
                lundi = sh.getCell(11, id_ligne);
                mardi = sh.getCell(12, id_ligne);
                mercredi = sh.getCell(13, id_ligne);
                jeudi = sh.getCell(14, id_ligne);
                vendredi = sh.getCell(15, id_ligne);
                email = sh.getCell(5,id_ligne);

            } else if (secondUpdateFile.exists()){
                Workbook wb = Workbook.getWorkbook(secondUpdateFile);
                Sheet sh = wb.getSheet(0);
                Sheet sheet = wb.getSheet(1);
                int row2 = sheet.getRows();
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    id_employe = sh.getCell(0, i);
                    if (id_employe.getContents().contains(id)) {
                        id_ligne = id_employe.getRow();
                    }
                }
                switch (date){
                    case Calendar.FRIDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(5,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.THURSDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(4,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.WEDNESDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(3,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.TUESDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(2,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.MONDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet.getCell(1,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                }
                nom = sh.getCell(1, id_ligne);
                prenom = sh.getCell(2, id_ligne);
                fonction = sh.getCell(3, id_ligne);
                validation_manager = sh.getCell(10, id_ligne);
                lundi = sh.getCell(11, id_ligne);
                mardi = sh.getCell(12, id_ligne);
                mercredi = sh.getCell(13, id_ligne);
                jeudi = sh.getCell(14, id_ligne);
                vendredi = sh.getCell(15, id_ligne);
                email = sh.getCell(5,id_ligne);
            } else {
                AssetManager assetManager = getAssets();
                InputStream inputStream = assetManager.open("Compagny.xls");
                Workbook wb = Workbook.getWorkbook(inputStream);
                WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls"), wb);
                //WritableSheet sheet = wwb.getSheet(0);
                wwb.write();
                wwb.close();
                Sheet sh = wb.getSheet(0);
                Sheet sheet1 = wb.getSheet(1);
                int row = sh.getRows();
                int row2 = sheet1.getRows();
                for (int i = 0; i < row; i++) {
                    id_employe = sh.getCell(0, i);
                    if (id_employe.getContents().contains(id)) {
                        id_ligne = id_employe.getRow();
                    }
                }
                switch (date){
                    case Calendar.FRIDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet1.getCell(5,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet1.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.THURSDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet1.getCell(4,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet1.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.WEDNESDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet1.getCell(3,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet1.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.TUESDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet1.getCell(2,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet1.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                    case Calendar.MONDAY:
                        for (int j=0;j<row2;j++){
                            Cell office = sheet1.getCell(1,j);
                            if (office.getContents().matches(id)){
                                bureau = sheet1.getCell(0,j);
                                presentiel=true;
                            }
                        }
                        break;
                }
                nom = sh.getCell(1, id_ligne);
                prenom = sh.getCell(2, id_ligne);
                fonction = sh.getCell(3, id_ligne);
                validation_manager = sh.getCell(10, id_ligne);
                lundi = sh.getCell(11, id_ligne);
                mardi = sh.getCell(12, id_ligne);
                mercredi = sh.getCell(13, id_ligne);
                jeudi = sh.getCell(14, id_ligne);
                vendredi = sh.getCell(15, id_ligne);
                email = sh.getCell(5,id_ligne);
            }
        } catch (Exception e) {}

        if (lundi.getContents().matches("1")){
            days.add("Lundi");
        }
        if (mardi.getContents().matches("1")){
            days.add("Mardi");
        }
        if (mercredi.getContents().matches("1")){
            days.add("Mercredi");
        }
        if (jeudi.getContents().matches("1")){
            days.add("Jeudi");
        }
        if (vendredi.getContents().matches("1")){
            days.add("Vendredi");
        }
        for (int i = 0; i < select_qualification.length; i++) {
            StateVo stateVo = new StateVo();
            stateVo.setTitle(select_qualification[i]);
            if (days.contains(select_qualification[i])){
                stateVo.setSelected(true);
            }else {
                stateVo.setSelected(false);
            }
            listVOs.add(stateVo);
        }
        MyAdapter myAdapter = new MyAdapter(ProfilActivity.this, 0, listVOs,id_ligne,textViewValidation);
        spinnerJours.setAdapter(myAdapter);
        textViewNom.setText(nom.getContents()+" "+prenom.getContents());
        textViewNom.setTypeface(typeface);
        textViewFonction.setText(fonction.getContents());
        textViewFonction.setTypeface(typeface);
        textViewEmail.setText(email.getContents());
        textViewEmail.setTypeface(typeface);
        String text = textViewValidation.getText().toString();
        if (validation_manager.getContents().matches("0")){
            textViewValidation.setText(text+" Non");
        }else {
            textViewValidation.setText(text+" Oui");
        }
        textViewValidation.setTypeface(typeface);
        String nBureau = textViewBureau.getText().toString();
        if (presentiel==true) {
            textViewBureau.setText(nBureau + " " + bureau.getContents());
        }else {
            textViewBureau.setText(nBureau + " Distanciel");
        }
        textViewBureau.setTypeface(typeface);
    }
}
