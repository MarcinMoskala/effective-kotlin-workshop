package safe

data class Name(var name: String)

fun main() {
    val set = mutableSetOf<Name>()
    val name = Name("AAA")
    set.add(name)
    // ...
    print(set.first() == name) // true
    print(set.contains(name)) // false
}