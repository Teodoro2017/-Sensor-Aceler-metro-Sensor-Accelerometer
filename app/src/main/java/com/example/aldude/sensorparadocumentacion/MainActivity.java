package com.example.aldude.sensorparadocumentacion;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

//Cuando creamos el activity nos viene por defecto la siguiente línea de código "public class MainActivity extends AppCompatActivity"
//Pero ahora que estamos trabajando con sensores tenemos que agregarle lo siguiente "implements SensorEventListener"
// Nos marcará error al inicio, esto se soluciona pasando el puntero sobre el foco rojo que nos aparece
//Sobre la línea del código, le damos a implementar métodos, y automáticamente se escrbirán las líneas que se ven en la parte inferior

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Declaramos las variables, entre ellas el de acerlerómetro
    private Sensor acelerometro;
    TextView txt;
    SensorManager primerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView)findViewById(R.id.texto);

        //Esta línea me conecta con el servicio de los sensores en el celular
        primerSensor = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Aquí estableceremos qué tipo de sensor utilizaremos
        acelerometro = primerSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        //Aquí declaramos que en una lista nos arrojará todos los sensores de los que dispone nuestro celular
        //Pondré este código como comentario ya que no lo necesitaremos, en caso de que quiera verlo solo le quita los /*
        /*List<Sensor> mList = primerSensor.getSensorList(Sensor.TYPE_ALL);
        for (int i = 1; i < mList.size(); i++){
            txt.append("\n" + mList.get(i).getName() + "\n" + mList.get(i).getVendor() + "\n" + mList.get(i).getVersion());
        }*/

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Declaramos tres valores X,Y,Z para nustro sensor con Float
        float x, y,z;
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        //Mostramos los valores de X,Y,Z que nos da nuestro acelerómetro
        //Copiamos la línea de código que fue hecha comentario desde el "txt" hasta el ";"
        //Una vez copiado la línea entonces borramos aquellos que tengan mList().get...(), y en su lugar trasladamos nuestro valores X,Y,Z
        txt.setText("");
        txt.append("\n" + "El valor de x es: " + x + "\n" + "El valor de y es: " + y + "\n" + "El valor de z es: " + z);
        //Con esa línea nos arrojará los valores de los valores del acelerómetro conforme el sensor vaya cambiando
        //Nos daremos cuenta de que funciona que cuando movemos el celular de un lado a otro los núnreos iniciales cambian, y cuando lo tenemos estático
        //los que son dinámicos son los decimales
    }

    //Actúa cuando cambia la precisión del sensor, cosa que no haremos en este ejercicio
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //Las siguientes líneas de código que son las funciones onResume y onPuase
    //Son importantes estas líneas porque sin ellas, el acelerómetro al minimizar la app, seguirá trabajando
    //Y eso ocasiona un consumo indiscriminado de la batería

    //Para que el código se autocomplete solo debemos escribir la palabra protected y nos aparecerá una lista de opciones
    //Elegimos la palabra onResume
    @Override
    protected void onResume() {
        super.onResume();

        //El SENSOR_DELAY_NORMAL lo utilizamos para la velocidad que tomará los valores de nuestro sensor, es decir la velocidad del acelerómetro
        primerSensor.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Para que el código se autocomplete solo debemos escribir la palabra protected y nos aparecerá una lista de opciones
    //Elegimos la palabra onPause
    @Override
    protected void onPause() {
        super.onPause();
        primerSensor.unregisterListener(this);
    }
}
