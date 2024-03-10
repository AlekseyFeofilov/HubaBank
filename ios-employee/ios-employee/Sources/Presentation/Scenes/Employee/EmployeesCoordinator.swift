import Foundation

final class EmployeesCoordinator: Coordinator {
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
		showEmployeesScreen(animated: animated)
	}

	// MARK: - Private

	private func showEmployeesScreen(animated: Bool) {
		let viewModel = EmployeesViewModel()
		viewModel.delegate = self
		let viewController = EmployeesViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}

	private func showCreateEmployeeScreen(animated: Bool) {
		let viewModel = CreateEmployeeViewModel()
		let viewController = CreateEmployeeViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}
}

// MARK: - EmployeesViewModelDelegate

extension EmployeesCoordinator: EmployeesViewModelDelegate {
	func showCreateEmployeeScreen() {
		showCreateEmployeeScreen(animated: true)
	}
}
