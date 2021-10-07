package thelameres.hospital.server.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import thelameres.hospital.server.models.entities.Patient;
import thelameres.hospital.server.models.repositories.PatientRepository;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public Page<Patient> getAllPatient(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @GetMapping("/find")
    public Page<Patient> findPatient(Pageable pageable, @RequestParam(defaultValue = "") String search) {
        return patientRepository.findAllByFirstNameOrLastNameOrMiddleNameOrPassport(search, pageable);
    }

    @GetMapping("/count")
    public Map<String, Long> count() {
        return Collections.singletonMap("count", patientRepository.count());
    }

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody @Valid Patient patient) {
        try {
            Patient save = patientRepository.save(patient);
            return ResponseEntity.ok(save);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @PostMapping("/validate")
    public Map<String, String> validate(@RequestBody @Valid Patient patient) {
        return Collections.singletonMap("Valid", "ok");
    }
}
