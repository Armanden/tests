import SwiftUI

struct ContentView: View {
    @State private var count: Int = 0
    @State private var name: String = ""
    @State private var isRandomColor: Bool = false

    var body: some View {
        NavigationView {
            VStack(spacing: 20) {
                Text("Hello, \(name.isEmpty ? "Guest" : name)!")
                    .font(.title2)
                    .bold()
                    .padding(.top)

                HStack(spacing: 24) {
                    Button(action: {
                        withAnimation(.spring()) { count -= 1 }
                    }) {
                        Image(systemName: "minus.circle.fill")
                            .font(.system(size: 44))
                            .foregroundColor(.primary)
                    }

                    Text("\(count)")
                        .font(.system(size: 48, weight: .semibold, design: .rounded))
                        .frame(minWidth: 100)
                        .foregroundColor(currentColor)
                        .scaleEffect(count == 0 ? 1.0 : 1.05)
                        .animation(.easeInOut, value: count)

                    Button(action: {
                        withAnimation(.spring()) { count += 1 }
                    }) {
                        Image(systemName: "plus.circle.fill")
                            .font(.system(size: 44))
                            .foregroundColor(.primary)
                    }
                }

                Stepper("Count: \(count)", value: $count, in: -100...100)
                    .padding(.horizontal)

                Toggle("Random color (hue based on count)", isOn: $isRandomColor)
                    .padding(.horizontal)

                TextField("Enter your name", text: $name)
                    .textFieldStyle(.roundedBorder)
                    .padding(.horizontal)

                Spacer()
            }
            .padding(.bottom)
            .navigationTitle("Simple SwiftUI UI")
        }
    }

    private var currentColor: Color {
        if isRandomColor {
            // Generate a hue from count so color changes as you increment/decrement
            let hue = Double((abs(count) % 360)) / 360.0
            return Color(hue: hue, saturation: 0.85, brightness: 0.9)
        } else {
            return count >= 0 ? .green : .red
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}