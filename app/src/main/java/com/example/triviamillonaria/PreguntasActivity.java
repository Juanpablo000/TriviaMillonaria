package com.example.triviamillonaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

public class PreguntasActivity extends AppCompatActivity {

    TextView tpregunta;
    Chip opcion1, opcion2, opcion3, opcion4;
    String[] opciones = new String[]{"Lima","Quito","Bogota","La paz"};
    Pregunta pregunta = new Pregunta("¿Cual es la capital de Ecuador?","facil",opciones);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tpregunta = (TextView) findViewById(R.id.txtPregunta);
        opcion1= (Chip) findViewById(R.id.chip4);
        opcion2= (Chip) findViewById(R.id.chip5);
        tpregunta.setText(pregunta.getDescripcion());
        opcion1.setText(pregunta.getOpciones()[0]);
        opcion2.setText(pregunta.getOpciones()[1]);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}