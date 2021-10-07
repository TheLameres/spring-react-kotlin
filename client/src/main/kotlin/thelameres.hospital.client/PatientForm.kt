package thelameres.hospital.client

import kotlinx.datetime.LocalDate
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import react.*
import react.dom.*
import styled.css
import styled.styledForm
import thelameres.hospital.client.models.Gender
import thelameres.hospital.client.models.Patient

external interface PatientFormProps : Props {
    var currentPatient: Patient
    var violations: MutableMap<String, String>
}

class PatientForm(props: PatientFormProps) : RComponent<PatientFormProps, State>(props) {
    override fun RBuilder.render() {
        styledForm {
            div(classes = "row md-g-3") {
                div(classes = "col-md-4") {
                    label(classes = "form-label") {
                        +"Фамилия"
                    }
                    input(type = InputType.text, classes = "form-control") {
                        val mutableSet = attrs.classes as MutableSet<String>
                        if (props.violations.containsKey("lastName")) {
                            mutableSet.add("is-invalid")
                        }
                        this.attrs.classes = mutableSet
                        attrs {

                            required = true
                            value = props.currentPatient.lastName
                            onChangeFunction = {
                                val htmlInputElement = it.target as HTMLInputElement
                                setState {
                                    props.currentPatient.lastName = htmlInputElement.value
                                }
                            }
                        }
                    }
                }
                div(classes = "col-md-4") {
                    label(classes = "form-label") {
                        +"Имя"
                    }
                    input(type = InputType.text, classes = "form-control") {
                        val mutableSet = attrs.classes as MutableSet<String>
                        if (props.violations.containsKey("firstName")) {
                            mutableSet.add("is-invalid")
                        }
                        this.attrs.classes = mutableSet
                        attrs {
                            required = true
                            value = props.currentPatient.firstName
                            onChangeFunction = {
                                val htmlInputElement = it.target as HTMLInputElement
                                setState {
                                    props.currentPatient.firstName = htmlInputElement.value
                                }
                            }
                        }
                    }
                }
                div(classes = "col-md-4") {
                    label(classes = "form-label") {
                        +"Отчество"
                    }
                    input(type = InputType.text, classes = "form-control") {
                        val mutableSet = attrs.classes as MutableSet<String>
                        if (props.violations.containsKey("middleName")) {
                            mutableSet.add("is-invalid")
                        }
                        this.attrs.classes = mutableSet
                        attrs {
                            required = true
                            value = props.currentPatient.middleName
                            onChangeFunction = {
                                val htmlInputElement = it.target as HTMLInputElement
                                setState {
                                    props.currentPatient.middleName = htmlInputElement.value
                                }
                            }
                        }
                    }
                }
                div(classes = "col-md-4") {
                    label(classes = "form-label") {
                        +"Дата рождения"

                    }
                    input(type = InputType.date, classes = "form-control") {
                        val mutableSet = attrs.classes as MutableSet<String>
                        if (props.violations.containsKey("birthDate")) {
                            mutableSet.add("is-invalid")
                        }
                        this.attrs.classes = mutableSet
                        attrs {
                            value = props.currentPatient.birthDate.toString()
                            onChangeFunction = {
                                val htmlInputElement = it.target as HTMLInputElement
                                setState {
                                    props.currentPatient.birthDate = LocalDate.parse(htmlInputElement.value)
                                }
                            }
                        }
                    }
                    div {
                    }
                }
                div(classes = "col-md-4") {
                    label(classes = "form-label") {
                        +"Паспорт (серия, номер)"
                    }
                    input(type = InputType.text, classes = "form-control") {
                        val mutableSet = attrs.classes as MutableSet<String>
                        if (props.violations.containsKey("passport")) {
                            mutableSet.add("is-invalid")
                        }
                        this.attrs.classes = mutableSet
                        attrs {
                            required = true
                            value = props.currentPatient.passport
                            pattern = "[0-9]{4}\\s[0-9]{6}"
                            placeholder = "0000 000000"
                            onChangeFunction = {
                                val htmlInputElement = it.target as HTMLInputElement
                                setState {
                                    props.currentPatient.passport = htmlInputElement.value
                                }
                            }
                        }
                    }
                }
                div(classes = "col-md-4") {
                    label(classes = "form-label") {
                        +"Пол"
                    }
                    select(classes = "form-select") {
                        val mutableSet = attrs.classes as MutableSet<String>
                        if (props.violations.containsKey("gender")) {
                            mutableSet.add("is-invalid")
                        }
                        this.attrs.classes = mutableSet
                        attrs {
                            value = props.currentPatient.gender.name
                            onChangeFunction = {
                                val htmlSelectElement = it.target as HTMLSelectElement
                                setState {
                                    props.currentPatient.gender = Gender.valueOf(htmlSelectElement.value)
                                }
                            }
                        }
                        option {
                            attrs.value = "UNDEFINED"
                            attrs.disabled = true

                            +"Выберите пол"
                        }
                        option {
                            attrs.value = "MALE"
                            +"Мужской"
                        }
                        option {
                            attrs.value = "FEMALE"
                            +"Женский"
                        }
                    }
                }
            }
            css {

            }
        }
    }
}

fun RBuilder.patientForm(handler: PatientFormProps.() -> Unit) {
    child(PatientForm::class) {
        this.attrs(handler)
    }
}
