import UIKit

enum TabBarItem: CaseIterable {
	case clients, employees, credit, profile

	var icon: UIImage {
		switch self {
		case .clients:
			return UIImage.AppIcons.tabBarHomeIcon
		case .employees:
			return UIImage.AppIcons.tabBarLibraryIcon
		case .credit:
			return UIImage.AppIcons.tabBarLibraryIcon
		case .profile:
			return UIImage.AppIcons.tabBarProfileIcon
		}
	}

	var iconSelected: UIImage {
		switch self {
		case .clients:
			return UIImage.AppIcons.tabBarHomeSelectedIcon
		case .employees:
			return UIImage.AppIcons.tabBarLibrarySelectedIcon
		case .credit:
			return UIImage.AppIcons.tabBarLibrarySelectedIcon
		case .profile:
			return UIImage.AppIcons.tabBarProfileSelectedIcon
		}
	}
}
