struct ClientResponse: Codable {
	let id: String
	let fullNameDto: FullNameDto
	let phone: String
	let employee: Bool
	let additionPrivileges: [String]
	let roles: [String]
	let privileges: [String]
	let blocked: Bool
}

struct FullNameDto: Codable {
	let firstName: String
	let secondName: String
	let thirdName: String
}
