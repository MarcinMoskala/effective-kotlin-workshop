package collections.less

data class StudentJson(
    val name: String?,
    val surname: String?,
    val result: Double,
    val pointsInSemester: Int
)

fun List<StudentJson>.getPassingSurnames(): List<String> = this
    .filter { it.result >= 50 }
    .filter { it.pointsInSemester >= 15 }
    .map { it.surname }
    .filter { it != null }
    .map { it!! }

