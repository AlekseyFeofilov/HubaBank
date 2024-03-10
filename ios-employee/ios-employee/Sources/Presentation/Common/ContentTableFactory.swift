final class ContentTableFactory {
	// MARK: - Methods

	func makeContent(contentType: ContentType) -> ContentTableViewProtocol {
		switch contentType {
		case .credits:
			return makeContentCreditsView()
		case .bills:
			return makeContentBillsView()
		}
	}

	// MARK: - Private Methods

	private func makeContentCreditsView() -> ContentTableViewProtocol {
		CreditContentView()
	}

	private func makeContentBillsView() -> ContentTableViewProtocol {
		BillsContentView()
	}
}
