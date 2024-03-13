package com.example.peliculas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.Manifest
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import com.squareup.picasso.Picasso
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerImagen: ImageView
    private val REQUEST_IMAGE_PICK = 1001
    private val  RESPUESTA_PERMISO_GALERIA = 300;
    private val RESPUESTA_PERMISO_CAMARA = 100;
    private val REQUEST_IMAGE_CAPTURE=300;
    private lateinit var bitmap: Bitmap
    private lateinit var lanzadorCamara: ActivityResultLauncher<Intent>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        registerEmail = findViewById(R.id.registerEmail)
        registerPassword = findViewById(R.id.registerPassword)
        registerImagen = findViewById(R.id.imagen)

        lanzadorCamara = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap?
                imageBitmap?.let {
                    // Guardar la imagen capturada en la galería
                    guardarImagenEnGaleria(it)
                }
            }
        }


        val pickImageButton: Button = findViewById(R.id.pickImageButton)
        pickImageButton.setOnClickListener {
            seleccionarImagenDesdeGaleria()
        }

        val registerButton: Button = findViewById(R.id.button)
        registerButton.setOnClickListener {
            registrarUsuario()
        }

        val capturaFoto: Button = findViewById(R.id.button3)
        capturaFoto.setOnClickListener {
            if (compruebaPermisosCamara()) {
                tomarFotoCamara()

            }
        }
        compruebaPermisoLecturaGaleria()

    }

    private fun tomarFotoCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        lanzadorCamara.launch(intent)
    }

    private fun compruebaPermisoLecturaGaleria(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), RESPUESTA_PERMISO_GALERIA)
            return false
        }
        return true
    }

    private fun compruebaPermisosCamara(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    RESPUESTA_PERMISO_CAMARA
                )
                return false
            }
        } else {
            return true
        }
    }

    fun seleccionarImagenDesdeGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            data?.data?.let { selectedImageUri ->
                // Mostrar la imagen seleccionada en el ImageView
                Picasso.get().load(selectedImageUri).into(registerImagen)

                // Guardar la URI de la imagen en las preferencias compartidas o en otro almacenamiento persistente
                val sharedPreferences = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("imagenUri", selectedImageUri.toString())
                editor.apply()
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            imageBitmap?.let {
                // Guardar la imagen capturada en la galería
                guardarImagenEnGaleria(it)
            }
        }
    }

    private fun guardarImagenEnGaleria(bitmap: Bitmap) {
        val imagesFolder = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "imagenes")
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs()
        }

        val imageFile = File(imagesFolder, "imagen_${System.currentTimeMillis()}.jpg")

        try {
            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
            Toast.makeText(this, "Imagen guardada en la galería", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Actualizar la galería con la nueva imagen
        MediaScannerConnection.scanFile(this, arrayOf(imageFile.absolutePath), null, null)
    }

    private fun agregarFotoGaleria(archivoImagen: File) {

    }

    private fun registrarUsuario() {
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()

        guardarDatosUsuario(email, password)

        // Iniciar la actividad de inicio de sesión y pasar los datos de registro
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
            putExtra("password", password)
        }
        startActivity(intent)

    }
    private fun guardarDatosUsuario(email: String, password: String) {
        // Guarda los datos del usuario en preferencias compartidas
        val sharedPreferences = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }


}
