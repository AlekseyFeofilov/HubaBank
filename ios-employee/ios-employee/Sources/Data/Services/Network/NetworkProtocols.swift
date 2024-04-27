import Foundation

protocol AuthNetworkProtocol {
	func login(body: AuthRequest) async throws -> AuthTokenPair
	func refresh(refreshToken: String) async throws -> AuthTokenPair
	func updateSessionCredentials(with tokens: AuthTokenPair?)
}

protocol ClientsNetworkProtocol {
	func getClients(authToken: String) async throws -> [ClientResponse]
}
