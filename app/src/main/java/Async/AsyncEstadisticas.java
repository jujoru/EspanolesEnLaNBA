package Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Modelos.Estadistica;

public class AsyncEstadisticas extends AsyncTask<Void, Void, ArrayList<Estadistica>> {
    Context c;
    ProgressDialog dialog;
    String url_stat;

    ArrayList<Estadistica> estadisticas = new ArrayList<Estadistica>();
    public AsyncEstadisticas(Context c, String url){

        this.c=c;
        this.url_stat = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       /* dialog= new ProgressDialog(c);
        dialog.setMessage("Cargando datos");
        dialog.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
        dialog.setCancelable(false);
        dialog.setMax(100);
        dialog.show();*/

    }



    @Override
    protected ArrayList<Estadistica> doInBackground(Void... voids) {
        String result = null;
        try {
            URL url = new URL(url_stat);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            result = inputStreamToString(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("resultSets");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray jsonArray2  = jsonArray.getJSONObject(i).getJSONArray("rowSet");

                for (int j=0; j<jsonArray2.length(); j++){
                    JSONArray jsonArray3 = jsonArray2.getJSONArray(j);

                    String fecha = jsonArray3.getString(3);
                    String rival = jsonArray3.getString(4);
                    String resultado = jsonArray3.getString(5);
                    int minutos = jsonArray3.getInt(6);
                    int t2i = jsonArray3.getInt(8);
                    int t2c = jsonArray3.getInt(7);
                    double t2per = jsonArray3.getDouble(9)*100;
                    int t3i = jsonArray3.getInt(11);
                    int t3c = jsonArray3.getInt(10);
                    double t3per = jsonArray3.getDouble(12);
                    int tli = jsonArray3.getInt(14);
                    int tlc = jsonArray3.getInt(13);
                    double tlper = jsonArray3.getDouble(15)*100;
                    int puntos = jsonArray3.getInt(24);
                    int rebotes = jsonArray3.getInt(18);
                    int rebotes_def= jsonArray3.getInt(17);
                    int rebotes_of = jsonArray3.getInt(16);
                    int asistencias = jsonArray3.getInt(19);
                    int tapones = jsonArray3.getInt(21);
                    int robos = jsonArray3.getInt(20);
                    int perdidas = jsonArray3.getInt(22);

                    Estadistica e=new Estadistica(fecha,rival,resultado,minutos,puntos,rebotes,asistencias,tapones,perdidas,robos,rebotes_of,rebotes_def,t2i,t2c,t2per,tli,tlc,tlper,t3i,t3c,t3per);

                    estadisticas.add(e);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return estadisticas;
    }

    @Override
    protected void onPostExecute(ArrayList<Estadistica> stats) {
        super.onPostExecute(stats);
      /*  dialog.dismiss();*/


    }

    public ArrayList<Estadistica> GetEstadisticas(){
        return estadisticas;
    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }
}

