package Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Estadistica implements Parcelable {

    String fecha;
    String partido;
    String resultado;
    int minutos;
    int puntos;
    int rebotes;
    int asistencias;
    int tapones;
    int perdidas;
    int robos;
    int rebotes_ofensivos;
    int rebotes_defensivos;
    int t2i;
    int t2c;
    double t2per;
    int tli;
    int tlc;
    double tlper;
    int t3i;
    int t3c;
    double t3per;



    public Estadistica(String fecha, String partido, String resultado, int minutos, int puntos, int rebotes, int asistencias, int tapones, int perdidas, int robos, int rebotes_ofensivos, int rebotes_defensivos, int t2i, int t2c, double t2per, int tli, int tlc, double tlper, int t3i, int t3c, double t3per) {
        this.fecha = fecha;
        this.partido = partido;
        this.resultado = resultado;
        this.minutos = minutos;
        this.puntos = puntos;
        this.rebotes = rebotes;
        this.asistencias = asistencias;
        this.tapones = tapones;
        this.perdidas = perdidas;
        this.robos = robos;
        this.rebotes_ofensivos = rebotes_ofensivos;
        this.rebotes_defensivos = rebotes_defensivos;
        this.t2i = t2i;
        this.t2c = t2c;
        this.t2per = t2per;
        this.tli = tli;
        this.tlc = tlc;
        this.tlper = tlper;
        this.t3i = t3i;
        this.t3c = t3c;
        this.t3per = t3per;
    }

    protected Estadistica(Parcel in) {
        fecha = in.readString();
        partido = in.readString();
        resultado = in.readString();
        minutos = in.readInt();
        puntos = in.readInt();
        rebotes = in.readInt();
        asistencias = in.readInt();
        tapones = in.readInt();
        perdidas = in.readInt();
        robos = in.readInt();
        rebotes_ofensivos = in.readInt();
        rebotes_defensivos = in.readInt();
        t2i = in.readInt();
        t2c = in.readInt();
        t2per = in.readDouble();
        tli = in.readInt();
        tlc = in.readInt();
        tlper = in.readDouble();
        t3i = in.readInt();
        t3c = in.readInt();
        t3per = in.readDouble();
    }

    public static final Creator<Estadistica> CREATOR = new Creator<Estadistica>() {
        @Override
        public Estadistica createFromParcel(Parcel in) {
            return new Estadistica(in);
        }

        @Override
        public Estadistica[] newArray(int size) {
            return new Estadistica[size];
        }
    };

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getTapones() {
        return tapones;
    }

    public void setTapones(int tapones) {
        this.tapones = tapones;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getRobos() {
        return robos;
    }

    public void setRobos(int robos) {
        this.robos = robos;
    }

    public int getRebotes_ofensivos() {
        return rebotes_ofensivos;
    }

    public void setRebotes_ofensivos(int rebotes_ofensivos) {
        this.rebotes_ofensivos = rebotes_ofensivos;
    }

    public int getRebotes_defensivos() {
        return rebotes_defensivos;
    }

    public void setRebotes_defensivos(int rebotes_defensivos) {
        this.rebotes_defensivos = rebotes_defensivos;
    }

    public int getT2i() {
        return t2i;
    }

    public void setT2i(int t2i) {
        this.t2i = t2i;
    }

    public int getT2c() {
        return t2c;
    }

    public void setT2c(int t2c) {
        this.t2c = t2c;
    }

    public double getT2per() {
        return t2per;
    }

    public void setT2per(double t2per) {
        this.t2per = t2per;
    }

    public int getTli() {
        return tli;
    }

    public void setTli(int tli) {
        this.tli = tli;
    }

    public int getTlc() {
        return tlc;
    }

    public void setTlc(int tlc) {
        this.tlc = tlc;
    }

    public double getTlper() {
        return tlper;
    }

    public void setTlper(double tlper) {
        this.tlper = tlper;
    }

    public int getT3i() {
        return t3i;
    }

    public void setT3i(int t3i) {
        this.t3i = t3i;
    }

    public int getT3c() {
        return t3c;
    }

    public void setT3c(int t3c) {
        this.t3c = t3c;
    }

    public double getT3per() {
        return t3per;
    }

    public void setT3per(double t3per) {
        this.t3per = t3per;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fecha);
        dest.writeString(partido);
        dest.writeString(resultado);
        dest.writeInt(minutos);
        dest.writeInt(puntos);
        dest.writeInt(rebotes);
        dest.writeInt(asistencias);
        dest.writeInt(tapones);
        dest.writeInt(perdidas);
        dest.writeInt(robos);
        dest.writeInt(rebotes_ofensivos);
        dest.writeInt(rebotes_defensivos);
        dest.writeInt(t2i);
        dest.writeInt(t2c);
        dest.writeDouble(t2per);
        dest.writeInt(tli);
        dest.writeInt(tlc);
        dest.writeDouble(tlper);
        dest.writeInt(t3i);
        dest.writeInt(t3c);
        dest.writeDouble(t3per);
    }
}
