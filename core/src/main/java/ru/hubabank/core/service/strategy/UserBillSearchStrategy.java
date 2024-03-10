package ru.hubabank.core.service.strategy;

import lombok.RequiredArgsConstructor;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.repository.BillRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class UserBillSearchStrategy implements BillSearchStrategy {

    private final UUID userId;
    private final UUID billId;

    @Override
    public Optional<Bill> findBill(BillRepository billRepository) {
        return billRepository.findByIdAndClosingInstantIsNull(billId)
                .filter(e -> e.getUserId().equals(userId));
    }
}
