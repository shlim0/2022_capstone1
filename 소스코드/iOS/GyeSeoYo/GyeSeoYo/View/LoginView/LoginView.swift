//
//  LoginView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/26.
//

import SwiftUI

struct LoginView: View {
    @State private var userId: String = "iiidddd"
    @State private var password: String = "happyy"
    @State var showingAlert: Bool = false
    @Binding var tabViewTempleteActive: Bool
    @Binding var isLogin: Bool
    @ObservedObject var sLVM: SpaceListViewModel
    
    var body: some View {
        NavigationStack{
            VStack{
                Text("계세요")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .padding(.bottom, 40)
                TextField("Userid", text: $userId)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                SecureField("Password", text: $password)
                    .modifier(FieldStyle())
                
                Spacer()
                //로그인 버튼
                
                AsyncButton(action: {
                    login(userId, password)
                    print("isLogin in LoginView : \(isLogin)")
                    if isLogin == true{
                        tabViewTempleteActive = false
                    }
                }
                ){
                    Text("LOGIN")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 280, height: 45)
                        .background(Color.blue)
                        .cornerRadius(10.0)
                }
                //                    .alert(isPresented: $showingAlert) {
                //                        Alert(title: Text("불일치"), message: Text("아이디 또는 패스워드가 잘못되었습니다."), dismissButton: .default(Text("닫기")))
                //                    }
                //                }
                
                //회원 가입 버튼
                NavigationLink {
                    RegistrationView(id: "iiidddd", pwd: "happyy", pwdCheck: "happyy", nickname: "never", comment: "wtf", isLogin: false, isPwdValid: false)
                } label: {
                    Text("회원 가입")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 280, height: 45)
                        .background(Color.red)
                        .cornerRadius(10.0)
                }
                
                //홈 화면 버튼
                NavigationLink {
                    if isLogin == false{
                        TabViewTemplte(isLogin: $isLogin, userId: $userId, sLVM: sLVM).navigationBarBackButtonHidden(true)
                    }
                } label: {
                    Text("홈 화면")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 280, height: 45)
                        .background(Color.black)
                        .cornerRadius(10.0)
                }
                Spacer()
            }.padding()
                .padding(.top, 120)
                .ignoresSafeArea()
                .onAppear (perform : UIApplication.shared.hideKeyboard)
            
            
            Spacer()
        }
    }
    func login(_ userId: String, _ password: String) {
        let loginInfo: Login = .init(user_id: userId, user_pwd: password, APNs_token: "dasd")
        let data = loginInfo.toDictionary
        //        print(data)
        //        print("data \(type(of: data))")
        //                let params = ["id":"아이디", "pw":"패스워드"] as Dictionary
        //        print("para \(type(of: params))")
        
        
        Task{
            await Network().postLoginData(input_url: "http://119.192.31.33:10001/session",input_params: data!)
            
        }
        print("session_id in LOGIN VIEW : \(SessionManager.session_id)")
        print("statusCode in LOGIN VIEW : \(SessionManager.statusCode!)")
        
        if(SessionManager.statusCode == 200){
            self.password = ""
            isLogin = true
        }
        else{
            showingAlert = true
        }
    }
}
