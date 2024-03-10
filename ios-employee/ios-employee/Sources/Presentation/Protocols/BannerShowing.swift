import NotificationBannerSwift
import UIKit

class BannerColors: BannerColorsProtocol {
	func color(for style: BannerStyle) -> UIColor {
		switch style {
		case .danger:
			return .red
		case .info:
			return .blue
		case .customView:
			return .black
		case .success:
			return .green
		case .warning:
			return .yellow
		}
	}
}

protocol BannerShowing {
	func showBanner(title: String?, subtitle: String?, style: BannerStyle)
}

// MARK: - Base realisation

extension BannerShowing {
	func showBanner(title: String?, subtitle: String?, style: BannerStyle) {
//		let banner = FloatingNotificationBanner(title: title,
//		                                        subtitle: subtitle,
//		                                        titleFont: title == nil ? nil : .AppFonts.bodyBold,
//		                                        titleColor: title == nil ? nil : .AppColors.baseWhite100,
//		                                        subtitleFont: subtitle == nil ? nil : .AppFonts.bodyRegular,
//		                                        subtitleColor: subtitle == nil ? nil : .AppColors.baseWhite100,
//		                                        style: style,
//		                                        colors: BannerColors())
//		banner.bannerQueue.dismissAllForced()
//		banner.show(edgeInsets: UIEdgeInsets(top: 16, left: 16, bottom: 16, right: 16),
//		            cornerRadius: 8,
//		            shadowColor: .AppColors.baseBlack,
//		            shadowOpacity: 0.15,
//		            shadowBlurRadius: 24)
	}
}
