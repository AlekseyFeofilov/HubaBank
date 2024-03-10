import SnapKit
import UIKit

final class CreateCreditRateViewController: BaseViewController, TabBarHiding {
	// MARK: - Init

	init(viewModel: CreateCreditRateViewModel) {
		self.viewModel = viewModel
		super.init(nibName: nil, bundle: nil)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Lifecycle methods

	override func viewDidLoad() {
		super.viewDidLoad()
		setup()
	}

	// MARK: - Actions

	@objc
	private func tappedAddButton() {
		viewModel.tappedAddButton(title: titleTextField.text ?? "", interestRate: interestRateTextField.text ?? "")
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let textFieldsStackView = UIStackView(axis: .vertical, spacing: 20)
	private let titleTextField = UITextField()
	private let interestRateTextField = UITextField()
	private let addButton = UIButton(type: .system)

	private let viewModel: CreateCreditRateViewModel

	private func setup() {
		setupTitleScreenLabel()
		setupTextFieldsStackView()
		setupTitleTextField()
		setupInterestRateTextField()
		setupAddButton()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Добавление\nнового кредитного тарифа"
		titleScreenLabel.textColor = .black
		titleScreenLabel.font = UIFont.systemFont(ofSize: 24)
		titleScreenLabel.textAlignment = .center
		titleScreenLabel.numberOfLines = .zero
		titleScreenLabel.snp.makeConstraints { make in
			make.top.equalTo(view.safeAreaLayoutGuide).inset(85)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupTextFieldsStackView() {
		view.addSubview(textFieldsStackView)
		textFieldsStackView.snp.makeConstraints { make in
			make.centerY.equalToSuperview()
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupTitleTextField() {
		titleTextField.placeholder = "Введите название"
		titleTextField.textColor = .black
		titleTextField.font = UIFont.systemFont(ofSize: 16)
		titleTextField.backgroundColor = .gray.withAlphaComponent(0.5)
		textFieldsStackView.addArrangedSubview(titleTextField)
	}

	private func setupInterestRateTextField() {
		interestRateTextField.placeholder = "Введите процентную ставку"
		interestRateTextField.textColor = .black
		interestRateTextField.keyboardType = .numberPad
		interestRateTextField.font = UIFont.systemFont(ofSize: 16)
		interestRateTextField.backgroundColor = .gray.withAlphaComponent(0.5)
		textFieldsStackView.addArrangedSubview(interestRateTextField)
	}

	private func setupAddButton() {
		addButton.setTitle("Добавить", for: .normal)
		addButton.setTitleColor(.black, for: .normal)
		addButton.titleLabel?.font = UIFont.systemFont(ofSize: 18)
		addButton.backgroundColor = .gray.withAlphaComponent(0.5)
		addButton.titleLabel?.textAlignment = .center
		addButton.layer.cornerRadius = 10
		addButton.contentEdgeInsets = UIEdgeInsets(top: 8, left: 16, bottom: 8, right: 16)
		view.addSubview(addButton)
		addButton.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(32)
		}
		addButton.addTarget(self, action: #selector(tappedAddButton), for: .touchUpInside)
	}
}
