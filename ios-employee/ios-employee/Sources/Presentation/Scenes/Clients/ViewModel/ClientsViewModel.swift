protocol ClientsViewModelDelegate: AnyObject {
	func showClientScreen(clientId: String, clientName: String)
	func showCreateClientScreen()
}

final class ClientsViewModel {
	// MARK: - Init

	// MARK: - Public

	weak var delegate: ClientsViewModelDelegate?

	private(set) var clients: [ShortClient] = []

	var onDidUpdateView: (() -> Void)?

	func loadData() {
		clients = [ShortClient(id: "1", name: "Чел 1"),
		           ShortClient(id: "2", name: "Чел 2"),
		           ShortClient(id: "3", name: "Чел 3")]
		onDidUpdateView?()
	}

	func tappedClient(id: String, name: String) {
		delegate?.showClientScreen(clientId: id, clientName: name)
	}

	func tappedAddNewClientButton() {
		delegate?.showCreateClientScreen()
	}
}
