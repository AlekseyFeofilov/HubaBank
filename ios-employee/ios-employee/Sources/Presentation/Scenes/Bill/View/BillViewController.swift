import SnapKit
import UIKit

final class BillViewController: BaseViewController, TabBarHiding {
	// MARK: - Init

	init(viewModel: BillViewModel) {
		self.viewModel = viewModel
		super.init(nibName: nil, bundle: nil)
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Life cycles

	override func viewDidLoad() {
		super.viewDidLoad()
		setup()
		bindViewModel()
	}

	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)
		viewModel.loadData()
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let titleBillLabel = UILabel()
	private let moneyLabel = UILabel()
	private let historyLabel = UILabel()
	private let historyTableView = UITableView()

	private let viewModel: BillViewModel

	private func bindViewModel() {
		viewModel.onDidLoadData = { [weak self] in
			guard let self = self else { return }
			guard let bill = self.viewModel.bill else { return }
			titleBillLabel.text = bill.id
			moneyLabel.text = String(describing: bill.balance) + " Р"
			historyTableView.reloadData()
		}
	}

	private func setup() {
		setupTitleScreenLabel()
		setupTitleBillLabel()
		setupMoneyLabel()
		setupHistoryLabel()
		setupHistoryTableView()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Счет\n" + viewModel.clientName
		titleScreenLabel.textColor = .black
		titleScreenLabel.font = UIFont.systemFont(ofSize: 24, weight: .medium)
		titleScreenLabel.textAlignment = .center
		titleScreenLabel.numberOfLines = .zero
		titleScreenLabel.snp.makeConstraints { make in
			make.top.equalTo(view.safeAreaLayoutGuide)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupTitleBillLabel() {
		view.addSubview(titleBillLabel)
		titleBillLabel.textColor = .black
		titleBillLabel.font = UIFont.systemFont(ofSize: 24, weight: .medium)
		titleBillLabel.numberOfLines = .zero
		titleBillLabel.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.top.equalTo(titleScreenLabel.snp.bottom).offset(14)
		}
	}

	private func setupMoneyLabel() {
		view.addSubview(moneyLabel)
		moneyLabel.textColor = .black
		moneyLabel.font = UIFont.systemFont(ofSize: 16, weight: .regular)
		moneyLabel.numberOfLines = .zero
		moneyLabel.snp.makeConstraints { make in
			make.top.equalTo(titleBillLabel.snp.bottom).offset(4)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupHistoryLabel() {
		view.addSubview(historyLabel)
		historyLabel.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.top.equalTo(moneyLabel.snp.bottom).offset(32)
		}
	}

	private func setupHistoryTableView() {
		view.addSubview(historyTableView)
		historyTableView.showsVerticalScrollIndicator = false
		historyTableView.showsHorizontalScrollIndicator = false
		historyTableView.backgroundColor = .none
		historyTableView.separatorStyle = .none
		historyTableView.alwaysBounceVertical = false
		historyTableView.dataSource = self
		historyTableView.register(TransactionTabelViewCell.self, forCellReuseIdentifier: TransactionTabelViewCell.reuseIdentifier)
		historyTableView.snp.makeConstraints { make in
			make.top.equalTo(historyLabel.snp.bottom).offset(12)
			make.horizontalEdges.equalToSuperview().inset(24)
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(32)
		}
	}
}

// MARK: - UITableViewDataSource

extension BillViewController: UITableViewDataSource {
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		viewModel.transactions.count
	}

	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		guard let cell = tableView
			.dequeueReusableCell(withIdentifier: TransactionTabelViewCell.reuseIdentifier, for: indexPath) as? TransactionTabelViewCell
		else {
			return UITableViewCell()
		}

		let transaction = viewModel.transactions[indexPath.row]
		cell.configure(transactionType: "\(transaction.reason)",
		               date: "\(String(describing: DateFormatter.dayOfMonth.date(from: transaction.date)))",
		               amount: String(describing: transaction.bakanceChange))

		return cell
	}
}
