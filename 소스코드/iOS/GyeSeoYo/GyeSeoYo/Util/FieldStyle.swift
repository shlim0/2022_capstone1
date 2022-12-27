//
//  FieldStyle.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/25.
//

import Foundation
import SwiftUI

struct FieldStyle: ViewModifier {
    let lightGreyColor = Color(red: 240.0/255.0, green: 240.0/255.0, blue: 240.0/255.0, opacity: 1.0)
    func body(content: Content) -> some View {
        return content
            .padding()
            .background(lightGreyColor)
            .cornerRadius(5.0)
            .padding(.bottom, 5)
            .disableAutocorrection(true)
    }
}
