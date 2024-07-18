# Order Karo - Food Ordering App

![Order Karo](https://res.cloudinary.com/dc1uxxvox/image/upload/v1721297318/github/git_hub_banner_oxmqba.png)

## Description
Order Karo is a native Android application built using Kotlin for seamless food ordering. It features Firebase authentication (sign in, sign up, splash screen) and consists of five main fragments: Home, Cart, Search, History, and Profile. Users can add items to their cart, purchase food items, and all user details, cart information, order history, and profile data are stored in Firebase Realtime Database. The menu items are also stored in the database for easy management.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Authentication](#authentication)
- [Fragments](#fragments)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)
- [Contact](#contact)
- [Download](#download)

## Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/adilashraf770/OrderKaroUserApp.git
    ```
2. Open the project in Android Studio.
3. Build the project to download all dependencies.
4. Set up Firebase in your project:
    - Go to the [Firebase Console](https://console.firebase.google.com/).
    - Create a new project or use an existing one.
    - Add an Android app to your Firebase project.
    - Download the `google-services.json` file and place it in the `app` directory of your project.
    - Add the Firebase SDK dependencies to your `build.gradle` files as instructed.

## Usage
1. Run the app on an emulator or a physical device.
2. Sign up or log in using the authentication feature.
3. Browse through the home, search for food items, add items to the cart, view your order history, and manage your profile.
4. Purchase food items by paying through the app.

## Configuration
- Update the `google-services.json` file with your Firebase project credentials.
- Modify the Firebase rules as needed for your application.

## Authentication

![Authentication Banner](https://res.cloudinary.com/dc1uxxvox/image/upload/v1721297329/github/git_hub_auth_gooigf.png)
 

- The app uses Firebase Authentication for sign in, sign up, and splash screen.
- Ensure the Firebase Authentication methods are enabled in the Firebase Console.

## Fragments
- **Home**: Browse food items and add them to your cart.
- **Cart**: View items added to the cart and proceed to checkout.
- **Search**: Search for specific food items.
- **History**: View your past orders.
- **Profile**: Manage your profile information and settings.

## Contributing
1. Fork the repository.
2. Create a new branch for your feature or bug fix:
    ```sh
    git checkout -b feature-name
    ```
3. Commit your changes:
    ```sh
    git commit -m "Add some feature"
    ```
4. Push to the branch:
    ```sh
    git push origin feature-name
    ```
5. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements
- [Firebase](https://firebase.google.com/) for providing the backend services.
- [Android Developers](https://developer.android.com/) for the documentation and tools.

## Contact
If you have any questions or suggestions, feel free to contact me at [smadal770@gmail.com.com].

## Download
You can download the APK file from the following link:
[Order Karo APK](https://drive.google.com/file/d/1pEo0rJixixycKPsTAM1I3LOLETO6TmSQ/view?usp=sharing)
