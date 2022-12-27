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
    @Binding var tabViewTempleteActive: Bool
    @ObservedObject var sLVM: SpaceListViewModel
    
    var body: some View {
        NavigationLink(destination: MyPageView(userId: $userId, isLogin: $isLogin, tabViewTempleteActive: $tabViewTempleteActive, sLVM: sLVM)
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
