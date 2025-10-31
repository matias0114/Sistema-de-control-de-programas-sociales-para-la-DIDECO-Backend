package cl.municipalidadchillan.dideco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidadchillan.dideco.model.BeneficiarioPrograma;
import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.service.BeneficiarioProgramaService;
import cl.municipalidadchillan.dideco.service.ProgramaService;

@RestController
@RequestMapping("/beneficiarios-programa")
public class BeneficiarioProgramaController {

    @Autowired
    private BeneficiarioProgramaService beneficiarioProgramaService;

    @Autowired
    private ProgramaService programaService;

    @GetMapping
    public List<BeneficiarioPrograma> getAll() {
        return beneficiarioProgramaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiarioPrograma> getById(@PathVariable int id) {
        BeneficiarioPrograma beneficiario = beneficiarioProgramaService.findById(id);
        if (beneficiario != null) {
            return ResponseEntity.ok(beneficiario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/programa/{idPrograma}")
    public List<BeneficiarioPrograma> findByProgramaId(@PathVariable int idPrograma) {
        return beneficiarioProgramaService.findByProgramaId(idPrograma);
    }

    @PostMapping
    public ResponseEntity<BeneficiarioPrograma> create(@RequestBody BeneficiarioPrograma beneficiario) {
        if (beneficiario.getPrograma() == null || beneficiario.getPrograma().getIdPrograma() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Programa programa = programaService.findById(beneficiario.getPrograma().getIdPrograma());
        if (programa == null) {
            return ResponseEntity.badRequest().build();
        }
        beneficiario.setPrograma(programa);
        BeneficiarioPrograma nuevo = beneficiarioProgramaService.save(beneficiario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiarioPrograma> update(@PathVariable int id, @RequestBody BeneficiarioPrograma actualizado) {
        BeneficiarioPrograma existente = beneficiarioProgramaService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setNombreCompleto(actualizado.getNombreCompleto());
        existente.setRut(actualizado.getRut());
        existente.setTelefono(actualizado.getTelefono());
        existente.setDireccion(actualizado.getDireccion());

        if (actualizado.getPrograma() == null || actualizado.getPrograma().getIdPrograma() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Programa programa = programaService.findById(actualizado.getPrograma().getIdPrograma());
        if (programa == null) {
            return ResponseEntity.badRequest().build();
        }
        existente.setPrograma(programa);

        BeneficiarioPrograma resultado = beneficiarioProgramaService.save(existente);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        beneficiarioProgramaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}