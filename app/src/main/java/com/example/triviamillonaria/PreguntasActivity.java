package com.example.triviamillonaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class PreguntasActivity extends AppCompatActivity {
    private TextView tpregunta, tTimer, tSaldo, tNivel;
    private RadioButton[] arregloRb;
    private final static int[] saldosDisponibles = new int[]{100,300,500,800,1000,2000,4000,8000,16000,32000,64000,125000,250000,500000,1000000};
    private RadioButton opcion1, opcion2, opcion3, opcion4;
    boolean comodinSegunda;

    private String categoria;
    private Button btnPregunta;
    private ImageButton[] ArrayBtns;
    private RadioGroup radioGroup;
    private Pregunta pActual;
    private ImageButton imageButton1,imageButton2,imageButton3;
    private Dialog mDialog;
    private Random r;

    private int contadorPreguntas=0, preguntasTotales =15, dineroFinal=0;

    private long cuentaAtrasMls = 30000;// 30 segundos
    private CountDownTimer countDownTimer;
    private long tiempoRestanteMls;
    private ArrayList<Pregunta> listaPreguntas = new ArrayList<Pregunta>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDialog = new Dialog(this);

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
        mostrarProximaPregunta();

        btnPregunta.setOnClickListener(view -> {
            revisarRespuesta();
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
            //preguntas faciles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Quien pinto la Mona Lisa?","facil",new String[]{"Miguel ??ngel","Rafael Sanzio ","Sandro Botticelli","Leonardo Da Vinci"},3));
            listaPreguntas.add(new Pregunta("??Cu??l de estas es una pintura hecha de pigmentos y pl??stico?","facil",new String[]{"Gesso ","Tempera","Acrilico","Acetona"},2));
            listaPreguntas.add(new Pregunta("??Qu?? animal simboliza a menudo la paz en el arte?","facil", new String[]{"Ciervo","Perro","Paloma","Pato"},2));
            listaPreguntas.add(new Pregunta("??Cu??l fue el tema de las primeras pinturas conocidas?","facil", new String[]{"Flores","Deportes","Paisajes","Animales"},3));

            //preguntas medias (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??A qu?? movimiento art??stico pertenece El Cristo amarillo de Paul Gauguin?","medio", new String[]{"Fauvismo","Cloisonnismo","Impresionismo","Bauhaus"},1));
            listaPreguntas.add(new Pregunta("??Qu?? representa la Venus de Brassempouy?","medio", new String[]{"Un ??ngel","Una figura humana","Un hombre viejo","La cabeza de una mujer"},3));
            listaPreguntas.add(new Pregunta("??Qu?? arquitecto fund?? la escuela de dise??o Bauhaus?","medio", new String[]{"Frank Lloyd Wright","Walter Gropius","I.M. Pei","Frank Gehry"},1));
            listaPreguntas.add(new Pregunta("??Qui??n dise???? el Monumento a los Veteranos de Vietnam?","medio", new String[]{"Henri Matisse","Maya Lin","Frank Lloyd Wright","Frank Gehry"},1));

            //preguntas dificiles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Qu?? dise???? I.M. Pei fuera del Louvre, en Par??s?","dificil", new String[]{"Pir??mide","Sarc??fago","Zigurat","Obelisco"},0));
            listaPreguntas.add(new Pregunta("??Cu??l de estos no es un escultor indio conocido?","dificil", new String[]{"Krishna Reddy","Ramkinkar Baij","Roger Bacon","Henry Moore"},3));
            listaPreguntas.add(new Pregunta("??Qui??n pint?? el controvertido Luncheon on the Grass (1863)?","dificil",new String[]{"Leonardo Da Vinci","Arthur Holly C.","Monet","Manet"},3));
            listaPreguntas.add(new Pregunta("??Los primeros fot??grafos hicieron sus im??genes en cu??l de estos materiales?","dificil",new String[]{"Plastico","Piedra","Cristal","Papel"},2));


            //preguntas muy dificiles (minimo num preguntas 3)
            listaPreguntas.add(new Pregunta("Nombra el t??rmino para describir una figura que est?? parada con una pierna sosteniendo todo su peso y la otra pierna relajada","muy dificil", new String[]{"Musa","Contrapposto","Modelo","Perfil"},2));
            listaPreguntas.add(new Pregunta("??Qu?? escultor es m??s conocido por su colosal Estatua de la Libertad?","muy dificil", new String[]{"Clark Mills","Thomas Crawford","Hiram Powers","Horatio Greenough"},1));
            listaPreguntas.add(new Pregunta("??Qu?? artista es conocido como el creador del cubismo?","muy dificil", new String[]{"Claude Monet","Rembrandt","Salvador Dal??","Pablo Picasso"},3));


        }else if(categoria.equals("Ciencia")){
            //preguntas faciles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Cu??l es la fuerza m??s d??bil de la naturaleza?","facil",new String[]{"Gravedad","Fuerza fuerte ","Fuerza electromagnetica","Fuerza debil"},0));
            listaPreguntas.add(new Pregunta("Pierre Gassendi, un fil??sofo-cient??fico franc??s, observ?? disparos desde la distancia para medir ??qu?? fen??meno natural?","facil",new String[]{"Gravedad","Velociad del sonido","Velocidad de la luz","Interferencia de la atmosfera"},1));
            listaPreguntas.add(new Pregunta("??Qui??n de estos hombres fue el primer cient??fico en realizar un experimento de reacci??n en cadena nuclear controlada?","facil",new String[]{"Albert Einsten","Arthur Holly C.","Richard P. Feynman","Enrico Fermi"},3));
            listaPreguntas.add(new Pregunta("??En qu?? intervalo de tiempo geol??gico vivi?? el megalod??n?","facil",new String[]{"Per??odos carbon??fero y p??rmico","E??n h??dico","Periodo Jurasico","??pocas del Mioceno y Plioceno"},3));

            //preguntas medias (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Qu?? significa la palabra megalodon, un compuesto de que ra??ces griegas?","medio", new String[]{"Diente gigante","Hocico gigante","Ojo gigante","Boca gigante"},0));
            listaPreguntas.add(new Pregunta("??Qu?? ave no voladora med??a un metro de altura, viv??a en la isla de Mauricio y se extingui?? en el siglo XVII?","medio", new String[]{"Gran auk","Avestruz ??rabe","dodo","Curruca de Bachman"},2));
            listaPreguntas.add(new Pregunta("??De qu?? prote??na est??n hechas las plumas, el cabello humano y las u??as humanas?","medio", new String[]{"Queratina","Fibro??na","Col??geno","Elastina"},0));
            listaPreguntas.add(new Pregunta("??Qu?? ave anida en densas colonias en acantilados y se sumerge en el mar para pescar peces y calamares?","medio", new String[]{"Alcatraz","Piqueros","Arao negro","Quetzal"},0));

            //preguntas dificiles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Qu?? qu??mico franc??s fue el primero en aislar la code??na?","dificil", new String[]{"Pierre-Joseph Pelletier","Bernard Meunier","Yves Chauvin","Pierre-Jean Robiquet"},3));
            listaPreguntas.add(new Pregunta("??Qu?? erudito ingl??s escribi?? la receta de p??lvora m??s antigua existente en un idioma europeo?","dificil", new String[]{"Henry Daniel","Roger de Aswardby","Roger Bacon","Edmund Lacey"},2));
            listaPreguntas.add(new Pregunta("??Qui??n fue el autor del primer libro de texto de qu??mica moderna, Alchymia, en 1606?","dificil", new String[]{"Paul Friedlander","Carl Remigius Fresenius","Andreas Libavius","Ludwig Darmstaedter"},2));
            listaPreguntas.add(new Pregunta("En 1910, ??qu?? ingeniero y qu??mico franc??s descubri?? que el paso de corriente el??ctrica a trav??s de gases inertes produc??a luz y procedi?? a desarrollar la l??mpara de ne??n?","dificil", new String[]{"Edward Adam","Charles Janet","Ren?? Jacques L??vy","Georges Claude"},3));

            //preguntas muy dificiles (minimo num preguntas 3)
            listaPreguntas.add(new Pregunta("??Qu?? descubrio el f??sico J.J. Thomson en la d??cada de 1890 analizando a los rayos cat??dicos?","muy dificil", new String[]{"Los iones","Los fotones","Los electrones","La radioactividad"},2));
            listaPreguntas.add(new Pregunta("??Cu??l es el fen??meno de la dispersi??n Raman, que lleva el nombre del f??sico indio C.V. Raman?","muy dificil", new String[]{"Distribuci??n estad??stica de impactos de meteoritos","Movimiento de ??tomos en gas ionizado","Efectos de las capas de nubes en las emisiones de radio","Cambio de longitud de onda de la luz desviada"},3));
            listaPreguntas.add(new Pregunta("??Qu?? reacci??n que involucra n??cleos at??micos es la fuente de energ??a del Sol?","muy dificil", new String[]{"Fusi??n nuclear","Ionizaci??n","Combusti??n qu??mica","Fisi??n nuclear"},0));

        }else if(categoria.equals("Geografia")){
            //preguntas faciles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Cual es la capital de Ecuador?","facil",new String[]{"Lima","Quito","Bogota","La paz"},1));
            listaPreguntas.add(new Pregunta("??Cual es el estado m??s peque??o del mundo?","facil",new String[]{"M??naco","Nauru","Ciudad del baticano","Tuvalu"},2));
            listaPreguntas.add(new Pregunta("??Cuantos continentes existen?","facil",new String[]{"7","4","6","5"},3));
            listaPreguntas.add(new Pregunta("??Capital de la India?","facil",new String[]{"Nueva Deli","Harare","Goa","Praia"},0));

            //preguntas medias (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Riyadh es la ciudad capital de?","medio", new String[]{"Maine","Alaska","Arabia Sauidita","Suecia"},2));
            listaPreguntas.add(new Pregunta("??Cual de las siguientes NO es una ciudad de Brasil?","medio", new String[]{"Brasilia","Guimar??es","Rio de Janeiro","Belo Horizonte"},1));
            listaPreguntas.add(new Pregunta("??Cuantas comunidades aut??nomas posee Espa??a?","medio", new String[]{"15","17","16","20"},1));
            listaPreguntas.add(new Pregunta("??El rio Sena pasa por cual pa??s?","medio", new String[]{"Inglaterra","Espa??a","Francia","Suiza"},2));

            //preguntas dificiles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Kuala Lumpur es la ciudad capital de?","dificil", new String[]{"Maurruecos","Trinidad y Tobago","Malasia","Nepal"},2));
            listaPreguntas.add(new Pregunta("??Ciudad m??s poblada de Nepal?","dificil", new String[]{"Dharan","P??tan","Birgunj","Kathmandu"},3));
            listaPreguntas.add(new Pregunta("??Porto-Novo hace parte del pais de?","dificil", new String[]{"Benin","Portugal","Brasil","Burundi"},0));
            listaPreguntas.add(new Pregunta("??Segunda ciudad m??s importante de Suecia?","dificil", new String[]{"Estocolmo","Malm??","Copenhague ","Gotemburgo"},3));

            //preguntas muy dificiles (minimo num preguntas 3)
            listaPreguntas.add(new Pregunta("??Cuantas zonas horarias posee Rusia?","muy dificil", new String[]{"10","11","15 ","7"},1));
            listaPreguntas.add(new Pregunta("??Cual es la diferencia horaria entre las Islas Diomede (las cuales separan USA de Rusia)?","muy dificil", new String[]{"5 horas","21 horas","11 horas","1 hora"},1));
            listaPreguntas.add(new Pregunta("??Cual es el punto m??s alto de la Tierra?","muy dificil", new String[]{"Cho Oyu","El Makalu","El Everest ","El Chimborazo "},1));

        }else if(categoria.equals("Historia")){
            //preguntas faciles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Qu?? emperador romano provoc?? la Pax Romana, un per??odo de paz, prosperidad y expansi??n para Roma?","dificil",new String[]{"Neron","Auguto","Tiberio","Galba"},1));
            listaPreguntas.add(new Pregunta("??En que a??o se descubrio america?","facil",new String[]{"1356","1500","1492","1497"},2));
            listaPreguntas.add(new Pregunta("??Cual fue el nombre del primer satelite artificial?","facil",new String[]{"Sputnik 1","Explorer 1","Ast??rix","Osumi"},0));
            listaPreguntas.add(new Pregunta("??El imperio romano de occidente cayo en el a??o de?","facil", new String[]{"12 ac","312 dc","476 dc","453 dc"},2));

            //preguntas medias (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Qui??n acu???? el t??rmino Guerra Fr??a?","medio", new String[]{"George Orwell","Winston Churchill","Harry S. Truman","Dean Acheson"},0));
            listaPreguntas.add(new Pregunta("Despu??s de la Segunda Guerra Mundial, este programa patrocinado por Estados Unidos proporcion?? ayuda econ??mica muy necesaria a Europa occidental","medio", new String[]{"Plan Marshall","Acuerdos de Dayton","Wilmot Proviso","Plan Dawes"},0));
            listaPreguntas.add(new Pregunta("La muerte de este l??der sovi??tico en 1953 trajo una relajaci??n temporal de las tensiones de la Guerra Fr??a","medio", new String[]{"Nikita Khrushchev","Joseph Stalin","Vladimir Lenin","Georgy Zhukov"},1));
            listaPreguntas.add(new Pregunta("??En que a??o ocurrio la crisis de los misiles en Cuba?","medio", new String[]{"1950","1967","1962","1960"},2));

            //preguntas dificiles (minimo num preguntas 4)
            listaPreguntas.add(new Pregunta("??Cu??l de estos lugares nunca estuvo bajo el dominio otomano?","dificil", new String[]{"Vienna","Egipto","Kosovo","Macedonia"},0));
            listaPreguntas.add(new Pregunta("Constantinopla fue conquistada por los otomanos, ??bajo qu?? sult??n?","dificil", new String[]{"Solim??n el Magn??fico","Murad I","Orhan","Mehmed II"},3));
            listaPreguntas.add(new Pregunta("La Real Expedici??n Filantr??pica de la vacuna, fue una expedici??n de car??cter filantr??pico en contra de la viruela que dio la vuelta al mundo, realizada entre los a??os de ","dificil", new String[]{"1800 a 1810","1789 a 1799","1803 a 1806","1804 a 1811"},2));
            listaPreguntas.add(new Pregunta("??Qu?? poeta inmortaliz?? el \"paseo de medianoche\" de Paul Revere para advertir a los colonos de Lexington y Concord sobre la llegada de las tropas brit??nicas?","dificil", new String[]{"Henry Wadsworth Longfellow","William Carlos Williams","John Greenleaf Whittier","James Russell Lowell"},0));

            //preguntas muy dificiles (minimo num preguntas 3)
            listaPreguntas.add(new Pregunta("??Qui??n fue el primer emperador del Sacro Imperio Romano Germ??nico?","muy dificil", new String[]{"Luis I","Henry IV","Lothair I ","Carlomagno"},3));
            listaPreguntas.add(new Pregunta("??Qu?? emperatriz china se levant?? del concubinato para convertirse en emperatriz de China durante la dinast??a Tang?","muy dificil", new String[]{"Cixi","Gaohou","Ci???an","Wuhou"},3));
            listaPreguntas.add(new Pregunta("??Cu??ndo ocurri?? la hambruna irlandesa?","muy dificil", new String[]{"1810","1845","1895","1925"},1));
        }
        //Collections.shuffle(listaPreguntas);
        //preguntasAleatorias();
    }

    private void preguntasAleatorias(){

        ArrayList<Pregunta> faciles = new ArrayList<>();
        ArrayList<Pregunta> medio = new ArrayList<>();
        ArrayList<Pregunta> dificil = new ArrayList<>();
        ArrayList<Pregunta> muydificil = new ArrayList<>();



        ArrayList<Pregunta> tmpPregunta = new ArrayList<>();
        Collections.shuffle(listaPreguntas);
        int pTotales=0;
        for(Pregunta p:listaPreguntas){
            if(p.getDificultad().equals("facil") &&pTotales<=4){
                faciles.add(p);
                pTotales++;
            }else if(p.getDificultad().equals("medio") &&pTotales>=5 && pTotales<=8){
                tmpPregunta.add(p);
                pTotales++;
            }else if(p.getDificultad().equals("dificil") &&pTotales>=9 && pTotales<=12){
                tmpPregunta.add(p);
                pTotales++;
            }else if(p.getDificultad().equals("muy dificil") &&pTotales>=13 && pTotales<=15){
                tmpPregunta.add(p);
                pTotales++;
            }
        }

        listaPreguntas=tmpPregunta;
    }

    private void mostrarProximaPregunta(){
        radioGroup.clearCheck();

        if(contadorPreguntas<preguntasTotales){
            pActual=listaPreguntas.get(contadorPreguntas);
            tpregunta.setText(pActual.getDescripcion());
            int i=0;
            for(RadioButton radioButton: arregloRb){
                if(!comodinSegunda){
                    radioButton.setEnabled(true);
                }

                radioButton.setText(pActual.getOpciones()[i]);
                i++;
            }
            contadorPreguntas++;
            tNivel.setText("Nivel actual: " + contadorPreguntas+ "/" + preguntasTotales);

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
                //revisarRespuesta();


                if(comodinSegunda==true){
                    revisarRespuesta();
                }else{
                    finalizarQuiz();
                }

            }
        }.start();
    }

    private void revisarRespuesta(){
        countDownTimer.cancel();
        RadioButton rbselecionado = findViewById(radioGroup.getCheckedRadioButtonId());

        int salida = radioGroup.indexOfChild(rbselecionado);

        if(salida==pActual.getIndiceRespuesta()){
            dineroFinal=saldosDisponibles[contadorPreguntas-1];
            tSaldo.setText("Dinero: " + dineroFinal);
            mostrarProximaPregunta();

        }else{
            if(comodinSegunda==true){
                //Inhabilita la opcion de respuesta incorrecta
                for(int i=0; i<4; i++){
                    if(salida==i){
                        arregloRb[i].setEnabled(false);
                    }
                }
                contadorPreguntas--;
                mostrarProximaPregunta();
                comodinSegunda=false;
            }else{
                //fin del juego
                finalizarQuiz();
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
        mDialog.setContentView(R.layout.layoutfinjuego);
        mDialog.setCancelable(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView = mDialog.findViewById(R.id.finalIV);
        TextView tvFinal = mDialog.findViewById(R.id.descripcionFinal);
        Button btnFinal = mDialog.findViewById(R.id.btnAceptarFinal);

        if(contadorPreguntas==preguntasTotales){
            tvFinal.setText("??Felicidades has ganado " + dineroFinal + " dolares!");
            imageView.setImageResource(R.drawable.gana);
        }else{
            tvFinal.setText("Solo lograste ganar " + dineroFinal + " dolares");
            imageView.setImageResource(R.drawable.pierde);
        }

        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mDialog.show();
    }

    private void comodinCincuenta(){
        radioGroup.clearCheck();

        int idicieRespuesta = pActual.getIndiceRespuesta();
        ArrayList<String>tmpArreglo = new ArrayList<>(Arrays.asList(pActual.getOpciones()));
        tmpArreglo.remove(idicieRespuesta);
        Collections.shuffle(tmpArreglo);


        for(int i=0; i<4; i++){
            if(pActual.getOpciones()[i].equals(tmpArreglo.get(0))){
                arregloRb[i].setEnabled(false);
            }

            if(pActual.getOpciones()[i].equals(tmpArreglo.get(1))){
                arregloRb[i].setEnabled(false);
            }
        }

    }

    private void comodinSaltar(){
        if(contadorPreguntas<preguntasTotales){
            countDownTimer.cancel();
            mostrarProximaPregunta();
        }else{
            Toast.makeText(getApplicationContext(),
                    "Comodin invalido para la ultima pregunta", Toast.LENGTH_SHORT).show();
        }

    }

    private void btnImgs(ImageButton imageButton, String s){
        imageButton.setEnabled(false);
        imageButton.setImageResource(R.drawable.usado);

        if(s.equals("50:50")){
            comodinCincuenta();
        }else if(s.equals("Saltar pregunta")){
            comodinSaltar();
        }else if(s.equals("Preguntar al publico")){
            comodinPublico();
        }else if(s.equals("Segunda oportunidad")) {
            comodinSegunda=true;
        }else if(s.equals("Preguntar a un amigo")) {
           comodinAmigo();
        }
    }

    private void comodinAmigo(){
        mDialog.setContentView(R.layout.layoutamigo);
        mDialog.setCancelable(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        String[]tmp = pActual.getOpciones();
        TextView txtAmigo = mDialog.findViewById(R.id.descripcionAmigo);
        Collections.shuffle(Arrays.asList(tmp));
        txtAmigo.setText("Tu amigo cree que la opci??n correcta es: " +tmp[0]);

        Button btnSalida = mDialog.findViewById(R.id.btnAceptar);
        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void comodinPublico(){

        mDialog.setContentView(R.layout.layoutpublico);
        mDialog.setCancelable(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        r = new Random();
        int[] tmp= new int[4];

        for(int i=0; i<4;i++){
            tmp[i]=  r.nextInt(11);// Entre 0 y 10
        }


        GraphView grafica = (GraphView) mDialog.findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(1, tmp[0]),
                new DataPoint(2, tmp[1]),
                new DataPoint(3, tmp[2]),
                new DataPoint(4, tmp[3])
        });
        grafica.addSeries(series);


        //set manual X bounds
        grafica.getViewport().setXAxisBoundsManual(true);
        grafica.getViewport().setMinX(0);
        grafica.getViewport().setMaxX(5);

        //set manual Y bounds
        grafica.getViewport().setYAxisBoundsManual(true);
        grafica.getViewport().setMinY(0);
        grafica.getViewport().setMaxY(10);


        // use static labels for horizontal labels
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(grafica);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","A", "B", "C","D",""});
        grafica.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        series.setSpacing(30);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

        Button btnSalida = mDialog.findViewById(R.id.btnAceptarPublico);
        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }

}

/*
        for(int i=0; i<4; i++){
            if(!arregloRb[i].isEnabled()){
                //no me recomiendes esa opcion
            }
        }
 */
