package com.utile.strong_sun.map_utile.location;

import com.esri.arcgisruntime.location.LocationDataSource;

public class CustomDataSource extends LocationDataSource{
    @Override
    protected void onStart() {
        onStartCompleted(null);
    }

    @Override
    protected void onStop() {

    }

    public void UpdateLocation(LocationDataSource.Location location){
        this.updateLocation(location);
    }

}
