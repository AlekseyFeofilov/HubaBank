class Observable<T> {
	typealias Listener = (T) -> Void
	var listener: Listener?

	func bind(_ listener: Listener?) {
		self.listener = listener
	}

	var value: T {
		didSet {
			listener?(value)
		}
	}

	init(_ value: T) {
		self.value = value
	}
}
