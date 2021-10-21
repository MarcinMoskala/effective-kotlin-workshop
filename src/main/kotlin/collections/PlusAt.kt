package collections

fun <T> List<T>.plusAt(index: Int, element: T): List<T> {
    // ...
    return take(index) + element + drop(index) // Do it better
}