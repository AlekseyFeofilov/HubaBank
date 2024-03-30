package ru.hubabank.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hubabank.core.entity.Bill;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {

    Optional<Bill> findByIdAndClosingInstantIsNull(UUID id);

    List<Bill> findAllByUserIdAndClosingInstantIsNull(UUID clientId);

    @Query("select b from Bill b where b.type = 'TERMINAL'")
    Bill findTerminalBill();

    @Query("select b from Bill b where b.type = 'USER' and b.userId = :userId")
    Optional<Bill> findMainUserBill(UUID userId);
}
