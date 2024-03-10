struct ProfileResponse: Codable {
	let userId: String
	let name: String?
	let level: Int
	let progress: Int
	let money: Int
}
