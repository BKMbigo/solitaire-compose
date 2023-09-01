package cafe.adriel.voyager.core.platform

import kotlin.reflect.KClass

public val KClass<*>.multiplatformName: String? get() = simpleName
