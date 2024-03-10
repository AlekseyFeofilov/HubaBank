import UIKit

extension DateFormatter {
	// MARK: - Public

	static let dayOfWeek = dateFormatter(format: Constants.dayOfWeek)
	static let dayOfMonth = dateFormatter(format: Constants.dayOfMonth)
	static let dayWithMonth = dateFormatter(format: Constants.dayWithMonth)
	static let fullMonthYear = dateFormatter(format: Constants.fullMonthYear)
	static let yearMonthDayISO = dateFormatter(format: Constants.yearMonthDayISO)
	static let shortHoursMinutes = dateFormatter(format: Constants.shortHoursMinutes)
	static let shortHoursMinutesWithMeridiem = dateFormatter(format: Constants.shortHoursMinutesWithMeridiem)
	static let dayMonthDisplay = dateFormatter(format: Constants.dayMonthDisplay)
	static let dayMonthYearDisplay = dateFormatter(format: Constants.dayMonthYearDisplay)
	static let yearMonthDayGMT0 = dateFormatter(format: Constants.yearMonthDayISO,
	                                            timeZone: TimeZone(secondsFromGMT: 0))
	static let fullDateISO = dateFormatter(format: Constants.fullDateISO)
	static let ISODate = dateFormatter(format: Constants.ISODate, locale: NSLocale(localeIdentifier: "en_US_POSIX") as Locale)

	// MARK: - Private

	private static func dateFormatter(format: String, locale: Locale = .current, timeZone: TimeZone? = .current) -> DateFormatter {
		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = format
		dateFormatter.locale = locale
		dateFormatter.timeZone = timeZone
		return dateFormatter
	}
}

// MARK: - Constant

private extension Constants {
	static let dayOfWeek = "E"
	static let dayOfMonth = "d"
	static let dayWithMonth = "d.MM"
	static let fullMonthYear = "LLLL yyyy"
	static let yearMonthDayISO = "yyyy-MM-dd"
	static let shortHoursMinutes = "H:mm"
	static let shortHoursMinutesWithMeridiem = "h:mm a"
	static let dayMonthDisplay = "d MMMM"
	static let dayMonthYearDisplay = "d MMMM yyyy"
	static let fullDateISO = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
	static let ISODate = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
}
