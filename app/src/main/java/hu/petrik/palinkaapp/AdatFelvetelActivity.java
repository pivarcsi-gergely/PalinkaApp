package hu.petrik.palinkaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdatFelvetelActivity extends AppCompatActivity {
    private EditText edittext_fozo, edittext_gyum, edittext_alkTart;
    private Button felvetel_felvetel_gomb, felvetel_vissza_gomb;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adat_felvetel);
        init();
        felvetel_vissza_gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visszaMainreIntent = new Intent(AdatFelvetelActivity.this, MainActivity.class);
                startActivity(visszaMainreIntent);
                finish();
            }
        });
        felvetel_felvetel_gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fozo = edittext_fozo.getText().toString().trim();
                String gyum = edittext_gyum.getText().toString().trim();
                int alkTart = Integer.parseInt(edittext_alkTart.getText().toString().trim());
                if (fozo.isEmpty() || gyum.isEmpty() || alkTart == 0) {
                    Toast.makeText(getApplicationContext(), "Minden mező kitöltése kötelező", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                            if (dbHelper.rogzites(fozo, gyum, alkTart) ) {
                                Toast.makeText(getApplicationContext(), "Sikeres rögzítés", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Sikertelen rögzítés", Toast.LENGTH_SHORT).show();
                            }
                    }
                    catch (NumberFormatException n) {
                        Toast.makeText(getApplicationContext(), "Az alkoholtartalomnak számnak kell lennie", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void init() {
        edittext_fozo = findViewById(R.id.edittext_fozo);
        edittext_gyum = findViewById(R.id.edittext_gyum);
        edittext_alkTart = findViewById(R.id.edittext_alkTart);
        felvetel_felvetel_gomb = findViewById(R.id.felvetel_felvetel_gomb);
        felvetel_vissza_gomb = findViewById(R.id.felvetel_vissza_gomb);
        dbHelper = new DBHelper(this);
    }
}