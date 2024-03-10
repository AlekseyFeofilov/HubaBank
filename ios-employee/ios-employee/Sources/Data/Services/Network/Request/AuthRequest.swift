struct AuthRequest {
	enum CodingKeys: String, CodingKey {
		case userIdentifier = "userUid"
		case currentTime = "currentTimestamp"
	}

	let userIdentifier: String
	let currentTime: String
}
