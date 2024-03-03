package com.blink.blinkid.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class ImageViewModel @Inject constructor(
    private val storageReference: StorageReference
) :
    ViewModel() {

    private val _uploadedImage = MutableStateFlow("")
    val uploadedImage = _uploadedImage.asStateFlow()


    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun uploadImage(part: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadImageAsync(part)
        }
    }

    private suspend fun uploadImageAsync(part: Uri) {

        val ref = storageReference
            .child(
                "images/"
                        + UUID.randomUUID().toString()
            )

        ref.putFile(part)
            .addOnSuccessListener {
                val task = it.storage.downloadUrl
                task.addOnSuccessListener { uri ->
                    _uploadedImage.value = uri.toString()
                }
            }.addOnFailureListener {
                _error.value = it.message.toString()
                Log.e("ImageViewModel", "uploadImageAsync: failure ${it.message}")
            }
            .addOnProgressListener {
                Log.e("ImageViewModel", "uploadImageAsync:  progress${it.bytesTransferred}")
            }

    }
}
