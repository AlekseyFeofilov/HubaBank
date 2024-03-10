final class ProfileViewModel {
	// MARK: - Init

	// MARK: - Public

	private(set) var fullName: String?
	private(set) var phoneNumber: String?

	var onDidLoadData: (() -> Void)?

	func loadData() {
		fullName = "Бебенюк Бебень Бебеновович"
		phoneNumber = "89521113344"
		onDidLoadData?()
	}

	func tappedLogoutButton() {
		// TODO: do it
	}
}
