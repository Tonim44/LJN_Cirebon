package id.adiva.invoices;

import android.os.Parcel;
import android.os.Parcelable;

public class Departements implements Parcelable {

    private String department_name, description, judul, prioitas, pesan;

    public Departements(String prioitas, String judul, String pesan) {
        this.judul = judul;
        this.prioitas = prioitas;
        this.pesan = pesan;
    }

    public Departements (String department_name, String description) {
        this.department_name = department_name;
        this.description = description;
    }

    public String getPrioitas() {
        return prioitas;
    }

    public void setPrioitas(String prioitas) {
        this.prioitas = prioitas;}

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.department_name);
        parcel.writeString(this.description);
        parcel.writeString(this.judul);
        parcel.writeString(this.prioitas);
        parcel.writeString(this.pesan);
    }

    protected Departements(Parcel in ){
        this.department_name = in.readString();
        this.description = in.readString();
        this.judul = in.readString();
        this.prioitas = in.readString();
        this.pesan = in.readString();
    }

    public static final Creator<Departements> CREATOR = new Creator<Departements>(){
        @Override
        public Departements createFromParcel(Parcel source) {
            return new Departements(source);
        }

        @Override
        public Departements[] newArray(int size) {
            return new Departements[size];
        }
    };
}
