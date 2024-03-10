protocol RegisterViewModelDelegate: AnyObject {
	func showAuthScreen()
}

final class RegisterViewModel {
	// MARK: - Public

	weak var delegate: RegisterViewModelDelegate?

	func tappedAlreadyHaveAccountButton() {
		delegate?.showAuthScreen()
	}
}
