import Alamofire
import Foundation

struct AuthTokenPair: Codable, AuthenticationCredential {
	let accessToken: String
	let refreshToken: String

	var requiresRefresh: Bool {
		let date = DateFormatter.ISODate.date(from: "\(Date())") ?? Date()
		return Date().timeIntervalSince1970 > date.timeIntervalSince1970
	}
}
