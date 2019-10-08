package com.example.parrking;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView parkingname;
    private TextView latitude;
    private TextView longitude;
    private TextView avaliableparking;
    private Button button;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        parkingname=itemView.findViewById(R.id.parkingname);
        latitude=itemView.findViewById(R.id.latitude);
        longitude=itemView.findViewById(R.id.longitude);
        avaliableparking=itemView.findViewById(R.id.avaliableparking);
        button=itemView.findViewById(R.id.showmap);

    }
    public TextView getParkingname() {
        return parkingname;
    }

    public void setParkingname(String parkingname) {
        this.parkingname.setText(parkingname);
    }
    public TextView getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude.setText(latitude);
    }
    public TextView getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude.setText(longitude);
    }
    public TextView getAvaliableparking() {
        return avaliableparking;
    }

    public void setAvaliableparking(String avaliableparking) {
        this.avaliableparking.setText(avaliableparking);
    }
    public Button getButton() {
        return button;
    }

    public void setButton() {
      //  this.avaliableparking.setText(avaliableparking);
        //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }

}
