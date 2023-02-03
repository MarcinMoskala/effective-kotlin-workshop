

class User(val name: String, val surname: String, val id: Int) {
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = result * 31 + surname.hashCode()
        result = result * 31 + id.hashCode()
        return result
    }
}