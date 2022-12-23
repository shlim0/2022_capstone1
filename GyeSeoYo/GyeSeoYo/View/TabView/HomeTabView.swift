//
//  HomeTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct HomeTabView: View{
    var body: some View {
        ZStack {
            Circle()
                .frame(width: 300, height: 300)
                .foregroundColor(.green)
            
            Text("\(3)")
                .font(.system(size: 70))
                .foregroundColor(.white)
                .fontWeight(.bold)
        }
    }
    
}
