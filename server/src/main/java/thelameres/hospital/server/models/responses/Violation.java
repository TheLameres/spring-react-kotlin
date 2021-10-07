package thelameres.hospital.server.models.responses;

import lombok.Data;

@Data
public class Violation {
    private final String fieldName;
    private final String message;
}
