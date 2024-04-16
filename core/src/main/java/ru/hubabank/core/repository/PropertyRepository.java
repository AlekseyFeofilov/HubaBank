package ru.hubabank.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hubabank.core.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, String> {
}