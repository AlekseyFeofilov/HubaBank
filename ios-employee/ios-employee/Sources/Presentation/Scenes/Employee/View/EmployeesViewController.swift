import SnapKit
import UIKit

final class EmployeesViewController: BaseViewController, NavigationBarHiding {
	// MARK: - Init

	init(viewModel: EmployeesViewModel) {
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
	private let addNewEmployeeButton = UIButton(type: .system)
	private let employeesTableView = UITableView()

	private let viewModel: EmployeesViewModel

	private func bindViewModel() {
		viewModel.onDidUpdateView = { [weak self] in
			self?.employeesTableView.reloadData()
		}
	}

	private func setup() {
		setupTitleScreenLabel()
		setupAddNewEmployeeButton()
		setupEmployeesTableView()
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

	private func setupAddNewEmployeeButton() {
		view.addSubview(addNewEmployeeButton)
		addNewEmployeeButton.setTitle("Создать нового сотрудника", for: .normal)
		addNewEmployeeButton.setTitleColor(.black, for: .normal)
		addNewEmployeeButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
		addNewEmployeeButton.backgroundColor = .gray.withAlphaComponent(0.6)
		addNewEmployeeButton.layer.cornerRadius = 6
		addNewEmployeeButton.contentEdgeInsets = UIEdgeInsets(top: 6, left: 12, bottom: 6, right: 12)
		addNewEmployeeButton.snp.makeConstraints { make in
			make.top.equalTo(titleScreenLabel.snp.bottom).offset(25)
			make.trailing.equalToSuperview().inset(24)
		}
		addNewEmployeeButton.addTarget(self, action: #selector(tappedAddNewEmployeeButton), for: .touchUpInside)
	}

	private func setupEmployeesTableView() {
		view.addSubview(employeesTableView)
		employeesTableView.showsVerticalScrollIndicator = false
		employeesTableView.showsHorizontalScrollIndicator = false
		employeesTableView.backgroundColor = .none
		employeesTableView.separatorStyle = .none
		employeesTableView.alwaysBounceVertical = false
		employeesTableView.dataSource = self
		employeesTableView.register(EmployeeTableViewCell.self, forCellReuseIdentifier: EmployeeTableViewCell.reuseIdentifier)
		employeesTableView.snp.makeConstraints { make in
			make.top.equalTo(addNewEmployeeButton.snp.bottom).offset(12)
			make.horizontalEdges.equalToSuperview().inset(24)
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(32)
		}
	}
}

// MARK: - UITableViewDataSource

extension EmployeesViewController: UITableViewDataSource {
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		viewModel.clients.count
	}

	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		guard let cell = tableView
			.dequeueReusableCell(withIdentifier: EmployeeTableViewCell.reuseIdentifier, for: indexPath) as? EmployeeTableViewCell else {
			return UITableViewCell()
		}

		cell.configure(title: viewModel.clients[indexPath.row].name)

		return cell
	}
}
