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
import retrofit2.HttpException
import java.io.IOException

class ShowImagesViewModel : ViewModel() {
    private val _imageList = MutableLiveData<List<Image>>()
    private val _timeoutOccurred = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()

    val imageList: LiveData<List<Image>> get() = _imageList
    val timeoutOccurred: LiveData<Boolean> get() = _timeoutOccurred
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchImages(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withTimeout(Constants.RETROFIT.HTTP_TIMEOUT) {
                    val response = RetrofitInstance.api.searchCats(Constants.RETROFIT.SEARCH_PARAMETER, page)
                    if (response != null && response.data != null) {
                        val currentImages = _imageList.value ?: emptyList()
                        val existingImageIds = currentImages.map { it.id }.toSet()
                        val newImages = response.data.flatMap { imageData ->
                            imageData.images.filter { image ->
                                Constants.RETROFIT.IMAGE_EXTENSIONS.any { ext -> image.link.endsWith(ext) } &&
                                        !existingImageIds.contains(image.id)
                            }
                        }

                        val updatedImages = currentImages + newImages

                        _errorMessage.postValue(null)
                        _imageList.postValue(updatedImages)
                        _timeoutOccurred.postValue(false)
                    }
                }
            } catch (e: TimeoutCancellationException) {
                Log.e(Constants.MESSAGES.TIMEOUT_ERROR_TAG, "${Constants.MESSAGES.TIMEOUT_ERROR_MSG}: ${e.message}", e)
                _timeoutOccurred.postValue(true)
                _errorMessage.postValue(Constants.MESSAGES.TIMEOUT_ERROR_MSG)
            } catch (e: IOException) {
                Log.e(Constants.MESSAGES.CONECTION_ERROR_TAG, "${Constants.MESSAGES.CONECTION_ERROR_MSG}: ${e.message}", e)
                _errorMessage.postValue(Constants.MESSAGES.CONECTION_ERROR_MSG)
            } catch (e: HttpException) {
                Log.e(Constants.MESSAGES.ACCESS_ERROR_TAG, "${Constants.MESSAGES.ACCESS_ERROR_MSG}: ${e.code()}", e)
                _errorMessage.postValue("${Constants.MESSAGES.ACCESS_ERROR_MSG}: ${e.message}")
            } catch (e: NullPointerException) {
                Log.e(Constants.MESSAGES.NO_IMAGE_TAG, "${Constants.MESSAGES.NO_IMAGE_MSG}: ${e.message}", e)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                Log.e(Constants.MESSAGES.API_ERROR_TAG, "${Constants.MESSAGES.API_ERROR_MSG}: ${e.message}", e)
                _errorMessage.postValue("${Constants.MESSAGES.API_ERROR_MSG}: ${e.message}")
            }
        }
    }
}
