final class AuthServiceMock: AuthServiceProtocol {
	// MARK: - Init

	init(authNetworkService: AuthNetworkProtocol, keyChainService: KeyChainServiceProtocol) {
		self.authNetworkService = authNetworkService
		self.keyChainService = keyChainService

		if isAuthorized {
			let tokens: AuthTokenPair? = keyChainService.read(service: .token, account: .iSpace)
			authNetworkService.updateSessionCredentials(with: tokens)
		}
	}

	// MARK: - Public

	var isAuthorized: Bool {
		keyChainService.hasData(service: .token, account: .iSpace)
	}

	func login(userIdentifier: String) async throws {
		do {
			let authTokens = AuthTokenPair(accessToken: "asd123", refreshToken: "asd123")
			updateTokens(authTokens)
		} catch {
			throw error
		}
	}

	func logout() {
		keyChainService.delete(service: .token, account: .iSpace)
	}

	// MARK: - Private

	private let authNetworkService: AuthNetworkProtocol
	private let keyChainService: KeyChainServiceProtocol

	private func updateTokens(_ tokens: AuthTokenPair) {
		keyChainService.save(tokens, service: .token, account: .iSpace)
		authNetworkService.updateSessionCredentials(with: tokens)
	}
}

// MARK: - Keys

private extension AuthServiceMock {
	enum Keys: String {
		case token
	}
}

extension AuthServiceMock: AuthAuthenticatorDelegate {
	func authAuthenticatorDidRequestRefresh(_ authAuthenticator: AuthAuthenticator, with credential: AuthTokenPair,
	                                        completion: @escaping (Result<AuthTokenPair, Error>) -> Void) {
		let authTokens = AuthTokenPair(accessToken: "asd123", refreshToken: "asd123")
		updateTokens(authTokens)
	}
}
