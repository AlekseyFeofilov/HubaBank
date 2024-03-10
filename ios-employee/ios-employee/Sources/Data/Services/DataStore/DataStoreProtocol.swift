import Foundation

protocol DataStoreProtocol: AnyObject {
	var observer: DataStoreObserver { get }

	var lastGratitudes: [String]? { get set }
	var lastGratitudesDate: Date? { get set }

	var hasShownOnboarding: Bool { get set }

	var isSecondOpenApp: Bool { get set }

	func clearAllData()
}
