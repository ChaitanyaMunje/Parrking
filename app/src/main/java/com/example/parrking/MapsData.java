package com.example.parrking;

public class MapsData {
    public String parkingname;
    public String latitude;
    public String longitude;
    public String avaliablepark;
    public MapsData(){

    }
    public String getParkingname() {
        return parkingname;
    }

    public void setParkingname(String parkingname) {
        this.parkingname = parkingname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAvaliablepark() {
        return avaliablepark;
    }

    public void setAvaliablepark(String avaliablepark) {
        this.avaliablepark = avaliablepark;
    }


    public MapsData(String parkingname, String latitude, String longitude, String avaliablepark) {
        this.parkingname = parkingname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avaliablepark = avaliablepark;
    }


}
