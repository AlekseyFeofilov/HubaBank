import UIKit

final class TabBarItemBackgroundView: UIView {
	// MARK: - Properties

	private let backgroundNoiseImageView = UIImageView()

	// MARK: - Init

	init() {
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		super.init(coder: coder)
		setup()
	}

	// MARK: - Setup

	private func setup() {
		layer.cornerRadius = 24
		clipsToBounds = true
		snp.makeConstraints { make in
			make.size.equalTo(48)
		}

		addSubview(backgroundNoiseImageView)
		backgroundNoiseImageView.layer.cornerRadius = 20
		backgroundNoiseImageView.clipsToBounds = true
		backgroundNoiseImageView.layer.compositingFilter = "multiplyBlendMode"
		backgroundNoiseImageView.snp.makeConstraints { make in
			make.edges.equalToSuperview().inset(2)
		}
	}
}
