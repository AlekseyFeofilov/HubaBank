import UIKit

class SceneDelegate: UIResponder, UIWindowSceneDelegate {
	var window: UIWindow?

	func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
		guard let scene = scene as? UIWindowScene else { return }

		appCoordinator = createMainCoordinator(scene: scene)
		appCoordinator?.start(animated: false)
	}

	// MARK: - Private

	private var appCoordinator: AppCoordinator?

	private func createMainCoordinator(scene: UIWindowScene) -> AppCoordinator {
		let window = UIWindow(windowScene: scene)
		let navigationController = NavigationController()
		window.rootViewController = navigationController
		window.makeKeyAndVisible()

		self.window = window

		return AppCoordinator(navigationController: navigationController)
	}
}
