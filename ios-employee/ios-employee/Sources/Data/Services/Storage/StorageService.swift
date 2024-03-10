import Foundation

enum StorageServiceError: LocalizedError {
	case couldNotSaveFile, noDataSaved
}

class StorageService {
	// MARK: - Public

	func saveObject<T: Codable>(_ object: T, key: String) throws {
		let fileName = makeFileName(type: T.self, key: key)
		let fileURL = try makeDirectoryURL().appendingPathComponent(fileName, isDirectory: false)
		let data = try JSONEncoder().encode(object)

		if fileManager.fileExists(atPath: fileURL.path) {
			try fileManager.removeItem(at: fileURL)
		}

		let isSaved = fileManager.createFile(atPath: fileURL.path, contents: data)
		guard isSaved else {
			throw StorageServiceError.couldNotSaveFile
		}
	}

	func getObject<T: Codable>(ofType type: T.Type, key: String) throws -> T {
		let fileName = makeFileName(type: type, key: key)
		let fileURL = try makeDirectoryURL().appendingPathComponent(fileName, isDirectory: false)

		guard let data = fileManager.contents(atPath: fileURL.path) else {
			throw StorageServiceError.noDataSaved
		}

		return try JSONDecoder().decode(type, from: data)
	}

	// MARK: - Private

	private let fileManager = FileManager.default

	private func makeDirectoryURL() throws -> URL {
		let directoryURL = try fileManager.url(for: .documentDirectory,
		                                       in: .userDomainMask,
		                                       appropriateFor: nil,
		                                       create: true).appendingPathComponent(Constants.folderName, isDirectory: true)
		try fileManager.createDirectory(at: directoryURL, withIntermediateDirectories: true, attributes: nil)
		return directoryURL
	}

	private func makeFileName<T: Codable>(type: T.Type, key: String) -> String {
		"\(String(describing: T.self)).\(key)"
	}
}

// MARK: - Constants

private extension Constants {
	static let folderName = "ISpaceData"
}
