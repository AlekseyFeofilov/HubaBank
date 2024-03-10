import UIKit

class ActivityIndicatorView: UIView {
	var hideWhenStopped: Bool = true

	override var intrinsicContentSize: CGSize {
		CGSize(width: 40, height: 40)
	}

	private let circleImageView = UIImageView()

	init() {
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		super.init(coder: coder)
		setup()
	}

	func startAnimating() {
		isHidden = false
	}

	func stopAnimating() {
		if hideWhenStopped {
			isHidden = true
		}
	}

	private func setup() {
		isHidden = true

		addSubview(circleImageView)
		circleImageView.image = .AppImages.loaderIcon
		circleImageView.contentMode = .scaleAspectFit
		circleImageView.snp.makeConstraints { make in
			make.edges.equalToSuperview()
		}
	}

	private func setupRotateSpace(imageView: UIImageView, aCircleTime: Double) {
		let rotationAnimation = CABasicAnimation(keyPath: "transform.rotation")
		rotationAnimation.fromValue = 0.0
		rotationAnimation.toValue = -Double.pi * 2
		rotationAnimation.duration = aCircleTime
		rotationAnimation.repeatCount = .infinity
		imageView.layer.add(rotationAnimation, forKey: nil)
	}

	private func rotateSpaceImage(imageView: UIImageView, aCircleTime: Double) {
		UIView.animate(withDuration: aCircleTime / 2, delay: 0.5, options: .curveLinear, animations: {
			imageView.transform = CGAffineTransform(rotationAngle: CGFloat(Double.pi))
		}, completion: { _ in
			UIView.animate(withDuration: aCircleTime / 2, delay: 0.5, options: .curveLinear, animations: {
				imageView.transform = CGAffineTransform(rotationAngle: CGFloat(Double.pi * 2))
			}, completion: { _ in
				self.setupRotateSpace(imageView: imageView, aCircleTime: aCircleTime)
			})
		})
	}
}

// MARK: - Constants

private extension Constants {
	static let rotateDurationTime: Double = 5
}

// MARK: - Container

final class ActivityIndicatorContainer: UIView {
	// MARK: - Init

	init() {
		super.init(frame: .zero)
		setup()
	}

	required init?(coder: NSCoder) {
		super.init(coder: coder)
		setup()
	}

	// MARK: - Private

	private func setup() {
		backgroundColor = .AppColors.background
	}
}
