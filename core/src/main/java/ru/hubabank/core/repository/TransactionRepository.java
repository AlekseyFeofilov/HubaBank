package ru.hubabank.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hubabank.core.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAllByBillId(UUID billId);
}
