package thelameres.hospital.client

import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*
import thelameres.hospital.client.api.ApiException
import thelameres.hospital.client.api.PatientApi
import thelameres.hospital.client.api.Violation
import thelameres.hospital.client.models.*

@JsExport
class App : RComponent<PropsWithChildren, AppState>() {
    override fun AppState.init() {
        pageable = Page(
            content = emptyList(),
            empty = true,
            first = false,
            last = false,
            number = 0,
            numberOfElements = 0,
            pageable = Pageable(offset = 0,
                pageNumber = 0,
                pageSize = 0,
                paged = true,
                Sort(empty = true, sorted = false, unsorted = true),
                unpaged = false
            ),
            size = 0,
            sort = SortX(empty = true, sorted = false, unsorted = true),
            totalElements = 0,
            totalPages = 0
        )
        loading = false
        search = ""
        currentPatient = Patient(firstName = "",
            lastName = "",
            middleName = "",
            birthDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            gender = Gender.UNDEFINED,
            passport = "")
        page = 0
        violations = mutableMapOf<String, String>()
    }

    override fun componentDidMount() {
        timer(state.search)
        window.setInterval(this.timer(state.search), 1000)
    }

    private fun timer(search: String, page: Int = 0) {
        val patientApi = PatientApi()
        val mainScope = MainScope()
        mainScope.launch {
            if (search.isEmpty()) {
                val patient = patientApi.getPatient(page)
                setState {
                    this.pageable = patient
                    this.loading = true
                }
            } else {
                val findPatient = patientApi.findPatient(search, page)
                setState {
                    this.pageable = findPatient
                    this.loading = true
                }
            }


        }
    }

    override fun RBuilder.render() {
        header {
            nav(classes = "navbar-expand-md navbar-dark bg-dark") {
                div(classes = "container-fluid d-flex") {
                    a(classes = "navbar-brand") { +"Больница" }
                    button(classes = "navbar-toggler", type = ButtonType.button) {
                        attrs["data-bs-toggle"] = "collapse"
                        attrs["data-bs-target"] = "#navbarCollapse"
                        span(classes = "navbar-toggler-icon") { }
                    }
                    div(classes = "collapse navbar-collapse") {
                        attrs["id"] = "navbarCollapse"
                        ul(classes = "navbar-nav me-auto mb-2 mb-md-0") {
                            li(classes = "nav-item") {
                                a(classes = "nav-link") {
                                    attrs["data-bs-toggle"] = "modal"
                                    attrs["data-bs-target"] = "#modalForm"
                                    +"Добавить"
                                }
                            }
                        }
                        div(classes = "d-flex") {
                            input(classes = "form-control me-2", type = InputType.text) {
                                attrs {
                                    placeholder = "Поиск"
                                    value = state.search
                                    onChangeFunction = {
                                        val htmlInputElement = it.target as HTMLInputElement
                                        setState {
                                            search = htmlInputElement.value
                                        }
                                        timer(htmlInputElement.value)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        main {
            div(classes = "container-fluid") {
                if (state.loading) {
                    child(PatientTable::class) {
                        this.attrs.patients = state.pageable.content.toTypedArray()
                    }
                    nav(classes = "d-flex justify-content-center") {
                        ul(classes = "pagination") {
                            val totalPages = state.pageable.totalPages
                            val pages = totalPages
                            if (!state.pageable.first) {
                                a(classes = "page-link") {
                                    +"Начальная"
                                    attrs {
                                        onClickFunction = {
                                            setState {
                                                page = 0
                                                timer(state.search)
                                            }
                                        }
                                    }
                                }
                            }
                            if (pages < 10) {
                                for (i in 1..pages) {
                                    page(i)
                                }
                            } else {
                                val pageNumber = state.pageable.pageable.pageNumber
                                if (pageNumber < 5) {
                                    for (i in 0..10) {
                                        page(i)
                                    }
                                } else {
                                    val i1 =
                                        if (totalPages > pageNumber + 5) pageNumber + 5 else totalPages - 1
                                    console.log(i1)
                                    for (i in pageNumber - 5..i1) {
                                        page(i)
                                    }
                                }
                            }

                            if (!state.pageable.last) {
                                a(classes = "page-link") {
                                    +"Последняя"
                                    attrs {
                                        onClickFunction = {
                                            setState {
                                                page = state.pageable.totalPages - 1
                                                timer(state.search, page)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    h4 { +"Загрузка" }
                }

            }
        }
        div(classes = "modal fade") {
            attrs["id"] = "modalForm"
            attrs["tabIndex"] = "-1"
            attrs["aria-labelledby"] = "modalFormLabel"
            attrs["aria-hidden"] = "true"
            div(classes = "modal-dialog modal-xl modal-dialog-centered") {
                div(classes = "modal-content") {
                    div(classes = "modal-header") {
                        h5(classes = "modal-title") {
                            attrs["id"] = "modalFormLabel"
                            +"Добавить пациента"
                        }
                        button(type = ButtonType.button, classes = "btn-close") {
                            attrs["data-bs-dismiss"] = "modal"
                            attrs["aria-label"] = "Close"
                        }
                    }
                    div(classes = "modal-body") {
                        patientForm {
                            attrs {
                                currentPatient = state.currentPatient
                                violations = state.violations
                            }
                        }
                    }
                    div(classes = "modal-footer") {
                        button(type = ButtonType.button, classes = "btn btn-secondary") {
                            +"Закрыть"
                            attrs["data-bs-dismiss"] = "modal"
                            attrs["aria-label"] = "Close"

                        }
                        button(type = ButtonType.button, classes = "btn btn-primary") {
                            +"Сохранить"
                            attrs {
                                onClickFunction = {
                                    val patientApi = PatientApi()
                                    val mainScope = MainScope()
                                    var savePatient: Patient? = null
                                    mainScope.launch {
                                        try {
                                            savePatient = patientApi.savePatient(state.currentPatient)
                                        } catch (e: ApiException) {
                                            setState {
                                                 e.validationErrorResponse.violations!!.forEach {
                                                     violations[it.fieldName!!] = it.message!!
                                                 }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.page(i: Int) {
        li(classes = "page-item ${if (state.pageable.pageable.pageNumber == i) "active" else ""}") {
            a(classes = "page-link") {
                +"$i"
                attrs.onClickFunction = {
                    setState {
                        page = i
                        timer(state.search, i)
                    }
                }
            }
        }
    }
}


external interface AppState : State {
    var pageable: Page<Patient>
    var violations: MutableMap<String, String>
    var loading: Boolean
    var search: String
    var currentPatient: Patient
    var page: Int
}