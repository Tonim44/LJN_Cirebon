package id.adiva.invoices;

import android.os.Parcel;
import android.os.Parcelable;

public class Subcription implements Parcelable {

    private String subcription_code, subcription_date, subtotal_formatted, totall_formatted, status, next_invoice_date,  tax_formatted, description ;

    public Subcription(String subcription_code, String subcription_date, String subtotal_formatted, String totall_formatted, String status, String next_invoice_date, String tax_formatted, String description) {
        this.subcription_code = subcription_code;
        this.subcription_date = subcription_date;
        this.subtotal_formatted = subtotal_formatted;
        this.totall_formatted = totall_formatted;
        this.status = status;
        this.next_invoice_date = next_invoice_date;
        this.tax_formatted = tax_formatted;
        this.description = description;
    }

    public String getSubcription_code() {
        return subcription_code;
    }

    public void setSubcription_code(String subcription_code) { this.subcription_code = subcription_code; }

    public String getSubcription_date() {
        return subcription_date;
    }

    public void setSubcription_date(String subcription_date) { this.subcription_date = subcription_date; }

    public String getSubtotal_formatted() {
        return subtotal_formatted;
    }

    public void setSubtotal_formatted(String subtotal_formatted) { this.subtotal_formatted = subtotal_formatted; }

    public String getTotall_formatted() {
        return totall_formatted;
    }

    public void setTotall_formatted(String totall_formatted) { this.totall_formatted = totall_formatted; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNext_invoice_date() {
        return next_invoice_date;
    }

    public void setNext_invoice_date(String next_invoice_date) { this.next_invoice_date = next_invoice_date; }

    public String getTax_formatted() {
        return tax_formatted;
    }

    public void setTax_formatted(String tax_formatted) {
        this.tax_formatted = tax_formatted;
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
        parcel.writeString(this.subcription_code);
        parcel.writeString(this.subcription_date);
        parcel.writeString(this.subtotal_formatted);
        parcel.writeString(this.totall_formatted);
        parcel.writeString(this.status);
        parcel.writeString(this.next_invoice_date);
        parcel.writeString(this.tax_formatted);
        parcel.writeString(this.description);
    }

    protected Subcription(Parcel in ){
        this.subcription_code = in.readString();
        this.subcription_date = in.readString();
        this.subtotal_formatted = in.readString();
        this.totall_formatted = in.readString();
        this.status = in.readString();
        this.next_invoice_date = in.readString();
        this.tax_formatted = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Subcription> CREATOR = new Parcelable.Creator<Subcription>(){
        @Override
        public Subcription createFromParcel(Parcel source) {
            return new Subcription(source);
        }

        @Override
        public Subcription[] newArray(int size) {
            return new Subcription[size];
        }
    };

}
