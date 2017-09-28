package com.milkmachine.hotels.utils.glide

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.view.WindowManager
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

class SmartTransformation(private val context: Context,
                          private val edgesWidthPx: Int = 0
) : BitmapTransformation() {

    private val imageHeightForLandscape: Int

    init {
        val windowService = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        windowService.defaultDisplay.getRealSize(point)
        imageHeightForLandscape = (point.y * HEIGHT_RELATION_FOR_LANDSCAPE).toInt()
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        var outBitmap = if (edgesWidthPx > 0) trimBitmapEdges(toTransform) else toTransform

        val configuration = context.resources.configuration

        outBitmap = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val recalculatedOutHeight = outWidth / toTransform.width.toDouble() * toTransform.height
            TransformationUtils.centerCrop(pool, outBitmap, outWidth, recalculatedOutHeight.toInt())
        } else {
            TransformationUtils.fitCenter(pool, outBitmap, outWidth, imageHeightForLandscape)
        }

        return outBitmap
    }

    private fun trimBitmapEdges(bitmap: Bitmap): Bitmap {
        return Bitmap.createBitmap(bitmap, edgesWidthPx, edgesWidthPx,
                bitmap.width - edgesWidthPx * 2, bitmap.height - edgesWidthPx * 2)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(idBytes)
    }

    override fun equals(other: Any?): Boolean = other is SmartTransformation

    override fun hashCode(): Int = ID.hashCode()

    companion object {
        private const val HEIGHT_RELATION_FOR_LANDSCAPE = 0.5
        private const val ID = "com.milkmachine.hotels.utils.glide.TrimEdgesTransformation"
        private val idBytes = ID.toByteArray(Key.CHARSET)
    }
}