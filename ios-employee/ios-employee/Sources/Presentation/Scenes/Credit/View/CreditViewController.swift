import SnapKit
import UIKit

final class CreditViewController: BaseViewController, TabBarHiding {
	// MARK: - Init

	init(viewModel: CreditViewModel) {
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
	private let titleCreditLabel = UILabel()
	private let informationStackView = UIStackView(axis: .vertical, spacing: 16)
	private let creditAmount = TitleWithDescriptionView()
	private let creditPaidOut = TitleWithDescriptionView()
	private let balanceForPayment = TitleWithDescriptionView()
	private let interestRate = TitleWithDescriptionView()

	private let viewModel: CreditViewModel

	private func bindViewModel() {
		viewModel.onDidLoadData = { [weak self] in
			guard let self = self else { return }
			guard let credit = self.viewModel.credit else { return }
			titleCreditLabel.text = credit.title
			self.creditAmount.configure(title: "Сумма кредита:", description: String(describing: credit.amount) + " Р")
			self.creditPaidOut.configure(title: "Выплачено:", description: String(describing: credit.paidOut) + " Р")
			self.balanceForPayment.configure(title: "Остаток на выплату:",
			                                 description: String(describing: credit.amount - credit.paidOut) + " Р")
			self.interestRate.configure(title: "Процентная ставка:", description: String(describing: credit.interestRate) + "%")
		}
	}

	private func setup() {
		setupTitleScreenLabel()
		setupTitleCreditLabel()
		setupInformationStackView()
		setupCreditAmount()
		setupCreditPaidOut()
		setupBalanceForPayment()
		setupInterestRate()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Кредит\n" + viewModel.clientName
		titleScreenLabel.textColor = .black
		titleScreenLabel.font = UIFont.systemFont(ofSize: 24, weight: .medium)
		titleScreenLabel.textAlignment = .center
		titleScreenLabel.numberOfLines = .zero
		titleScreenLabel.snp.makeConstraints { make in
			make.top.equalTo(view.safeAreaLayoutGuide)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupTitleCreditLabel() {
		view.addSubview(titleCreditLabel)
		titleCreditLabel.textColor = .black
		titleCreditLabel.font = UIFont.systemFont(ofSize: 24, weight: .medium)
		titleCreditLabel.numberOfLines = .zero
		titleCreditLabel.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.top.equalTo(titleScreenLabel.snp.bottom).offset(14)
		}
	}

	private func setupInformationStackView() {
		view.addSubview(informationStackView)
		informationStackView.snp.makeConstraints { make in
			make.top.equalTo(titleCreditLabel.snp.bottom).offset(16)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupCreditAmount() {
		informationStackView.addArrangedSubview(creditAmount)
	}

	private func setupCreditPaidOut() {
		informationStackView.addArrangedSubview(creditPaidOut)
	}

	private func setupBalanceForPayment() {
		informationStackView.addArrangedSubview(balanceForPayment)
	}

	private func setupInterestRate() {
		informationStackView.addArrangedSubview(interestRate)
	}
}
