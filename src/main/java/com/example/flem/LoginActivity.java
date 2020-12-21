package com.example.flem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class LoginActivity extends AppCompatActivity {

    TextView title, emailLogin, passwordLogin;
    Button buttonConnexion;
    ArrayList<String> email, password, id_employe;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        title = findViewById(R.id.textViewTitle);
        buttonConnexion = findViewById(R.id.buttonConnexion);
        emailLogin = findViewById(R.id.editTextEmailLogin);
        passwordLogin = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar1);
        email = new ArrayList<String>();
        password = new ArrayList<String>();
        id_employe = new ArrayList<String>();
        Typeface typeface = ResourcesCompat.getFont(this,R.font.sansita);
        Typeface typeface1 = ResourcesCompat.getFont(this,R.font.nunito_regular);
        title.setTypeface(typeface);
        buttonConnexion.setTypeface(typeface1);
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    Cell mail=null, pass=null, id=null, role=null;
                    AssetManager assetManager = getAssets();
                    InputStream inputStream = assetManager.open("Compagny.xls");
                    Workbook workbook = Workbook.getWorkbook(inputStream);
                    Sheet sheet = workbook.getSheet(0);
                    int row = sheet.getRows();
                    for (int i=0;i<row;i++){
                        mail = sheet.getCell(5,i);
                        pass = sheet.getCell(6,i);
                        id = sheet.getCell(0,i);
                        role = sheet.getCell(7,i);
                        if (mail.getContents().matches(emailLogin.getText().toString())&&pass.getContents().matches(passwordLogin.getText().toString())){
                            if (role.getContents().matches("1")){
                                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                                intent.putExtra("id_employe",id.getContents());
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                                intent.putExtra("id_employe",id.getContents());
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Email ou mot de passe invalide",Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e){}
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
