import UIKit

final class TabBarView: UIView {
	// MARK: - Properties

	var onDidSelectTab: ((_ index: Int) -> Void)?

	private let backgroundNoiseImageView = UIImageView()
	private let itemBackgorundView = TabBarItemBackgroundView()
	private let stackView = UIStackView()

	private var selectedItem = TabBarItem.clients

	// MARK: - Init

	init() {
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		super.init(coder: coder)
		setup()
	}

	// MARK: - Public methods

	func select(item: TabBarItem) {
		self.selectedItem = item
		stackView.arrangedSubviews.forEach { view in
			(view as? TabBarItemView)?.isSelected = (view as? TabBarItemView)?.item == item
		}
		updateItemBackground(animated: true)
		onDidSelectTab?(TabBarItem.allCases.firstIndex(of: item) ?? 0)
	}

	// MARK: - Setup

	private func setup() {
		setupContainer()
		setupItemBackgroundView()
		setupStackView()
		updateItemBackground(animated: false)
	}

	private func setupContainer() {
		layer.cornerRadius = 32
		snp.makeConstraints { make in
			make.height.equalTo(64)
		}

		addSubview(backgroundNoiseImageView)
		backgroundNoiseImageView.layer.cornerRadius = 28
		backgroundNoiseImageView.clipsToBounds = true
		backgroundNoiseImageView.layer.compositingFilter = "multiplyBlendMode"
		backgroundNoiseImageView.snp.makeConstraints { make in
			make.edges.equalToSuperview().inset(2)
		}
	}

	private func setupItemBackgroundView() {
		addSubview(itemBackgorundView)
	}

	private func setupStackView() {
		addSubview(stackView)
		stackView.axis = .horizontal
		stackView.distribution = .fillProportionally

		TabBarItem.allCases.forEach { item in
			let itemView = TabBarItemView(item: item)
			itemView.isSelected = item == selectedItem
			itemView.onDidTap = { [weak self] item in
				self?.select(item: item)
			}
			stackView.addArrangedSubview(itemView)
		}

		stackView.snp.makeConstraints { make in
			make.edges.equalToSuperview().inset(8)
		}
	}

	// MARK: - Private methods

	private func updateItemBackground(animated: Bool) {
		stackView.arrangedSubviews.forEach { view in
			if let itemView = (view as? TabBarItemView), itemView.item == selectedItem {
				self.itemBackgorundView.snp.remakeConstraints { make in
					make.size.equalTo(48)
					make.center.equalTo(itemView)
				}
				UIView.animate(withDuration: animated ? 0.2 : 0, delay: 0, options: .curveEaseInOut) {
					self.layoutIfNeeded()
				}
			}
		}
	}
}
