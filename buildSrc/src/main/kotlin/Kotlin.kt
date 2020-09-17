/**
 * des 管理kotlin相关依赖
 * @author zs
 * @date   2020/9/15
 */
object Kotlin {
    const val kotlinVersion = "1.3.61"
    const val jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"

    val coroutines = Coroutines
    object Coroutines {
        private const val coroutines_version = "1.3.7"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
    }
}