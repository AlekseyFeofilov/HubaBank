import Alamofire
import Foundation

extension NetworkService: ClientsNetworkProtocol {
	func getClients(authToken: String) async throws -> [ClientResponse] {
		try await request(method: .get, url: URLFactory.Clients.getClients, headers: [.authorization(bearerToken: authToken)])
	}
}
