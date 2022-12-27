//
//  SpaceUploadTabView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/25.
//

import Foundation
import SwiftUI

struct SpaceUploadTabView: View{
    @State var title: String = "park"
    @State var location: String = "seoul"
    @State var price: String = "10000"
    @State var priceUnit: String = "day"
    @State var description: String = "sibal"
    @State var isButtonPushed: Bool = false
    @State var space: Space? = nil
    @State var tags: String = "kimbap"
    @Binding var isLogin: Bool
    @Binding var tabViewTempleteActive: Bool
    @ObservedObject var sLVM: SpaceListViewModel
    
    var body: some View{
        if(isLogin == true){
            VStack{
                Text("장소 등록")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .padding(.bottom, 40)
                TextField("제목", text: $title)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                TextField("위치 등록", text: $location)
                    .modifier(FieldStyle())
                TextField("가격", text: $price)
                    .modifier(FieldStyle())
                TextField("가격 단위", text: $priceUnit)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                TextField("해시 태그", text: $tags)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                TextField("장소 설명 (선택)", text: $description)
                    .modifier(FieldStyle())
                    .autocapitalization(.none)
                AsyncButton {
                    let imageURL: String = makeImageBase64Encoding(UIImage(named: "사무실")!)
                    space = Space(id: 4, session_id: SessionManager.session_id, title: title, distance: 11.3, location: location, description: description, status: 0, price: Int(price)!, priceUnit: priceUnit, rank: 4.32, seller: registrationManager.nickname, peopleCnt: 2, image_count: 1, images: [imageURL], tag_count: 0, tags: [tags])
                    
//                    let _ = print("daa \(sLVM.spaceList.count)")
//                    sLVM.objectWillChange.send()
                    sLVM.spaceList.append(space!)
//                    let _ = print("dasd \(sLVM.spaceList.count)")
                    
                    print("session id in SpaceUploadTabView: \(SessionManager.session_id)")
                    SpaceImageList.append(UIImage(named: "사무실")!)
                    await upload(space!)
                    
//                    print(type(of: imageURL))
//                    print("imageURL\(imageURL)")
                    
                }label: {
                    Text("장소 등록")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 280, height: 45)
                        .background(Color.blue)
                        .cornerRadius(10.0)
                }
            }.padding()
                .ignoresSafeArea()
            
            
            //        .navigationDestination(isPresented: $isButtonPushed, destination: ConfirmView())
            
            
        }
        else {
            LoginView(tabViewTempleteActive: $tabViewTempleteActive, isLogin: $isLogin, sLVM: sLVM)
        }
    }
    
    func makeImageBase64Encoding(_ spaceImage: UIImage) -> String{
        if let data = spaceImage.pngData() {
            let base64 = data.base64EncodedString()
            return base64
        }
        return "ERROR"
    }
    func upload(_ space: Space) async {
        let uploadInfo: Space = space
        
        let data = uploadInfo.toDictionary
//        print(data!)
        await Network().postSpaceUploadData(input_url: "http://119.192.31.33:10006/space", input_params: data!)
    }
    
}

//struct SpaceUploadTabView_Previews: PreviewProvider {
//    static var previews: some View {
//        SpaceUploadTabView(space:)
//    }
//}
