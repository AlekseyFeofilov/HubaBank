protocol AuthServiceProtocol: AnyObject {
	var isAuthorized: Bool { get }

	func login(userIdentifier: String) async throws
	func logout()
}
