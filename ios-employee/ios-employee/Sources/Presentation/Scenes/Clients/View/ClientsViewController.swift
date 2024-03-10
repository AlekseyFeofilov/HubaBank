import SnapKit
import UIKit

final class ClientsViewController: BaseViewController, NavigationBarHiding {
	// MARK: - Init

	init(viewModel: ClientsViewModel) {
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
	private func tappedAddNewClientButton() {
		viewModel.tappedAddNewClientButton()
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let addNewClientButton = UIButton(type: .system)
	private let clientsTableView = UITableView()

	private let viewModel: ClientsViewModel

	private func bindViewModel() {
		viewModel.onDidUpdateView = { [weak self] in
			self?.clientsTableView.reloadData()
		}
	}

	private func setup() {
		setupTitleScreenLabel()
		setupAddNewClientButton()
		setupClientsTableView()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Клиенты"
		titleScreenLabel.textColor = .black
		titleScreenLabel.textAlignment = .center
		titleScreenLabel.font = UIFont.systemFont(ofSize: 24)
		titleScreenLabel.snp.makeConstraints { make in
			make.top.equalTo(view.safeAreaLayoutGuide).inset(16)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupAddNewClientButton() {
		view.addSubview(addNewClientButton)
		addNewClientButton.setTitle("Создать нового клиента", for: .normal)
		addNewClientButton.setTitleColor(.black, for: .normal)
		addNewClientButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
		addNewClientButton.backgroundColor = .gray.withAlphaComponent(0.6)
		addNewClientButton.layer.cornerRadius = 6
		addNewClientButton.contentEdgeInsets = UIEdgeInsets(top: 6, left: 12, bottom: 6, right: 12)
		addNewClientButton.snp.makeConstraints { make in
			make.top.equalTo(titleScreenLabel.snp.bottom).offset(25)
			make.trailing.equalToSuperview().inset(24)
		}
		addNewClientButton.addTarget(self, action: #selector(tappedAddNewClientButton), for: .touchUpInside)
	}

	private func setupClientsTableView() {
		view.addSubview(clientsTableView)
		clientsTableView.showsVerticalScrollIndicator = false
		clientsTableView.showsHorizontalScrollIndicator = false
		clientsTableView.backgroundColor = .none
		clientsTableView.separatorStyle = .none
		clientsTableView.alwaysBounceVertical = false
		clientsTableView.delegate = self
		clientsTableView.dataSource = self
		clientsTableView.register(ClientsTableViewCell.self, forCellReuseIdentifier: ClientsTableViewCell.reuseIdentifier)
		clientsTableView.snp.makeConstraints { make in
			make.top.equalTo(addNewClientButton.snp.bottom).offset(12)
			make.horizontalEdges.equalToSuperview().inset(24)
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(32)
		}
	}
}

// MARK: -

extension ClientsViewController: UITableViewDataSource {
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		viewModel.clients.count
	}

	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		guard let cell = tableView
			.dequeueReusableCell(withIdentifier: ClientsTableViewCell.reuseIdentifier, for: indexPath) as? ClientsTableViewCell else {
			return UITableViewCell()
		}

		cell.configure(title: viewModel.clients[indexPath.row].name)

		return cell
	}
}

extension ClientsViewController: UITableViewDelegate {
	func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		let client = viewModel.clients[indexPath.row]
		viewModel.tappedClient(id: client.id, name: client.name)
	}
}
