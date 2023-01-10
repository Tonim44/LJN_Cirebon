package id.adiva.invoices;

import android.os.Parcel;
import android.os.Parcelable;

public class Invoices implements Parcelable {

    private String invoice_number, invoice_date, due_date, total_formatted, status, paid_date, subtotal_formatted, description;

    public Invoices(String invoice_number, String invoice_date, String status, String total_formatted, String due_date, String paid_date, String subtotal_formatted, String description) {
        this.invoice_number = invoice_number;
        this.invoice_date = invoice_date;
        this.due_date = due_date;
        this.total_formatted = total_formatted;
        this.status = status;
        this.paid_date = paid_date;
        this.subtotal_formatted = subtotal_formatted;
        this.description = description;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTotal_formatted() {
        return total_formatted;
    }

    public void setTotal_formatted(String total_formatted) {
        this.total_formatted = total_formatted; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(String paid_date) {
        this.paid_date = paid_date;
    }

    public String getSubtotal_formatted() {
        return subtotal_formatted;
    }

    public void setSubtotal_formatted(String subtotal_formatted) {
        this.subtotal_formatted = subtotal_formatted; }

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
        parcel.writeString(this.invoice_number);
        parcel.writeString(this.invoice_date);
        parcel.writeString(this.due_date);
        parcel.writeString(this.total_formatted);
        parcel.writeString(this.status);
        parcel.writeString(this.paid_date);
        parcel.writeString(this.subtotal_formatted);
        parcel.writeString(this.description);
    }

    protected Invoices(Parcel in ){
        this.invoice_number = in.readString();
        this.invoice_date = in.readString();
        this.due_date = in.readString();
        this.total_formatted = in.readString();
        this.status = in.readString();
        this.paid_date = in.readString();
        this.subtotal_formatted = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Invoices> CREATOR = new Parcelable.Creator<Invoices>(){
        @Override
        public Invoices createFromParcel(Parcel source) {
            return new Invoices(source);
        }

        @Override
        public Invoices[] newArray(int size) {
            return new Invoices[size];
        }
    };

}