package org.hydev

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.int
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLDecoder

/**
 * The data model of apiData from MIUI theme API.
 *
 * @property downloadUrl raw data, encoded in url format.
 * @property fileHash length is 40, lower case.
 * @property fileSize unit is bytes.
 */
@Serializable
data class MIUITheme internal constructor(
    val downloadUrl: String,
    val fileHash: String,
    val fileSize: Long
) {
    val fileName: String
        get() = URLDecoder.decode(downloadUrl.split("/").last(), "UTF-8")

    companion object {
        private const val THEME_API_URL = "https://thm.market.xiaomi.com/thm/download/v2/"

        /**
         * Get MIUITheme via theme share link, can also specify MIUI version.
         *
         * @param themeUrl is share url from MIUI theme store
         * @param miuiVersion default is MIUI v11, you can also choose v10 - v12.
         * @return MIUITheme object or null
         */
        fun get(themeUrl: String, miuiVersion: MIUIVersion = MIUIVersion.V11): MIUITheme? {
            val okHttpClient = OkHttpClient()

            // Make a request and execute it, and get the string of response body.
            val request = Request.Builder().url("$THEME_API_URL${themeUrl.split("/").last()}$miuiVersion")
            val response = okHttpClient.newCall(request = request.build()).execute()
            val responseBody = response.body!!.string()

            // Parse it to JsonElement, if apiCode == 1, the error occurred.
            val jsonElement = Json(JsonConfiguration.Stable).parseJson(responseBody)
            if (jsonElement.jsonObject["apiCode"]!!.int == 1) {
                return null
            }

            // Else, we have apiData.
            return Json(JsonConfiguration.Stable).fromJson(serializer(), jsonElement.jsonObject["apiData"]!!)
        }
    }

    /**
     * The enum of MIUI version request string.
     */
    enum class MIUIVersion {
        V10, V11, V12;

        override fun toString(): String {
            return "?miuiUIVersion=${this.name}"
        }
    }
}