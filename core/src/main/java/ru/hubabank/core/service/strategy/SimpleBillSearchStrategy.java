package ru.hubabank.core.service.strategy;

import lombok.RequiredArgsConstructor;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.repository.BillRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class SimpleBillSearchStrategy implements BillSearchStrategy {

    private final UUID billId;

    @Override
    public Optional<Bill> findBill(BillRepository billRepository) {
        return billRepository.findById(billId);
    }
}
