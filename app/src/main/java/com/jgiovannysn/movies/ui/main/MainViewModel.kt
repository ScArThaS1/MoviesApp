package com.jgiovannysn.movies.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.jgiovannysn.movies.base.ViewModelBase
import com.jgiovannysn.movies.data.AppDatabase
import com.jgiovannysn.movies.data.Executor
import com.jgiovannysn.movies.data.entities.Movie
import com.jgiovannysn.movies.data.entities.Movies
import com.jgiovannysn.movies.data.entities.NotificationContent
import com.jgiovannysn.movies.data.entities.UserLocation
import com.jgiovannysn.movies.network.repositories.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val database: AppDatabase
): ViewModelBase() {
    private val handler = Handler()
    private val storage = Firebase.storage

    var movies = MutableLiveData<List<Movie>>()
    var requestLocation = MutableLiveData<Boolean>()

    var userLocation = MutableLiveData<UserLocation>()
    var userLocationList = MutableLiveData<List<UserLocation>>()
    var imageUploadSuccess = MutableLiveData<Boolean>()

    var images = MutableLiveData<List<StorageReference>>()

    // creating a variable for our
    // Firebase Database.
    var firebaseDatabase: FirebaseDatabase? = null

    // creating a variable for our Database
    // Reference for Firebase.
    var databaseReference: DatabaseReference? = null
    var isGet = false;

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("locations")
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                val list: ArrayList<UserLocation> = ArrayList()
                for (ds in snapshot.children) {
                    val td: Map<String, Double> = ds.value as Map<String, Double>
                    val userLocation = td["latitude"]
                        ?.let { td["longitude"]?.let { it1 -> UserLocation(it, it1) } }
                    userLocation?.let { list.add(it) }
                }

                userLocationList.postValue(list)
            }

            override fun onCancelled(@NonNull error: DatabaseError) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
            }
        })
        isGet = true
        databaseReference!!.get()
    }

    private fun getMovies() {
        val disposable = moviesRepository.getDiscoverMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(subscribeToDatabase())

        subscribe(disposable)
    }

    fun sendLocation(latitude: Double, longitude: Double) {
        val userLocation = UserLocation(latitude, longitude)
        databaseReference = firebaseDatabase!!.getReference("locations").push()
        databaseReference!!.setValue(userLocation)
    }

    fun initSendLocationTimer() {
        val time: Long = 5000 * 60
        handler.postDelayed(object : Runnable {
            override fun run() {
                requestLocation.value = true
                handler.postDelayed(this, time)
            }
        }, time)
    }

    fun fetchFromDatabase() {
        val disposable = database.moviesDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movies.value = it
                getMovies()
            }, {
                getMovies()
            })

        subscribe(disposable)
    }

    fun fetchOnlyFromDatabase() {
        val disposable = database.moviesDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movies.value = it
            }, {
            })

        subscribe(disposable)
    }

    fun sendNotification(ctx: Context, content: NotificationContent) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(content.channel, content.channel, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = ctx.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(ctx, content.channel)
        builder.setContentTitle(content.title)
        builder.setContentText(content.text)
        builder.setSmallIcon(content.icon)
        builder.setAutoCancel(true)

        NotificationManagerCompat.from(ctx).notify(1, builder.build())
    }

    private fun subscribeToDatabase(): DisposableSubscriber<Movies> {
        return object : DisposableSubscriber<Movies>() {

            override fun onNext(dataResult: Movies?) {
                dataResult?.let {
                    Executor.thread {
                        database.moviesDao().insertAll(it.results)
                    }
                }
            }

            override fun onError(t: Throwable?) {

            }

            override fun onComplete() {
                fetchOnlyFromDatabase()
            }
        }
    }

    fun setCurrentLocationMarker(user: UserLocation, map: GoogleMap) {
        val latLng = LatLng(user.latitude, user.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map.animateCamera(CameraUpdateFactory.zoomTo(11f))
    }

    fun setLocationMarker(user: UserLocation, map: GoogleMap) {
        val latLng = LatLng(user.latitude, user.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        map.addMarker(markerOptions)
    }

    fun uploadImage(image: Bitmap) {
        val storageRef = storage.reference
        val uniqueString: String = UUID.randomUUID().toString()
        val mountainsRef = storageRef.child("images/${uniqueString}.jpg")
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            imageUploadSuccess.postValue(false)
        }.addOnSuccessListener {
            imageUploadSuccess.postValue(true)
        }
    }

    fun getImages() {
        val listRef = storage.reference.child("images")
        listRef.listAll().addOnSuccessListener {
            images.postValue(it.items)
        }
    }
}