package es.jujoru.espanolesenlanba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import Async.AsyncEstadisticas;
import Modelos.AdaptadorJugadores;
import Modelos.Estadistica;
import Modelos.Jugador;

public class ActivityJugadores extends AppCompatActivity {
    ArrayList<Jugador> jugadores = new ArrayList<>();
    RecyclerView rvJugadores;
    private static String URL_STAT = "https://stats.nba.com/stats/playergamelog";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugadores);
        getSupportActionBar().setTitle(R.string.ajultimos);
        rvJugadores=(RecyclerView)findViewById(R.id.rvJugadores);
        rvJugadores.setHasFixedSize(true);
        rvJugadores.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if(isNetworkAvailable()){
            cargarRecyclerViewExamen();
        }else{
            Toast.makeText(getApplicationContext(), "Debes de tener conexión a internet para que la app funcione correctamente", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void cargarRecyclerViewExamen (){

        Jugador pau=new Jugador(2200,16,"Pau Gasol", "2200.png");
        Jugador marc=new Jugador(201188,33,"Marc Gasol", "201188.png");
        Jugador mirotic=new Jugador(202703,3,"Nikola Mirotic", "202703.png");
        Jugador rubio=new Jugador(201937,3,"Ricky Rubio", "201937.png");
        Jugador willy=new Jugador(1626195,41,"Willy Hernangomez", "1626195.png");
        Jugador juancho=new Jugador(1627823,41,"Juancho Hernangomez", "1627823.png");
        Jugador calderon=new Jugador(101181,81,"Jose Calderon", "101181.png");
        Jugador abrines=new Jugador(203518,8,"Alex Abrines", "203518.png");
        Jugador ibaka=new Jugador(201586,9,"Serge Ibaka", "201586.png");

        Collections.addAll(jugadores, pau,marc,rubio,mirotic,willy,abrines,juancho,calderon,ibaka);

        for (Jugador j:jugadores) {
            j.setEstadisticas(CargarEstadisticas(j.getId(),"2018-19","Regular%20Season",this));
        }
        AdaptadorJugadores adapter=new AdaptadorJugadores(this,jugadores);

        rvJugadores.setAdapter(adapter);

    }

    public ArrayList<Estadistica> CargarEstadisticas(int id, String Season, String typeSeason, Context context){

        String params = "?PlayerID="+id+"&Season="+Season+"&SeasonType="+typeSeason;
        String url = URL_STAT+params;
        AsyncEstadisticas myAsync = new AsyncEstadisticas(context, url);
       // myAsync.execute().get();
        ArrayList<Estadistica> estadisticas = null;
        try {
            estadisticas = myAsync.execute().get();
            Log.i("asdas","Asdas");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return estadisticas;
    }

    private Jugador obtenerJugador(int position){
        return jugadores.get(position);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
