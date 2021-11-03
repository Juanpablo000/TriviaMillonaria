package com.example.triviamillonaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {

    Spinner comboCategorias;
    String text;
    Button btnConfirmar;
    Boolean[] arregloComodines = new Boolean[5];

    CheckBox cincuenta, saltar, publico, segunda, amigo;
             //0          1         2        3       4



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        cincuenta = (CheckBox)  findViewById(R.id.cb50);
        saltar = (CheckBox)  findViewById(R.id.cbSaltar);
        publico = (CheckBox)  findViewById(R.id.cbPublico);
        segunda = (CheckBox)  findViewById(R.id.cbSegundaOportunidad);
        amigo = (CheckBox)  findViewById(R.id.cbAmigo);
        CheckBox[] chk = new CheckBox[]{cincuenta, saltar, publico, segunda, amigo};
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int contador =0;

                int i=0;
                for (CheckBox checkBox: chk){

                    if(checkBox.isChecked()==true){
                        arregloComodines[i]=true;
                        contador++;
                    }else{
                        arregloComodines[i]=false;
                    }
                    i++;
                }


                /*
                if(cincuenta.isChecked()==true){
                    arregloComodines[0]=true;
                    contador++;
                }else{
                    arregloComodines[0]=false;
                }

                if(saltar.isChecked()==true){
                    arregloComodines[1]=true;
                    contador++;
                }else{
                    arregloComodines[1]=false;
                }

                if(publico.isChecked()==true){
                    arregloComodines[2]=true;
                    contador++;
                }else{
                    arregloComodines[2]=false;
                }

                if(segunda.isChecked()==true){
                    arregloComodines[3]=true;
                    contador++;
                }else{
                    arregloComodines[3]=false;
                }

                if(amigo.isChecked()==true){
                    arregloComodines[4]=true;
                    contador++;
                }else{
                    arregloComodines[4]=false;
                }

                 */
                if(!text.isEmpty() && contador==3){

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Todo piola ", Toast.LENGTH_SHORT);
                    toast1.show();

                    startActivity(new Intent(ConfiguracionActivity.this, PreguntasActivity.class));

                }else{

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Ta mal ", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        comboCategorias = findViewById(R.id.idSpiner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.combo_categorias, android.R.layout.simple_spinner_item);

        comboCategorias.setAdapter(adapter);




        comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text = comboCategorias.getSelectedItem().toString();

                if (text.equals("Seleccione")){
                    text="";
                }
                /*
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Toast por defecto " + text, Toast.LENGTH_SHORT);
                toast1.show();
                */

                if(text.equals("Arte")){
                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "Arte seleccionado", Toast.LENGTH_SHORT);

                    toast2.show();
                }else if(text.equals("Historia")){
                    Toast toast3 =
                            Toast.makeText(getApplicationContext(),
                                    "Historia seleccionada", Toast.LENGTH_SHORT);

                    toast3.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}