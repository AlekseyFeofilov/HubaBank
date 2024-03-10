import Foundation

final class ClientsCoordinator: Coordinator {
	// MARK: - Init

	init(navigationController: NavigationController, appDependency: AppDependency) {
		self.navigationController = navigationController
		self.appDependency = appDependency
	}

	// MARK: - Public

	let navigationController: NavigationController
	let appDependency: AppDependency

	var childCoordinators: [Coordinator] = []
	var onDidFinish: (() -> Void)?

	// MARK: - Navigation

	func start(animated: Bool) {
		showClientsScreen(animated: animated)
	}

	// MARK: - Private

	private func showClientsScreen(animated: Bool) {
		let viewModel = ClientsViewModel()
		viewModel.delegate = self
		let viewController = ClientsViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}

	private func showClientScreen(clientId: String, clientName: String, animated: Bool) {
		let viewModel = ClientViewModel(clientId: clientId, clientName: clientName)
		viewModel.delegate = self
		let viewController = ClientViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}

	private func showCreateClientScreen(animated: Bool) {
		let viewModel = CreateClientViewModel()
		let viewController = CreateClientViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}
}

// MARK: - ClientsViewModelDelegate

extension ClientsCoordinator: ClientsViewModelDelegate {
	func showClientScreen(clientId: String, clientName: String) {
		showClientScreen(clientId: clientId, clientName: clientName, animated: true)
	}

	func showCreateClientScreen() {
		showCreateClientScreen(animated: true)
	}
}

// MARK: - ClientViewModelDelegate

extension ClientsCoordinator: ClientViewModelDelegate {
	func showBill(clientId: String, clientName: String, billId: String) {
		show(BillCoordinator.self,
		     configuration: BillCoordinatorConfiguration(clientId: clientId, clientName: clientName, billId: billId),
		     animated: true)
	}

	func showCredit(clientName: String, creditId: String) {
		show(CreditCoordinator.self, configuration: CreditCoordinatorConfiguration(clientName: clientName, creditId: creditId),
		     animated: true)
	}
}
