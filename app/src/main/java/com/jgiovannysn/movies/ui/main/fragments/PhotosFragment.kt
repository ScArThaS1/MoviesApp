package com.jgiovannysn.movies.ui.main.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgiovannysn.movies.R
import com.jgiovannysn.movies.base.BaseFragment
import com.jgiovannysn.movies.ui.main.MainViewModel
import com.jgiovannysn.movies.ui.main.adapters.PhotosAdapter
import kotlinx.android.synthetic.main.fragment_photos.*

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class PhotosFragment: BaseFragment() {

    private var mainViewModel: MainViewModel? = null
    private lateinit var adapter: PhotosAdapter

    companion object {
        @JvmStatic
        fun newInstance() = PhotosFragment()

        private const val OPERATION_CAPTURE_PHOTO = 1
        private const val OPERATION_CHOOSE_PHOTO = 2
        private const val INDEX_EXTRA_DATA = "data"
    }

    override fun getLayoutId(): Int = R.layout.fragment_photos

    override fun init() {
        adapter = PhotosAdapter()

        val layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)

        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
    }

    override fun initViewModel() {
        mainViewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }
    }

    override fun listeners() {
        add_button.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage(getString(R.string.selected_option_text))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.from_galery_text)) { dialog, _ ->
                    dialog.cancel()
                    requestPermissionsForGallery()
                }
                .setNegativeButton(getString(R.string.take_photo_text)) { dialog, _ ->
                    dialog.cancel()
                    capturePhoto()
                }
            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }

    private fun requestPermissionsForGallery() {
        val checkSelfPermission = ContextCompat.checkSelfPermission(context!!,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
            //Requests permissions to be granted to this application at runtime
            ActivityCompat.requestPermissions(activity!!,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
        else{
            openGallery()
        }
    }

    override fun observers() {
        mainViewModel?.getImages()
        mainViewModel?.imageUploadSuccess?.observe(this, {
            if(it) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setMessage(getString(R.string.success_upload_image))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.accept_text)) { dialog, _ ->
                        dialog.cancel()
                        mainViewModel?.getImages()
                    }
                val alert: AlertDialog = builder.create()
                alert.show()
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setMessage(getString(R.string.failed_upload_image))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.accept_text)) { dialog, _ ->
                        dialog.cancel()
                    }
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        })

        mainViewModel?.images?.observe(this, {
            adapter.data = it.toMutableList()
        })
    }

    private fun capturePhoto(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, OPERATION_CAPTURE_PHOTO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
                                            , grantedResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when(requestCode){
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                   if(data != null) {
                       val imageBitmap: Bitmap? = data.extras?.get(INDEX_EXTRA_DATA) as Bitmap?
                       imageBitmap?.let {
                           mainViewModel?.uploadImage(it)

                       }
                   }
                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if(data != null) {
                        val selectedImage: Uri? = data.data
                        val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, selectedImage)
                        bitmap?.let {
                            mainViewModel?.uploadImage(it)

                        }
                    }
                }
        }
    }

    private fun openGallery() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }
}