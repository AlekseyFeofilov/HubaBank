import SnapKit
import UIKit

final class TitleWithDescriptionView: UIView {
	// MARK: - Init

	init() {
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Public

	func configure(title: String, description: String) {
		titleLabel.text = title
		descriptionLabel.text = description
	}

	// MARK: - Private

	private let titleLabel = UILabel()
	private let descriptionLabel = UILabel()

	private func setup() {
		setupTitleLabel()
		setupDesctriptionLabel()
	}

	private func setupTitleLabel() {
		addSubview(titleLabel)
		titleLabel.font = UIFont.systemFont(ofSize: 14, weight: .semibold)
		titleLabel.textColor = .black
		titleLabel.numberOfLines = .zero
		titleLabel.snp.makeConstraints { make in
			make.top.equalToSuperview()
			make.horizontalEdges.equalToSuperview()
		}
	}

	private func setupDesctriptionLabel() {
		addSubview(descriptionLabel)
		descriptionLabel.font = UIFont.systemFont(ofSize: 12, weight: .regular)
		descriptionLabel.textColor = .black
		descriptionLabel.numberOfLines = .zero
		descriptionLabel.snp.makeConstraints { make in
			make.leading.equalTo(titleLabel).inset(6)
			make.top.equalTo(titleLabel.snp.bottom).offset(12)
			make.trailing.bottom.equalToSuperview()
		}
	}
}
