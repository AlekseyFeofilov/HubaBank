final class CreditViewModel {
	// MARK: - Init

	init(creditId: String, clientName: String) {
		self.creditId = creditId
		self.clientName = clientName
	}

	// MARK: - Public

	private(set) var credit: Credit?
	private(set) var clientName: String

	var onDidLoadData: (() -> Void)?

	func loadData() {
		credit = Credit(id: creditId, title: "Credit 1", amount: 1000, paidOut: 1, interestRate: 99)
		onDidLoadData?()
	}

	// MARK: - Private

	private let creditId: String
}
