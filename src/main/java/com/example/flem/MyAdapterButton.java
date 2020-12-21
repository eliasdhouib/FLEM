package com.example.flem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class MyAdapterButton extends ArrayAdapter<StateVo> {
    private Context mContext;
    private ArrayList<StateVo> listState;
    private MyAdapterButton myAdapterButton;
    private boolean isFromView = false;
    private ArrayList<Integer> checked;
    String name;
    ArrayList<String> id_ligne;
    Typeface typeface;

    public MyAdapterButton(Context context, int resource, List<StateVo> objects,ArrayList<String> id_ligne) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<StateVo>) objects;
        this.myAdapterButton = this;
        checked = new ArrayList<Integer>();
        this.id_ligne=(ArrayList<String>) id_ligne;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position+1,convertView,parent);
    }

    public int getCount(){
        return listState.size()-1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_button, null);
            holder = new ViewHolder();
            holder.mButton = (Button) convertView
                    .findViewById(R.id.button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        typeface = ResourcesCompat.getFont(mContext,R.font.nunito_regular);
        holder.mButton.setText(listState.get(position).getTitle());
        holder.mButton.setTypeface(typeface);

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mButton.setClickable(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mButton.setVisibility(View.INVISIBLE);
        } else {
            holder.mButton.setVisibility(View.VISIBLE);
        }
        holder.mButton.setTag(position);
        final View finalConvertView = convertView;
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = listState.get(position).getTitle();
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
                            Cell nameFile = sh.getCell(1, i);
                            Cell idFile = sh.getCell(0, i);
                            Cell idTeamFile = sh.getCell(5,i);
                            if (nameFile.getContents().matches(name)) {
                                if (id_ligne.contains(idFile.getContents())) {
                                    Intent intent = new Intent(getContext(), DaySelectionActivity.class);
                                    intent.putExtra("id", idFile.getContents());
                                    intent.putExtra("id_team",idTeamFile.getContents());
                                    finalConvertView.getContext().startActivity(intent);
                                    ((Activity)getContext()).finish();
                                }
                            }


                        }
                    } else if (secondUpdateFile.exists()) {
                        wb = Workbook.getWorkbook(secondUpdateFile);
                        sh = wb.getSheet(0);
                        int row = sh.getRows();
                        for (int i = 0; i < row; i++) {
                            Cell nameFile = sh.getCell(1, i);
                            Cell idFile = sh.getCell(0, i);
                            Cell idTeamFile = sh.getCell(5,i);
                            if (nameFile.getContents().matches(name)) {
                                if (id_ligne.contains(idFile.getContents())) {
                                    Intent intent = new Intent(getContext(), DaySelectionActivity.class);
                                    intent.putExtra("id", idFile.getContents());
                                    intent.putExtra("id_team",idTeamFile.getContents());
                                    finalConvertView.getContext().startActivity(intent);
                                    ((Activity)getContext()).finish();
                                }
                            }


                        }
                    }
                }catch (Exception e){}

            }
        });
        return convertView;
    }


    private class ViewHolder {
        private Button mButton;
    }
}
