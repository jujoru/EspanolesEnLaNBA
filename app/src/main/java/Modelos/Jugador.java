package Modelos;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;


public class Jugador implements Parcelable {

    int id;
    int dorsal;
    String nombre;
    String imagen_cabecera;
    ArrayList<Estadistica> estadisticas=new ArrayList<>();


    public Jugador(int id, int dorsal, String nombre, String imagen_cabecera) {
        this.id = id;
        this.dorsal = dorsal;
        this.nombre = nombre;
        this.imagen_cabecera=imagen_cabecera;
    }


    protected Jugador(Parcel in) {
        id = in.readInt();
        dorsal = in.readInt();
        nombre = in.readString();
        imagen_cabecera = in.readString();
        estadisticas = in.createTypedArrayList(Estadistica.CREATOR);
    }

    public static final Creator<Jugador> CREATOR = new Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Estadistica> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(ArrayList<Estadistica> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public String getImagen_cabecera() {
        return imagen_cabecera;
    }

    public void setImagen_cabecera(String imagen_cabecera) {
        this.imagen_cabecera = imagen_cabecera;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(dorsal);
        dest.writeString(nombre);
        dest.writeString(imagen_cabecera);
        dest.writeTypedList(estadisticas);
    }
}
