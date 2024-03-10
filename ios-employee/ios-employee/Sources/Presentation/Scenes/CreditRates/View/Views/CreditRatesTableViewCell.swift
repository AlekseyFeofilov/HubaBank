import SnapKit
import UIKit

final class CreditRatesTableViewCell: UITableViewCell {
	// MARK: - Init

	override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
		super.init(style: style, reuseIdentifier: reuseIdentifier)
		setup()
	}

	required init?(coder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}

	// MARK: - Public

	func configure(title: String, interestRate: Int) {
		titleLabel.text = title
		interestRateLabel.text = String(describing: interestRate) + "%"
	}

	// MARK: - Private

	private let cellBackgroundView = UIView()
	private let titleLabel = UILabel()
	private let interestRateTitleLabel = UILabel()
	private let interestRateLabel = UILabel()

	private func setup() {
		backgroundColor = .clear
		selectionStyle = .none
		setupCellBackgroundView()
		setupTitleLabel()
		setupInterestRateTitleLabel()
		setupInterestRateLabel()
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
		titleLabel.numberOfLines = 1
		titleLabel.snp.makeConstraints { make in
			make.leading.equalToSuperview().inset(16)
			make.top.equalToSuperview().inset(6)
		}
	}

	private func setupInterestRateTitleLabel() {
		cellBackgroundView.addSubview(interestRateTitleLabel)
		interestRateTitleLabel.textColor = .black
		interestRateTitleLabel.font = UIFont.systemFont(ofSize: 12, weight: .medium)
		interestRateTitleLabel.text = "Процентная ставка:"
		interestRateTitleLabel.textAlignment = .right
		interestRateTitleLabel.snp.makeConstraints { make in
			make.top.equalTo(titleLabel.snp.bottom).offset(30)
			make.horizontalEdges.equalToSuperview().inset(16)
		}
	}

	private func setupInterestRateLabel() {
		cellBackgroundView.addSubview(interestRateLabel)
		interestRateLabel.textColor = .red
		interestRateLabel.font = UIFont.systemFont(ofSize: 12, weight: .light)
		interestRateLabel.textAlignment = .right
		interestRateLabel.snp.makeConstraints { make in
			make.trailing.equalToSuperview().inset(16)
			make.top.equalTo(interestRateTitleLabel.snp.bottom)
			make.horizontalEdges.equalToSuperview().inset(16)
			make.bottom.equalToSuperview().inset(14)
		}
	}
}
