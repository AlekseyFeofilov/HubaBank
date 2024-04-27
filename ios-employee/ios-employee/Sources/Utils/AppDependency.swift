import Alamofire
import Foundation

protocol HasClientsService {
	var clients: ClientsNetworkProtocol { get }
}

final class AppDependency {
	init() {
		authAuthenticator = AuthAuthenticator()
		keyChainService = KeyChainService()
		dataStoreService = DataStoreService()
		authenticationInterceptor = AuthenticationInterceptor(authenticator: authAuthenticator)
		networkService = NetworkService(authenticationInterceptor: authenticationInterceptor)
		storageService = StorageService()
		authService = AuthService(authNetworkService: networkService, keyChainService: keyChainService)
	}

	// MARK: - Private

	let authService: AuthServiceProtocol & AuthAuthenticatorDelegate
	let authAuthenticator: AuthAuthenticator
	private let networkService: NetworkService
	let keyChainService: KeyChainServiceProtocol
	private let dataStoreService: DataStoreProtocol
	private let authenticationInterceptor: AuthenticationInterceptor<AuthAuthenticator>
	private let storageService: StorageService
}

extension AppDependency: HasClientsService {
	var clients: ClientsNetworkProtocol {
		networkService
	}
}
