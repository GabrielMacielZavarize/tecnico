package com.example.tecnico.repositories;

import com.example.tecnico.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}