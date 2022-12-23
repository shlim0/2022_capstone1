//
//  SettingButtonView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct SettingButtonView: View{
    var body: some View {
        NavigationLink(destination: SettingView()
        , label:  {
            Image(systemName: "ellipsis")
                .font(.system(.headline))
                .padding([.top, .trailing], 20.0)
                .foregroundColor(.black)
                .background(Color.clear)
        })
        .frame(maxHeight: .infinity, alignment: .top)
    }
}
