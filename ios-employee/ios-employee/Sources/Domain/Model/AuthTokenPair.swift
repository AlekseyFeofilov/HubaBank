import Alamofire
import Foundation

struct AuthTokenPair: Codable, AuthenticationCredential {
	enum CodingKeys: String, CodingKey {
		case accessToken
		case accessTokenExpirationTime = "accessTokenExpiresAt"
		case refreshToken
		case refreshTokenExpirationTime = "refreshTokenExpiresAt"
		case isProfileFilled
	}

	let accessToken: String
	let accessTokenExpirationTime: String
	let refreshToken: String
	let refreshTokenExpirationTime: String
	let isProfileFilled: Bool

	var requiresRefresh: Bool {
		let date = DateFormatter.ISODate.date(from: accessTokenExpirationTime) ?? Date()
		return Date().timeIntervalSince1970 > date.timeIntervalSince1970
	}
}
