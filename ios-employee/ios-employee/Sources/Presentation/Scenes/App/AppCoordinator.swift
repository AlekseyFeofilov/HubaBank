import UIKit

final class AppCoordinator: Coordinator {
	// MARK: - Init

	init(navigationController: NavigationController,
	     appDependency: AppDependency = AppDependency()) {
		self.navigationController = navigationController
		self.appDependency = appDependency
	}

	// MARK: - Public

	var childCoordinators: [Coordinator] = []
	var onDidFinish: (() -> Void)?

	let navigationController: NavigationController
	let appDependency: AppDependency

	func start(animated: Bool) {
		showMainScreen(animated: animated)
		//    if appDependency.authService.isAuthorized {
		//      showMainScreen(animated: animated)
		//    } else {
		//      showAuthScreen(animated: animated)
		//    }
	}

	// MARK: - Private

	private func showAuthScreen(animated: Bool) {
		let coordinator = show(AuthCoordinator.self, animated: animated)
	}

	private func showMainScreen(animated: Bool) {
		let coordinator = show(TabBarCoordinator.self, animated: animated)
	}

	private func resetCoordinators() {
		navigationController.dismiss(animated: false, completion: nil)
		navigationController.setViewControllers([], animated: false)
		navigationController.removeAllPopObservers()
		childCoordinators.removeAll()
		if let window = UIApplication.shared.connectedScenes
			.filter({ $0.activationState == .foregroundActive })
			.map({ $0 as? UIWindowScene })
			.compactMap({ $0 })
			.first?.windows
			.first(where: { $0.isKeyWindow }) {
			changeRootViewController(of: window, to: navigationController)
		}
		start(animated: false)
	}

	private func changeRootViewController(of window: UIWindow,
	                                      to viewController: UIViewController,
	                                      animationDuration: TimeInterval = 0.5) {
		let animations = {
			UIView.performWithoutAnimation {
				window.rootViewController = self.navigationController
			}
		}
		UIView.transition(with: window, duration: animationDuration, options: .transitionFlipFromLeft,
		                  animations: animations, completion: nil)
	}
}
