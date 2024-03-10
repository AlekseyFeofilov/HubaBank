import Alamofire
import Foundation

extension DataRequest {
	func responseDataAsync() async -> AFDataResponse<Data> {
		await withCheckedContinuation { continuation in
			responseData { response in
				continuation.resume(returning: response)
			}
		}
	}
}
