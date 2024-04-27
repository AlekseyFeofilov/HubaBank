import SnapKit
import UIKit

final class AuthViewController: BaseViewController, NavigationBarHiding {
	// MARK: - Init

	init(viewModel: AuthViewModel) {
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
	private func tappedNotHaveAccountLabel() {
		viewModel.tappedNotHaveAccountButton()
	}

	@objc
	private func tappedAuthBitton() {
		viewModel.tappedAuthButton()
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let textFieldsStackView = UIStackView(axis: .vertical, spacing: 20)
	private let phoneNumberTextField = UITextField()
	private let passwordTextField = UITextField()
	private let buttonsStackView = UIStackView(axis: .vertical, spacing: 10)
	private let notHaveAccountLabel = UILabel()
	private let authButton = UIButton(type: .system)

	private let viewModel: AuthViewModel

	private func setup() {
		setupTitleScreenLabel()
		setupTextFieldsStackView()
		setupPhoneNumberTextField()
		setupPasswordTextField()
		setupButtonsStackView()
		setupNotHaveAccountLabel()
		setupAuthButton()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Вход"
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
		view.addSubview(buttonsStackView)
		buttonsStackView.snp.makeConstraints { make in
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(25)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupNotHaveAccountLabel() {
		notHaveAccountLabel.text = "Еще нет аккаунта"
		notHaveAccountLabel.textColor = .white
		notHaveAccountLabel.textAlignment = .center
		notHaveAccountLabel.font = UIFont.systemFont(ofSize: 16)
		notHaveAccountLabel.isUserInteractionEnabled = true
		buttonsStackView.addArrangedSubview(notHaveAccountLabel)
		notHaveAccountLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tappedNotHaveAccountLabel)))
	}

	private func setupAuthButton() {
		authButton.setTitle("Вход", for: .normal)
		authButton.setTitleColor(.black, for: .normal)
		authButton.titleLabel?.textAlignment = .center
		authButton.titleLabel?.font = UIFont.systemFont(ofSize: 18)
		authButton.backgroundColor = .AppColors.baseWhite40
		authButton.contentEdgeInsets = UIEdgeInsets(top: 8, left: 16, bottom: 8, right: 16)
		buttonsStackView.addArrangedSubview(authButton)
		authButton.addTarget(self, action: #selector(tappedAuthBitton), for: .touchUpInside)
	}
}
