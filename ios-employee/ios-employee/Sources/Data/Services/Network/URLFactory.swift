import Foundation

enum URLFactory {
	private static let baseURL = "https://api.ispace.one/api/mobile"

	enum Auth {
		static let login = baseURL + "/auth/login"
		static let refresh = baseURL + "/auth/refresh"
	}

	enum AIChat {
		static let digitalMentor = baseURL + "/aichat/digital-mentor"
		static let digitalMentorQuestions = digitalMentor + "/questions"
		static let digitalConfession = baseURL + "/aichat/digital-confession"
		static let digitalConfessionQuestions = digitalConfession + "/questions"
	}

	enum Profile {
		static let getProfile = baseURL + "/users/profile"
		static let updateProfile = baseURL + "/users"
		static let activities = baseURL + "/users/activity"
		static let deleteProfile = baseURL + "/users"
	}

	enum MeaningLibrary {
		static let categories = "\(baseURL)/categories"
		static let recommendedContent = "\(baseURL)/contents/recommended"
		static func categoriesContents(categoriesID: String) -> String {
			"\(baseURL)/categories/\(categoriesID)/contents"
		}
	}

	enum Tasks {
		static let getTasks = baseURL + "/tasks"
	}

	enum Gratitudes {
		static let saveGratitudes = baseURL + "/gratitudes"
		static let loadGratitudes = baseURL + "/gratitudes"
		static let loadGratitudesArchive = baseURL + "/gratitudes/archive"
	}
}
