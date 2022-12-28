//
//  SpaceDetailView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/24.
//

import SwiftUI

struct SpaceDetailView: View{
    let space: Space
    let spaceImage: UIImage
    var body: some View{
        VStack{
            VStack(alignment: .leading, spacing: 5){
                SpaceImage
                SpaceTitle
                SpaceDescription
            }
            VStack(alignment: .center){
                HStack{
                    Image(systemName: "heart")
                        .imageScale(.large)
                        .foregroundColor(.pink)
                        .frame(width: 32, height: 32)
                        .padding(100)
                    Text("asd")
                        .foregroundColor(.black)
                    
                    
                    
                    Image(systemName: "cart")
                        .foregroundColor(.pink)
                        .frame(width: 32, height: 32)
                        .padding(100)
                    
                }
            }
        }
    }
    
    var SpaceImage: some View{
        Image(uiImage: spaceImage)
            .resizable()
            .frame(width: 400, height: 200)
            .clipShape(Rectangle())
    }
    var SpaceTitle: some View{
        HStack{
            Text(space.title)
                .bold()
                .padding(.leading, 10)
                .foregroundColor(Color.black)
            Spacer()
            Image(systemName: "star.fill")
                .foregroundColor(Color.black)
//            let rank = String(format: "%.2f", space.rank)
//            Text("\(rank)")
            Text(space.rank)
                .padding(10)
                .foregroundColor(Color.black)
            
        }
    }
    var SpaceDescription: some View{
        VStack(alignment: .leading, spacing: 5){
            Text(space.location)
                .padding(.leading, 10)
                .foregroundColor(Color.gray)
            let dis = String(format: "%.1f", space.distance)
            Text("\(dis)km")
                .padding(.leading, 10)
                .foregroundColor(Color.gray)
            if space.status == 0{
                Text("현재 이용 가능")
                    .padding(.leading, 10)
                    .foregroundColor(Color.gray)
                    .padding(.bottom, 10)
            }
            else if space.status == 1{
                Text("예약 마감")
                    .padding(.leading, 10)
                    .foregroundColor(Color.gray)
                    .padding(.bottom, 10)
            }
            HStack{
                Text("\(space.price) 원 / \(space.priceUnit)")
                    .padding(.leading, 10)
                    .bold()
                    .foregroundColor(Color.black)
                
            }
            .padding(.bottom, 20)
            Divider()
                
            
            Text(space.description)
                .padding(.leading, 10)
                .padding(.trailing, 10)
                .frame(height: 100)
                
            Divider()
                .padding(.bottom, 20)
            Text("판매자 : \(space.seller) 님")
                .padding(.leading, 10)
                .multilineTextAlignment(.leading)
                .font(.system(size: 20, weight: .bold))
            Text("최대 인원 \(space.peopleCnt)명")
                .padding(.leading, 10)
                .foregroundColor(Color.gray)
                .padding(.bottom, 10)
        }
    }
}

struct SpaceDetailView_Previews: PreviewProvider {
    static var previews: some View {
        ForEach(0..<3){
            SpaceDetailView(space: SpaceList[$0], spaceImage: SpaceImageList[$0])
        }
    }
}
