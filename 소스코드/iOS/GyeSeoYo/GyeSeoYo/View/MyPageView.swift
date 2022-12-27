//
//  MyPageView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct MyPageView: View{
    @Binding var userId: String
    @Binding var isLogin: Bool
    @Binding var tabViewTempleteActive: Bool
    @ObservedObject var sLVM: SpaceListViewModel
    
    var body: some View {
        if(isLogin == true){
            VStack{
                Text(self.userId)
                    .font(.largeTitle)
                    .foregroundColor(.blue)
                    .bold()
                Toggle(" LOGOUT", isOn: $isLogin)
                    .frame(width: 130)
                    .foregroundColor(.yellow)
                    .padding()
//                NavigationLink
            }
            .padding(.horizontal, 40)
        }
        else{
            NavigationLink(destination: LoginView(tabViewTempleteActive: $tabViewTempleteActive, isLogin: $isLogin, sLVM: sLVM ).navigationBarBackButtonHidden(true), label: {Text("로그인이 필요합니다")})
        }
    }
}
