import Foundation

protocol Configurable {
	associatedtype ViewModel

	func configure(with viewModel: ViewModel)
}
