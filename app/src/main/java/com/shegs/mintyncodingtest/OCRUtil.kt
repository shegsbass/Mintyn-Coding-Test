package com.shegs.mintyncodingtest

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import com.googlecode.tesseract.android.TessBaseAPI

object OCRUtils {

    private const val TAG = "OCRUtils"
    private const val TESS_DATA_PATH = "/mnt/sdcard/tesseract/"
    private const val LANGUAGE = "eng"

    fun getTextFromBitmap(context: Context, bitmap: Bitmap): String {
        val tessBaseAPI = TessBaseAPI()

        // Initialize the Tesseract API
        if (!tessBaseAPI.init(TESS_DATA_PATH, LANGUAGE)) {
            Log.e(TAG, "TessBaseAPI initialization failed.")
            return ""
        }

        tessBaseAPI.setImage(bitmap)

        // Perform OCR on a separate thread
        val ocrTask = OCRAsyncTask(tessBaseAPI)
        return ocrTask.execute().get()
    }

    private class OCRAsyncTask(private val tessBaseAPI: TessBaseAPI) :
        AsyncTask<Void?, Void?, String>() {

        override fun doInBackground(vararg params: Void?): String {
            return try {
                tessBaseAPI.utF8Text
            } catch (e: Exception) {
                Log.e(TAG, "Error during OCR: ${e.message}")
                ""
            } finally {
                tessBaseAPI.end()
            }
        }
    }
}