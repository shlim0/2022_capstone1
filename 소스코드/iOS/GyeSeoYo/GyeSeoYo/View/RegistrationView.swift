//
//  RegistrationView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/23.
//

import CoreData
import SwiftUI

struct RegistrationView: View{
    @ObservedObject var userViewModel = UserViewModel()
    @ObservedObject var sessionViewModel = SessionViewModel()
    @State var id: String
    @State var pwd: String
    @State var pwdCheck: String
    @State var nickname: String
    @State var comment: String
    @State var isLogin: Bool
    @State var isPwdValid: Bool
    
    var body: some View {
        NavigationStack{
            VStack{
                Text("회원 가입")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .padding(.bottom, 40)
                TextField("아이디 입력", text: $id)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                SecureField("비밀번호 입력", text: $pwd)
                    .modifier(FieldStyle())
                SecureField("비밀번호 확인", text: $pwdCheck)
                    .modifier(FieldStyle())
                TextField("닉네임 입력", text: $nickname)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                TextField("자기소개 입력 (선택)", text: $comment)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
            }.padding()
                .padding(.top, 120)
                .ignoresSafeArea()
            
//회원 가입 완료 버튼
            Button {
                userViewModel.addUser(id: id, pwd: sessionViewModel.toSHA256(pwd), nickname: nickname, comment: comment)
                //                if pwd != pwdCheck {
                //                    isPwdValid = false
                //                }
                //                else{
                //                    isPwdValid = true
                //                }
            }label: {
                Text("회원 가입")
                    .font(.headline)
                    .foregroundColor(.white)
                    .padding()
                    .frame(width: 280, height: 45)
                    .background(Color.blue)
                    .cornerRadius(10.0)
            }
                        .alert(isPresented: $isPwdValid) {
                            Alert(title: Text("불일치"), message: Text("아이디 또는 패스워드가 잘못되었습니다."), dismissButton: .default(Text("닫기")))
                        }
            //            .navigationDestination(isPresented: $isLogin) {
            //                MainView(isLogin: $isLogin, userId: $id).navigationBarBackButtonHidden(true)
            Spacer()
        }
    }
    
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
}

