import UIKit

protocol AuthCoordinatorDelegate: AnyObject {
	func showMain()
}

final class AuthCoordinator: Coordinator {
	// MARK: - Init

	init(navigationController: NavigationController, appDependency: AppDependency) {
		self.navigationController = navigationController
		self.appDependency = appDependency
	}

	// MARK: - Public

	weak var delegate: AuthCoordinatorDelegate?

	let navigationController: NavigationController
	let appDependency: AppDependency

	var childCoordinators: [Coordinator] = []
	var onDidFinish: (() -> Void)?

	// MARK: - Navigation

	func start(animated: Bool) {
		showAuthScreen(animated: animated)
	}

	// MARK: - Private

	private func showAuthScreen(animated: Bool) {
		let viewModel = AuthViewModel(keychainService: appDependency.keyChainService)
		viewModel.delegate = self
		let viewController = AuthViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}

	private func showRegisterScreen(animated: Bool) {
		let viewModel = RegisterViewModel()
		viewModel.delegate = self
		let viewController = RegisterViewController(viewModel: viewModel)

		addPopObserver(for: viewController)
		navigationController.pushViewController(viewController, animated: animated)
	}
}

// MARK: - AuthViewModelDelegate

extension AuthCoordinator: AuthViewModelDelegate {
	func showRegisterScreen() {
		showRegisterScreen(animated: true)
	}

	func showMainScreen() {
		delegate?.showMain()
	}
}

extension AuthCoordinator: RegisterViewModelDelegate {
	func showAuthScreen() {
		showAuthScreen(animated: true)
	}
}
