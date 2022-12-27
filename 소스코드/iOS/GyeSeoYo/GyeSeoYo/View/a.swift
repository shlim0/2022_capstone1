//
//  a.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/26.
//

import Foundation
import SwiftUI

struct aa: View{
    @State private var score = 0
     
        let formatter: NumberFormatter = {
            let formatter = NumberFormatter()
            formatter.numberStyle = .decimal
            return formatter
        }()
     
        var body: some View {
            VStack {
                TextField("점수", value: $score, formatter: formatter)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
     
                Text("당신의 점수는 '\(score)'점 입니다.")
            }
        }
}

struct aa_Previews: PreviewProvider {
    static var previews: some View {
        aa()
    }
}
