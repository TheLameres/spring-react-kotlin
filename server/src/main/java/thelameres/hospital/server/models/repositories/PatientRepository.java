package thelameres.hospital.server.models.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import thelameres.hospital.server.models.entities.Patient;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public interface PatientRepository extends AbstractEntityRepository<Patient> {
    @Query("select p from Patient p where p.firstName = ?1 or p.lastName = ?2 or p.middleName = ?3 or p.passport = ?4")
    Page<Patient> findAllByFirstNameOrLastNameOrMiddleNameOrPassport(@NotNull @NotEmpty String firstName,
                                                                     @NotNull @NotEmpty String lastName,
                                                                     String middleName,
                                                                     @Pattern(regexp = "\\d{4}\\s\\d{6}")
                                                                     @NotNull @NotEmpty String passport,
                                                                     Pageable pageable);

    @Query("select p from Patient p where p.firstName like concat('%', ?1, '%') or p.lastName like concat('%', ?1, '%') or p.middleName like concat('%', ?1, '%') or p.passport like concat('%', ?1, '%')")
    Page<Patient> findAllByFirstNameOrLastNameOrMiddleNameOrPassport(String search, Pageable pageable);
}