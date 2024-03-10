import SnapKit
import UIKit

final class CreateClientViewController: BaseViewController, TabBarHiding {
	// MARK: - Init

	init(viewModel: CreateClientViewModel) {
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
		viewModel.tappedAddButton(fullName: nameTextField.text ?? "", phone: phoneNumberTextField.text ?? "",
		                          password: passwordTextField.text ?? "")
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let textFieldsStackView = UIStackView(axis: .vertical, spacing: 20)
	private let nameTextField = UITextField()
	private let phoneNumberTextField = UITextField()
	private let passwordTextField = UITextField()
	private let addButton = UIButton(type: .system)

	private let viewModel: CreateClientViewModel

	private func setup() {
		setupTitleScreenLabel()
		setupTextFieldsStackView()
		setupNameTextField()
		setupPhoneNumberTextField()
		setupPasswordTextField()
		setupAddButton()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Добавление\nнового клиента"
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

	private func setupNameTextField() {
		nameTextField.placeholder = "Введите ФИО"
		nameTextField.textColor = .black
		nameTextField.font = UIFont.systemFont(ofSize: 16)
		nameTextField.backgroundColor = .gray.withAlphaComponent(0.5)
		textFieldsStackView.addArrangedSubview(nameTextField)
	}

	private func setupPhoneNumberTextField() {
		phoneNumberTextField.placeholder = "Введите номер телефона"
		phoneNumberTextField.textColor = .black
		phoneNumberTextField.keyboardType = .numberPad
		phoneNumberTextField.font = UIFont.systemFont(ofSize: 16)
		phoneNumberTextField.backgroundColor = .gray.withAlphaComponent(0.5)
		textFieldsStackView.addArrangedSubview(phoneNumberTextField)
	}

	private func setupPasswordTextField() {
		passwordTextField.placeholder = "Введите пароль"
		passwordTextField.textColor = .black
		passwordTextField.font = UIFont.systemFont(ofSize: 16)
		passwordTextField.backgroundColor = .gray.withAlphaComponent(0.5)
		textFieldsStackView.addArrangedSubview(passwordTextField)
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
