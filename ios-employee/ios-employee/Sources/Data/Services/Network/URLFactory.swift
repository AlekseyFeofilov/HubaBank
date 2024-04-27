import Foundation

enum URLFactory {
	private static let baseURL = "http://194.147.90.192:9002/api/gateway"

	enum Auth {
		static let login = baseURL
		static let register = baseURL
		static let refresh = baseURL
	}

	enum Clients {
		static let getClients = baseURL + "/profile/clients"
	}
}
