package com.grosalex.sidetoilets.activity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.grosalex.sidetoilets.R
import com.grosalex.sidetoilets.contract.ToiletsContract
import com.grosalex.sidetoilets.model.Marker
import com.grosalex.sidetoilets.presenter.ToiletsPresenter
import com.grosalex.sidetoilets.provider.ToiletsProvider

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ToiletsContract.View {

    private lateinit var presenter: ToiletsPresenter

    private lateinit var googleMap: GoogleMap

    private lateinit var loader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initView()

        presenter = ToiletsPresenter(this, ToiletsProvider())
        presenter.getToilets()
    }

    private fun initView() {
        loader = findViewById(R.id.loader)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        displayMyLocation()
    }

    private fun askForLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                displayMyLocation()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun displayMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForLocationPermission()
        } else {
            googleMap.isMyLocationEnabled = true
        }
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        loader.visibility = View.GONE
    }

    override fun onLoading() {
        loader.visibility = View.VISIBLE
    }

    override fun onBindToiletsList(list: List<Marker>) {
        loader.visibility = View.GONE

        list.forEach { marker ->
            googleMap.addMarker(MarkerOptions().position(marker.latLng))
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
