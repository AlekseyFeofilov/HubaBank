import Alamofire

extension NetworkService: AuthNetworkProtocol {
	private enum Keys {
		static let userUid = "userUid"
		static let currentTime = "currentTimestamp"
	}

	func login(body: AuthRequest) async throws -> AuthTokenPair {
		let parameters: Parameters = [Keys.userUid: body.userIdentifier,
		                              Keys.currentTime: body.currentTime]
		return try await request(method: .post, url: URLFactory.Auth.login, parameters: parameters)
	}

	func refresh(refreshToken: String) async throws -> AuthTokenPair {
		try await request(method: .post, url: URLFactory.Auth.refresh, headers: [.authorization(bearerToken: refreshToken)])
	}

	func updateSessionCredentials(with tokens: AuthTokenPair?) {
		authenticationInterceptor.credential = tokens
	}
}
