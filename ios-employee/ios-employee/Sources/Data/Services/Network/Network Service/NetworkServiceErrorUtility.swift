import Foundation

enum NetworkServiceErrorUtility {
	static func isNoInternetError(_ error: Error) -> Bool {
		Constants.noInternetErrorCodes.contains((error as NSError).code)
	}
}

// MARK: - Constant

private extension Constants {
	static let noInternetErrorCodes = [NSURLErrorCallIsActive,
	                                   NSURLErrorCannotConnectToHost,
	                                   NSURLErrorCannotFindHost,
	                                   NSURLErrorCannotLoadFromNetwork,
	                                   NSURLErrorDataNotAllowed,
	                                   NSURLErrorInternationalRoamingOff,
	                                   NSURLErrorNetworkConnectionLost,
	                                   NSURLErrorNotConnectedToInternet,
	                                   NSURLErrorSecureConnectionFailed,
	                                   NSURLErrorTimedOut,
	                                   alamofireNoInternetCode]
	static let alamofireNoInternetCode = 13
}
