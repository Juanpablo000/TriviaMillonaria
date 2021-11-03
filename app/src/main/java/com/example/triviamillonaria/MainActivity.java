package com.example.triviamillonaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnInstrucciones;
    Button btnAceca;
    Button btnJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInstrucciones = (Button)  findViewById(R.id.btnInstrucciones);
        btnAceca = (Button) findViewById(R.id.btnAcerca);
        btnJugar = (Button) findViewById(R.id.btnJugar);

        btnInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InstruccionesActivity.class));
            }
        });

        btnAceca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AcercaActivity.class));
            }
        });

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConfiguracionActivity.class));
            }
        });

    }
}