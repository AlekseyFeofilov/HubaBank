import UIKit

class BaseTableViewController: UITableViewController {
	override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
		super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
		hidesBottomBarWhenPushed = self is TabBarHiding
	}

	required init?(coder: NSCoder) {
		super.init(coder: coder)
		hidesBottomBarWhenPushed = self is TabBarHiding
	}
}
