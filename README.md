# Order Karo - Food Ordering App For User

 <img src= "https://res.cloudinary.com/dc1uxxvox/image/upload/v1721299488/github/orderkaroappuser1.png" height="600" />

 
## Description
Order Karo is a native Android application meticulously crafted using Kotlin, designed to provide a seamless food ordering experience. Central to its functionality is Firebase authentication, enabling robust sign-in and sign-up processes along with a welcoming splash screen. The app is divided into five primary fragments, each serving a distinct purpose: Home, Cart, Search, History, and Profile.

In the Home fragment, users can browse an extensive menu of food items, all of which are dynamically loaded from the Firebase Realtime Database. The Cart fragment allows users to view items they have added, providing a clear and intuitive interface for managing their selections before proceeding to checkout. The Search fragment facilitates quick and efficient searching of specific food items, enhancing user convenience. The History fragment keeps track of past orders, offering users a detailed overview of their purchase history, which is also stored in Firebase for consistency and reliability. The Profile fragment enables users to manage their personal information and app settings, ensuring a personalized and secure user experience.

Firebase Realtime Database plays a pivotal role in the app's architecture, storing all user details, cart information, order history, and profile data. This ensures that all data is synchronized in real-time, providing users with up-to-date information and a smooth interaction with the app. Additionally, the menu items are stored in the Firebase database, allowing for easy updates and management by the app administrators.

Overall, Order Karo combines the powerful features of Kotlin and Firebase to deliver a comprehensive and user-friendly food ordering application, making it a reliable and enjoyable tool for users to order their favorite meals with ease.

<img src="https://res.cloudinary.com/dc1uxxvox/image/upload/v1721299971/github/orderkarouserapp3.png" height="600" />

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

 <img src="https://res.cloudinary.com/dc1uxxvox/image/upload/v1721299597/github/orderkaroappuser2.png" height="600" />


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
[Order Karo APK](https://drive.google.com/file/d/1iFAA8MWk1eLNF35-mRvQnU2jgpxioyKe/view?usp=sharing)
