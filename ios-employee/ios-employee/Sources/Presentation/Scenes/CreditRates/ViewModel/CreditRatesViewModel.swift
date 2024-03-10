protocol CreditRatesViewModelDelegate: AnyObject {
	func showCreateCreditRateScreen()
}

final class CreditRatesViewModel {
	// MARK: - Init

	// MARK: - Public

	weak var delegate: CreditRatesViewModelDelegate?

	private(set) var creditRates: [CreditRate] = []

	var onDidUpdateView: (() -> Void)?

	func loadData() {
		creditRates = [CreditRate(id: "1", title: "rate 1", interestRate: 15),
		               CreditRate(id: "2", title: "rate 2", interestRate: 1),
		               CreditRate(id: "3", title: "rate 3", interestRate: 99)]
		onDidUpdateView?()
	}

	func tappedAddNewEmployeeButton() {
		delegate?.showCreateCreditRateScreen()
	}
}
