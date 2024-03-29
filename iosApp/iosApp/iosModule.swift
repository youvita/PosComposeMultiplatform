//
//  iosModule.swift
//  iosApp
//
//  Created by Chan Youvita on 29/3/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp

class iosModule {
    static var koin = {
        KoinInit().doInit(
                appDeclaration: { _ in
                    // Do nothing
                }
        )
    }()
}
