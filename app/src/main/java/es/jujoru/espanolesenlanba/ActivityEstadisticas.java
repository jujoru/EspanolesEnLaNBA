package es.jujoru.espanolesenlanba;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

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
    int posicion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            j = bundle.getParcelable("JUG");
            Log.i("asda","asdas");
            CargarElementos();
            //Create a placeholder gray scrim while the image loads
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.GRAY);



            //Set the text from the Intent extra
            tvNombre.setText(j.getNombre());


            Glide.with(getApplicationContext()).load(URL_IMAGE+j.getImagen_cabecera()).placeholder(gradientDrawable).into(ivJugador);

        }
    }
    
    private void CargarElementos(){
        tvFecha = (TextView)findViewById(R.id.tvEstadisticasFecha);
        tvFecha.setText(j.getEstadisticas().get(posicion).getFecha());
        tvNombre = (TextView)findViewById(R.id.estadisticaRowNombre);
        tvNombre.setText(j.getNombre());
        tvMIN = (TextView)findViewById(R.id.estadisticaRowMIN);
        tvMIN.setText(""+j.getEstadisticas().get(posicion).getMinutos());
        tvPTS = (TextView)findViewById(R.id.estadisticaRowPTS);
        tvPTS.setText(""+j.getEstadisticas().get(posicion).getPuntos());
        tvREB = (TextView)findViewById(R.id.estadisticaRowREB);
        tvREB.setText(""+j.getEstadisticas().get(posicion).getRebotes());
        tvASI = (TextView)findViewById(R.id.estadisticaRowASI);
        tvASI.setText(""+j.getEstadisticas().get(posicion).getAsistencias());
        tvTAP = (TextView)findViewById(R.id.estadisticaRowTA);
        tvTAP.setText(""+j.getEstadisticas().get(posicion).getTapones());
        tvROB = (TextView)findViewById(R.id.estadisticaRowROB);
        tvROB.setText(""+j.getEstadisticas().get(posicion).getRobos());
        tvPER = (TextView)findViewById(R.id.estadisticaRowPER);
        tvPER.setText(""+j.getEstadisticas().get(posicion).getPerdidas());
        tvT2C = (TextView)findViewById(R.id.estadisticaRowT2C);
        tvT2C.setText(""+j.getEstadisticas().get(posicion).getT2c());
        tvT2I = (TextView)findViewById(R.id.estadisticaRowT2I);
        tvT2I.setText(""+j.getEstadisticas().get(posicion).getT2i());
        tvT2PER = (TextView)findViewById(R.id.estadisticaRowT2PER);
        tvT2PER.setText(""+j.getEstadisticas().get(posicion).getT2per());
        tvT3C = (TextView)findViewById(R.id.estadisticaRowT3C);
        tvT3C.setText(""+j.getEstadisticas().get(posicion).getT3c());
        tvT3I = (TextView)findViewById(R.id.estadisticaRowT3I);
        tvT3I.setText(""+j.getEstadisticas().get(posicion).getT3i());
        tvT3PER = (TextView)findViewById(R.id.estadisticaRowT3PER);
        tvT3PER.setText(""+j.getEstadisticas().get(posicion).getT3per());
        tvT1C = (TextView)findViewById(R.id.estadisticaRowT1C);
        tvT1C.setText(""+j.getEstadisticas().get(posicion).getTlc());
        tvT1I = (TextView)findViewById(R.id.estadisticaRowT1I);
        tvT1I.setText(""+j.getEstadisticas().get(posicion).getTli());
        tvT1PER = (TextView)findViewById(R.id.estadisticaRowT1PER);
        tvT1PER.setText(""+j.getEstadisticas().get(posicion).getTlper());
        ivRival = (ImageView) findViewById(R.id.EstadisticaImagenRival);
        String logo = GetLogoRival(j.getEstadisticas().get(0).getPartido());
        CargarImagenSVG(logo);

        ivJugador = (ImageView) findViewById(R.id.EstadisticaImagenJugador);
        Glide.with(getApplicationContext()).load(URL_IMAGE+j.getImagen_cabecera()).into(ivJugador);

        ivRes = (ImageView) findViewById(R.id.EstadisticaImagenResultado);
        if(j.getEstadisticas().get(0).getResultado().equals("W")){
            ivRes.setImageResource(R.drawable.ic_check);
        }else if(j.getEstadisticas().get(0).getResultado().equals("L")){
            ivRes.setImageResource(R.drawable.ic_remove);

        }else{
            ivRes.setImageResource(R.drawable.ic_directo);
        }
    }

    private String GetLogoRival(String str){
        String[] array_str = str.split(" ");
        String logo = URL_LOGO+array_str[array_str.length-1].toLowerCase().concat("_logo.svg");

        return logo;
    }
    private void CargarImagenSVG(String logo){
        GenericRequestBuilder requestBuilder;
        requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_directo)
                .error(R.drawable.ic_remove)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        String url_link = GetLogoRival(logo);
        Uri uri = Uri.parse(url_link);
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(ivRival);
    }
}
