package cl.municipalidadchillan.dideco.controller;

import java.util.List;
import java.util.Map; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.service.ProgramaService;

@RestController
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    @GetMapping
    public List<Programa> getAll() {
        return programaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Programa> getById(@PathVariable int id) {
        Programa programa = programaService.findById(id);
        if (programa != null) {
            return ResponseEntity.ok(programa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Programa> create(@RequestBody Programa programa) {
        Programa nuevo = programaService.save(programa);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
public ResponseEntity<Programa> update(@PathVariable int id, @RequestBody Programa programaActualizado) {
    Programa existente = programaService.findById(id);
    if (existente == null) {
        return ResponseEntity.notFound().build();
    }

    existente.setNombrePrograma(programaActualizado.getNombrePrograma());
    existente.setDescripcion(programaActualizado.getDescripcion());
    existente.setTipoPrograma(programaActualizado.getTipoPrograma());
    existente.setOficinaResponsable(programaActualizado.getOficinaResponsable());
    existente.setContactoEncargado(programaActualizado.getContactoEncargado());
    existente.setFechaInicio(programaActualizado.getFechaInicio());
    existente.setFechaFin(programaActualizado.getFechaFin());
    existente.setCupos(programaActualizado.getCupos());
    existente.setMetas(programaActualizado.getMetas());
    existente.setRequisitosIngreso(programaActualizado.getRequisitosIngreso());
    existente.setBeneficios(programaActualizado.getBeneficios());
    existente.setEstado(programaActualizado.getEstado());

    Programa actualizado = programaService.save(existente);

    return ResponseEntity.ok(actualizado);
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        programaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public List<Programa> findByEstado(@PathVariable String estado) {
        return programaService.findByEstado(estado);
    }

    @GetMapping("/{id}/gastos-mensuales")
    public ResponseEntity<List<Map<String, Object>>> getGastosMensuales(@PathVariable int id) {
        List<Map<String, Object>> gastos = jdbcTemplate.query(
            """
            SELECT 
              MONTH(fecha_registro) AS mes,
              YEAR(fecha_registro) AS anio,
              SUM(monto_ejecutado) AS total_gastado
            FROM presupuesto
            WHERE id_programa = ?
              AND monto_ejecutado IS NOT NULL
            GROUP BY anio, mes
            ORDER BY anio, mes;
            """,
            (rs, rowNum) -> Map.of(
                "mes", rs.getInt("mes"),
                "anio", rs.getInt("anio"),
                "totalGasto", rs.getDouble("total_gastado")
            ),
            id
        );
        return ResponseEntity.ok(gastos);
    }
}