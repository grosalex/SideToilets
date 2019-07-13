package com.grosalex.sidetoilets.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

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

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        this.googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
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
}
