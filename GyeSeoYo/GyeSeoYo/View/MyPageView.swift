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
            }
            .padding(.horizontal, 40)
        }
        else{
            Text("MyPageView")
        }
    }
}
