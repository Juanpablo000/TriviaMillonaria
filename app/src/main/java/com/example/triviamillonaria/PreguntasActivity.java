package com.example.triviamillonaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PreguntasActivity extends AppCompatActivity {
    TextView tpregunta, tTimer, tSaldo, tNivel;
    RadioButton[] arregloRb;
    int[] saldosDisponibles = new int[]{100,300,500,800,1000};
    RadioButton opcion1, opcion2, opcion3, opcion4;
    int saldo, nivel;
    Boolean respondido, condicion=false;

    Button btnPregunta, butnC1, butnC2,butnC3;
    Button[] ArrayBtns;
    RadioGroup radioGroup;
    Pregunta pActual;

    int contadorPreguntas=0, preguntasTotales =3;

    private static long cuentaAtrasMls = 30000;// 30 segundos
    private CountDownTimer countDownTimer;
    private long tiempoRestanteMls;
    ArrayList<Pregunta> listaPreguntas = new ArrayList<Pregunta>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        butnC1 = (Button) findViewById(R.id.btnC1);
        butnC2 = (Button) findViewById(R.id.btnC2);
        butnC3 = (Button) findViewById(R.id.btnC3);

        btnPregunta = (Button) findViewById(R.id.btnPregunta);
        tNivel = (TextView) findViewById(R.id.txtNivel);
        tSaldo = (TextView) findViewById(R.id.txtSaldo);
        tTimer = (TextView) findViewById(R.id.txtTimer);
        tpregunta = (TextView) findViewById(R.id.txtPregunta);
        opcion1= (RadioButton) findViewById(R.id.rb1);
        opcion2= (RadioButton) findViewById(R.id.rb2);
        opcion3= (RadioButton) findViewById(R.id.rb3);
        opcion4= (RadioButton) findViewById(R.id.rb4);
        radioGroup = (RadioGroup) findViewById(R.id.radiog);
        arregloRb = new RadioButton[]{opcion1,opcion2,opcion3,opcion4};
        ArrayBtns = new Button[]{butnC1, butnC2, butnC3};

        Bundle recibirDatos = getIntent().getExtras();

        String[] Asalida = recibirDatos.getStringArray("kayArr");
        //boolean[] ArregloBools = recibirDatos.getBooleanArray("keyArreglo");
        String categoria = recibirDatos.getString("keyCategoria");

        //cincuenta, saltar, publico, segunda, amigo;



        //butnC1.setBackgroundResource(R.drawable.ble);
        butnC1.setText(Asalida[0]);
        butnC2.setText(Asalida[1]);
        butnC3.setText(Asalida[2]);



        butnC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butnC1.setAutofillHints("Cincuenta");
                butnC1.setHint("otra pistas");

                if(butnC1.getText().toString().equals("50:50")){
                    
                }
            }
        });


        cargarPreguntas();
        MostrarProximaPregunta();

        btnPregunta.setOnClickListener(view -> {
           if(respondido){
               MostrarProximaPregunta();
           }
        });

        /*
        int i;
        for (Pregunta pActual: listaPreguntas){
            tpregunta.setText(pActual.getDescripcion());
            i=0;
            for(Chip chip: arregloChips){
                chip.setText(pActual.getOpciones()[i]);
                i++;
            }
        }
        */

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarPreguntas() {

        String[] opciones = new String[]{"Lima","Quito","Bogota","La paz"};
        String[] opciones2 = new String[]{"Bogota","Santiago","Caracas","Nueva York"};
        String[] opciones3 = new String[]{"7","4","6","5"};


        Pregunta pregunta = new Pregunta("¿Cual es la capital de Ecuador?","facil",opciones,1);
        Pregunta pregunta2 = new Pregunta("¿Cual es la capital de Colombia?","facil",opciones2,0);
        Pregunta pregunta3 = new Pregunta("¿Cuantos continentes existen?","facil",opciones3,3);


        listaPreguntas.add(pregunta);
        listaPreguntas.add(pregunta2);
        listaPreguntas.add(pregunta3);
    }

    private void MostrarProximaPregunta(){

        radioGroup.clearCheck();
        if(contadorPreguntas<preguntasTotales){
            pActual=listaPreguntas.get(contadorPreguntas);
            tpregunta.setText(pActual.getDescripcion());
            int i=0;
            for(RadioButton radioButton: arregloRb){
                radioButton.setText(pActual.getOpciones()[i]);
                i++;
            }
            contadorPreguntas++;
            tNivel.setText("Nivel actual: " + contadorPreguntas+ "/" + preguntasTotales);
            respondido=false;
            btnPregunta.setText("Confirmar");
        }else{
            finalizarQuiz();
        }
        tiempoRestanteMls = cuentaAtrasMls;
        comenzarCuentaAtras();
    }

    private void comenzarCuentaAtras(){
        countDownTimer = new CountDownTimer(tiempoRestanteMls,1000) {
            @Override
            public void onTick(long l) {
                tiempoRestanteMls=l;
                actualizarCuentaAtrasTexto();
            }

            @Override
            public void onFinish() {
                tiempoRestanteMls=0;
                actualizarCuentaAtrasTexto();
                revisarRespuesta();
            }
        }.start();
    }

    private void revisarRespuesta(){
        respondido=true;
        countDownTimer.cancel();
        RadioButton rbselecionado = findViewById(radioGroup.getCheckedRadioButtonId());

        int salida = radioGroup.indexOfChild(rbselecionado);

        if(salida==pActual.getIndiceRespuesta()){
            saldo=saldo+100;
            tSaldo.setText("Dinero: " + saldo);

        }else{
            //fin del juego
        }

    }

    private void actualizarCuentaAtrasTexto(){
        int minutos = (int) (tiempoRestanteMls/1000)/60;
        int segundos = (int) (tiempoRestanteMls/1000) % 60;

        String tiempo = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
        tTimer.setText(tiempo);

        if(tiempoRestanteMls < 10000){
            tTimer.setTextColor(Color.RED);
        }else{
            tTimer.setTextColor(Color.GRAY);
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    public void finalizarQuiz(){
        Intent resultadoInet = new Intent();
        resultadoInet.putExtra("Dinero ganado: ",saldo);
        setResult(RESULT_OK);
        finish();
    }

    public void onWindowFocusChanged(){
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }



}


/*
*  int i;
        for (Pregunta pActual: listaPreguntas){

            tpregunta.setText(pActual.getDescripcion());
            i=0;
            for(Chip chip: arregloChips){
                chip.setText(pActual.getOpciones()[i]);
                i++;
            }
            tiempoRestanteMls = cuentaAtrasMls;
            comenzarCuentaAtras();
        }
*
* */