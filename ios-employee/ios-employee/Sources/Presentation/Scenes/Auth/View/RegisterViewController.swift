import SnapKit
import UIKit

final class RegisterViewController: BaseViewController, NavigationBarHiding {
	// MARK: - Init

	init(viewModel: RegisterViewModel) {
		self.viewModel = viewModel
		super.init(nibName: nil, bundle: nil)
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
	private func tappedAlreadyHaveAccountLabel() {
		viewModel.tappedAlreadyHaveAccountButton()
	}

	@objc
	private func tappedRegisterBitton() {
		// TODO: do it
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let textFieldsStackView = UIStackView(axis: .vertical, spacing: 20)
	private let nameTextField = UITextField()
	private let phoneNumberTextField = UITextField()
	private let passwordTextField = UITextField()
	private let buttonsStackView = UIStackView(axis: .vertical, spacing: 10)
	private let alreadyHaveAccountLabel = UILabel()
	private let registerButton = UIButton(type: .system)

	private let viewModel: RegisterViewModel

	private func setup() {
		setupTitleScreenLabel()
		setupTextFieldsStackView()
		setupNameTextField()
		setupPhoneNumberTextField()
		setupPasswordTextField()
		setupButtonsStackView()
		setupAlreadyHaveAccountLabel()
		setupRegisterButton()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Регистрация"
		titleScreenLabel.textColor = .white
		titleScreenLabel.font = UIFont.systemFont(ofSize: 32)
		titleScreenLabel.textAlignment = .center
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
		nameTextField.backgroundColor = .white.withAlphaComponent(02)
		textFieldsStackView.addArrangedSubview(nameTextField)
	}

	private func setupPhoneNumberTextField() {
		phoneNumberTextField.placeholder = "Введите номер телефона"
		phoneNumberTextField.textColor = .black
		phoneNumberTextField.font = UIFont.systemFont(ofSize: 16)
		phoneNumberTextField.backgroundColor = .white.withAlphaComponent(02)
		textFieldsStackView.addArrangedSubview(phoneNumberTextField)
	}

	private func setupPasswordTextField() {
		passwordTextField.placeholder = "Введите пароль"
		passwordTextField.textColor = .black
		passwordTextField.font = UIFont.systemFont(ofSize: 16)
		passwordTextField.backgroundColor = .white.withAlphaComponent(02)
		textFieldsStackView.addArrangedSubview(passwordTextField)
	}

	private func setupButtonsStackView() {
		view.addSubview(textFieldsStackView)
		textFieldsStackView.snp.makeConstraints { make in
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(25)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupAlreadyHaveAccountLabel() {
		alreadyHaveAccountLabel.text = "Уже есть аккаунт"
		alreadyHaveAccountLabel.textColor = .black
		alreadyHaveAccountLabel.font = UIFont.systemFont(ofSize: 16)
		alreadyHaveAccountLabel.textAlignment = .center
		alreadyHaveAccountLabel.isUserInteractionEnabled = true
		textFieldsStackView.addArrangedSubview(alreadyHaveAccountLabel)
		alreadyHaveAccountLabel.addGestureRecognizer(UITapGestureRecognizer(target: self,
		                                                                    action: #selector(tappedAlreadyHaveAccountLabel)))
	}

	private func setupRegisterButton() {
		registerButton.setTitle("Зарегистрироваться", for: .normal)
		registerButton.setTitleColor(.black, for: .normal)
		registerButton.titleLabel?.font = UIFont.systemFont(ofSize: 18)
		registerButton.backgroundColor = .gray
		registerButton.titleLabel?.textAlignment = .center
		registerButton.contentEdgeInsets = UIEdgeInsets(top: 8, left: 16, bottom: 8, right: 16)
		textFieldsStackView.addArrangedSubview(registerButton)
		registerButton.addTarget(self, action: #selector(tappedRegisterBitton), for: .touchUpInside)
	}
}
