import Foundation

final class CreditRatesCoordinator: Coordinator {
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
		showCreditsRatesScreen(animated: animated)
	}

	// MARK: - Private

	private func showCreditsRatesScreen(animated: Bool) {
		let viewModel = CreditRatesViewModel()
		viewModel.delegate = self
		let viewController = CreditsRatesViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}

	private func showCreateCreditRateScreen(animated: Bool) {
		let viewModel = CreateCreditRateViewModel()
		let viewController = CreateCreditRateViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}
}

// MARK: -

extension CreditRatesCoordinator: CreditRatesViewModelDelegate {
	func showCreateCreditRateScreen() {
		showCreateCreditRateScreen(animated: true)
	}
}
