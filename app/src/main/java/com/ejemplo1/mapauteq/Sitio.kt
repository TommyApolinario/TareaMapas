package com.ejemplo1.mapauteq

import com.google.android.gms.maps.model.LatLng

open class Sitio {
    var id = 0
    var titulo: String? = null
    var descripcion: String? = null
    var urlImage: String? = null
    var faculta: String? = null
    var decano: String? = null
    var ubicacion: String? = null
    var posicion: LatLng? = null

    fun Sitio() {}
}