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
import android.util.Log;
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

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ManagerActivity extends AppCompatActivity {

    TextView textViewNom, textViewFonction, textViewEmail;
    Spinner spinnerJours;
    String id,team_id;
    int id_ligne;
    ArrayList<String> days;
    ArrayList<StateVo> listVOs;
    Typeface typeface;
    ImageView profil;
    private String Document_img1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_manager);
        days = new ArrayList<String>();
        textViewNom = findViewById(R.id.texViewNameFirstManager);
        textViewFonction = findViewById(R.id.textViewFonctionManager);
        textViewEmail = findViewById(R.id.textViewEmailManager);
        spinnerJours = findViewById(R.id.textViewJoursManager);
        profil = findViewById(R.id.imageViewProfilManager);
        typeface = ResourcesCompat.getFont(this,R.font.nunito_regular);
        String[] select_qualification = {"Mes jours", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
        listVOs = new ArrayList<>();
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
                Log.d("path of image from gallery", "picture "+picturePath);
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

    public void FicheEmploye(String[] select_qualification) {
        Cell email=null,id_team=null,id_employe = null, nom = null, prenom = null, fonction = null, validation_manager = null, lundi = null, mardi = null, mercredi = null, jeudi = null, vendredi = null;

        try {
            File updateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
            File secondUpdateFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
            if (updateFile.exists()) {
                Workbook wb = Workbook.getWorkbook(updateFile);
                Sheet sh = wb.getSheet(0);
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    id_employe = sh.getCell(0, i);
                    if (id_employe.getContents().contains(id)) {
                        id_ligne = id_employe.getRow();
                    }
                }
                nom = sh.getCell(1, id_ligne);
                prenom = sh.getCell(2, id_ligne);
                fonction = sh.getCell(3, id_ligne);
                validation_manager = sh.getCell(7, id_ligne);
                lundi = sh.getCell(11, id_ligne);
                mardi = sh.getCell(12, id_ligne);
                mercredi = sh.getCell(13, id_ligne);
                jeudi = sh.getCell(14, id_ligne);
                vendredi = sh.getCell(15, id_ligne);
                id_team = sh.getCell(8,id_ligne);
                email = sh.getCell(5,id_ligne);

            } else if (secondUpdateFile.exists()) {
                Workbook wb = Workbook.getWorkbook(secondUpdateFile);
                Sheet sh = wb.getSheet(0);
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    id_employe = sh.getCell(0, i);
                    if (id_employe.getContents().contains(id)) {
                        id_ligne = id_employe.getRow();
                    }
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
                id_team = sh.getCell(8,id_ligne);
                email = sh.getCell(5,id_ligne);
            } else {
                AssetManager assetManager = getAssets();
                InputStream inputStream = assetManager.open("Compagny.xls");
                Workbook wb = Workbook.getWorkbook(inputStream);
                Sheet sh = wb.getSheet(0);
                int row = sh.getRows();
                for (int i = 0; i < row; i++) {
                    id_employe = sh.getCell(0, i);
                    if (id_employe.getContents().contains(id)) {
                        id_ligne = id_employe.getRow();
                    }
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
                id_team = sh.getCell(8,id_ligne);
                email = sh.getCell(5,id_ligne);
            }
        } catch (Exception e) {}

        if (lundi.getContents().matches("1")) {
            days.add("Lundi");
            Log.d("test", "AddLundi");
        }
        if (mardi.getContents().matches("1")) {
            days.add("Mardi");
            Log.d("test", "AddMardi");
        }
        if (mercredi.getContents().matches("1")) {
            days.add("Mercredi");
        }
        if (jeudi.getContents().matches("1")) {
            days.add("Jeudi");
        }
        if (vendredi.getContents().matches("1")) {
            days.add("Vendredi");
        }
        team_id = id_team.getContents();
        for (int i = 0; i < select_qualification.length; i++) {
            StateVo stateVo = new StateVo();
            stateVo.setTitle(select_qualification[i]);
            if (days.contains(select_qualification[i])){
                Log.d("contains","contains");
                stateVo.setSelected(true);
            }else {
                stateVo.setSelected(false);
                Log.d("contains","contains2");
            }
            listVOs.add(stateVo);
        }
        MyAdapter myAdapter = new MyAdapter(ManagerActivity.this, 0, listVOs,id_ligne,new TextView(this));
        spinnerJours.setAdapter(myAdapter);
        textViewNom.setText(nom.getContents()+" "+prenom.getContents());
        textViewNom.setTypeface(typeface);
        textViewFonction.setText(fonction.getContents());
        textViewFonction.setTypeface(typeface);
        textViewEmail.setText(email.getContents());
        textViewEmail.setTypeface(typeface);
    }

    public void viewTeamDays(View v){
        Intent intent = new Intent(getApplicationContext(),TimetableTeam.class);
        intent.putExtra("id_team",team_id);
        startActivity(intent);
        finish();
    }

    public void viewDesktop(View v){
        Intent intent = new Intent(getApplicationContext(),DesktopActivity.class);
        startActivity(intent);
        finish();
    }
}
