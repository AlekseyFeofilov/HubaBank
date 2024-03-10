struct ContentTable {
	let id: String
	let title: String
	let money: Int

	init(id: String, title: String, money: Int) {
		self.id = id
		self.title = title
		self.money = money
	}

	init() {
		self.init(id: String(), title: String(), money: 0)
	}
}
