//
//  Encodable.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/25.
//

import Foundation

extension Encodable {
    
    var toDictionary : [String: Any]? {
        guard let object = try? JSONEncoder().encode(self) else { return nil }
        guard let dictionary = try? JSONSerialization.jsonObject(with: object, options: []) as? [String:Any] else { return nil }
        return dictionary
    }
}
