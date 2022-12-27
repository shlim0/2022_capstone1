//
//  AsyncButton.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/26.
//

//refenced by : https://www.swiftbysundell.com/articles/building-an-async-swiftui-button/
// https://seons-dev.tistory.com/entry/SwiftUI-ProgressView-%EC%9E%91%EC%97%85-%EC%A7%84%ED%96%89%EB%A5%A0
import SwiftUI

struct AsyncButton<Label: View>: View {
    var action: () async -> Void
    @ViewBuilder var label: () -> Label
    
    @State private var isPerformingTask = false
    @State private var progress = 0.0
    
    let timer = Timer.publish(every: 0.1, on: .main, in: .common).autoconnect()
    
    var body: some View {
        Button(
            action: {
                isPerformingTask = true
                
                Task {
                    await action()
                    isPerformingTask = false
                }
            },
            label: {
                ZStack {
                    // We hide the label by setting its opacity
                    // to zero, since we don't want the button's
                    // size to change while its task is performed:
                    label().opacity(isPerformingTask ? 0 : 1)
                    if isPerformingTask {
                        ProgressView(value: progress, total: 100)
                            .progressViewStyle(CircularProgressViewStyle(tint: .black))
                            .padding()
                    }
                }
                .onReceive(timer){
                    _ in
                    if progress < 100 {
                        progress += 5
                    }
                }
            }
        )
        .disabled(isPerformingTask)
    }
}

