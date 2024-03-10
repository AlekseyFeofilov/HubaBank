import UIKit

final class TabBarItemView: UIView {
	// MARK: - Properties

	var onDidTap: ((_ item: TabBarItem) -> Void)?

	var isSelected: Bool = false {
		didSet {
			UIView.transition(with: iconImageView,
			                  duration: 0.3,
			                  options: .transitionCrossDissolve,
			                  animations: {
			                  	self.iconImageView.image = self.isSelected ? self.item?.iconSelected : self.item?.icon
			                  },
			                  completion: nil)
		}
	}

	let item: TabBarItem?

	private let iconImageView = UIImageView()

	// MARK: - Init

	init(item: TabBarItem) {
		self.item = item
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		item = nil
		super.init(coder: coder)
		setup()
	}

	// MARK: - Overrides

	override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
		super.touchesBegan(touches, with: event)
		iconImageView.alpha = 0.6
	}

	override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
		super.touchesEnded(touches, with: event)
		iconImageView.alpha = 1
	}

	override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
		super.touchesCancelled(touches, with: event)
		iconImageView.alpha = 1
	}

	// MARK: - Actions

	@objc private func handleTap() {
		guard let item else { return }
		onDidTap?(item)
	}

	// MARK: - Setup

	private func setup() {
		setupContainer()
		setupIconImageView()
	}

	private func setupContainer() {
		snp.makeConstraints { make in
			make.size.equalTo(48)
		}
		let recognizer = UITapGestureRecognizer(target: self, action: #selector(handleTap))
		recognizer.cancelsTouchesInView = false
		addGestureRecognizer(recognizer)
	}

	private func setupIconImageView() {
		addSubview(iconImageView)
		iconImageView.image = item?.icon
		iconImageView.contentMode = .scaleAspectFit
		iconImageView.snp.makeConstraints { make in
			make.size.equalTo(24)
			make.center.equalToSuperview()
		}
	}
}
