final class BillViewModel {
	// MARK: - Init

	init(clientName: String, billId: String) {
		self.clientName = clientName
		self.billId = billId
	}

	// MARK: - Public

	private(set) var bill: Bill?
	private(set) var transactions: [Transaction] = []
	private(set) var clientName: String

	var onDidLoadData: (() -> Void)?

	func loadData() {
		loadBill()
		loadTransactions()
		onDidLoadData?()
	}

	// MARK: - Private

	private let billId: String

	private func loadBill() {
		bill = Bill(id: billId, userId: "1", balance: 100, closed: true)
	}

	private func loadTransactions() {
		transactions = [Transaction(id: "1", billId: billId, bakanceChange: 100, reason: .terminal, date: "2024-03-09T18:27:00.419Z"),
		                Transaction(id: "2", billId: billId, bakanceChange: -100, reason: .terminal,
		                            date: "2024-03-09T10:27:00.419Z"),
		                Transaction(id: "3", billId: billId, bakanceChange: -1, reason: .terminal, date: "2024-02-09T00:10:00.419Z")]
	}
}
