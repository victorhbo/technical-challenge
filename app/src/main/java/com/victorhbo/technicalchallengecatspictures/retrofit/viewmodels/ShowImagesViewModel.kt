package com.victorhbo.technicalchallengecatspictures.retrofit.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorhbo.technicalchallengecatspictures.models.Image
import com.victorhbo.technicalchallengecatspictures.retrofit.api.RetrofitInstance
import com.victorhbo.technicalchallengecatspictures.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.launch

class ShowImagesViewModel : ViewModel() {
    private val _imageList = MutableLiveData<List<Image>>()
    private val _timeoutOccurred = MutableLiveData<Boolean>()

    val imageList: LiveData<List<Image>> get() = _imageList
    val timeoutOccurred: LiveData<Boolean> get() = _timeoutOccurred

    fun fetchImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withTimeout(Constants.HTTP_TIMEOUT) {
                    val response = RetrofitInstance.api.searchCats(Constants.SEARCH_PARAMETER)
                    val filteredImages = response.data.flatMap { imageData ->
                        imageData.images.filter { image ->
                            Constants.IMAGE_EXTENSIONS.any { ext -> image.link.endsWith(ext) }
                        }
                    }
                    _imageList.postValue(filteredImages)
                    _timeoutOccurred.postValue(false)
                }
            } catch (e: TimeoutCancellationException) {
                Log.e(Constants.TIMEOUT_ERROR, Constants.TIMEOUT_ERROR_MSG, e)
                _imageList.postValue(emptyList())
                _timeoutOccurred.postValue(true)
            } catch (e: Exception) {
                Log.e(Constants.API_ERROR, "${Constants.API_ERROR_MSG} ${e.printStackTrace()}")
                _timeoutOccurred.postValue(false)
            }
        }
    }
}

