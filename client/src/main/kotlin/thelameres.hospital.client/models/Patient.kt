package thelameres.hospital.client.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Patient(
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var birthDate: LocalDate,
    var passport: String,
    var gender: Gender,
)
enum class Gender {
    FEMALE, MALE, UNDEFINED
}

