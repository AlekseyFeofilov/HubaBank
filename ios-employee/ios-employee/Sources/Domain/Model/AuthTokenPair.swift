import Alamofire
import Foundation

struct AuthTokenPair: Codable, AuthenticationCredential {
	enum CodingKeys: String, CodingKey {
		case accessToken
		case refreshToken
	}

	let accessToken: String
	let refreshToken: String

	var requiresRefresh: Bool {
		let date = DateFormatter.ISODate.date(from: "2024-03-09T19:18:12.020Z") ?? Date()
		return Date().timeIntervalSince1970 > date.timeIntervalSince1970
	}
}
