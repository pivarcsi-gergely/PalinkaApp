package hu.petrik.palinkaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button db_insert_gomb, db_select_gomb, db_selectAll_gomb;
private TextView db_selectAll_list;
private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        db_insert_gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent felvetelIntent = new Intent(MainActivity.this, AdatFelvetelActivity.class);
                startActivity(felvetelIntent);
                finish();
            }
        });
        db_select_gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keresesIntent = new Intent(MainActivity.this, AdatKeresesActivity.class);
                startActivity(keresesIntent);
                finish();
            }
        });
        db_selectAll_gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor adatok = dbHelper.listazas();
                if (adatok.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Nincs adat az adatbázisban", Toast.LENGTH_SHORT).show();
                }
                else {
                    StringBuilder sb = new StringBuilder();
                    while (adatok.moveToNext()){
                        sb.append("ID: ").append(adatok.getString(0));
                        sb.append(System.lineSeparator());
                        sb.append("Főző neve: ").append(adatok.getString(1));
                        sb.append(System.lineSeparator());
                        sb.append("Gyümölcs: ").append(adatok.getString(2));
                        sb.append(System.lineSeparator());
                        sb.append("Alkoholtartalom: ").append(adatok.getString(3));
                        sb.append(" %");
                        sb.append(System.lineSeparator());
                        sb.append(System.lineSeparator());
                    }
                    db_selectAll_list.setText(sb.toString());
                    Toast.makeText(MainActivity.this, "Sikeres listázás!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init() {
        db_insert_gomb = findViewById(R.id.db_insert_gomb);
        db_select_gomb = findViewById(R.id.db_select_gomb);
        db_selectAll_gomb = findViewById(R.id.db_selectAll_gomb);
        db_selectAll_list = findViewById(R.id.db_selectAll_list);
        db_selectAll_list.setMovementMethod(new ScrollingMovementMethod());
        dbHelper = new DBHelper(this);
    }
}