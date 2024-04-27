import Foundation

protocol ClientsViewModelDelegate: AnyObject {
	func showClientScreen(clientId: String, clientName: String)
	func showCreateClientScreen()
}

final class ClientsViewModel {
	typealias Dependencies = HasClientsService

	// MARK: - Init

	// MARK: - Public

	init(dependencies: Dependencies) {
		self.dependencies = dependencies
	}

	weak var delegate: ClientsViewModelDelegate?

	private(set) var clients: [ShortClient] = []

	var onDidUpdateView: (() -> Void)?

	func loadData() {
		Task {
			do {
				let clients = try await dependencies.clients.getClients(authToken: // swiftlint:disable:next line_length
					"eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTQwNDcyMjUsImlkIjoiYmM1Y2UxNTctNDNmOS00MTE2LWEzM2YtYTZmN2MyZjRiYTFiIiwiZmlyc3ROYW1lIjoiZWxlbmEiLCJzZWNvbmROYW1lIjoiZ2VyYXNpbWNodWsiLCJ0aGlyZE5hbWUiOiJldmdlbmV2bmEiLCJwcml2aWxlZ2VzIjpbIlRSQU5TQUNUSU9OX1JFQUQiLCJUUkFOU0FDVElPTl9XUklURSIsIkJJTExfV1JJVEUiLCJCSUxMX1JFQUQiXX0.rn57xvObaUaA_GbdF_WO-SX4LPRk71xRPQfjDi706tTWyQmA369xCzevKqw5MX84wYvvOtm4GWYYlt9zEXId_Q")
				self.clients = clients.map {
					ShortClient(id: $0.id, name: $0.fullNameDto.secondName + " " + $0.fullNameDto.firstName + " "  + $0.fullNameDto.thirdName)
				}
				DispatchQueue.main.async {
					self.onDidUpdateView?()
				}
			} catch {
				print(error)
			}
		}
	}

	func tappedClient(id: String, name: String) {
		delegate?.showClientScreen(clientId: id, clientName: name)
	}

	func tappedAddNewClientButton() {
		delegate?.showCreateClientScreen()
	}

	private let dependencies: Dependencies
}
