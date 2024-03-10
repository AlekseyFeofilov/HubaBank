struct BillCoordinatorConfiguration {
	let clientId: String
	let clientName: String
	let billId: String
}

final class BillCoordinator: ConfigurableCoordinator {
	typealias Configuration = BillCoordinatorConfiguration

	// MARK: - Init

	init(navigationController: NavigationController, appDependency: AppDependency, configuration: Configuration) {
		self.navigationController = navigationController
		self.appDependency = appDependency
		self.configuration = configuration
	}

	// MARK: - Public

	let navigationController: NavigationController
	let appDependency: AppDependency
	let configuration: Configuration

	var childCoordinators: [Coordinator] = []
	var onDidFinish: (() -> Void)?

	// MARK: - Navigation

	func start(animated: Bool) {
		showBillScreen(clientId: configuration.clientId, clientName: configuration.clientName, billId: configuration.billId,
		               animated: animated)
	}

	// MARK: - Private

	private func showBillScreen(clientId: String, clientName: String, billId: String, animated: Bool) {
		let viewModel = BillViewModel(clientName: clientName, billId: billId)
		let viewController = BillViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}
}
