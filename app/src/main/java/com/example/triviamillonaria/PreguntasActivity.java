package com.example.triviamillonaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PreguntasActivity extends AppCompatActivity {
    TextView tpregunta, tTimer, tSaldo, tNivel, tAmigo;
    RadioButton[] arregloRb;
    int[] saldosDisponibles = new int[]{100,300,500,800,1000};
    RadioButton opcion1, opcion2, opcion3, opcion4;
    boolean respondido, comodinSegunda=false;

    String categoria;
    Button btnPregunta;
    ImageButton[] ArrayBtns;
    RadioGroup radioGroup;
    Pregunta pActual;
    ImageButton imageButton1,imageButton2,imageButton3;
    Dialog mDialog;

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

        mDialog = new Dialog(this);
        tAmigo = (TextView) findViewById(R.id.descripcionAmigo);

        imageButton1 = (ImageButton) findViewById(R.id.ibtn1);
        imageButton2 = (ImageButton) findViewById(R.id.ibtn2);
        imageButton3 = (ImageButton) findViewById(R.id.ibtn3);

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
        ArrayBtns = new ImageButton[]{imageButton1, imageButton2, imageButton3};

        Bundle recibirDatos = getIntent().getExtras();

        String[] Asalida = recibirDatos.getStringArray("kayArr");
        categoria = recibirDatos.getString("keyCategoria");


        //Asigna una imagen a cada imageBotton
        int c=0;
        for(ImageButton imageButton:ArrayBtns){
            if(Asalida[c].equals("50:50")){
                imageButton.setImageResource(R.drawable.cincuenta);
            }else if(Asalida[c].equals("Saltar pregunta")){
                imageButton.setImageResource(R.drawable.cambiar);
            }else if(Asalida[c].equals("Preguntar al publico")){
                imageButton.setImageResource(R.drawable.publico);
            }else if(Asalida[c].equals("Segunda oportunidad")){
                imageButton.setImageResource(R.drawable.segunda);
            }else if(Asalida[c].equals("Preguntar a un amigo")){
                imageButton.setImageResource(R.drawable.amigo);
            }
            c++;
        }


        cargarPreguntas();
        MostrarProximaPregunta();

        btnPregunta.setOnClickListener(view -> {
           if(respondido){
               MostrarProximaPregunta();
           }
        });

        imageButton1.setOnClickListener(view -> {
            btnImgs(imageButton1,Asalida[0]);
        });

        imageButton2.setOnClickListener(view -> {
            btnImgs(imageButton2,Asalida[1]);
        });

        imageButton3.setOnClickListener(view -> {
            btnImgs(imageButton3,Asalida[2]);
        });

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

        if(categoria.equals("Arte")){
            String[] opciones = new String[]{"Miguel Ángel","Rafael Sanzio ","Sandro Botticelli","Leonardo Da Vinci"};
            String[] opciones2 = new String[]{"Gesso ","Tempera","Acrilico","Acetona"};
            String[] opciones3 = new String[]{"Leonardo Da Vinci","Arthur Holly C.","Monet","Manet"};


            Pregunta pregunta = new Pregunta("¿Quien pinto la Mona Lisa?","facil",opciones,3);
            Pregunta pregunta2 = new Pregunta("¿Cuál de estas es una pintura hecha de pigmentos y plástico?","facil",opciones2,2);
            Pregunta pregunta3 = new Pregunta("¿Quién pintó el controvertido Luncheon on the Grass (1863)?","facil",opciones3,3);


            listaPreguntas.add(pregunta);
            listaPreguntas.add(pregunta2);
            listaPreguntas.add(pregunta3);


        }else if(categoria.equals("Ciencia")){
            String[] opciones = new String[]{"Gravedad","Fuerza fuerte ","Fuerza electromagnetica","Fuerza debil"};
            String[] opciones2 = new String[]{"Gravedad","Velociad del sonido","Velocidad de la luz","Interferencia de la atmosfera"};
            String[] opciones3 = new String[]{"Albert Einsten","Arthur Holly C.","Richard P. Feynman","Enrico Fermi"};


            Pregunta pregunta = new Pregunta("¿Cuál es la fuerza más débil de la naturaleza?","facil",opciones,0);
            Pregunta pregunta2 = new Pregunta("Pierre Gassendi, un filósofo-científico francés, observó disparos desde la distancia para medir ¿qué fenómeno natural?","facil",opciones2,1);
            Pregunta pregunta3 = new Pregunta("¿Quién de estos hombres fue el primer científico en realizar un experimento de reacción en cadena nuclear controlada?","facil",opciones3,3);


            listaPreguntas.add(pregunta);
            listaPreguntas.add(pregunta2);
            listaPreguntas.add(pregunta3);

        }else if(categoria.equals("Geografia")){
            String[] opciones = new String[]{"Lima","Quito","Bogota","La paz"};
            String[] opciones2 = new String[]{"Mónaco","Nauru","Ciudad del baticano","Tuvalu"};
            String[] opciones3 = new String[]{"7","4","6","5"};


            Pregunta pregunta = new Pregunta("¿Cual es la capital de Ecuador?","facil",opciones,1);
            Pregunta pregunta2 = new Pregunta("¿Cual es el estado más pequeño del mundo?","facil",opciones2,2);
            Pregunta pregunta3 = new Pregunta("¿Cuantos continentes existen?","facil",opciones3,3);


            listaPreguntas.add(pregunta);
            listaPreguntas.add(pregunta2);
            listaPreguntas.add(pregunta3);
        }else if(categoria.equals("Historia")){
            String[] opciones = new String[]{"Neron","Auguto","Tiberio","Galba"};
            String[] opciones2 = new String[]{"1356","1500","1492","1497"};
            String[] opciones3 = new String[]{"Sputnik 1","Explorer 1","Astérix","Osumi"};


            Pregunta pregunta = new Pregunta("¿Qué emperador romano provocó la Pax Romana, un período de paz, prosperidad y expansión para Roma?","dificil",opciones,1);
            Pregunta pregunta2 = new Pregunta("¿En que año se descubrio america?","facil",opciones2,2);
            Pregunta pregunta3 = new Pregunta("¿Como se llamaba el primer satelite artificial?","facil",opciones3,0);


            listaPreguntas.add(pregunta);
            listaPreguntas.add(pregunta2);
            listaPreguntas.add(pregunta3);
        }


    }

    private void MostrarProximaPregunta(){

        radioGroup.clearCheck();

        if(contadorPreguntas<preguntasTotales){
            pActual=listaPreguntas.get(contadorPreguntas);
            tpregunta.setText(pActual.getDescripcion());
            int i=0;
            for(RadioButton radioButton: arregloRb){
                if(comodinSegunda==false){
                    radioButton.setEnabled(true);
                }

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
            tSaldo.setText("Dinero: " + saldosDisponibles[contadorPreguntas-1]);

        }else{
            if(comodinSegunda==true){
                //Inhabilita la opcion de respuesta incorrecta
                for(int i=0; i<4; i++){
                    if(salida==i){
                        arregloRb[i].setEnabled(false);
                    }
                }
                contadorPreguntas--;
                MostrarProximaPregunta();
                comodinSegunda=false;
            }else{
                //fin del juego
            }
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
            tTimer.setTextColor(Color.BLACK);
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
        resultadoInet.putExtra("Dinero ganado: ",saldosDisponibles[contadorPreguntas-1]);
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

    private void comodinCincuenta(){
        radioGroup.clearCheck();

        int aleatorio1=-1,aleatorio2=-2, idicieRespuesta;

        idicieRespuesta=pActual.getIndiceRespuesta();
        boolean con1=true,con2=true;

        //validar que NO sean los mismo numeros aleatorios
        do{
            if(con1){
                aleatorio1 = (int) (Math.random() * 4);//0
            }
            if(con2){
                aleatorio2 = (int) (Math.random() * 4);//3
            }

            if(aleatorio1!=idicieRespuesta && aleatorio1!=aleatorio2){
                con1=false;
            }

            if(aleatorio2!=idicieRespuesta && aleatorio1!=aleatorio2){
                con2=false;
            }

            if(!con1 && !con2){
                break;
            }
        }while(2>1);


        //Inhabilita dos opciones de respuesta INCORRECTAS
        for(int i=0; i<4; i++){
            if(aleatorio1==i){
                arregloRb[i].setEnabled(false);
            }
            if(aleatorio2==i){
                arregloRb[i].setEnabled(false);
            }
        }
    }

    private void comodinSaltar(){
        countDownTimer.cancel();
        MostrarProximaPregunta();
    }

    private void btnImgs(ImageButton imageButton, String s){
        imageButton.setEnabled(false);
        imageButton.setImageResource(R.drawable.usado);

        if(s.equals("50:50")){
            comodinCincuenta();
        }else if(s.equals("Saltar pregunta")){
            comodinSaltar();
        }else if(s.equals("Preguntar al publico")){
            //comodinPublico
        }else if(s.equals("Segunda oportunidad")) {
            //Se gasta y epera a que el timer finalice para ver si se aplica o no
            comodinSegunda=true;
        }else if(s.equals("Preguntar a un amigo")){
            mDialog.setContentView(R.layout.layoutamigo);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.show();
        }
    }


}