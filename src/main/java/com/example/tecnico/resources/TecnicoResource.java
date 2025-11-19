package com.example.tecnico.resources;

import com.example.tecnico.dto.TecnicoDTO;
import com.example.tecnico.services.TecnicoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TecnicoDTO> insert(@Valid @RequestBody TecnicoDTO dto) {
        TecnicoDTO newDto = service.insert(dto);
        URI uri = fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody TecnicoDTO dto) {
        TecnicoDTO newDto = service.update(id, dto);
        return ResponseEntity.ok(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
