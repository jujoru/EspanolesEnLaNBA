package es.jujoru.espanolesenlanba;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Modelos.Estadistica;
import Modelos.Jugador;
import SVGImage.SvgDecoder;
import SVGImage.SvgDrawableTranscoder;
import SVGImage.SvgSoftwareLayerSetter;

public class ActivityEstadisticas extends AppCompatActivity {
    private static String URL_IMAGE = "http://espanolesnba.jujoru.es/img/jugadores/";
    private static String URL_LOGO = "https://stats.nba.com/media/img/teams/logos/";

    TextView tvNombre, tvDorsal, tvMIN, tvPTS, tvREB, tvASI, tvTAP, tvFecha, tvREBOFE,tvREBDEF, tvROB, tvPER;
    TextView tvT2C, tvT2I,tvT2PER, tvT3C, tvT3I,tvT3PER, tvT1C, tvT1I,tvT1PER;
    ImageView ivRival, ivRes, ivJugador;
    Jugador j=null;
    Button btnAnterior, btnPosterior;
    int posicion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            j = bundle.getParcelable("JUG");

            CargarElementos();
            //Create a placeholder gray scrim while the image loads
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.GRAY);



            //Set the text from the Intent extra
            tvNombre.setText(j.getNombre());


            Glide.with(getApplicationContext()).load(URL_IMAGE+j.getImagen_cabecera()).placeholder(gradientDrawable).into(ivJugador);

        }
    }
    private void MostrarEstaditicas(){
        tvMIN.setText(""+j.getEstadisticas().get(posicion).getMinutos());
        tvPTS.setText(""+j.getEstadisticas().get(posicion).getPuntos());
        tvREB.setText(""+j.getEstadisticas().get(posicion).getRebotes());
        tvREBOFE.setText(""+j.getEstadisticas().get(posicion).getRebotes_ofensivos());
        tvREBDEF.setText(""+j.getEstadisticas().get(posicion).getRebotes_defensivos());
        tvASI.setText(""+j.getEstadisticas().get(posicion).getAsistencias());
        tvTAP.setText(""+j.getEstadisticas().get(posicion).getTapones());
        tvROB.setText(""+j.getEstadisticas().get(posicion).getRobos());
        tvPER.setText(""+j.getEstadisticas().get(posicion).getPerdidas());
        tvT2C.setText(""+j.getEstadisticas().get(posicion).getT2c());
        tvT2I.setText(""+j.getEstadisticas().get(posicion).getT2i());
        tvT2PER.setText(""+NumberFormatMethod(j.getEstadisticas().get(posicion).getT2per()));
        tvT3C.setText(""+j.getEstadisticas().get(posicion).getT3c());
        tvT3I.setText(""+j.getEstadisticas().get(posicion).getT3i());
        tvT3PER.setText(""+NumberFormatMethod(j.getEstadisticas().get(posicion).getT3per()*100));
        tvT1C.setText(""+j.getEstadisticas().get(posicion).getTlc());
        tvT1I.setText(""+j.getEstadisticas().get(posicion).getTli());
        tvT1PER.setText(""+NumberFormatMethod(j.getEstadisticas().get(posicion).getTlper()));

        tvFecha.setText(j.getEstadisticas().get(posicion).getFecha());
        String logo = GetLogoRival(j.getEstadisticas().get(posicion).getPartido());
        CargarImagenSVG(logo);

        ivJugador = (ImageView) findViewById(R.id.EstadisticaImagenJugador);
        Glide.with(getApplicationContext()).load(URL_IMAGE+j.getImagen_cabecera()).into(ivJugador);

        ivRes = (ImageView) findViewById(R.id.EstadisticaImagenResultado);
        if(j.getEstadisticas().get(posicion).getResultado().equals("W")){
            ivRes.setImageResource(R.drawable.ic_check);
        }else if(j.getEstadisticas().get(posicion).getResultado().equals("L")){
            ivRes.setImageResource(R.drawable.ic_remove);

        }else{
            ivRes.setImageResource(R.drawable.ic_directo);
        }
    }
    public void btnClickMedia(View view){
        ArrayList<Estadistica> estadisticas = j.getEstadisticas();
        double min=0;
        double puntos=0;
        double reb=0;
        double rebof=0;
        double rebdef=0;
        double asi=0;
        double tap=0;
        double rob=0;
        double per=0;
        double t2i=0;
        double t2c=0;
        double t3i=0;
        double t3c=0;
        double t1i=0;
        double t1c=0;
        int tam = estadisticas.size();
        double total_min=0;
        double total_puntos=0;
        double total_reb=0;
        double total_rebof=0;
        double total_rebdef=0;
        double total_asi=0;
        double total_tap=0;
        double total_rob=0;
        double total_per=0;
        double total_t2i=0;
        double total_t2c=0;
        double total_t3i=0;
        double total_t3c=0;
        double total_t1i=0;
        double total_t1c=0;
        for (Estadistica e: estadisticas){
            min+=e.getMinutos();
            puntos+=e.getPuntos();
            reb+=e.getRebotes();
            rebof+=e.getRebotes_ofensivos();
            rebdef+=e.getRebotes_defensivos();
            asi+=e.getAsistencias();
            tap+=e.getTapones();
            rob+=e.getRobos();
            per+=e.getPerdidas();
            t2i+=e.getT2i();
            t2c+=e.getT2c();
            t3i+=e.getT3i();
            t3c+=e.getT3c();
            t1i+=e.getTli();
            t1c+=e.getTlc();
        }

        total_min=min/tam;
        total_puntos=puntos/tam;
        total_reb=reb/tam;
        total_rebof=rebof/tam;
        total_rebdef=rebdef/tam;
        total_asi=asi/tam;
        total_tap=tap/tam;
        total_rob=rob/tam;
        total_per=per/tam;
        total_t2i=t2i;
        total_t2c=t2c;
        double t2per = t2c*100/t2i;
        total_t3i=t3i;
        total_t3c=t3c;
        double t3per = t3c*100/t3i;
        total_t1i=t1i;
        total_t1c=t1c;
        double t1per = t1c*100/t1i;

        tvMIN.setText(NumberFormatMethod(total_min)+"");
        tvPTS.setText(""+NumberFormatMethod(total_puntos));
        tvREB.setText(""+NumberFormatMethod(total_reb));
        tvREBOFE.setText(""+NumberFormatMethod(total_rebof));
        tvREBDEF.setText(""+NumberFormatMethod(total_rebdef));
        tvASI.setText(""+NumberFormatMethod(total_asi));
        tvTAP.setText(""+NumberFormatMethod(total_tap));
        tvROB.setText(""+NumberFormatMethod(total_rob));
        tvPER.setText(""+NumberFormatMethod(total_per));
        tvT2C.setText(""+NumberFormatMethod(total_t2c));
        tvT2I.setText(""+NumberFormatMethod(total_t2i));
        tvT2PER.setText(""+NumberFormatMethod(t2per));
        tvT3C.setText(""+NumberFormatMethod(total_t3c));
        tvT3I.setText(""+NumberFormatMethod(total_t3i));
        tvT3PER.setText(""+NumberFormatMethod(t3per));
        tvT1C.setText(""+NumberFormatMethod(total_t1c));
        tvT1I.setText(""+NumberFormatMethod(total_t1i));
        tvT1PER.setText(""+NumberFormatMethod(t1per));
        tvFecha.setText("Media de la temporada");
        ivRival.setImageResource(R.drawable.ic_iconapp_layer);
        ivRes.setImageResource(R.drawable.ic_iconapp_layer);

    }
    private void CargarElementos(){
        tvFecha = (TextView)findViewById(R.id.tvEstadisticasFecha);
        tvNombre = (TextView)findViewById(R.id.estadisticaRowNombre);
        tvNombre.setText(j.getNombre());
        tvMIN = (TextView)findViewById(R.id.estadisticaRowMIN);

        tvPTS = (TextView)findViewById(R.id.estadisticaRowPTS);
         tvREB = (TextView)findViewById(R.id.estadisticaRowREB);

        tvREBOFE = (TextView)findViewById(R.id.estadisticaRowROFE);

        tvREBDEF = (TextView)findViewById(R.id.estadisticaRowRDEF);
        tvASI = (TextView)findViewById(R.id.estadisticaRowASI);
        tvTAP = (TextView)findViewById(R.id.estadisticaRowTA);
        tvROB = (TextView)findViewById(R.id.estadisticaRowROB);
        tvPER = (TextView)findViewById(R.id.estadisticaRowPER);
        tvT2C = (TextView)findViewById(R.id.estadisticaRowT2C);
        tvT2I = (TextView)findViewById(R.id.estadisticaRowT2I);
        tvT2PER = (TextView)findViewById(R.id.estadisticaRowT2PER);
        tvT3C = (TextView)findViewById(R.id.estadisticaRowT3C);
        tvT3I = (TextView)findViewById(R.id.estadisticaRowT3I);
        tvT3PER = (TextView)findViewById(R.id.estadisticaRowT3PER);
        tvT1C = (TextView)findViewById(R.id.estadisticaRowT1C);
        tvT1I = (TextView)findViewById(R.id.estadisticaRowT1I);
        tvT1PER = (TextView)findViewById(R.id.estadisticaRowT1PER);

        ivRival = (ImageView) findViewById(R.id.EstadisticaImagenRival);
        //btnPosterior = (Button) findViewById(R.id.btnEstadisticaPosterior);
        //btnAnterior = (Button) findViewById(R.id.btEstadisticasAnterior);
        MostrarEstaditicas();

    }
    public void btnClickAnterior(View view){

        if(posicion < j.getEstadisticas().size()-1){
            posicion++;
            MostrarEstaditicas();
        }else{
            posicion = j.getEstadisticas().size()-1;
        }


    }
    public void btnClickPosterior(View view){
        if(posicion > 0){
            posicion--;
            MostrarEstaditicas();
        }else{
            posicion = 0;
        }

    }
    private String GetLogoRival(String str){
        String[] array_str = str.split(" ");
        String logo = URL_LOGO+array_str[array_str.length-1].toLowerCase().concat("_logo.svg");

        return logo;
    }
    private String NumberFormatMethod(double num){


        String pattern = "##.#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        String format = decimalFormat.format(num);

        return format;

    }
    private void CargarImagenSVG(String logo){
        try {


        GenericRequestBuilder requestBuilder;
        requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, getApplicationContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_iconapp_layer)
                .error(R.drawable.ic_iconapp_layer)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

      // String url_link = GetLogoRival(logo);
        Uri uri = Uri.parse(logo);
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(ivRival);
        }catch (Exception e){
            Log.e("errorsvg", e.getMessage());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
