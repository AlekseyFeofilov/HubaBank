import SnapKit
import UIKit

final class EmployeeTableViewCell: UITableViewCell {
	// MARK: - Init

	override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
		super.init(style: style, reuseIdentifier: reuseIdentifier)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Public

	func configure(title: String) {
		titleLabel.text = title
	}

	// MARK: - Private

	private let cellBackgroundView = UIView()
	private let titleLabel = UILabel()
	private let blockLabel = UILabel()

	private func setup() {
		backgroundColor = .clear
		selectionStyle = .none
		setupCellBackgroundView()
		setupTitleLabel()
		setupBlockLabel()
	}

	private func setupCellBackgroundView() {
		addSubview(cellBackgroundView)
		cellBackgroundView.layer.cornerRadius = 12
		cellBackgroundView.backgroundColor = .green.withAlphaComponent(0.5)
		cellBackgroundView.snp.makeConstraints { make in
			make.verticalEdges.equalToSuperview().inset(8)
			make.horizontalEdges.equalToSuperview()
		}
	}

	private func setupTitleLabel() {
		cellBackgroundView.addSubview(titleLabel)
		titleLabel.textColor = .black
		titleLabel.font = UIFont.systemFont(ofSize: 16)
		titleLabel.numberOfLines = 3
		titleLabel.snp.makeConstraints { make in
			make.leading.equalToSuperview().inset(24)
			make.verticalEdges.equalToSuperview().inset(12)
		}
	}

	private func setupBlockLabel() {
		cellBackgroundView.addSubview(blockLabel)
		blockLabel.textColor = .red
		blockLabel.font = UIFont.systemFont(ofSize: 12, weight: .light)
		blockLabel.text = "Заблокировать"
		blockLabel.textAlignment = .right
		blockLabel.snp.makeConstraints { make in
			make.trailing.equalToSuperview().inset(16)
			make.verticalEdges.equalToSuperview().inset(4)
			make.leading.equalTo(titleLabel.snp.trailing).offset(8)
		}
	}
}
