package ru.hubabank.core.service.strategy;

import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.repository.BillRepository;

import java.util.Optional;

public interface BillSearchStrategy {

    Optional<Bill> findBill(BillRepository billRepository);
}
