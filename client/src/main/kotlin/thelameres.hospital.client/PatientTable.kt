package thelameres.hospital.client

import kotlinx.html.ThScope
import thelameres.hospital.client.models.Patient
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.*

external interface PatientTableProps : Props {
    var patients: Array<Patient>
}

class PatientTable(props: PatientTableProps) : RComponent<PatientTableProps, State>(props) {
    override fun RBuilder.render() {
        table("table") {
            thead() {
                tr {
                    th(scope = ThScope.col) {
                        +"id"
                    }
                    th(scope = ThScope.col) {
                        +"Фамилия"
                    }
                    th(scope = ThScope.col) {
                        +"Имя"
                    }
                    th(scope = ThScope.col) {
                        +"Отчество"
                    }
                    th(scope = ThScope.col) {
                        +"Дата рождения"
                    }
                    th(scope = ThScope.col) {
                        +"Паспорт (Серия, номер)"
                    }
                    th(scope = ThScope.col) {
                        +"Пол"
                    }
                }
            }
            tbody {
                props.patients.map {
                    patientCell {
                        patient = it
                    }
                }
            }
        }
    }
}