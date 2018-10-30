package Modelos;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

import SVGImage.SvgDecoder;
import SVGImage.SvgDrawableTranscoder;
import SVGImage.SvgSoftwareLayerSetter;
import es.jujoru.espanolesenlanba.R;

public class ViewHolderJugadoresOLD extends RecyclerView.ViewHolder {
    private static String URL_IMAGE = "http://espanolesnba.jujoru.es/img/jugadores/";
    private static String URL_LOGO = "https://stats.nba.com/media/img/teams/logos/";

    private View mView;
    TextView tvNombre, tvDorsal, tvMIN, tvPTS, tvREB, tvASI, tvTAP, tvFecha;
    ImageView ivCabecera, ivRival, ivResultado;
    Jugador jugador;
    Context mContext;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public ViewHolderJugadoresOLD(View itemView, Context c) {
        super(itemView);
        this.mView = itemView;
        tvNombre = (TextView)itemView.findViewById(R.id.itemJugadorNombre);
        tvDorsal = (TextView)itemView.findViewById(R.id.itemJugadorDorsal);
        tvMIN = (TextView)itemView.findViewById(R.id.itemRowMIN);
        tvPTS = (TextView)itemView.findViewById(R.id.itemRowPTS);
        tvREB = (TextView)itemView.findViewById(R.id.itemRowREB);
        tvASI = (TextView)itemView.findViewById(R.id.itemRowASI);
        tvTAP = (TextView)itemView.findViewById(R.id.itemRowTA);
        tvDorsal = (TextView)itemView.findViewById(R.id.itemJugadorDorsal);

       // tvFecha = (TextView)itemView.findViewById(R.id.itemJugadorEstadistica);
        ivCabecera = (ImageView)itemView.findViewById(R.id.itemImagenCabecera);
        ivRival = (ImageView)itemView.findViewById(R.id.ItemImagenRival);
        ivResultado = (ImageView)itemView.findViewById(R.id.itemImagenResultado);

        this.mContext=c;


    }

    public void bindExamen(Jugador j){
        jugador=j;

        tvNombre.setText(j.getNombre());
        tvDorsal.setText(""+j.getDorsal());
        String imagen=j.getImagen_cabecera();

        if(j.getEstadisticas().get(0).getResultado().equals("W")){
            ivResultado.setImageResource(R.drawable.ic_check);
        }else if(j.getEstadisticas().get(0).getResultado().equals("L")){
            ivResultado.setImageResource(R.drawable.ic_remove);

        }else{
            ivResultado.setImageResource(R.drawable.ic_directo);
        }

        String logo = GetLogoRival(j.getEstadisticas().get(0).getPartido());

        //int res = mContext.getResources().getSystem().getIdentifier(logo, "drawable", mContext.getPackageName());
        /*int res = mContext.getResources().getIdentifier(logo, "drawable", mContext.getPackageName());
        ivRival.setImageResource(res);*/

       //tvFecha.setText(j.getEstadisticas().get(0).getFecha());

        tvPTS.setText(""+j.getEstadisticas().get(0).getPuntos());
        tvMIN.setText(""+j.getEstadisticas().get(0).getMinutos());
        tvREB.setText(""+j.getEstadisticas().get(0).getRebotes());
        tvASI.setText(""+j.getEstadisticas().get(0).getAsistencias());
        tvTAP.setText(""+j.getEstadisticas().get(0).getTapones());
        tvMIN.setText(""+j.getEstadisticas().get(0).getMinutos());
        Glide.with(mContext).load(URL_IMAGE+imagen).into(ivCabecera);

        CargarImagenSVG(j.getEstadisticas().get(0).getPartido());

    }

    private void CargarImagenSVG(String logo){
        GenericRequestBuilder requestBuilder;
        requestBuilder = Glide.with(mContext)
                .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
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

    private String GetLogoRival(String str){
        String[] array_str = str.split(" ");
        String logo = URL_LOGO+array_str[array_str.length-1].toLowerCase().concat("_logo.svg");

        return logo;
    }




}
