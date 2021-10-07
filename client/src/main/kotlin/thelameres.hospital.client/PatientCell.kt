package thelameres.hospital.client

import react.PropsWithClassName
import react.RBuilder
import react.RComponent
import react.State
import react.dom.td
import react.dom.tr
import thelameres.hospital.client.models.Gender
import thelameres.hospital.client.models.Patient

external interface PatientProps : PropsWithClassName {
    var patient: Patient
}

class PatientCell(props: PatientProps) : RComponent<PatientProps, State>(props) {
    override fun RBuilder.render() {
        tr {
            key = props.patient.id.toString()
            td {
                +"${props.patient.id}"
            }
            td {
                +props.patient.lastName
            }
            td {
                +props.patient.firstName
            }
            td {
                +props.patient.middleName
            }
            td {
                +"${props.patient.birthDate}"
            }
            td {
                +props.patient.passport
            }
            td {
                when (props.patient.gender) {
                    Gender.FEMALE -> +"Женский"
                    Gender.MALE -> +"Мужской"
                    Gender.UNDEFINED -> +""
                }
            }
        }
    }
}

fun RBuilder.patientCell(handler: PatientProps.() -> Unit) {
    return child(PatientCell::class) {
        this.attrs(handler)
    }
}