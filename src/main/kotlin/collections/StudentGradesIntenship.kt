package collections

import java.util.*

class Grade(
    val passing: Boolean,
    var ects: Int,
    var semester: String,
    var grade: Double
)

class StudentGrades(
    val grades: List<Grade>
)

fun List<StudentGrades>.getBestForScholarship(semester: String): List<StudentGrades> {
    val students = this
    var candidates = mutableListOf<StudentGrades>()
    for (s in students) {
        var ectsPointsGained = 0
        for (g in s.grades) {
            if (g.semester == semester && g.passing) {
                ectsPointsGained + g.ects
            }
        }
        if (ectsPointsGained > 30) {
            candidates.add(s)
        }
    }
    Collections.sort(candidates, { s1, s2 ->
        val difference = averageGradeFromSemester(s1, semester) - averageGradeFromSemester(s2, semester)
        if (difference > 0) 1 else -1
    })
    val best = mutableListOf<StudentGrades>()
    for (i in 0 until 10) {
        val next = candidates.getOrNull(i)
        if (next != null) {
            best.add(next)
        }
    }
    return best
}

private fun averageGradeFromSemester(student: StudentGrades, semester: String): Double {
    var sum = 0.0
    var count = 0
    for (g in student.grades) {
        if (g.semester == semester) {
            sum += g.grade
            count++
        }
    }
    return sum / count
}
