import SnapKit
import UIKit

final class ProfileViewController: BaseViewController, NavigationBarHiding {
	// MARK: - Init

	init(viewModel: ProfileViewModel) {
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

	// MARK: - Actions

	@objc
	private func tappedLogoutLabel() {
		viewModel.tappedLogoutButton()
	}

	// MARK: - Private

	private let titleScreenLabel = UILabel()
	private let nameStackView = UIStackView(axis: .horizontal, spacing: 7)
	private let nameTitleLabel = UILabel()
	private let nameLabel = UILabel()
	private let phoneNumberStackView = UIStackView(axis: .horizontal, spacing: 7)
	private let phoneNumberTitleLabel = UILabel()
	private let phoneNumberLabel = UILabel()
	private let logoutLabel = UILabel()

	private let viewModel: ProfileViewModel

	private func bindViewModel() {
		viewModel.onDidLoadData = { [weak self] in
			guard let self = self else { return }
			self.nameLabel.text = self.viewModel.fullName
			self.phoneNumberLabel.text = self.viewModel.phoneNumber
		}
	}

	private func setup() {
		setupTitleScreenLabel()
		setupNameStackView()
		setupNameTitleLabel()
		setupNameLabel()
		setupPhoneNumberStackView()
		setupPhoneNumberTitleLabel()
		setupPhoneNumberLabel()
	}

	private func setupTitleScreenLabel() {
		view.addSubview(titleScreenLabel)
		titleScreenLabel.text = "Профиль"
		titleScreenLabel.textAlignment = .center
		titleScreenLabel.textColor = .black
		titleScreenLabel.font = UIFont.systemFont(ofSize: 24, weight: .medium)
		titleScreenLabel.snp.makeConstraints { make in
			make.top.equalTo(view.safeAreaLayoutGuide).inset(16)
			make.horizontalEdges.equalToSuperview().inset(24)
		}
	}

	private func setupNameStackView() {
		view.addSubview(nameStackView)
		nameStackView.distribution = .fillEqually
		nameStackView.snp.makeConstraints { make in
			make.top.equalTo(titleScreenLabel.snp.bottom).offset(23)
			make.horizontalEdges.equalToSuperview().inset(16)
		}
	}

	private func setupNameTitleLabel() {
		nameStackView.addArrangedSubview(nameTitleLabel)
		nameTitleLabel.text = "ФИО"
		nameTitleLabel.textColor = .black
		nameTitleLabel.font = UIFont.systemFont(ofSize: 16, weight: .medium)
	}

	private func setupNameLabel() {
		nameStackView.addArrangedSubview(nameLabel)
		nameLabel.textColor = .black
		nameLabel.font = UIFont.systemFont(ofSize: 16, weight: .regular)
		nameLabel.numberOfLines = .zero
	}

	private func setupPhoneNumberStackView() {
		view.addSubview(phoneNumberStackView)
		phoneNumberStackView.distribution = .fillEqually
		phoneNumberStackView.snp.makeConstraints { make in
			make.top.equalTo(nameStackView.snp.bottom).offset(24)
			make.horizontalEdges.equalToSuperview().inset(16)
		}
	}

	private func setupPhoneNumberTitleLabel() {
		phoneNumberStackView.addArrangedSubview(phoneNumberTitleLabel)
		phoneNumberTitleLabel.text = "телефон"
		phoneNumberTitleLabel.textColor = .black
		phoneNumberTitleLabel.font = UIFont.systemFont(ofSize: 16, weight: .medium)
	}

	private func setupPhoneNumberLabel() {
		phoneNumberStackView.addArrangedSubview(phoneNumberLabel)
		phoneNumberLabel.textColor = .black
		phoneNumberLabel.font = UIFont.systemFont(ofSize: 16, weight: .regular)
		phoneNumberLabel.numberOfLines = .zero
	}

	private func setupLogoutLabel() {
		view.addSubview(logoutLabel)
		logoutLabel.text = "Выйти"
		logoutLabel.textColor = .red
		logoutLabel.font = UIFont.systemFont(ofSize: 12, weight: .medium)
		logoutLabel.isUserInteractionEnabled = true
		logoutLabel.snp.makeConstraints { make in
			make.horizontalEdges.equalToSuperview().inset(24)
			make.bottom.equalTo(view.safeAreaLayoutGuide).inset(24)
		}
		logoutLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(tappedLogoutLabel)))
	}
}
