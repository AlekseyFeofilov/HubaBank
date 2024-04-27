protocol AuthViewModelDelegate: AnyObject {
	func showRegisterScreen()
	func showMainScreen()
}

final class AuthViewModel {
	init(keychainService: KeyChainServiceProtocol) {
		self.keychainService = keychainService
	}

	// MARK: - Public

	weak var delegate: AuthViewModelDelegate?

	func tappedNotHaveAccountButton() {
		delegate?.showRegisterScreen()
	}

	func tappedAuthButton() {
		keychainService.save(AuthTokenPair(// swiftlint:disable:next line_length
			accessToken: "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTQwNDcyMjUsImlkIjoiYmM1Y2UxNTctNDNmOS00MTE2LWEzM2YtYTZmN2MyZjRiYTFiIiwiZmlyc3ROYW1lIjoiZWxlbmEiLCJzZWNvbmROYW1lIjoiZ2VyYXNpbWNodWsiLCJ0aGlyZE5hbWUiOiJldmdlbmV2bmEiLCJwcml2aWxlZ2VzIjpbIlRSQU5TQUNUSU9OX1JFQUQiLCJUUkFOU0FDVElPTl9XUklURSIsIkJJTExfV1JJVEUiLCJCSUxMX1JFQUQiXX0.rn57xvObaUaA_GbdF_WO-SX4LPRk71xRPQfjDi706tTWyQmA369xCzevKqw5MX84wYvvOtm4GWYYlt9zEXId_Q",
			refreshToken: "39916210"), service: .tokens, account: .buba)
		delegate?.showMainScreen()
	}

	private let keychainService: KeyChainServiceProtocol
}
