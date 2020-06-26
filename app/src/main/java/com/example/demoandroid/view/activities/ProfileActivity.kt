package com.example.demoandroid.view.activities
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.demoandroid.utils.Constants
import com.example.demoandroid.R
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ProfileActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_TAKE_PHOTO = 1
    private var currentPhotoPath: String = ""
    var name: String = ""
    var lastName: String = ""
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        name = intent.extras?.get(Constants.EXTRA_NAME).toString()
        lastName = intent.extras?.get(Constants.EXTRA_LAST_NAME).toString()
        email =  intent.extras?.get(Constants.EXTRA_EMAIL).toString()

        first_name.text = name
        last_name.text = lastName
        user_email.text = email

    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.demoandroid.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun takePhoto(view: View) {
        dispatchTakePictureIntent()
    }

    private fun showPhoto() {
        val imgFile = File(currentPhotoPath)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            profile_picture.setImageBitmap(myBitmap)
        }
    }


    companion object {
        fun newInstance(name: String, lastName: String, email:String, context : Context): Intent {
            return  Intent(context, ProfileActivity::class.java).apply {
                putExtra(Constants.EXTRA_NAME, name)
                putExtra(Constants.EXTRA_LAST_NAME, lastName)
                putExtra(Constants.EXTRA_EMAIL, email)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            showPhoto()
        }
    }
}
