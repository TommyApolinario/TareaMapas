package com.ejemplo1.mapauteq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


class InfoWindowLugar(var context: Context, var titulo: String) :
    InfoWindowAdapter {
    var inflater: LayoutInflater? = null
    var mWindow: View
    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View? {
        val sitio: Sitio
        sitio = try {
            marker.tag as Sitio
        } catch (ex: Exception) {
            println(ex.toString())
            return mWindow
        }
        val image = mWindow.findViewById<View>(R.id.image) as ImageView
        (mWindow.findViewById<View>(R.id.tvTitle) as TextView).setText(sitio.titulo)
        (mWindow.findViewById<View>(R.id.tvDescription) as TextView).setText(sitio.descripcion)
        (mWindow.findViewById<View>(R.id.tvfaculta) as TextView).setText(sitio.faculta)
        (mWindow.findViewById<View>(R.id.tvdecano) as TextView).setText(sitio.decano)
        (mWindow.findViewById<View>(R.id.tvubicacion) as TextView).setText(sitio.ubicacion)
        Glide.with(mWindow).load(sitio.urlImage).into(image)
        return mWindow
    }

    // Trae los datos desde la api
    fun GetSitionAPI(ID: Int): Sitio {
        return object : Sitio() {
            init {
                id = 1
                titulo = "Ecuador"
                descripcion = "Ecuador es un Pais"
                urlImage = ""
                faculta = "Ciencia de la ingenieria"
                decano = "Roberto El doc"
                ubicacion = "Quevedo"
                posicion = LatLng(-0.10820363732123867, -78.47378477657662)
            }
        }
    }

    init {
        mWindow = LayoutInflater.from(context).inflate(R.layout.windowinfo, null)
    }
}
