import SnapKit
import UIKit

final class CreditContentView: UIView {
	// MARK: - Init

	init() {
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Public

	var getCredits: (() -> [ContentTable]?)?
	var onCreditTapped: ((String) -> Void)?

	// MARK: - Private

	private lazy var creditsTableView = UITableView(frame: .zero)

	private func setup() {
		setupContentTableView()
	}

	private func setupContentTableView() {
		creditsTableView.backgroundColor = .none
		creditsTableView.showsVerticalScrollIndicator = false
		creditsTableView.showsHorizontalScrollIndicator = false
		creditsTableView.separatorStyle = .none
		creditsTableView.alwaysBounceVertical = false

		creditsTableView.delegate = self
		creditsTableView.dataSource = self
		creditsTableView.register(ContentTableViewCell.self, forCellReuseIdentifier: ContentTableViewCell.reuseIdentifier)

		addSubview(creditsTableView)

		creditsTableView.snp.makeConstraints {
			$0.top.horizontalEdges.equalToSuperview()
			$0.bottom.equalToSuperview().inset(32)
		}
	}
}

// MARK: - ContentTable

extension CreditContentView: ContentTableViewProtocol {
	func reloadData() {
		creditsTableView.reloadData()
	}
}

// MARK: - UICollectionViewDataSource

extension CreditContentView: UITableViewDataSource {
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		guard let creditsCount = getCredits?()?.count else { return 0 }
		return creditsCount
	}

	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		guard let cell = tableView
			.dequeueReusableCell(withIdentifier: ContentTableViewCell.reuseIdentifier, for: indexPath) as? ContentTableViewCell
		else {
			return UITableViewCell()
		}

		if let credit = getCredits?()?[indexPath.row] {
			cell.configure(title: credit.title, money: credit.money, isCredit: true)
		}

		return cell
	}
}

// MARK: - UICollectionViewDelegate

extension CreditContentView: UITableViewDelegate {
	func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		if let credits = getCredits?() {
			onCreditTapped?(credits[indexPath.row].id)
		}
	}

	func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
		UITableView.automaticDimension
	}
}
