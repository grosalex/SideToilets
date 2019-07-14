package com.grosalex.sidetoilets.activity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.grosalex.sidetoilets.contract.ToiletsContract
import com.grosalex.sidetoilets.model.ToiletData
import com.grosalex.sidetoilets.presenter.ToiletsPresenter
import com.grosalex.sidetoilets.provider.ToiletsProvider
import com.grosalex.sidetoilets.R


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ToiletsContract.View {

    private val markerClickListener: GoogleMap.OnMarkerClickListener =
        GoogleMap.OnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }

    private lateinit var menu: Menu
    private lateinit var presenter: ToiletsPresenter
    private val mapFragment = SupportMapFragment.newInstance()
    private val listFragment = ToiletsListFragment()
    private lateinit var googleMap: GoogleMap
    private lateinit var loader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment.getMapAsync(this)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, mapFragment)
        fragmentTransaction.commit()

        initView()

        presenter = ToiletsPresenter(this, ToiletsProvider())
        presenter.getToilets()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.map_menu, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.refresh -> {
            presenter.refreshToilets()
            true
        }
        R.id.list -> {
            switchToListFragment()
            true
        }
        R.id.map -> {
            switchToMapFragment()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun switchToMapFragment() {
        supportFragmentManager.popBackStack()
        menu.findItem(R.id.list).isVisible = true
        menu.findItem(R.id.map).isVisible = false
    }

    private fun switchToListFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, listFragment)
            .addToBackStack(listFragment.tag)
            .commit()
        menu.findItem(R.id.list).isVisible = false
        menu.findItem(R.id.map).isVisible = true
    }

    private fun initView() {
        loader = findViewById(R.id.loader)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setOnMarkerClickListener(markerClickListener)

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

    override fun onBindToiletsList(list: List<ToiletData>) {
        loader.visibility = View.GONE
        googleMap.clear()
        list.forEach { marker ->
            googleMap.addMarker(MarkerOptions().position(marker.latLng).title(marker.title).snippet(marker.opening))
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
