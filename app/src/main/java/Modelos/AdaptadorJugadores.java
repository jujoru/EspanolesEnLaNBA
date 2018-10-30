package Modelos;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;

import SVGImage.SvgDecoder;
import SVGImage.SvgDrawableTranscoder;
import SVGImage.SvgSoftwareLayerSetter;
import es.jujoru.espanolesenlanba.ActivityEstadisticas;
import es.jujoru.espanolesenlanba.R;

public class AdaptadorJugadores
        extends RecyclerView.Adapter<AdaptadorJugadores.ViewHolderJugadores>
         {

             private GradientDrawable mGradientDrawable;
    private ArrayList<Jugador> jugadores;
             private Context mContext;

    public AdaptadorJugadores(Context context, ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.mContext= context;

    }


             @NonNull
             @Override
             public ViewHolderJugadores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View itemView = LayoutInflater.from(mContext)
                         .inflate(R.layout.item_jugador, parent, false);


                 ViewHolderJugadores tvh = new ViewHolderJugadores(itemView,parent.getContext(),mGradientDrawable);

                 return tvh;
             }

             @Override
             public void onBindViewHolder(@NonNull ViewHolderJugadores holder, int position) {
                     Jugador jugador = jugadores.get(position);
                 holder.bindExamen(jugador);
             }
             @Override
             public void onViewAttachedToWindow(ViewHolderJugadores holder) {
                 super.onViewAttachedToWindow(holder);
                 animateCircularReveal(holder.itemView);

             }
             @Override
    public int getItemCount() {
        return jugadores.size();
    }

             public void animateCircularReveal(View view) {
                 int centerX = 0;
                 int centerY = 0;
                 int startRadius = 0;
                 int endRadius = Math.max(view.getWidth(), view.getHeight());
                 Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
                 view.setVisibility(View.VISIBLE);
                 animation.start();
             }


static class ViewHolderJugadores extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private static String URL_IMAGE = "http://espanolesnba.jujoru.es/img/jugadores/";
    private static String URL_LOGO = "https://stats.nba.com/media/img/teams/logos/";

    private View mView;
    TextView tvNombre, tvDorsal, tvMIN, tvPTS, tvREB, tvASI, tvTAP, tvFecha;
    ImageView ivCabecera, ivRival, ivResultado;
    Jugador jugador;
    Context mContext;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    private GradientDrawable mGradientDrawable;

    public ViewHolderJugadores(View itemView,  Context c,GradientDrawable gradientDrawable) {
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
        mGradientDrawable = gradientDrawable;
        itemView.setOnClickListener(this);
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
        Glide.with(mContext).load(URL_IMAGE+imagen).placeholder(mGradientDrawable).into(ivCabecera);

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


    @Override
    public void onClick(View v) {



        Intent i = new Intent(mContext, ActivityEstadisticas.class);
        i.putExtra("JUG",jugador);
       // mContext.startActivity(i);


        Pair<View, String> p1 = Pair.create((View)ivCabecera, "transition_imagen");
        Pair<View, String> p2 = Pair.create((View)tvNombre, "transition_nombre");

        ActivityOptionsCompat options;
        Activity act = (AppCompatActivity) mContext;
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, p1, p2);



        mContext.startActivity(i, options.toBundle());

    }
}
}
