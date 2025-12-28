package com.tech.empedancemachinetask.common

//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject
import java.util.Locale

class AppUtils @Inject constructor(
    private val networkHelper: NetworkHelper
) {
    fun loadIbbCoImage(
        context: Context,
        imageView: ImageView,
        galleryUrl: String
    ) {
        Log.d("AppUtils", "Starting load for: $galleryUrl")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Test connectivity first
                if (!networkHelper.observeNetworkStatus().first()) {
                    Log.e("AppUtils", "No internet connection")
                    withContext(Dispatchers.Main) {
                        imageView.setImageResource(R.drawable.ic_dialog_alert)
                    }
                    return@launch
                }

                Log.d("AppUtils", "Fetching HTML...")
                val doc = Jsoup.connect(galleryUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(30000)  // Increased timeout
                    .followRedirects(true)
                    .get()

                Log.d("AppUtils", "Page loaded successfully. Title: ${doc.title()}")

                // Multiple selectors for ibb.co
                val img1 = doc.select("img[src*='i.ibb.co']").firstOrNull()?.attr("src")
                val img2 = doc.select("meta[property=og:image]").attr("content")
                val img3 = doc.select("img").attr("src").takeIf { it.contains("i.ibb.co") }

                Log.d("AppUtils", "Img1: $img1 | Img2: $img2 | Img3: $img3")

                val directUrl = img1 ?: img2

                withContext(Dispatchers.Main) {
                    if (!directUrl.isNullOrEmpty()) {
                        Log.d("AppUtils", "Loading: $directUrl")
                        Glide.with(context)
                            .load(directUrl)
                            .placeholder(R.drawable.ic_menu_gallery)
                            .error(R.drawable.ic_delete)
                            .timeout(30000)
                            .into(imageView)
                    } else {
                        Log.e("AppUtils", "No image URL found")
                        imageView.setImageResource(R.drawable.ic_menu_report_image)
                    }
                }
            } catch (e: Exception) {
                Log.e("AppUtils", "Full error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    imageView.setImageResource(R.drawable.ic_dialog_alert)
                }
            }
        }
    }

    fun loadSvgOrImage(
        context: Context,
        imageView: ImageView,
        url: String?,
        placeholder: Int
    ) {
        if (url.isNullOrEmpty()) {
            imageView.setImageResource(placeholder)
            return
        }

        if (url.lowercase(Locale.ROOT).endsWith(".svg")) {
            imageView.setImageResource(placeholder)
            imageView.tag = url

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val inputStream = java.net.URL(url).openStream()
                    val svg = com.caverock.androidsvg.SVG.getFromInputStream(inputStream)
                    val drawable = PictureDrawable(svg.renderToPicture())

                    withContext(Dispatchers.Main) {
                        if (imageView.tag == url) {
                            imageView.setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
                            imageView.setImageDrawable(drawable)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("AppUtils", "Error loading SVG: ${e.message}")
                    withContext(Dispatchers.Main) {
                        if (imageView.tag == url) {
                            imageView.setImageResource(placeholder)
                        }
                    }
                }
            }
        } else {
            Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(placeholder)
                .into(imageView)
        }
    }
}
