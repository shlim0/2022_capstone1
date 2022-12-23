//
//  SpaceViewModel.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/23.
//

import Foundation
import CoreData

class SpaceViewModel: ObservableObject {
    let container: NSPersistentContainer
    @Published var savedEntities = [SpaceEntity]()
    init () {
        container = NSPersistentContainer(name: "SpaceEntity")
        container.loadPersistentStores { description, error in
            if let error = error {
                print("ERROR LOADING SPACE")
                print(error.localizedDescription)
            } else {
                print("SUCCESSFULLY LOAD SPACE")
            }
        }
        fetchSpaces()
    }
    
    func fetchSpaces() {
        let request = NSFetchRequest<SpaceEntity>(entityName: "SpaceEntity")
        do {
            savedEntities = try container.viewContext.fetch(request)
        } catch {
            print("ERROR FETCHING SPACE")
            print(error.localizedDescription)
        }
    }
    
    func addSpace(title: String, latitude: Double, longitude: Double, space_description: String = "", status: Int16 = 0) {
        let space = SpaceEntity(context: container.viewContext)
        space.id = UUID().uuidString    //시스템에서 id를 자동 생성
        space.title = title
        space.latitude = latitude
        space.longitude = longitude
        space.space_description = space_description
        saveData()
    }
    
    func deleteSpace(indexSet: IndexSet) {
        guard let index = indexSet.first else { return }
        let entity = savedEntities[index]
        container.viewContext.delete(entity)
        saveData()
    }
    
    func saveData() {
        do {
            try container.viewContext.save()
            fetchSpaces()
        } catch {
            print("ERROR SAVING SPACE")
            print(error.localizedDescription)
        }
    }
    
    func updateSpace(entity: SpaceEntity, title: String, latitude: Double, longitute: Double, space_description: String, status: Int16) {
        if entity.title != title{
            entity.title = title
        }
        if entity.latitude != latitude{
            entity.latitude = latitude
        }
        if entity.longitude != longitute{
            entity.longitude = longitute
        }
        if entity.space_description != space_description{
            entity.space_description = space_description
        }
        if entity.status != status{
            entity.status = status
        }
        saveData()
    }
}
