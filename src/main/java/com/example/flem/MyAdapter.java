package com.example.flem;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MyAdapter extends ArrayAdapter<StateVo> {

    private Context mContext;
    private ArrayList<StateVo> listState;
    private MyAdapter myAdapter;
    private boolean isFromView = false;
    private int id_ligne = 0;
    private ArrayList<Integer> checked;
    Typeface typeface;
    private TextView textView;

    public MyAdapter(Context context, int resource, List<StateVo> objects, int id_ligne, TextView textView) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<StateVo>) objects;
        this.myAdapter = this;
        this.id_ligne = id_ligne;
        this.textView = textView;
        checked = new ArrayList<Integer>();
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
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
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.textButton);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        typeface = ResourcesCompat.getFont(mContext,R.font.nunito_regular);
        holder.mTextView.setText(listState.get(position).getTitle());
        holder.mTextView.setTypeface(typeface);

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    for (int i=0;i<listState.size();i++){
                        if (listState.get(i).isSelected()==true){
                            if (!checked.contains(i)) {
                                checked.add(i);
                            }
                        }else {
                            if (checked.contains(i)){
                                checked.remove(checked.indexOf(i));
                            }
                        }
                    }
                    try {
                        File copyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls");
                        File secondCopyInitialFile = new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls");
                        if (secondCopyInitialFile.exists()) {
                            Workbook wb = Workbook.getWorkbook(secondCopyInitialFile);
                            WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy.xls"), wb);
                            WritableSheet workSheet = wwb.getSheet(0);
                            if (checked.contains(2)) {
                                Label label = new Label(12, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(12, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(1)) {
                                Label label = new Label(11, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(11, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(3)) {
                                Label label = new Label(13, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(13, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(4)) {
                                Label label = new Label(14, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(14, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(5)) {
                                Label label = new Label(15, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(15, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(1) || checked.contains(2) || checked.contains(3) || checked.contains(4) || checked.contains(5)) {
                                Label label = new Label(9, id_ligne, "1");
                                Label label1 = new Label(10, id_ligne, "0");
                                workSheet.addCell(label);
                                workSheet.addCell(label1);
                            } else {
                                Label label = new Label(9, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            wwb.write();
                            wwb.close();
                            secondCopyInitialFile.delete();
                            textView.setText("Validation par le manager: Non");
                        } else if (copyInitialFile.exists()) {
                            Workbook wb = Workbook.getWorkbook(copyInitialFile);
                            WritableWorkbook wwb = Workbook.createWorkbook(new File("/sdcard/Android/data/com.example.flem/filesCompagny-copy2.xls"), wb);
                            WritableSheet workSheet = wwb.getSheet(0);
                            if (checked.contains(2)) {
                                Label label = new Label(12, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(12, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(1)) {
                                Label label = new Label(11, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(11, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(3)) {
                                Label label = new Label(13, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(13, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(4)) {
                                Label label = new Label(14, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(14, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(5)) {
                                Label label = new Label(15, id_ligne, "1");
                                workSheet.addCell(label);
                            } else {
                                Label label = new Label(15, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            if (checked.contains(1) || checked.contains(2) || checked.contains(3) || checked.contains(4) || checked.contains(5)) {
                                Label label = new Label(9, id_ligne, "1");
                                Label label1 = new Label(10, id_ligne, "0");
                                workSheet.addCell(label);
                                workSheet.addCell(label1);
                            } else {
                                Label label = new Label(9, id_ligne, "0");
                                workSheet.addCell(label);
                            }
                            wwb.write();
                            wwb.close();
                            copyInitialFile.delete();
                            textView.setText("Validation par le manager: Non");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Exception", "Message " + e.toString());
                    }

                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

}
