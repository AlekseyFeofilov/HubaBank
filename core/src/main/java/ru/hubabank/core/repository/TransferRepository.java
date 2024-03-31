package ru.hubabank.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hubabank.core.entity.Transfer;

import java.util.List;
import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    @Query("select t from Transfer t where t.source.id = :billId or t.target.id = :billId")
    List<Transfer> findAllByBillId(UUID billId);
}
