struct CreditCoordinatorConfiguration {
	let clientName: String
	let creditId: String
}

final class CreditCoordinator: ConfigurableCoordinator {
	typealias Configuration = CreditCoordinatorConfiguration

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
		showCredit(clientName: configuration.clientName, creditId: configuration.creditId, animated: animated)
	}

	// MARK: - Private

	private func showCredit(clientName: String, creditId: String, animated: Bool) {
		let viewModel = CreditViewModel(creditId: creditId, clientName: clientName)
		let viewController = CreditViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}
}
