/**
 * des 管理AdnroidX相关依赖
 * @author zs
 * @date   2020/9/15
 */
@Suppress("SpellCheckingInspection")
object Android {

    const val appcompat         = "androidx.appcompat:appcompat:1.2.0"
    const val coreKtx           = "androidx.core:core-ktx:1.3.1"
    const val constraintlayout  = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val legacy            = "androidx.legacy:legacy-support-v4:1.0.0"
    const val paging            = "androidx.paging:paging-runtime:2.1.2"
    const val viewpager2        = "androidx.viewpager2:viewpager2:1.1.0-alpha01"
    const val multidex          = "com.android.support:multidex:1.0.3"
    const val material          = "com.google.android.material:material:1.3.0-alpha02"

    val lifecycle = Lifecycle
    object Lifecycle {
        private const val lifecycle_version = "2.2.0"
        const val extensions    = "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
        const val liveDataKtx   = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
        const val commonJava8   = "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
        const val viewModelKtx  = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    }

    val navigation = Navigation
    object Navigation {
        private const val navigation_version = "2.3.0"
        const val fragmentKtx   = "androidx.navigation:navigation-fragment-ktx:$navigation_version"
        const val uiKtx         = "androidx.navigation:navigation-ui-ktx:$navigation_version"
    }

    val room = Room
    object Room {
        private const val room_version = "2.2.3"
        const val roomRuntime   = "androidx.room:room-runtime:$room_version"
        const val roomCompiler  = "androidx.room:room-compiler:$room_version"
        const val roomKtx       = "androidx.room:room-ktx:$room_version"
    }
}