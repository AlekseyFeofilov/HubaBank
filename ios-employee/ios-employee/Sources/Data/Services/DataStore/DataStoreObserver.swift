import Foundation

enum DataStoreEvent {
	case example
}

protocol DataStoreSubscriber: AnyObject {
	func update(event: DataStoreEvent)
}

class DataStoreObserver {
	// MARK: - Public

	func subscribe(_ subscriber: DataStoreSubscriber) {
		subscribers.append(subscriber)
	}

	func unsubscribe(_ subscriber: DataStoreSubscriber) {
		subscribers.removeAll { $0 === subscriber }
	}

	func notify(event: DataStoreEvent) {
		subscribers.forEach { $0.update(event: event) }
	}

	// MARK: - Private

	private var subscribers: [DataStoreSubscriber] = []
}
