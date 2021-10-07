package thelameres.hospital.server.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import thelameres.hospital.server.models.entities.AbstractEntity;

public interface AbstractEntityRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}