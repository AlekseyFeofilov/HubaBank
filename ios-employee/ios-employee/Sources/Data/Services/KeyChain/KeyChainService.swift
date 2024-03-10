import Foundation

// MARK: - KeyChainAccountAttributeKey

enum KeyChainAccountAttributeKey: String {
	case iSpace = "i-space"
}

// MARK: - KeyChainServiceAttributeKey

enum KeyChainServiceAttributeKey: String {
	case token
}

protocol KeyChainServiceProtocol: AnyObject {
	func save<T>(_ item: T, service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey) where T: Codable
	func read<T>(service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey) -> T? where T: Codable
	func delete(service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey)
	func hasData(service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey) -> Bool
}

class KeyChainService: KeyChainServiceProtocol {
	// MARK: - Public

	func save<T>(_ item: T, service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey) where T: Codable {
		guard let data = try? JSONEncoder().encode(item) else { return }
		save(data, service: service.rawValue, account: account.rawValue)
	}

	func read<T>(service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey) -> T? where T: Codable {
		guard let data = read(service: service.rawValue, account: account.rawValue) else { return nil }
		return try? JSONDecoder().decode(T.self, from: data)
	}

	func delete(service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey) {
		let query = [kSecClass: kSecClassGenericPassword,
		             kSecAttrService: service.rawValue,
		             kSecAttrAccount: account.rawValue] as CFDictionary

		SecItemDelete(query)
	}

	func hasData(service: KeyChainServiceAttributeKey, account: KeyChainAccountAttributeKey) -> Bool {
		read(service: service.rawValue, account: account.rawValue) != nil
	}

	// MARK: - Private

	private func save(_ data: Data, service: String, account: String) {
		let query = [kSecValueData: data,
		             kSecClass: kSecClassGenericPassword,
		             kSecAttrService: service,
		             kSecAttrAccount: account] as CFDictionary

		let status = SecItemAdd(query, nil)

		if status == errSecDuplicateItem {
			update(data, service: service, account: account)
		}
	}

	private func read(service: String, account: String) -> Data? {
		let query = [kSecAttrService: service,
		             kSecAttrAccount: account,
		             kSecClass: kSecClassGenericPassword,
		             kSecReturnData: true] as CFDictionary

		var result: AnyObject?
		SecItemCopyMatching(query, &result)

		return (result as? Data)
	}

	private func update(_ data: Data, service: String, account: String) {
		let query = [kSecAttrService: service,
		             kSecAttrAccount: account,
		             kSecClass: kSecClassGenericPassword] as CFDictionary

		let updateQuery = [kSecValueData: data] as CFDictionary

		SecItemUpdate(query, updateQuery)
	}
}
