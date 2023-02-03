package safe

data class Name(var name: String)

fun main() {
    val name = Name("AAA")
    var set = setOf<Name>()
    name.name = "BBB"
    print(set.first() == name) // true
    print(set.contains(name)) // false
}