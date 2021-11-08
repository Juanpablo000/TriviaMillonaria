package com.example.triviamillonaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class InstruccionesActivity extends AppCompatActivity {

    private ListView listViewComodines;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listViewComodines = findViewById(R.id.lvComodines);
        adapter = new Adapter(this,obtenerArrayItems());
        listViewComodines.setAdapter(adapter);
    }

    private ArrayList<Comodin> obtenerArrayItems()
    {
        ArrayList<Comodin> listComodin = new ArrayList<>();
        listComodin.add(new Comodin(R.drawable.amigo,"Llamar a un amigo", "Este comodin se usa para preguntarle a un amigo la respuesta sobre la pregunta actual."));
        listComodin.add(new Comodin(R.drawable.cincuenta,"50:50", "Anula dos opciones de respuesta incorrectas dejando solo la mitad de opciones iniciales."));
        listComodin.add(new Comodin(R.drawable.cambiar,"Cambiar pregunta", "Cambia la pregunta actual por otra pregunta disponible."));
        listComodin.add(new Comodin(R.drawable.segunda,"Segunda oportunidad", "Anula una opción erronea y la cuenta regresiva se reinicia."));
        listComodin.add(new Comodin(R.drawable.publico,"Preguntarle al publico", "Muestra una grafica de barras con las opciones que el publico cree que son correctas."));
        return listComodin;
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