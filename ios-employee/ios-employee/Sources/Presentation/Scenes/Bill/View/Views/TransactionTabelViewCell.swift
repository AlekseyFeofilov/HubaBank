import SnapKit
import UIKit

final class TransactionTabelViewCell: UITableViewCell {
	// MARK: - Init

	override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
		super.init(style: style, reuseIdentifier: reuseIdentifier)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Public

	func configure(transactionType: String, date: String, amount: String) {
		transactionTypeLabel.text = transactionType
		dateLabel.text = date
		amountLabel.text = amount
	}

	// MARK: - Private

	private let cellBackgroundView = UIView()
	private let transactionTypeLabel = UILabel()
	private let dateLabel = UILabel()
	private let amountLabel = UILabel()

	private func setup() {
		backgroundColor = .clear
		selectionStyle = .none
		setupCellBackgroundView()
		setupBillTypeLabel()
		setupBillDateLabel()
		setupAmountLabel()
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

	private func setupBillTypeLabel() {
		cellBackgroundView.addSubview(transactionTypeLabel)
		transactionTypeLabel.textColor = .black
		transactionTypeLabel.font = UIFont.systemFont(ofSize: 16)
		transactionTypeLabel.numberOfLines = 3
		transactionTypeLabel.snp.makeConstraints { make in
			make.leading.equalToSuperview().inset(24)
			make.verticalEdges.equalToSuperview().inset(12)
		}
	}

	private func setupBillDateLabel() {
		cellBackgroundView.addSubview(dateLabel)
		dateLabel.textColor = .black
		dateLabel.font = UIFont.systemFont(ofSize: 10, weight: .light)
		dateLabel.textAlignment = .right
		dateLabel.snp.makeConstraints { make in
			make.trailing.equalToSuperview().inset(12)
			make.top.equalToSuperview().inset(4)
			make.leading.equalTo(transactionTypeLabel.snp.trailing).offset(8)
		}
	}

	private func setupAmountLabel() {
		cellBackgroundView.addSubview(amountLabel)
		amountLabel.textColor = .black
		amountLabel.font = UIFont.systemFont(ofSize: 15, weight: .regular)
		amountLabel.textAlignment = .right
		amountLabel.snp.makeConstraints { make in
			make.trailing.equalToSuperview().inset(12)
			make.top.equalTo(dateLabel.snp.bottom).offset(10)
			make.leading.equalTo(transactionTypeLabel.snp.trailing).offset(8)
		}
	}
}
