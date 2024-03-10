import UIKit

struct KeyboardInfo {
	let animationDuration: TimeInterval
	let animationCurve: UIView.AnimationCurve
	let startFrame: CGRect
	let endFrame: CGRect

	var keyboardHeight: CGFloat {
		UIScreen.main.bounds.height - endFrame.origin.y
	}

	init(notification: Notification) {
		let userInfo = notification.userInfo

		animationDuration = (userInfo?[UIResponder.keyboardAnimationDurationUserInfoKey] as? TimeInterval) ?? 0

		let animationCurveValue = (userInfo?[UIResponder.keyboardAnimationCurveUserInfoKey] as? Int) ?? 0
		animationCurve = UIView.AnimationCurve(rawValue: animationCurveValue) ?? .linear

		startFrame = (userInfo?[UIResponder.keyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue ?? .zero
		endFrame = (userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue ?? .zero
	}
}

protocol KeyboardVisibilityHandling: AnyObject {
	var keyboardWillShowObserver: NSObjectProtocol? { get set }
	var keyboardWillHideObserver: NSObjectProtocol? { get set }
	var keyboardWillChangeFrameObserver: NSObjectProtocol? { get set }

	func keyboardWillShow(keyboardInfo: KeyboardInfo)
	func keyboardWillHide(keyboardInfo: KeyboardInfo)
	func keyboardWillChangeFrame(keyboardInfo: KeyboardInfo)

	func subscribeForKeyboardStateUpdates()
	func unsubscribeFromKeyboardStateUpdates()
}

// MARK: - Base realisation

extension KeyboardVisibilityHandling {
	func keyboardWillShow(keyboardInfo: KeyboardInfo) {}
	func keyboardWillHide(keyboardInfo: KeyboardInfo) {}
	func keyboardWillChangeFrame(keyboardInfo: KeyboardInfo) {}

	func subscribeForKeyboardStateUpdates() {
		let notificationCenter = NotificationCenter.default

		keyboardWillShowObserver = notificationCenter.addObserver(forName: UIResponder.keyboardWillShowNotification,
		                                                          object: nil,
		                                                          queue: .main) { [weak self] notification in
			self?.keyboardWillShow(keyboardInfo: KeyboardInfo(notification: notification))
		}

		keyboardWillHideObserver = notificationCenter.addObserver(forName: UIResponder.keyboardWillHideNotification,
		                                                          object: nil,
		                                                          queue: .main) { [weak self] notification in
			self?.keyboardWillHide(keyboardInfo: KeyboardInfo(notification: notification))
		}

		keyboardWillChangeFrameObserver = notificationCenter.addObserver(forName: UIResponder.keyboardWillChangeFrameNotification,
		                                                                 object: nil,
		                                                                 queue: .main) { [weak self] notification in
			self?.keyboardWillChangeFrame(keyboardInfo: KeyboardInfo(notification: notification))
		}
	}

	func unsubscribeFromKeyboardStateUpdates() {
		if let keyboardWillShowObserver = keyboardWillShowObserver {
			NotificationCenter.default.removeObserver(keyboardWillShowObserver)
		}

		if let keyboardWillHideObserver = keyboardWillHideObserver {
			NotificationCenter.default.removeObserver(keyboardWillHideObserver)
		}

		if let keyboardWillChangeFrameObserver = keyboardWillChangeFrameObserver {
			NotificationCenter.default.removeObserver(keyboardWillChangeFrameObserver)
		}

		keyboardWillShowObserver = nil
		keyboardWillHideObserver = nil
		keyboardWillChangeFrameObserver = nil
	}
}
