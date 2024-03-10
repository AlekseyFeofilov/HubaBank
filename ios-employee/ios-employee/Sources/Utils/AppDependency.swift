import Alamofire

protocol HasAuthService {
	var auth: AuthServiceProtocol { get }
}

final class AppDependency {
	init() {
		keyChainService = KeyChainService()
		dataStoreService = DataStoreService()
		authAuthenticator = AuthAuthenticator()
		authenticationInterceptor = AuthenticationInterceptor(authenticator: authAuthenticator)
		networkService = NetworkService(authenticationInterceptor: authenticationInterceptor)
		authService = AuthServiceMock(authNetworkService: networkService, keyChainService: keyChainService)
	}

	private let dataStoreService: DataStoreProtocol
	private let networkService: NetworkService
	private let keyChainService: KeyChainServiceProtocol
	private let authenticationInterceptor: AuthenticationInterceptor<AuthAuthenticator>
	private let authAuthenticator: AuthAuthenticator
	private let authService: AuthServiceProtocol & AuthAuthenticatorDelegate
}

// MARK: - HasAuthService

extension AppDependency: HasAuthService {
	var auth: AuthServiceProtocol {
		authService
	}
}
