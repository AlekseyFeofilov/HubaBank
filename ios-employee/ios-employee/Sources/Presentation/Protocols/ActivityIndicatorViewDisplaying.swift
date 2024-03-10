import UIKit

protocol ActivityIndicatorViewDisplaying {
	var activityIndicatorView: ActivityIndicatorView { get }
	var activityIndicatorContainerView: UIView { get }

	func setupActivityIndicatorView()
}

// MARK: - Base realisation

extension ActivityIndicatorViewDisplaying {
	func setupActivityIndicatorView() {
		activityIndicatorContainerView.addSubview(activityIndicatorView)
		activityIndicatorView.snp.makeConstraints { make in
			make.center.equalToSuperview()
		}
	}
}

extension ActivityIndicatorViewDisplaying where Self: UIViewController {
	var activityIndicatorContainerView: UIView {
		view
	}
}

extension ActivityIndicatorViewDisplaying where Self: UIView {
	var activityIndicatorContainerView: UIView {
		self
	}
}
