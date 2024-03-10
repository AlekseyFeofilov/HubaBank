import SnapKit
import UIKit

final class ClientViewController: BaseViewController, TabBarHiding {
	// MARK: - Init

	init(viewModel: ClientViewModel) {
		self.viewModel = viewModel
		super.init(nibName: nil, bundle: nil)
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Life cycles

	override func viewDidLoad() {
		super.viewDidLoad()
		bindViewModel()
		setup()
	}

	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)
		viewModel.tappedBillsButton()
		viewModel.loadData()
	}

	// MARK: - Actions

	@objc
	private func tappedBillsLabel() {
		billsLabel.font = UIFont.systemFont(ofSize: 18, weight: .semibold)
		creditsLabel.font = UIFont.systemFont(ofSize: 18, weight: .regular)
		viewModel.tappedBillsButton()
	}

	@objc
	private func tappedCreditLabel() {
		billsLabel.font = UIFont.systemFont(ofSize: 18, weight: .regular)
		creditsLabel.font = UIFont.systemFont(ofSize: 18, weight: .semibold)
		viewModel.tappedCreditButton()
	}

	@objc
	private func tappedBlockLabel() {
		viewModel.tappedBlockButton()
	}

	// MARK: - Private

	private let clientNameLabel = UILabel()
	private let titlesStackView = UIStackView(axis: .horizontal)
	private let billsLabel = UILabel()
	private let creditsLabel = UILabel()
	private let contentFactory = ContentTableFactory()
	private let blockLabel = UILabel()
	var contentView: ContentTableViewProtocol? {
		didSet {
			bindContentView()
			setupContentView()
		}
	}

	private let viewModel: ClientViewModel

	private func bindViewModel() {
		viewModel.updateContentView = { [weak self] contentType in
			guard let self = self else { return }
			viewModel.loadData()
			clearContent()
			contentView = contentFactory.makeContent(contentType: contentType)
		}

		viewModel.onDidLoadData = { [weak self] in
			self?.contentView?.reloadData()
		}
	}

	private func bindContentView() {
		if let billsContentView = contentView as? BillsContentView {
			billsContentView.getBills = { [weak self] in
				self?.viewModel.bills.map {
					ContentTable(id: $0.id, title: $0.id, money: $0.balance)
				}
			}

			billsContentView.onBillTapped = { [weak self] billId in
				self?.viewModel.tappedBill(billId: billId)
			}
		}

		if let creditContentView = contentView as? CreditContentView {
			creditContentView.getCredits = { [weak self] in
				self?.viewModel.bills.map {
					ContentTable(id: $0.id, title: $0.id, money: $0.balance)
				}
			}

			creditContentView.onCreditTapped = { [weak self] creditId in
				self?.viewModel.tappedCredit(creditId: creditId)
			}
		}
	}

	private func setup() {
		setupClientNameLabel()
		setupTitlesStackView()
		setupBillsLabel()
		setupCreditsLabel()
		setupBlockLabel()
	}

	private func setupClientNameLabel() {
		view.addSubview(clientNameLabel)
		clientNameLabel.textColor = .black
		clientNameLabel.text = viewModel.clientName
		clientNameLabel.font = UIFont.systemFont(ofSize: 24, weight: .bold)
		clientNameLabel.textAlignment = .center
		clientNameLabel.numberOfLines = .zero
		clientNameLabel.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.top.equalTo(view.safeAreaLayoutGuide)
		}
	}

	private func setupTitlesStackView() {
		view.addSubview(titlesStackView)
		titlesStackView.distribution = .fillEqually
		titlesStackView.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.top.equalTo(clientNameLabel.snp.bottom).offset(24)
		}
	}

	private func setupBillsLabel() {
		titlesStackView.addArrangedSubview(billsLabel)
		billsLabel.text = "Счета"
		billsLabel.textColor = .black
		billsLabel.textAlignment = .center
		billsLabel.isUserInteractionEnabled = true
		billsLabel.font = UIFont.systemFont(ofSize: 18, weight: .semibold)
		billsLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tappedBillsLabel)))
	}

	private func setupCreditsLabel() {
		titlesStackView.addArrangedSubview(creditsLabel)
		creditsLabel.text = "Кредиты"
		creditsLabel.textColor = .black
		creditsLabel.textAlignment = .center
		creditsLabel.isUserInteractionEnabled = true
		creditsLabel.font = UIFont.systemFont(ofSize: 18)
		creditsLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tappedCreditLabel)))
	}

	private func setupContentView() {
		guard let contentView = contentView as? UIView else { return }
		view.addSubview(contentView)

		contentView.snp.makeConstraints {
			$0.top.equalTo(titlesStackView.snp.bottom).offset(24)
			$0.horizontalEdges.equalToSuperview().inset(24)
			$0.bottom.equalTo(blockLabel.snp.top).offset(24)
		}
	}

	private func setupBlockLabel() {
		view.addSubview(blockLabel)
		blockLabel.text = "Заблокировать пользователя"
		blockLabel.textColor = .red
		blockLabel.font = UIFont.systemFont(ofSize: 18, weight: .light)
		blockLabel.textAlignment = .center
		blockLabel.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(24)
		}
	}

	private func clearContent() {
		guard let contentView = contentView as? UIView else { return }

		contentView.removeFromSuperview()
	}
}
