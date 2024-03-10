import Foundation

class DataStoreService: DataStoreProtocol {
	// MARK: - Public

	let observer = DataStoreObserver()

	var lastGratitudes: [String]? {
		get {
			userDefaults.stringArray(forKey: UserDefaultsKeys.lastGratitudes)
		}
		set {
			userDefaults.set(newValue, forKey: UserDefaultsKeys.lastGratitudes)
		}
	}

	var lastGratitudesDate: Date? {
		get {
			userDefaults.object(forKey: UserDefaultsKeys.lastGratitudesDate) as? Date
		}
		set {
			userDefaults.set(newValue, forKey: UserDefaultsKeys.lastGratitudesDate)
		}
	}

	var hasShownOnboarding: Bool {
		get {
			userDefaults.bool(forKey: UserDefaultsKeys.hasShownOnboarding)
		}
		set {
			userDefaults.set(newValue, forKey: UserDefaultsKeys.hasShownOnboarding)
		}
	}

	var isSecondOpenApp: Bool {
		get {
			userDefaults.bool(forKey: UserDefaultsKeys.isFirstOpenApp)
		}
		set {
			userDefaults.set(newValue, forKey: UserDefaultsKeys.isFirstOpenApp)
		}
	}

	func clearAllData() {
		lastGratitudes = nil
		lastGratitudesDate = nil
	}

	// MARK: - Private

	private enum UserDefaultsKeys {
		static let lastGratitudes = "lastGratitudes"
		static let lastGratitudesDate = "lastGratitudesDate"
		static let hasShownOnboarding = "hasShownOnboarding"
		static let isFirstOpenApp = "isFirstOpenApp"
	}

	private let userDefaults = UserDefaults.standard
}
