import Alamofire
import Foundation

extension NetworkService: ProfileNetworkProtocol {
	private enum Keys {
		static let fromDateActivity = "from"
		static let toDateActivity = "to"
		static let name = "name"
		static let phoneNumber = "phoneNumber"
		static let birthDate = "birthDate"
		static let sex = "sex"
		static let language = "language"
	}

	func getProfile() async throws -> ProfileResponse {
		try await request(method: .get, url: URLFactory.Profile.getProfile, authorized: true)
	}

	func updateProfile(profileRequest: ProfileRequest) async throws -> EmptyResponse {
		let parameters: Parameters = [Keys.name: profileRequest.name,
		                              Keys.phoneNumber: profileRequest.phoneNumber,
		                              Keys.birthDate: profileRequest.birthDate,
		                              Keys.sex: profileRequest.sex,
		                              Keys.language: profileRequest.language]
		return try await request(method: .put, url: URLFactory.Profile.updateProfile, authorized: true, parameters: parameters)
	}
}
