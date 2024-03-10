protocol EmployeesViewModelDelegate: AnyObject {
	func showCreateEmployeeScreen()
}

final class EmployeesViewModel {
	// MARK: - Init

	// MARK: - Public

	weak var delegate: EmployeesViewModelDelegate?

	private(set) var clients: [ShortEmployee] = []

	var onDidUpdateView: (() -> Void)?

	func loadData() {
		clients = [ShortEmployee(id: "1", name: "Чел 1", isBlocked: false),
		           ShortEmployee(id: "2", name: "Чел 2", isBlocked: false),
		           ShortEmployee(id: "3", name: "Чел 3", isBlocked: true)]
		onDidUpdateView?()
	}

	func tappedAddNewEmployeeButton() {
		delegate?.showCreateEmployeeScreen()
	}
}
