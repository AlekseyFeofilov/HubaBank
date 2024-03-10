import SnapKit
import UIKit

final class ContentTableViewCell: UITableViewCell {
	// MARK: - Init

	override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
		super.init(style: style, reuseIdentifier: reuseIdentifier)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Public

	func configure(title: String, money: Int, isCredit: Bool) {
		moneyLabel.text = String(describing: money)
		moneyLabel.text = (moneyLabel.text ?? "") + " Р"
		titleLabel.text = title
		if isCredit {
			moneyLabel.text = "на сумму\n" + (moneyLabel.text ?? "")
		}
	}

	// MARK: - Private

	private let cellBackgroundView = UIView()
	private let moneyLabel = UILabel()
	private let titleLabel = UILabel()
	private let moreLabel = UILabel()

	private func setup() {
		backgroundColor = .clear
		selectionStyle = .none
		setupCellBackgroundView()
		setupMoneyLabel()
		setupTitleLabel()
		setupMoreLabel()
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

	private func setupMoneyLabel() {
		cellBackgroundView.addSubview(moneyLabel)
		moneyLabel.textColor = .black
		moneyLabel.font = UIFont.systemFont(ofSize: 16)
		moneyLabel.numberOfLines = 3
		moneyLabel.snp.makeConstraints { make in
			make.leading.equalToSuperview().inset(24)
			make.verticalEdges.equalToSuperview().inset(12)
		}
	}

	private func setupTitleLabel() {
		cellBackgroundView.addSubview(titleLabel)
		titleLabel.textColor = .black
		titleLabel.font = UIFont.systemFont(ofSize: 16, weight: .medium)
		titleLabel.textAlignment = .right
		titleLabel.snp.makeConstraints { make in
			make.trailing.equalToSuperview().inset(16)
			make.top.equalToSuperview().inset(4)
			make.leading.equalTo(moneyLabel.snp.trailing).offset(8)
		}
	}

	private func setupMoreLabel() {
		cellBackgroundView.addSubview(moreLabel)
		moreLabel.textColor = .black
		moreLabel.font = UIFont.systemFont(ofSize: 12, weight: .light)
		moreLabel.text = "Подробнее"
		moreLabel.textAlignment = .right
		moreLabel.snp.makeConstraints { make in
			make.trailing.equalToSuperview().inset(16)
			make.top.greaterThanOrEqualTo(titleLabel.snp.bottom).offset(24)
			make.bottom.equalToSuperview().inset(4)
			make.leading.equalTo(moneyLabel.snp.trailing).offset(8)
		}
	}
}
