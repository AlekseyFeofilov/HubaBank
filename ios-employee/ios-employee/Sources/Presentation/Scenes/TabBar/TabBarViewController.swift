import SnapKit
import UIKit

protocol TabBarViewControllerDelegate: AnyObject {
	func tabBarViewControllerDidTapMentorBall(_ viewController: TabBarViewController)
}

final class TabBarViewController: UITabBarController, NavigationBarHiding {
	// MARK: - Properties

	weak var tabBarDelegate: TabBarViewControllerDelegate?

	private let tabBarView = TabBarView()

	// MARK: - Overrides

	override func viewDidLoad() {
		super.viewDidLoad()
		setup()
	}

	// MARK: - Public methods

	func hideTabBar(animated: Bool) {
		self.tabBarView.snp.remakeConstraints { make in
			make.top.equalTo(self.view.snp.bottom).offset(18)
			make.leading.equalToSuperview().inset(24)
		}
		UIView.animate(withDuration: animated ? 0.3 : 0,
		               delay: 0,
		               options: .curveEaseInOut) {
			self.view.layoutIfNeeded()
		} completion: { _ in
			self.tabBarView.isHidden = true
		}
	}

	func showTabBar(animated: Bool) {
		tabBarView.isHidden = false
		tabBarView.snp.remakeConstraints { make in
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(16)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
		UIView.animate(withDuration: animated ? 0.3 : 0,
		               delay: 0,
		               options: .curveEaseInOut) {
			self.view.layoutIfNeeded()
		}
	}

	func showTab(withItem item: TabBarItem) {
		tabBarView.select(item: item)
	}

	// MARK: - Setup

	private func setup() {
		setupTabBar()
	}

	private func setupTabBar() {
		tabBar.isHidden = true
		view.addSubview(tabBarView)
		tabBarView.snp.makeConstraints { make in
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(16)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
		tabBarView.onDidSelectTab = { [weak self] index in
			self?.selectedIndex = index
		}
	}
}
