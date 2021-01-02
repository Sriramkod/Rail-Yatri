package com.example.vbtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class EditTrain extends AppCompatActivity {
    LinearLayout container;
    int c=0;
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_train);
        container = (LinearLayout) findViewById(R.id.container);
        mDatabaseHelper = new DatabaseHelper(this);
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            c++;
            listData.add(data.getString(1));
        }
        String[] k = new String[c];
        int ind=0;
        while (data.moveToNext()) {
            String src= data.getString(2);
            String dep = data.getString(3);
            k[ind++]=src+"-"+dep;
            //listData.add(data.getString(1));
        }
        for(int i=0;i<k.length;i++){
            if(k[i]!=null)
            addItems(k[i]);}
    }
    public void addItems(String a) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.item_row, null);

        final TextView vv = (TextView) addView.findViewById(R.id.place);

        final Button edit = (Button)addView.findViewById(R.id.edit);
        final Button addtocart = (Button)addView.findViewById(R.id.delete);
        vv.setText(""+a.toString());


        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        container.addView(addView);
    }
}