package Modelos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.jujoru.espanolesenlanba.R;

public class AdaptadorJugadores
        extends RecyclerView.Adapter<ViewHolderJugadores>
        implements View.OnClickListener {


    private ArrayList<Jugador> jugadores;
    private View.OnClickListener listener;


    public AdaptadorJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public ViewHolderJugadores onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_jugador, viewGroup, false);

        itemView.setOnClickListener(this);
        ViewHolderJugadores tvh = new ViewHolderJugadores(itemView,viewGroup.getContext());

        return tvh;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderJugadores viewHolder, int pos) {
        Jugador jugador = jugadores.get(pos);
        viewHolder.bindExamen(jugador);
    }


    @Override
    public int getItemCount() {
        return jugadores.size();
    }

}
