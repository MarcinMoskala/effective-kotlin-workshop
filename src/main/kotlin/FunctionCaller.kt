import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.typeOf

class FunctionCaller {
    inline fun <reified T> setConstant(value: T) {
        setConstant(typeOf<T>(), value)
    }

    fun setConstant(type: KType, value: Any?) {
        TODO()
    }

    // Call function with set values based on parameter type
    fun <T> call(function: KFunction<T>): T {
        TODO()
    }
}