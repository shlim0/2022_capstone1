//
//  FavoriteListTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/17.
//

import SwiftUI

struct FavoriteListTabView: View{
    @Binding var isLogin: Bool
    @Binding var tabViewTempleteActive: Bool
    @ObservedObject var sLVM: SpaceListViewModel
    
    var body: some View {
        if(isLogin == true){
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
        else {LoginView(tabViewTempleteActive: $tabViewTempleteActive, isLogin: $isLogin,sLVM: sLVM)}
    }
}
