//
//  UserViewModel.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/23.
//

import Foundation
import CoreData

class UserViewModel: ObservableObject {
     let container: NSPersistentContainer
     @Published var savedEntities = [UserEntity]()

     init () {
          container = NSPersistentContainer(name: "Model")
          container.loadPersistentStores { description, error in
               if let error = error {
                    print("ERROR LOADING USER")
                    print(error.localizedDescription)
               } else {
                    print("SUCCESSFULLY LOAD USER")
               }
          }
          fetchUsers()
     }
     
     func fetchUsers() {
          let request = NSFetchRequest<UserEntity>(entityName: "UserEntity")
          do {
               savedEntities = try container.viewContext.fetch(request)
          } catch {
               print("ERROR FETCHING USER")
               print(error.localizedDescription)
          }
     }
     
     func addUser(id: String, pwd: String, nickname: String, comment: String = "", withdrawal: Bool = true, image_path: Int64 = 0) {
          let user = UserEntity(context: container.viewContext)
          user.id = id
          user.pwd = pwd
          user.nickname = nickname
          user.comment = comment
          saveData()
     }
     
     func deleteUser(indexSet: IndexSet) {
          guard let index = indexSet.first else { return }
          let entity = savedEntities[index]
          container.viewContext.delete(entity)
          saveData()
     }
     
     func saveData() {
          do {
               try container.viewContext.save()
               fetchUsers()
          } catch {
               print("ERROR SAVING USER")
               print(error.localizedDescription)
          }
     }
     
     func updateUser(entity: UserEntity, pwd: String, nickname: String, comment: String) {
          if entity.pwd != pwd{
               entity.pwd = pwd
          }
          if entity.nickname != nickname{
               entity.nickname = nickname
          }
          if entity.comment != comment{
               entity.comment = comment
          }
//          if entity.user_image_path_id != image_path_id{
//               entity.user_image_path_id = image_path_id
//          }
          saveData()
     }
}
