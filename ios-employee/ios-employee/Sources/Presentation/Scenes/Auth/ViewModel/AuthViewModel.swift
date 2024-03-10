protocol AuthViewModelDelegate: AnyObject {
	func showRegisterScreen()
}

final class AuthViewModel {
	// MARK: - Public

	weak var delegate: AuthViewModelDelegate?

	func tappedNotHaveAccountButton() {
		delegate?.showRegisterScreen()
	}
}
