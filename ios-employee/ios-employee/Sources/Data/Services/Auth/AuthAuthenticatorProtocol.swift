protocol AuthAuthenticatorDelegate: AnyObject {
	func authAuthenticatorDidRequestRefresh(_ authAuthenticator: AuthAuthenticator,
	                                        with credential: AuthTokenPair,
	                                        completion: @escaping (Swift.Result<AuthTokenPair, Error>) -> Void)
}

protocol AuthAuthenticatorProtocol: AnyObject {
	var delegate: AuthAuthenticatorDelegate? { get set }
}
