package com.example.caravan.domain.use_cases

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.amplifyframework.core.Amplify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject



@RequiresApi(Build.VERSION_CODES.O)
class UploadImageGetUrlUseCase @Inject constructor() {



    private fun getRandomKey(): String {
        return (LocalDateTime.now().toString()).replace(":", "" + (111111..999999).random())
            .replace(".", "") + ".jpg"
    }


    operator fun invoke(uri: Uri, cn: ContentResolver, x: MutableState<String>) {


        fun getUrl(key: String) {

            Amplify.Storage.getUrl(
                key,
                { x.value = it.url.toString() },
                { error -> Log.e("Mito", "Failed to get url", error) }
            )

        }

        fun uploadPhoto(imageUri: Uri, cn: ContentResolver) {

            val stream = cn.openInputStream(imageUri)!!

            Amplify.Storage.uploadInputStream(
                getRandomKey(),
                stream,
                { getUrl(it.key) },
                { error -> Log.e("Mito", "Failed upload", error) }
            )
        }


        uploadPhoto(uri, cn)

    }

}