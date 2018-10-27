package es.jujoru.espanolesenlanba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import Modelos.Jugador;

public class ActivityEstadisticas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            Jugador j = bundle.getParcelable("JUG");
            Log.i("asda","asdas");
        }
    }
}
