package thelameres.hospital.client.api

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Headers
import thelameres.hospital.client.SERVER
import thelameres.hospital.client.models.Page
import thelameres.hospital.client.models.Patient
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString


class PatientApi {

    suspend fun getPatient(page: Int = 0): Page<Patient> {
        val await = window.fetch("$SERVER/api/patient?page=$page", RequestInit().apply {
            method = "GET"
        }).await().text().await()
        return Json.decodeFromString(Page.serializer(typeSerial0 = Patient.serializer()), await)
    }

    suspend fun findPatient(search: String, page: Int = 0): Page<Patient> {
        val await = window.fetch("$SERVER/api/patient/find?search=$search&page=$page", RequestInit().apply {
            method = "GET"

        }).await().text().await()
        return Json.decodeFromString(Page.serializer(typeSerial0 = Patient.serializer()), await)
    }

    suspend fun savePatient(patient: Patient): Patient {
        val await = window.fetch("$SERVER/api/patient", RequestInit().apply {
            method = "POST"
            body = Json.encodeToString(Patient.serializer(), patient)
            headers = Headers().apply {
                append("Content-Type", "application/json")
            }
        }).await()
        return when (await.status) {
            in 200..208 -> {
                Json.decodeFromString(Patient.serializer(),
                    await.text().await())
            }
            else -> {
                throw ApiException(await.status,
                    Json.decodeFromString(await.text().await()))
            }
        }
    }
}


class ApiException(val status: Short,
                   val validationErrorResponse: ValidationErrorResponse) : Exception()

@Serializable
data class ValidationErrorResponse(
    val violations: List<Violation>?
)

@Serializable
data class Violation(
    val fieldName: String?,
    val message: String?
)

