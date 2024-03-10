import Alamofire
import Foundation

final class AuthAuthenticator: Authenticator, AuthAuthenticatorProtocol {
	weak var delegate: AuthAuthenticatorDelegate?

	func apply(_ credential: AuthTokenPair, to urlRequest: inout URLRequest) {
		urlRequest.headers.add(.authorization(bearerToken: credential.accessToken))
	}

	func refresh(_ credential: AuthTokenPair,
	             for session: Session,
	             completion: @escaping (Swift.Result<AuthTokenPair, Error>) -> Void) {
		delegate?.authAuthenticatorDidRequestRefresh(self, with: credential, completion: completion)
	}

	func didRequest(_ urlRequest: URLRequest,
	                with response: HTTPURLResponse,
	                failDueToAuthenticationError error: Error) -> Bool {
		response.statusCode == HTTPStatusCode.unauthorized.rawValue
	}

	func isRequest(_ urlRequest: URLRequest, authenticatedWith credential: AuthTokenPair) -> Bool {
		let credentialToken = HTTPHeader.authorization(bearerToken: credential.accessToken).value
		let requestToken = urlRequest.value(forHTTPHeaderField: "Authorization")
		return requestToken == credentialToken
	}
}
