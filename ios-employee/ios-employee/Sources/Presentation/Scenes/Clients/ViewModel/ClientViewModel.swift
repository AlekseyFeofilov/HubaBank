protocol ClientViewModelDelegate: AnyObject {
	func showBill(clientId: String, clientName: String, billId: String)
	func showCredit(clientName: String, creditId: String)
}

final class ClientViewModel {
	// MARK: - Init

	init(clientId: String, clientName: String) {
		self.clientId = clientId
		self.clientName = clientName
	}

	// MARK: - Public

	weak var delegate: ClientViewModelDelegate?

	private(set) var clientName: String
	private(set) var bills: [Bill] = []
	private(set) var credits: [ShortCredit] = []
	private(set) var currentContentType: ContentType = .bills {
		didSet {
			updateContentView?(currentContentType)
		}
	}

	var updateContentView: ((ContentType) -> Void)?
	var onDidLoadData: (() -> Void)?

	func loadData() {
		switch currentContentType {
		case .bills:
			loadBills()
		case .credits:
			loadCredits()
		}
	}

	func tappedCredit(creditId: String) {
		delegate?.showCredit(clientName: clientName, creditId: creditId)
	}

	func tappedBill(billId: String) {
		delegate?.showBill(clientId: clientId, clientName: clientName, billId: billId)
	}

	func tappedBlockButton() {
		// TODO: do it
	}

	func tappedBillsButton() {
		currentContentType = .bills
	}

	func tappedCreditButton() {
		currentContentType = .credits
	}

	// MARK: - Private

	private let clientId: String

	private func loadBills() {
		bills = [Bill(id: "1", userId: clientId, balance: 1231231, closed: false),
		         Bill(id: "2", userId: clientId, balance: 123, closed: false),
		         Bill(id: "3", userId: clientId, balance: 0, closed: false)]
		onDidLoadData?()
	}

	private func loadCredits() {
		credits = [ShortCredit(id: "1", title: "Credit 1", money: 12312312000000),
		           ShortCredit(id: "2", title: "Credit 1", money: 70000)]
		onDidLoadData?()
	}
}
