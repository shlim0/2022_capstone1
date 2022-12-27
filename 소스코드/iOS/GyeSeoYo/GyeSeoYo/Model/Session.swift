//
//  Session.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/25.
//

import Foundation

struct Session: Codable{
    var session_id: Int
    var statusCode: Int?
}

var SessionManager: Session = Session(session_id: 0, statusCode: 0)
