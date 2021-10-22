package readable

import readable.ClassModel
import readable.GenerateDtoOptions
import readable.GenerateRequest
import readable.ParameterModel

class DtoGeneratorService {

    fun generateDto(model: ClassModel,     options: GenerateDtoOptions):            String {
        val newName: String=options.newName.takeUnless { it.isNullOrBlank() }
            ?: model.name + (options.suffix

                .takeUnless { it.isNullOrBlank() } ?: "Json")
        val paramsText=model.params
            .takeIf { it.isNotEmpty() }
            ?.joinToString(
                separator=",\n   ", prefix="\n   ",
                postfix="\n", transform={ it: ParameterModel -> "${it.visibilityModifier} ${it.name}: ${it.type}" }
            )
            .orEmpty()

        val paramTransformationsText      =model.params
            .takeIf { it.isNotEmpty() }
            ?.joinToString(separator=",\n   ",
                prefix= "\n   ",
                postfix="\n",
                transform={ it: ParameterModel ->    "${it.name}=${it.name}" }
            )
            .orEmpty()
        val paramTransformationsText1=model.params.takeIf { it.isNotEmpty() }
            ?.joinToString(
                separator=",\n   ",
                prefix="\n   ",
                postfix="\n",
                transform={ it: ParameterModel -> "${it.name}=${it.name}" }
            )
            .orEmpty()
        return """
            |class $newName($paramsText)
            |
            |${"fun ${model.name}.to${newName.capitalize()}()=$newName($paramTransformationsText1)"}
            |
            |${"fun $newName.to${model.name.capitalize()}()=${model.name}($paramTransformationsText1)"}""".trimMargin()
    }

    fun generateGroovyBuilder(model: ClassModel): String {
        val builderName: String=model.name + "Build"

        val fields=model.params
            .joinToString(
                separator="\n   ", transform = { "${
                    when (it.type) {
                        "Int" -> "int"
                        "Int?" -> "Integer"
                        "Long" -> "long"
                        "Long?" -> "Long"
                        "Float" -> "float"
                        "Float?" -> "Float"
                        "Char" -> "char"
                        "Char?" -> "Char"
                        else -> when {
                            it.type.endsWith(">") -> genericTypeToJava(it.type)
                            else -> it.type
                        }
                    }
                } ${it.name}" }
            )

        return """
            |@Builder(builderStrategy = SimpleStrategy, prefix = "with")
            |class $builderName {
            |   $fields
            |   
            |   static $builderName a${builderName.capitalize()}() {
            |       return new $builderName()
            |   }
            |   
            |   ${model.name} build() {
            |       return new ${model.name}(${model.params.joinToString { it.name }})
            |   }
            |}""".trimMargin()
    }

    fun generateGroovyObjectAssertion(  model:   ClassModel): String {
        val assertionName: String = model.name + "Assertion"
        val fieldName: String = model.name.decapitalize()

        fun     generateAssertionFunction(parameter: ParameterModel): String = """
            |$assertionName has${parameter.name.capitalize()}(${
            when (parameter.type) {
                "Int" -> "int"
                "Int?" -> "Integer"
                "Long" -> "long"
                "Long?" -> "Long"
                "Float" -> "float"
                "Float?" -> "Float"
                "Char" -> "char"
                "Char?" -> "Char"
                else -> when {
                    parameter.type.endsWith(">") -> genericTypeToJava(parameter.type)
                    else -> parameter.type
                }
            }
        } ${parameter.name}) {
            |    assert $fieldName.${parameter.name} == ${parameter.name}
            |    return this
            |}
        """.trimMargin()

        val assertionFunctions = model.params
            .filterNot { it.name.startsWith("`") }
            .takeIf { it.isNotEmpty() }
            ?.joinToString(
                separator = "\n\n",
                transform = ::generateAssertionFunction
            )
            ?.replace("\n", "\n${"   "}")
            .orEmpty()

        return """
            |class $assertionName {
            |   private ${model.name} $fieldName
            |   
            |   $assertionName(${model.name} $fieldName) {
            |       this.$fieldName = $fieldName
            |   }
            |   
            |   static $assertionName assertThat(${model.name} $fieldName) {
            |       return $assertionName($fieldName)
            |   }
            |   
            |   $assertionFunctions
            |}""".trimMargin()
    }

    fun generateTypeScriptObject(model: ClassModel): String {
        val assertionFunctions = model.params
            .takeIf { it.isNotEmpty() }
            ?.joinToString(
                separator = ",\n   ",
                prefix = "\n   ",
                postfix = "\n",
                transform = { p: ParameterModel -> "${p.name}: ${typeToTypeScript(p.type)}" }
            )
            .orEmpty()

        return """
            |type ${model.name} = {$assertionFunctions}""".trimMargin()
    }

    private fun typeToTypeScript(type: String): String = when (type) {
        "Int", "Long", "Float" -> "number"
        "Int?", "Long?", "Float?" -> "number"
        "Char", "String" -> "string"
        "Char?", "String?" -> "string"
        else -> when {
            type.startsWith("List<") || type.startsWith("Set<") || type.startsWith("Array<") -> {
                val (_, paramsStr) = "(.*?)<(.*?)>[?]?\$".toRegex()
                    .find(type)
                    ?.groupValues
                    ?.let { it[1] to it[2] }!!
                typeToTypeScript(paramsStr) + "[]"
            }
            else -> type
        }
    }

    private fun genericTypeToJava(type: String): String {
        val (baseName, paramsStr) = "(.*?)<(.*?)>[?]?\$".toRegex()
            .find(type)
            ?.groupValues
            ?.let { it[1] to it[2] } ?: return type

        val javaParams = paramsStr
            .split(",")
            .map { it.trim() }
            .joinToString(separator = ",") {
                when (it) {
                    "Int", "Int?" -> "Integer"
                    "Long", "Long?" -> "Long"
                    "Float", "Float?" -> "Float"
                    "Char", "Char?" -> "Char"
                    else -> it
                }
            }

        return "$baseName<$javaParams>"
    }
}

data class GenerateRequest(
    val code: String,
    val options: GenerateDtoOptions,
)

data class GenerateDtoOptions(
    val newName: String? = null,
    val suffix: String? = null
)

data class ClassModel(
    val name: String,
    val params: List<ParameterModel> = emptyList()
)

data class ParameterModel(
    val readOnly: Boolean?,
    val name: String,
    val type: String
) {
    val visibilityModifier = when (readOnly) {
        true -> "val"
        false -> "var"
        null -> ""
    }
}
