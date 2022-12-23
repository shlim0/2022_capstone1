//
//  MyPageButtonView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct MyPageButtonView: View{
    @Binding var userId: String
    @Binding var isLogin: Bool
//
    var body: some View {
        NavigationLink(destination: MyPageView(userId: $userId, isLogin: $isLogin)
        , label:  {
            Image(systemName: "person.fill")
                .font(.system(.headline))
                .padding()
                .foregroundColor(.black)
                .background(Color.clear)
        })
        .frame(maxHeight: .infinity, alignment: .top)
    }
}
