package br.com.vicentec12.desafio_android.util

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import br.com.vicentec12.desafio_android.R

object ShareUtil {

    private fun takeScreenshot(view: View): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    fun share(view: View) {
        try {
            val printScreen: Bitmap = takeScreenshot(view)!!
            val share = Intent(Intent.ACTION_SEND)
            val path: String = MediaStore.Images.Media.insertImage(
                view.context.contentResolver,
                printScreen,
                "title",
                null
            )
            var screenshotUri: Uri? = Uri.parse(path)
            share.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            share.type = "image/*"
            view.context.startActivity(
                Intent.createChooser(
                    share,
                    view.context.getString(R.string.text_share_image)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}