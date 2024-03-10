final class AuthCoordinator: Coordinator {
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
		showAuthScreen(animated: animated)
	}

	// MARK: - Private

	private func showAuthScreen(animated: Bool) {
		let viewModel = AuthViewModel()
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

// MARK: -

extension AuthCoordinator: AuthViewModelDelegate {
	func showRegisterScreen() {
		showRegisterScreen(animated: true)
	}
}

extension AuthCoordinator: RegisterViewModelDelegate {
	func showAuthScreen() {
		showAuthScreen(animated: true)
	}
}
