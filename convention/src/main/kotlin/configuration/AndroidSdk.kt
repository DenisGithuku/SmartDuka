package configuration

import java.text.SimpleDateFormat
import java.util.Locale


object AndroidSdk {
    const val targetSdk = 35
    const val minimumSdk = 21
    const val namespace = "com.githukudenis.smartduka"
    const val applicationId = "com.githukudenis.smartduka"


    private val code = 0
        .plus(SimpleDateFormat("yyyyMMdd", Locale.ROOT).format(System.currentTimeMillis()).plus("00").toInt())
        .plus(0) // change this if you're shipping for the same day

    val versionCode = code
    val versionName = "v".plus(SimpleDateFormat("yyyy.MM (dd)", Locale.ROOT).format(System.currentTimeMillis()))

}