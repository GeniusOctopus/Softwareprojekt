package com.softwareproject.backend.repository;

import com.softwareproject.backend.model.Dump;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DumpRepository extends JpaRepository<Dump, Integer> {
}
