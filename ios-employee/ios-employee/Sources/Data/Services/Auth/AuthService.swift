// import Foundation
//
// final class AuthService: AuthServiceProtocol {
//	// MARK: - Init
//
//	init(authNetworkService: AuthNetworkProtocol, keyChainService: KeyChainServiceProtocol) {
//		self.authNetworkService = authNetworkService
//		self.keyChainService = keyChainService
//
//		if isAuthorized {
//			let tokens: AuthTokenPair? = keyChainService.read(service: .token, account: .iSpace)
//			authNetworkService.updateSessionCredentials(with: tokens)
//		}
//	}
//
//	// MARK: - Public
//
//	var isAuthorized: Bool {
//		keyChainService.hasData(service: .token, account: .iSpace)
//	}
//
//	func login(userIdentifier: String) async throws {
//		do {
//			let authTokens = try await authNetworkService.login(body: AuthRequest(userIdentifier: userIdentifier,
//			                                                                      currentTime: DateFormatter.fullDateISO
//			                                                                      	.string(from: Date())))
//			updateTokens(authTokens)
//		} catch {
//			throw error
//		}
//	}
//
//	func logout() {
//		keyChainService.delete(service: .token, account: .iSpace)
//	}
//
//	// MARK: - Private
//
//	private let authNetworkService: AuthNetworkProtocol
//	private let keyChainService: KeyChainServiceProtocol
//
//	private func updateTokens(_ tokens: AuthTokenPair) {
//		keyChainService.save(tokens, service: .token, account: .iSpace)
//		authNetworkService.updateSessionCredentials(with: tokens)
//	}
// }
//
//// MARK: - Keys
//
// private extension AuthService {
//	enum Keys: String {
//		case token
//	}
// }
//
// extension AuthService: AuthAuthenticatorDelegate {
//	func authAuthenticatorDidRequestRefresh(_ authAuthenticator: AuthAuthenticator, with credential: AuthTokenPair,
//	                                        completion: @escaping (Result<AuthTokenPair, Error>) -> Void) {
//		Task {
//			do {
//				let authTokens = try await authNetworkService.refresh(refreshToken: credential.refreshToken)
//				updateTokens(authTokens)
//				completion(.success(authTokens))
//			} catch {
//				completion(.failure(error))
//			}
//		}
//	}
// }
