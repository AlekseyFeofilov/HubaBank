import SnapKit
import UIKit

final class CreditsRatesViewController: BaseViewController, NavigationBarHiding {
	// MARK: - Init

	init(viewModel: CreditRatesViewModel) {
		self.viewModel = viewModel
		super.init(nibName: nil, bundle: nil)
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MAKR: - Life cycles

	override func viewDidLoad() {
		super.viewDidLoad()
		viewModel.loadData()
		bindViewModel()
		setup()
	}

	// MARK: - Actions

	@objc
	private func tappedAddNewEmployeeButton() {
		viewModel.tappedAddNewEmployeeButton()
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let addNewCreditButton = UIButton(type: .system)
	private let creditsTableView = UITableView()

	private let viewModel: CreditRatesViewModel

	private func bindViewModel() {
		viewModel.onDidUpdateView = { [weak self] in
			self?.creditsTableView.reloadData()
		}
	}

	private func setup() {
		setupTitleScreenLabel()
		setupAddNewCreditButton()
		setupCreditsTableView()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Сотрудники"
		titleScreenLabel.textColor = .black
		titleScreenLabel.textAlignment = .center
		titleScreenLabel.font = UIFont.systemFont(ofSize: 24)
		titleScreenLabel.snp.makeConstraints { make in
			make.top.equalTo(view.safeAreaLayoutGuide).inset(16)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupAddNewCreditButton() {
		view.addSubview(addNewCreditButton)
		addNewCreditButton.setTitle("Создать нового сотрудника", for: .normal)
		addNewCreditButton.setTitleColor(.black, for: .normal)
		addNewCreditButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
		addNewCreditButton.backgroundColor = .gray.withAlphaComponent(0.6)
		addNewCreditButton.layer.cornerRadius = 6
		addNewCreditButton.contentEdgeInsets = UIEdgeInsets(top: 6, left: 12, bottom: 6, right: 12)
		addNewCreditButton.snp.makeConstraints { make in
			make.top.equalTo(titleScreenLabel.snp.bottom).offset(25)
			make.trailing.equalToSuperview().inset(24)
		}
		addNewCreditButton.addTarget(self, action: #selector(tappedAddNewEmployeeButton), for: .touchUpInside)
	}

	private func setupCreditsTableView() {
		view.addSubview(creditsTableView)
		creditsTableView.showsVerticalScrollIndicator = false
		creditsTableView.showsHorizontalScrollIndicator = false
		creditsTableView.backgroundColor = .none
		creditsTableView.separatorStyle = .none
		creditsTableView.alwaysBounceVertical = false
		creditsTableView.dataSource = self
		creditsTableView.register(CreditRatesTableViewCell.self, forCellReuseIdentifier: CreditRatesTableViewCell.reuseIdentifier)
		creditsTableView.snp.makeConstraints { make in
			make.top.equalTo(addNewCreditButton.snp.bottom).offset(12)
			make.horizontalEdges.equalToSuperview().inset(24)
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(32)
		}
	}
}

// MARK: - UITableViewDataSource

extension CreditsRatesViewController: UITableViewDataSource {
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		viewModel.creditRates.count
	}

	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		guard let cell = tableView
			.dequeueReusableCell(withIdentifier: CreditRatesTableViewCell.reuseIdentifier, for: indexPath) as? CreditRatesTableViewCell
		else {
			return UITableViewCell()
		}

		cell.configure(title: viewModel.creditRates[indexPath.row].title,
		               interestRate: viewModel.creditRates[indexPath.row].interestRate)

		return cell
	}
}
