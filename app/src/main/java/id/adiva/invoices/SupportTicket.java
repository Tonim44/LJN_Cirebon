package id.adiva.invoices;

import android.os.Parcel;
import android.os.Parcelable;

public class SupportTicket implements Parcelable {

    private String customer_name, department_name, title, contentd, status, priority, date;

    public SupportTicket(String customer_name, String department_name, String title, String contentd, String status, String priority, String date) {
        this.customer_name = customer_name;
        this.department_name = department_name;
        this.title = title;
        this.contentd = contentd;
        this.status = status;
        this.priority = priority;
        this.date = date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentd() {
        return contentd;
    }

    public void setContentd(String contentd) {
        this.contentd = contentd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.customer_name);
        parcel.writeString(this.department_name);
        parcel.writeString(this.title);
        parcel.writeString(this.contentd);
        parcel.writeString(this.priority);
        parcel.writeString(this.date);
        parcel.writeString(this.status);
    }

    protected SupportTicket(Parcel in ){
        this.customer_name = in.readString();
        this.department_name = in.readString();
        this.title = in.readString();
        this.contentd = in.readString();
        this.priority = in.readString();
        this.date = in.readString();
        this.status = in.readString();
    }

    public static final Creator<SupportTicket> CREATOR = new Creator<SupportTicket>(){
        @Override
        public SupportTicket createFromParcel(Parcel source) {
            return new SupportTicket(source);
        }

        @Override
        public SupportTicket[] newArray(int size) {
            return new SupportTicket[size];
        }
    };
}



