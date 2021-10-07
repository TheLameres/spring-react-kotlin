package thelameres.hospital.server.models.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "patients")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Patient extends AbstractEntity {
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String firstName;
    private String middleName;
    @PastOrPresent
    private LocalDate birthDate;
    @Pattern(regexp = "\\d{4}\\s\\d{6}")
    @NotNull
    @NotEmpty
    private String passport;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
