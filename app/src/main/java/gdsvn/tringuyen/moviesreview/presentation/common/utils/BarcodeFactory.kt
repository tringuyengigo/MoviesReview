package gdsvn.tringuyen.moviesreview.presentation.common.utils

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer


/**
 * Lazy BarcodeFactory Initialized Singleton Example
 */
class BarcodeFactory private constructor() {
    companion object {
        @Volatile
        private var instance: BarcodeFactory? = null

        fun getInstance(): BarcodeFactory? {
            if (instance == null) {
                //if there is no instance available... create new one
                synchronized(BarcodeFactory::class.java) {
                    //Check for the second time.
                    //if there is no instance available... create new one
                    if (instance == null) instance =
                        BarcodeFactory()
                }
            }
            return instance
        }
    }

    //private constructor.
    init {
        if (instance != null) {
            throw RuntimeException(
                "Use getInstance() method to get the single instance of this class."
            )
        }
    }

    fun stringOut(stringIn: String) : String {
        return stringIn
    }

    fun createBarcodeBitmap (
        barcodeValue: String,
        @ColorInt barcodeColor: Int,
        @ColorInt backgroundColor: Int,
        widthPixels: Int,
        heightPixels: Int
    ): Bitmap {
        val bitMatrix = Code128Writer().encode(
            barcodeValue,
            BarcodeFormat.CODE_128,
            widthPixels,
            heightPixels
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }
}

