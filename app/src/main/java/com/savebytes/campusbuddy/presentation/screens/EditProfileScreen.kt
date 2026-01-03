package com.savebytes.campusbuddy.presentation.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.savebytes.campusbuddy.R
import com.savebytes.campusbuddy.databinding.ActivityEditProfileScreenBinding
import java.io.File
import java.io.IOException

class EditProfileScreen : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileScreenBinding
    private var selectedImageUri: Uri? = null
    private var photoFile: File? = null

    // Image picker launcher - for gallery
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            handleImageSelection(it)
        }
    }

    // Camera launcher - for taking photo
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoFile?.let {
                handleImageSelection(Uri.fromFile(it))
            }
        }
    }

    // Camera permission launcher
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle keyboard properly - same as sign-in screen
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        initView()
        setupKeyboardHandling()

        setupClickListeners()
    }

    private fun initView() {

        binding.avatarImage.setImageResource(R.drawable.ic_profile_pic_placeholder)

    }

    private fun setupKeyboardHandling() {
        // Handle window insets for better keyboard behavior
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            // Add bottom padding when keyboard is visible
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                imeInsets.bottom
            )

            insets
        }
    }

    private fun setupClickListeners() {
        // Camera button click
        binding.cameraButton.setOnClickListener {
            showImagePickerDialog()
        }

        // Profile picture click (alternative way to change photo)
        binding.profilePictureContainer.setOnClickListener {
            showImagePickerDialog()
        }

        // Finish Setup button
        binding.btnFinishSetup.setOnClickListener {
            handleFinishSetup()
        }

        // Skip button
        binding.tvSkip.setOnClickListener {
            handleSkip()
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")

        AlertDialog.Builder(this)
            .setTitle("Select Photo")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> checkCameraPermissionAndOpen()
                    1 -> openGallery()
                    2 -> dialog.dismiss()
                }
            }
            .show()
    }

    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        try {
            photoFile = createImageFile()
            photoFile?.let { file ->
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "${packageName}.fileprovider",
                    file
                )
                takePictureLauncher.launch(photoUri)
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(null)
        return File.createTempFile(
            "profile_${System.currentTimeMillis()}",
            ".jpg",
            storageDir
        )
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun handleImageSelection(uri: Uri) {
        selectedImageUri = uri

        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Show selected image in avatar
            binding.avatarImage.setImageBitmap(bitmap)

            inputStream?.close()
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleFinishSetup() {
        val name = binding.etName.text.toString().trim()
        val course = binding.etCourse.text.toString().trim()
        val college = binding.etCollege.text.toString().trim()

        // Validate name field (required)
        if (name.isEmpty()) {
            binding.nameInputLayout.error = "Name is required"
            binding.etName.requestFocus()
            return
        }

        // Clear any previous errors
        binding.nameInputLayout.error = null

        // TODO: Save profile data to your backend/database
        // ProfileData(name, course, college, selectedImageUri)

        Toast.makeText(this, "Profile completed successfully!", Toast.LENGTH_SHORT).show()

        // Navigate to next screen (e.g., MainActivity)
        // val intent = Intent(this, MainActivity::class.java)
        // startActivity(intent)
        // finish()
    }

    private fun handleSkip() {
        // TODO: Skip profile setup and navigate to main screen
        Toast.makeText(this, "Profile setup skipped", Toast.LENGTH_SHORT).show()

        // Navigate to main screen without saving
        // val intent = Intent(this, MainActivity::class.java)
        // startActivity(intent)
        // finish()
    }
}