import Foundation

enum HTTPStatusCode: Int {
	case okStatus = 200
	case created = 201
	case accepted = 202
	case noContent = 204

	case badRequest = 400
	case unauthorized = 401
	case forbidden = 403
	case notFound = 404
	case methodNotAllowed = 405
	case conflict = 409

	case internalServerError = 500
	case badGateway = 502
	case serviceUnavailable = 503
	case gatewayTimeout = 504
}
