import Foundation

protocol AuthNetworkProtocol {
	func login(body: AuthRequest) async throws -> AuthTokenPair
	func refresh(refreshToken: String) async throws -> AuthTokenPair
	func updateSessionCredentials(with tokens: AuthTokenPair?)
}

protocol ProfileNetworkProtocol {
	func getProfile() async throws -> ProfileResponse
	@discardableResult
	func updateProfile(profileRequest: ProfileRequest) async throws -> EmptyResponse
}
