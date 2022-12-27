//
//  Space.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/24.
//

import SwiftUI

struct Space: Codable, Hashable, Identifiable{
    let id: Int
    //indent 처리한 건 서버에서 받음
        let session_id: Int
        let title: String
    let distance: Double
    let location: String
        let latitude: Double = 37.494576975240875   //숭실대 정보관
        let longitude: Double = 126.9597316987289   //숭실대 정보관
    let description: String
    let status: Int     //0: 사용 가능, 1: 예약 완료
    let price: Int
    let priceUnit: String   //가격 단위. ~~원에 6시간, ~~원에 1개월
    let rank: Double    //별점
    let seller: String
    let peopleCnt: Int
        let image_count: Int
        let images: [String]        //image을 base64 encoding(String)한 1개~10개까지의 이미지
        let tag_count: Int
        let tags: [String]
}

var SpaceList = [

    Space(id: 0, session_id: 0, title: "10인 전용 사무실, 대한민국", distance: 1, location: "서울 강남구 신사동 112-3", description: "사무 회의, 팀 프로젝트, 토론회 등 다양한 오피스 공간으로 활용 가능합니다. 선릉역 7번 출구에서 2분 거리입니다.", status: 0, price: 50000, priceUnit: "1시간", rank: 4.85, seller: "숭실대학교", peopleCnt: 10, image_count: 1, images: [""], tag_count: 0, tags: ["사무실", "조용함", "선릉역"]),
    Space(id: 1, session_id: 3, title: "사당 아파트 주차장 1자리 빌려드려요~", distance: 3.4, location: "서울 동작구 사당동 사당아파트 101동 지하주차장", description: "세대당 주차공간이 2자리인데 차가 1대뿐이라 필요하신분 있으면 1개월 단위로 쓰시면 좋을 것 같아요~~", status: 0, price: 70000, priceUnit: "1개월", rank: 4.11, seller: "동작구코딩일타", peopleCnt: 1, image_count: 1, images: [""], tag_count: 0, tags: ["주차장", "지하", "동작구"]),
    Space(id: 2, session_id: 2, title: "일주일 집비우는데 쓰실분만", distance: 2.3, location: "서울 동작구 흑석동 중앙대학교 기숙사 101호", description: "컨퍼런스 있어서 잠깐 해외 출장 가는데 필요한 분만 연락주세요. 네고 안되고 중앙대생만 가능합니다. 깨끗히 쓰시는 분만 연락주세요", status: 0, price: 10000, priceUnit: "7일", rank: 2.11, seller: "박진혁교수", peopleCnt: 1, image_count: 1, images: [""], tag_count: 0, tags: ["중앙대", "기숙사", "네고안됨", "흑석"])
]
