package ru.hubabank.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hubabank.core.entity.Bill;

import java.util.List;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {

    List<Bill> findAllByUserId(UUID clientId);
}
