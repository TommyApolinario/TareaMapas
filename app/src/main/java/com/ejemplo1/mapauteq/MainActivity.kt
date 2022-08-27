package com.ejemplo1.mapauteq

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson


public class MainActivity : AppCompatActivity() , OnMapReadyCallback, GoogleMap.OnMapClickListener  {

    var cola: RequestQueue? = null
    private var map: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        cola = Volley.newRequestQueue(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        AddMarker()
        map!!.setInfoWindowAdapter(InfoWindowLugar(this, "personal"))
    }

    fun AddMarker() {
        val arrayRequest = JsonArrayRequest(
            Request.Method.GET,
            "https://my-json-server.typicode.com/TommyApolinario/TareaMapas/sitios",
            null,
            { response ->
                val gson = Gson()

                // Deserializamos
                val sitios: Array<Sitio> = gson.fromJson(
                    response.toString(),
                    Array<Sitio>::class.java
                )

                // Mostramos
                for (i in sitios.indices) {
                    System.out.println(sitios[i].titulo)
                    map!!.addMarker(
                        MarkerOptions()
                            .position(sitios[i].posicion!!)
                            .title(sitios[i].titulo)
                    )!!.tag = sitios[i]
                    if (sitios.size - 1 == i) {
                        map!!.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                sitios[i].posicion!!,
                                18f
                            )
                        )
                    }
                }
            }
        ) { }
        cola!!.add(arrayRequest)
    }
    fun onChangeTypeMap(v: View?) {
        // Dependiendo del tipo de mapa se va a iterar.
        println(map!!.mapType)
        when (map!!.mapType) {
            GoogleMap.MAP_TYPE_NORMAL -> map!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
            GoogleMap.MAP_TYPE_SATELLITE -> map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
            GoogleMap.MAP_TYPE_TERRAIN -> map!!.mapType = GoogleMap.MAP_TYPE_HYBRID
            GoogleMap.MAP_TYPE_HYBRID -> map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }

    override fun onMapClick(latLng: LatLng) {
        map!!.clear()
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        val marker = map!!.addMarker(markerOptions)
        marker!!.showInfoWindow()
    }
    
}
