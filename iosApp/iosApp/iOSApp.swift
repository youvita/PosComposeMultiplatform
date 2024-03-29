import SwiftUI
import ComposeApp
import UIKit

@main
struct iOSApp: App {
    init() {
        iosModule.koin
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
