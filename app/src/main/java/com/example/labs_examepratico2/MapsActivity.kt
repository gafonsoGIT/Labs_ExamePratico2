package com.example.labs_examepratico2

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.labs_examepratico2.api.EndPoints
import com.example.labs_examepratico2.api.ServiceBuilder
import com.example.labs_examepratico2.api.User

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.labs_examepratico2.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var lastLocation : Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private var continenteLat: Double = 0.0
    private var continenteLong: Double = 0.0

    private var coordinates: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        continenteLat = 41.703
        continenteLong = -8.81

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val stopCoordsBtn = findViewById<Button>(R.id.stopCoordsBtn)

        createLocationRequest()

      locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
          super.onLocationResult(p0)
          lastLocation = p0.lastLocation
          var loc = LatLng(lastLocation.latitude, lastLocation.longitude)

          val distance = calculateDistance(
            lastLocation.latitude, lastLocation.longitude,
            continenteLat, continenteLong)

          if(distance < 5000){
            Toast.makeText(applicationContext, distance.toString() + " metros", Toast.LENGTH_SHORT).show()
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
            mMap.addMarker(MarkerOptions().position(loc).title("new Location"))
          }else {
            Toast.makeText(applicationContext, "Acima da distância referida", Toast.LENGTH_SHORT).show()
          }
          findViewById<TextView>(R.id.txtcoordenadas).setText("Lat: " + loc.latitude + " - Long: " + loc.longitude)
          Log.d("lab google maps", "new location received" + loc.latitude + " - " + loc.longitude)

          val address = getAddress(lastLocation.latitude, lastLocation.longitude)
          findViewById<TextView>(R.id.txtmorada).setText("Morada: " + address)

          findViewById<TextView>(R.id.txtdistancia).setText("Distância(metros) : " + distance.toString())

          /*findViewById<TextView>(R.id.txtdistancia).setText("Distância: " + calculateDistance(
            lastLocation.latitude, lastLocation.longitude,
            continenteLat, continenteLong).toString()
          )*/

        }
      }

      /*stopCoordsBtn.setOnClickListener {
        if(coordinates){
          fusedLocationClient.removeLocationUpdates(locationCallback)
          coordinates = !coordinates
        } else if(!coordinates) {
          startLocationUpdates()
          coordinates = !coordinates
          Toast.makeText(this, ""+lastLocation.latitude.toString() + "-" + lastLocation.longitude.toString(), Toast.LENGTH_LONG).show()
        }
      }*/
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

      val request = ServiceBuilder.buildService(EndPoints::class.java)
      val call = request.getUsers()
      var position: LatLng

      call.enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
          if(response.isSuccessful) {
            val users = response.body()!!
            for ( user in users) {
              position = LatLng(user.address.geo.lat.toDouble(),user.address.geo.lng.toDouble())
              mMap.addMarker(MarkerOptions().position(position).title(user.address.suite + " - " + user.address.city))
            }
          }
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
          val number = 1
        }
      })
    }

    private fun createLocationRequest() {
      locationRequest = LocationRequest()

      locationRequest.interval = 10000
      locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    companion object {
      private const val LOCATION_PERMISSION_REQUEST_CODE = 1

      private const val REQUEST_CHECK_SETTINGS = 2
    }

    private fun startLocationUpdates() {
      fusedLocationClient = FusedLocationProviderClient(this)
      if (ActivityCompat.checkSelfPermission(this,
          android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
          arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
          LOCATION_PERMISSION_REQUEST_CODE)
        return
      }
      fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    override fun onPause() {
      super.onPause()
      fusedLocationClient.removeLocationUpdates(locationCallback)
      Log.d("**** SARA", "onPause - removeLocationUpdates")
    }
    public override fun onResume() {
      super.onResume()
      startLocationUpdates()
      Log.d("**** SARA", "onResume - startLocationUpdates")
    }

    private fun getAddress(lat: Double, lng: Double): String {
      val geocoder = Geocoder(this)
      val list = geocoder.getFromLocation(lat,lng,11)
      return list[0].getAddressLine(0)
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
      val results = FloatArray(1)
      Location.distanceBetween(lat1, lng1, lat2, lng2, results)

      return results[0]
    }
}
