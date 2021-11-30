package hu.petrik.palinkaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdatKeresesActivity extends AppCompatActivity {
    private Button kereses_kereses_gomb, kereses_vissza_gomb;
    private EditText edittext_keres_fozo, edittext_keres_gyum;
    private TextView kereses_eredmeny_tv;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adat_kereses_activity);
        init();
        kereses_vissza_gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visszaMainreIntent = new Intent(AdatKeresesActivity.this, MainActivity.class);
                startActivity(visszaMainreIntent);
                finish();
            }
        });
        kereses_kereses_gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fozo = edittext_keres_fozo.getText().toString().trim();
                String gyum = edittext_keres_gyum.getText().toString().trim();
                Cursor kereses_adatok = dbHelper.kereses(fozo, gyum);
                if (fozo.isEmpty() || gyum.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Hiba a keresés során", Toast.LENGTH_SHORT).show();
                }
                else if (kereses_adatok.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Az megadott adatokkal nem található pálinka", Toast.LENGTH_SHORT).show();
                }
                else {
                    StringBuilder sb = new StringBuilder();
                    while (kereses_adatok.moveToNext()){
                        sb.append("Alkoholtartalom: ").append(kereses_adatok.getString(0));
                        sb.append(" %");
                    }
                    kereses_eredmeny_tv.setText(sb.toString());
                }
            }
        });
    }

    public void init() {
        kereses_kereses_gomb = findViewById(R.id.kereses_kereses_gomb);
        kereses_vissza_gomb = findViewById(R.id.kereses_vissza_gomb);
        edittext_keres_fozo = findViewById(R.id.edittext_keres_fozo);
        edittext_keres_gyum = findViewById(R.id.edittext_keres_gyum);
        kereses_eredmeny_tv = findViewById(R.id.kereses_eredmeny_tv);
        dbHelper = new DBHelper(this);
    }
}