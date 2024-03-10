import Foundation
import UIKit

protocol TabBarCoordinatorDelegate: AnyObject {
	func tabBarCoordinatorDidRequestToShowOnboarding(_ coordinator: TabBarCoordinator)
	func tabBarCoordinatorDidRequestLogout(_ coordinator: TabBarCoordinator)
}

final class TabBarCoordinator: Coordinator {
	// MARK: - Properties

	let navigationController: NavigationController
	let appDependency: AppDependency

	weak var delegate: TabBarCoordinatorDelegate?

	var childCoordinators: [Coordinator] = []
	var onDidFinish: (() -> Void)?

	private let tabBarController = TabBarViewController()
	private let clientsNavigationController = NavigationController()
	private let employeesNavigationController = NavigationController()
	private let creditRatesNavigationController = NavigationController()
	private let profileNavigationController = NavigationController()

	// MARK: - Init

	init(navigationController: NavigationController, appDependency: AppDependency) {
		self.navigationController = navigationController
		self.appDependency = appDependency
	}

	// MARK: - Navigation

	func start(animated: Bool) {
		showTabBar(animated: animated)
	}

	// MARK: - Private

	private func showTabBar(animated: Bool) {
//		tabBarController.tabBarDelegate = self

		let clientsCoordinator = ClientsCoordinator(navigationController: clientsNavigationController,
		                                            appDependency: appDependency)
		clientsCoordinator.start(animated: false)

		let employeesCoordinatorl = EmployeesCoordinator(navigationController: employeesNavigationController,
		                                                 appDependency: appDependency)
		employeesCoordinatorl.start(animated: false)

		let creditRatesCoordinator = CreditRatesCoordinator(navigationController: creditRatesNavigationController,
		                                                    appDependency: appDependency)
		creditRatesCoordinator.start(animated: true)

		let profileCoordinator = ProfileCoordinator(navigationController: profileNavigationController, appDependency: appDependency)
		profileCoordinator.start(animated: true)

		tabBarController.viewControllers = [clientsNavigationController, employeesNavigationController,
		                                    creditRatesNavigationController,
		                                    profileNavigationController]

		addPopObserver(for: tabBarController)
		navigationController.pushViewController(tabBarController, animated: animated)
	}
}
