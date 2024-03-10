import SnapKit
import UIKit

final class BillsContentView: UIView {
	// MARK: - Init

	init() {
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Public

	var getBills: (() -> [ContentTable]?)?
	var onBillTapped: ((String) -> Void)?

	// MARK: - Private

	private lazy var billsTableView = UITableView(frame: .zero)

	private func setup() {
		setupContentTableView()
	}

	private func setupContentTableView() {
		billsTableView.backgroundColor = .none
		billsTableView.showsVerticalScrollIndicator = false
		billsTableView.showsHorizontalScrollIndicator = false
		billsTableView.separatorStyle = .none
		billsTableView.alwaysBounceVertical = false

		billsTableView.delegate = self
		billsTableView.dataSource = self
		billsTableView.register(ContentTableViewCell.self, forCellReuseIdentifier: ContentTableViewCell.reuseIdentifier)

		addSubview(billsTableView)

		billsTableView.snp.makeConstraints {
			$0.edges.equalToSuperview()
		}
	}
}

// MARK: - ContentTable

extension BillsContentView: ContentTableViewProtocol {
	func reloadData() {
		billsTableView.reloadData()
	}
}

// MARK: - UICollectionViewDataSource

extension BillsContentView: UITableViewDataSource {
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		guard let billsCount = getBills?()?.count else { return 0 }
		return billsCount
	}

	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		guard let cell = tableView
			.dequeueReusableCell(withIdentifier: ContentTableViewCell.reuseIdentifier, for: indexPath) as? ContentTableViewCell
		else {
			return UITableViewCell()
		}

		if let bill = getBills?()?[indexPath.row] {
			cell.configure(title: bill.title, money: bill.money, isCredit: false)
		}

		return cell
	}
}

// MARK: - UICollectionViewDelegate

extension BillsContentView: UITableViewDelegate {
	func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		if let bills = getBills?() {
			onBillTapped?(bills[indexPath.row].id)
		}
	}

	func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
		UITableView.automaticDimension
	}
}
