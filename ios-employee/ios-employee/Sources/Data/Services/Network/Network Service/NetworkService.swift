import Alamofire
import Foundation

class NetworkService {
	let authenticationInterceptor: AuthenticationInterceptor<AuthAuthenticator>

	init(authenticationInterceptor: AuthenticationInterceptor<AuthAuthenticator>) {
		self.authenticationInterceptor = authenticationInterceptor
	}

	// MARK: - Make request

	func request<T: Decodable>(method: HTTPMethod,
	                           url: URLConvertible,
	                           authorized: Bool = false,
	                           queryParameters: [String: String]? = nil,
	                           headers: HTTPHeaders? = nil,
	                           parameters: Parameters? = nil) async throws -> T {
		guard let newURL = addQueryParams(to: url, queryParameters) else { throw NetworkServiceError.requestFailed }

		let requestResponse = await AF.request(newURL,
		                                       method: method,
		                                       parameters: parameters,
		                                       encoding: method == .get ?
		                                       	URLEncoding(arrayEncoding: .noBrackets) : JSONEncoding.default,
		                                       interceptor: authorized ? authenticationInterceptor : nil)
			.responseDataAsync()

		return try await handleResponse(requestResponse)
	}

	// MARK: - Handle response

	private func handleResponse<T: Decodable>(_ response: AFDataResponse<Data>) async throws -> T {
		Task.detached(priority: .utility) { [weak self] in
			await self?.logResponse(response)
		}

		if case let .failure(error) = response.result {
			throw error.underlyingError ?? error
		}

		let statusCode: HTTPStatusCode
		if let code = response.response?.statusCode {
			statusCode = HTTPStatusCode(rawValue: code) ?? .internalServerError
		} else {
			statusCode = .internalServerError
		}

		switch statusCode {
		case .okStatus, .created, .accepted, .noContent:
			if let data = response.data {
				return try await decode(data, ofType: T.self)
			} else if T.self == EmptyResponse.self, let response = EmptyResponse() as? T {
				return response
			} else {
				throw NetworkServiceError.noData
			}
		default:
			throw NetworkServiceError.requestFailed
		}
	}

	// MARK: - Decoding data

	private func decode<T: Decodable>(_ data: Data, ofType type: T.Type) async throws -> T {
		do {
			let object = try JSONDecoder().decode(type.self, from: data)
			return object
		} catch {
			print(error)
			throw NetworkServiceError.failedToDecodeData
		}
	}

	// MARK: - Logging to console

	private func logResponse(_ response: AFDataResponse<Data>) async {
		print("----------------------------------------")
		print("\(response.request?.method?.rawValue ?? "") \(response.request?.url?.absoluteString ?? "")")
		print("----------------------------------------")
		switch response.result {
		case let .success(responseData):
			if let object = try? JSONSerialization.jsonObject(with: responseData, options: []),
			   let data = try? JSONSerialization.data(withJSONObject: object, options: [.prettyPrinted]),
			   let prettyPrintedString = NSString(data: data, encoding: String.Encoding.utf8.rawValue) {
				print(prettyPrintedString)
			}
		case let .failure(error):
			print("Error \(response.response?.statusCode ?? 0)")
			print(String(data: response.data ?? Data(), encoding: .utf8) ?? "")
			print(error)
		}
		print("----------------------------------------")
	}

	private func addQueryParams(to URL: URLConvertible, _ params: [String: String]?) -> URLConvertible? {
		guard let urlString = try? URL.asURL().absoluteString, var urlComps = URLComponents(string: urlString) else { return nil }

		let newParams: [String: String] = params ?? [:]

		var queryItems = newParams.map {
			URLQueryItem(name: $0.key, value: $0.value)
		}
		urlComps.queryItems = queryItems

		return urlComps.url
	}
}
