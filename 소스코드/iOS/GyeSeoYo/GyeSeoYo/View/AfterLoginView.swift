import SwiftUI

struct AfterLoginView: View {
    @Binding var userId: String
    @Binding var isLogin: Bool
    
    var body: some View {
        VStack{
            Text(self.userId)
                .font(.largeTitle)
                .foregroundColor(.blue)
                .bold()
            Toggle(" LOGIN", isOn: $isLogin)
                .onChange(of: !isLogin) { value in
                    self.userId = ""
                }
                .frame(width: 130)
                .foregroundColor(.yellow)
                .padding()
        }
        .navigationBarHidden(true)
        .padding(.horizontal, 40)
    }
}

//struct AfterLoginView_Previews: PreviewProvider {
//    static var previews: some View {
//        AfterLoginView(userId: .constant("kim"), isLogin: .constant(false))
//    }
//}
