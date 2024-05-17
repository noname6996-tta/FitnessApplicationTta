<h1 align="center">FitnessX - Sports Application</h1>

<p align="center">
<img src="/preview/preview_1.png"/>
</p>

## About this app
<p align="center">
<img src="/preview/preview_2.png"/>
</p>
The application supports health monitoring and sports training with main functions including: tracking common indicators (water intake, food intake, sleep, running steps), building a sports training roadmap suitable for users, giving users articles and videos on health and sports topics.

Ứng dụng hỗ trợ theo dõi sức khỏe, tập luyện thể thao với các chức năng chính gồm: theo dõi các chỉ số thông dụng ( lượng nước, lượng đồ ăn, giấc ngủ, bước chạy), xây dựng lộ trình tập luyện thể thao phù hợp với người dùng, đưa cho người dùng những bài viết, video về chủ đề sức khỏe, thể thao.

<p align="center">
<img src="/preview/preview_3.png"/>
</p>
Tracking features include: tracking daily, weekly, and saving user history in the application. Supports calculation tools to suit the user based on criteria such as weight, gender, height, etc. Displays a notification to remind the user according to the time the user chooses.

Tính năng theo dõi gồm: theo dõi theo từng ngày, theo từng tuần, và lưu lại lịch sử của người dùng trong ứng dụng. Hỗ trợ công cụ tính toán sao cho phù hợp với người dùng dựa trên các tiêu chí về cân nặng, giới tính, chiều cao,... Hiển thị thông báo nhắc nhở người dùng theo thời gian người dùng chọn.

<p align="center">
<img src="/preview/preview_4.png"/>
</p>
With the sports training feature, the application provides a ready-made roadmap within 32 days for each type of exercise (28 days of training, 4 days of rest). Each workout day will include 11 exercises, each exercise has detailed information, images and videos illustrating each exercise so users can practice the movements accurately and effectively.

Với tính năng tập luyện thể thao, ứng dụng cung cấp lộ trình có sẵn với trong vòng 32 ngày với mỗi loại bài tập (28 ngày tập, 4 ngày nghỉ). Mỗi một ngày tập sẽ gồm 11 bài tâp, mỗi bài tập có thông tin, hình ảnh và video minh họa chi tiết cho mỗi bài tập cho người dùng có thể tập luyện lại động tác một cách chính xác và hiệu quả

## Tech stack
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
    - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - DataBinding: Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [Material-Components](https://github.com/material-components/material-components-android): Material design components for building ripple animation, and CardView.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette): Loading images from network.
- [Timber](https://github.com/JakeWharton/timber): A logger with a small, extensible API.
