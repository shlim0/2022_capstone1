//
//  SpaceView.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/24.
//

import SwiftUI

struct SpaceView: View{
    var body: some View {
        VStack{
            ForEach(0..<SpaceList.endIndex){
                SpaceMiniView(space: SpaceList[$0])
            }
        }
    }
}

struct SpaceView_Previews: PreviewProvider {
    static var previews: some View {
        SpaceView()
    }
}

struct SpaceMiniView: View {
    let space: Space
    var body: some View {
        NavigationLink(destination: SpaceDetailView(space: space)){
            VStack(alignment: .leading, spacing: 5){
                SpaceImage
                SpaceTitle
                SpaceDescription
            }
        }.frame(width: 400, height: 350, alignment: .center).padding(.bottom, 10)
        
    }
    
    var SpaceImage: some View{
        space.image
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
            let rank = String(format: "%.2f", space.rank)
            Text("\(rank)")
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
        }
    }
}
