package com.example.tecnico.resources;

import com.example.tecnico.dto.ChamadoDTO;
import com.example.tecnico.services.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/chamados")
public class ChamadoResource {

    @Autowired
    private ChamadoService service;

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<ChamadoDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Long id) {
        ChamadoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> insert(@Valid @RequestBody ChamadoDTO dto) {
        ChamadoDTO newDto = service.insert(dto);
        URI uri = fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody ChamadoDTO dto) {
        ChamadoDTO newDto = service.update(id, dto);
        return ResponseEntity.ok(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
